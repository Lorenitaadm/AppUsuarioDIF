package mx.gldm.userappl.view

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import mx.gldm.userappl.R
import mx.gldm.userappl.databinding.ActivityFormularioBinding
import mx.gldm.userappl.model.QR
import mx.gldm.userappl.model.dataClass.Singleton
import mx.gldm.userappl.viewmodel.FormularioViewModel
import mx.gldm.userappl.viewmodel.LogInViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FormularioActivity : AppCompatActivity() {

    private lateinit var fechaSeleccionada: Calendar //fecha
    private val qrGenerator = QR() //qr
    private lateinit var binding: ActivityFormularioBinding
    private lateinit var viewModel: FormularioViewModel
    private val importantSelectedCourses = mutableListOf<String>()
    //condiciones
    var selectedCurses = BooleanArray(0)
    var courseList = mutableListOf<Int>()
    var courseArray = listOf<String>()

    override fun onStart() {
        super.onStart()
        viewModel.solicitarListaVulneravilidades()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        //ViewModel
//        viewModel = ViewModelProvider(this).get(FormularioViewModel::class.java)
//        binding = ActivityFormularioBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        registrarEventos()
//        registrarObservadores()
//        fechaSeleccionada = Calendar.getInstance()
//
//
//        //opciones sexo
//        // Obtén una referencia al Spinner desde el archivo XML
//        val spinnerSexo = findViewById<Spinner>(R.id.spinnerSexo)
//
//        // Define las opciones de sexo
//        val generoOptions = arrayOf("", "Femenino", "Masculino", "Prefiero no decir")
//        // Crea un ArrayAdapter para el Spinner
//        val generoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, generoOptions)
//        // Configura el Spinner con el ArrayAdapter
//        binding.spinnerSexo.adapter = generoAdapter
//
//        // Define el listener para el Spinner
//        binding.spinnerSexo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                // Cuando no se selecciona ningún elemento
//            }
//        }
//    }
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    //ViewModel
    viewModel = ViewModelProvider(this).get(FormularioViewModel::class.java)
    binding = ActivityFormularioBinding.inflate(layoutInflater)
    setContentView(binding.root)
    registrarEventos()
    registrarObservadores()
    fechaSeleccionada = Calendar.getInstance()

    // Agregar el TextWatcher a etFecha
    binding.etFecha.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // No es necesario implementar nada aquí
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // No es necesario implementar nada aquí
        }

        override fun afterTextChanged(s: Editable?) {
            val text = s.toString()
            if ((text.length == 2 || text.length == 5) && !text.endsWith("/")) {
                s?.append("/")
            }
        }
    })

    //opciones sexo
    // Obtén una referencia al Spinner desde el archivo XML
    val spinnerSexo = findViewById<Spinner>(R.id.spinnerSexo)

    // Define las opciones de sexo
    val generoOptions = arrayOf("", "Femenino", "Masculino", "Prefiero no decir")
    // Crea un ArrayAdapter para el Spinner
    val generoAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, generoOptions)
    // Configura el Spinner con el ArrayAdapter
    binding.spinnerSexo.adapter = generoAdapter

    // Define el listener para el Spinner
    binding.spinnerSexo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Cuando no se selecciona ningún elemento
        }
    }
}



    private fun registrarObservadores() {
        viewModel.id.observe(this) {VATO ->
            Singleton.id = VATO
            Singleton.listaVulnerabilidades = importantSelectedCourses
            val intent = Intent(this, RegistroExitosoActivity::class.java)
            startActivity(intent)

        }
        viewModel.lst.observe(this){vulArray->
            val selectCard = binding.materialCardView
            selectedCurses = BooleanArray(vulArray.size)
            courseArray = vulArray.toMutableList()

            selectCard.setOnClickListener {
                showCoursesDialog()
            }
        }
    }

    private fun showCoursesDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Courses")
        builder.setCancelable(false)
        val tvCourses = binding.tv

        val selectedCourses = BooleanArray(courseArray.size)

        builder.setMultiChoiceItems(courseArray.toTypedArray(), selectedCourses) { dialog, which, isChecked ->
            if (isChecked) {
                courseList.add(which)
            } else {
                courseList.remove(which)
            }
        }

        builder.setPositiveButton("OK") { dialog, which ->
            val stringBuilder = StringBuilder()
            for (i in 0 until courseList.size) {
                importantSelectedCourses.add(courseArray[courseList[i]])
                stringBuilder.append(courseArray[courseList[i]])
                if (i != courseList.size - 1) {
                    stringBuilder.append(", ")
                }
            }
            tvCourses.text = stringBuilder.toString()
        }

        builder.setNegativeButton("Cancel") { dialog, which ->
            for (i in selectedCourses.indices) {
                selectedCourses[i] = false
            }
            importantSelectedCourses.clear()
            courseList.clear()
            tvCourses.text = ""
        }
        builder.show()
    }

    private fun registrarEventos() {
        binding.btnListoFormulario.setOnClickListener {

            val nombre = binding.etNombre.text.toString()
            Log.d("NOMBREE", nombre)
            val apellido = binding.etApellido.text.toString()
            Log.d("APELLIDOOOO", apellido)
            val curp = binding.etCURP.text.toString()
            Log.d("CUUUURP", curp)
            val sexo = binding.spinnerSexo.selectedItem.toString()
            Log.d("SEXOOOO", sexo)
            val fecha = binding.etFecha.text.toString()
            val fechaSinDiagonales = fecha.replace("/", "")
            Log.d("Fechaaaaa", fechaSinDiagonales)

            // Crea un AlertDialog de confirmación
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Alerta de Privacidad")
            alertDialog.setMessage("Al aceptar estás das consentimiento del uso de tu información personal.")

            // Botón "Aceptar" que ejecuta el código existente
            alertDialog.setPositiveButton("Aceptar") { dialog, which ->
                viewModel.registrarUsuario(nombre, apellido, curp, sexo, fechaSinDiagonales)
            }
            // Botón "Cancelar" que no ejecuta el código existente
            alertDialog.setNegativeButton("Cancelar") { dialog, which ->
                // Puedes agregar aquí cualquier acción que desees realizar al cancelar.
                // Por ejemplo, limpiar los campos del formulario.
                binding.etNombre.text.clear()
                binding.etApellido.text.clear()
                binding.etCURP.text.clear()
                binding.spinnerSexo.setSelection(0) // Volver a la opción por defecto
                binding.etFecha.text.clear()
            }

            // Muestra el AlertDialog
            alertDialog.show()
        }
    }


//    private fun registrarEventos() {
//        binding.btnListoFormulario.setOnClickListener {
//
//            val nombre = binding.etNombre.text.toString()
//            Log.d("NOMBREE", nombre)
//            val apellido = binding.etApellido.text.toString()
//            Log.d("APELLIDOOOO", apellido)
//            val curp = binding.etCURP.text.toString()
//            Log.d("CUUUURP", curp)
//            val sexo = binding.spinnerSexo.selectedItem.toString()
//            Log.d("SEXOOOO", sexo)
//            val fecha = binding.etFecha.text.toString()
//            Log.d("Fechaaaaa", fecha)
////            viewModel.registrarUsuario(nombre, apellido, curp, sexo, fecha) descomentar esta linea para que no funcione el popup
//
//            //Empieza POPUP
//            // Crea un AlertDialog de confirmación
//            val alertDialog = AlertDialog.Builder(this)
//            alertDialog.setTitle("Alerta de Privacidad")
//            alertDialog.setMessage("Al aceptar estás das consentimiento del uso de tu información personal.")
//
//            // Botón "Aceptar" que ejecuta el código existente
//            alertDialog.setPositiveButton("Aceptar") { dialog, which ->
//                viewModel.registrarUsuario(nombre, apellido, curp, sexo, fecha)
//            }
//            // Botón "Cancelar" que no ejecuta el código existente
//            alertDialog.setNegativeButton("Cancelar") { dialog, which ->
//                // Puedes agregar aquí cualquier acción que desees realizar al cancelar.
//                // Por ejemplo, limpiar los campos del formulario.
//                binding.etNombre.text.clear()
//                binding.etApellido.text.clear()
//                binding.etCURP.text.clear()
//                binding.spinnerSexo.setSelection(0) // Volver a la opción por defecto
//                binding.etFecha.text.clear()
//            }
//
//            // Muestra el AlertDialog
//            alertDialog.show()
//        }
//        //AQUI ACABA POPUP
//
//        }

    }
//}


