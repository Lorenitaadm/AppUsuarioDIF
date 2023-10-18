package mx.gldm.userappl.ui.home
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import mx.gldm.userappl.databinding.FragmentHomeBinding
import mx.gldm.userappl.model.QR
import mx.gldm.userappl.model.dataClass.Singleton


class HomeFragment : Fragment() {

//    private lateinit var qrViewModel: QRViewModel
    private lateinit var binding: FragmentHomeBinding
    private val qrGenerator = QR()
    private val viewModel : HomeViewModel by viewModels()
    val prefs = activity?.getSharedPreferences("datosUsuario", Context.MODE_PRIVATE)
//
    val codigo = Singleton.id

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        registrarObservables()

        return binding.root

    }

    private fun registrarObservables() {
        viewModel.bit.observe(viewLifecycleOwner, Observer {bitmap ->
            binding.imgQR.setImageBitmap(bitmap)
            binding.tvCodigoHome.text = codigo.toString()
        })
    }

    private fun generarCodigoQR(codigoPersonal: String?) {
        if (codigoPersonal.isNullOrEmpty()) {
            // Mostrar un mensaje de error o manejar de otra manera
            Toast.makeText(context, "Código personal no disponible", Toast.LENGTH_SHORT).show()
            return
        }

        val qrCodeBitmap = qrGenerator.generarQR(codigoPersonal)
        binding.imgQR.setImageBitmap(qrCodeBitmap)
    }

    override fun onStart() {
        super.onStart()

        // Recupera los datos de las preferencias compartidas
        Log.d("QUE ESTA SACANDO CURP", codigo.toString())


//        val txtCodigoPersonal = prefs?.getString("txtCodigoPersonal", "")
//        val curp = prefs?.getInt("CURP", 0)

        if (codigo != null) {
            viewModel.genQR(codigo)
        }



//        Log.d("AQUIIIII", curp.toString())

        // Establece la jerarquía para determinar qué dato mostrar
//        val datoAMostrar = when {
//            !txtCodigoPersonal.isNullOrEmpty() -> txtCodigoPersonal
//            !codigo.isNullOrEmpty() -> codigo
//            !curp.isNullOrEmpty() -> curp
//            else -> "xxxxxx"
//        }

//        // Genera el código QR con el dato a mostrar
//        generarCodigoQR(datoAMostrar)
//
//        // Establece el dato a mostrar en tvCodigoHome
//        binding.tvCodigoHome.text = datoAMostrar
    }
}