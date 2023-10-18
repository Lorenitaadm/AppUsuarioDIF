package mx.gldm.userappl.view

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import mx.gldm.userappl.R
import mx.gldm.userappl.databinding.ActivityFormularioBinding
import mx.gldm.userappl.databinding.ActivityRegistroExitosoBinding
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import mx.gldm.userappl.model.QR
import mx.gldm.userappl.model.dataClass.Singleton
import mx.gldm.userappl.viewmodel.FormularioViewModel
import mx.gldm.userappl.viewmodel.LogInViewModel
import mx.gldm.userappl.viewmodel.RegistroExitosoViewModel


class RegistroExitosoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroExitosoBinding
    private lateinit var viewModel: RegistroExitosoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //ViewModel
        viewModel = ViewModelProvider(this).get(RegistroExitosoViewModel::class.java)
        binding = ActivityRegistroExitosoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        registrarEventos()
        registrarObservadores()
    }

    private fun registrarObservadores() {
        viewModel.bit.observe(this){bit ->
            binding.imgQR.setImageBitmap(bit)
            binding.txtCodigoPersonal.text = Singleton.id.toString()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.genQR(Singleton.id)
        val vulnerabilidades = Singleton.listaVulnerabilidades
        val cod = Singleton.id
        for(i in vulnerabilidades){
            viewModel.insertarVul(cod, i)
            Log.d("AQUI", i)
        }
    }

    private fun registrarEventos() {
        binding.btnDirigir.setOnClickListener {
            val intInicio = Intent(this, MainActivity::class.java)
            startActivity(intInicio)
        }
    }

}