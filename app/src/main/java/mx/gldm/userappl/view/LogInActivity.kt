package mx.gldm.userappl.view
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import mx.gldm.userappl.databinding.ActivityLogInBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.encoder.QRCode
import com.journeyapps.barcodescanner.BarcodeEncoder
import mx.gldm.userappl.R
import mx.gldm.userappl.ui.home.HomeFragment
import mx.gldm.userappl.viewmodel.QRViewModel
import androidx.fragment.app.Fragment
import mx.gldm.userappl.model.dataClass.CURP
import mx.gldm.userappl.model.dataClass.Singleton
import mx.gldm.userappl.viewmodel.LogInViewModel

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var viewModel: LogInViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_log_in)

        val prefs = getSharedPreferences("datosUsuario", MODE_PRIVATE)
        prefs.edit().clear().commit()

        //ViewModel
        viewModel = ViewModelProvider(this).get(LogInViewModel::class.java)


        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registrarEventos()
        registrarObservadores()
    }

    private fun registrarObservadores() {
        viewModel.id.observe(this) { id ->

            Singleton.id = id
            val intObservadores = Intent(this, MainActivity::class.java)
            intObservadores.putExtra("codigoPersonal", id) // Esto puede ser opcional si ya estás guardando el dato en las preferencias
            startActivity(intObservadores)
        }
    }

    private fun registrarEventos() {
        binding.btnDirigir.setOnClickListener {
            val intFormulario = Intent(this, FormularioActivity::class.java)
            startActivity(intFormulario)
        }

        //Botón aceptar, para cuando el usuario ya fue registrado anteriormente y quiere iniciar sesión
        binding.btnAceptarLogIn.setOnClickListener {
            val codigoPersonal = binding.etCodigoLogIn.text.toString()
            val curp = binding.etCurpLogIn.text.toString()
            Log.d("Codigo", codigoPersonal)
            Log.d("CURP", curp)

            if (codigoPersonal.length == 0 && curp.length == 0) {
                Toast.makeText(this, "Completa tus datos para iniciar sesión", Toast.LENGTH_SHORT).show()
            }else{
                if(codigoPersonal.length == 0){
                    Log.d("TAG", "SI ENTROOOOO")
                    viewModel.verificarCURP(curp)
                }else{
                    val cod = codigoPersonal.toInt()
                    viewModel.verificarCodigo(cod)
                }
            }
        }
    }
}
