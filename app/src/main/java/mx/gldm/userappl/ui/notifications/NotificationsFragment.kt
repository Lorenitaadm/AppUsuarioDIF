package mx.gldm.userappl.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import mx.gldm.userappl.databinding.FragmentNotificationsBinding
import android.app.AlertDialog
import android.widget.RatingBar
import android.widget.EditText
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import mx.gldm.userappl.model.dataClass.Singleton
import mx.gldm.userappl.ui.home.HomeViewModel

class NotificationsFragment : Fragment() {
    private val viewModel : NotificationsViewModel by viewModels()
    private lateinit var binding: FragmentNotificationsBinding
//    private val binding get() = _binding!!

    private lateinit var ratingBar1: RatingBar
    private lateinit var ratingBar2: RatingBar
    private lateinit var ratingBar3: RatingBar
    private lateinit var commentEditText: EditText


    private fun resetFragment() {
        ratingBar1.rating = 0.0f
        ratingBar2.rating = 0.0f
        ratingBar3.rating = 0.0f
        commentEditText.text.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        binding = FragmentNotificationsBinding.inflate(layoutInflater)
        registrarEventos()
        registrarObservadores()




//        val root: View = binding.root
//
//        ratingBar1 = binding.ratingBar1
//        ratingBar2 = binding.ratingBar2
//        ratingBar3 = binding.ratingBar3
//        commentEditText = binding.commentEditText
//        val sendButton = binding.sendButton
//
//        sendButton.setOnClickListener {
//            val rating1 = ratingBar1.rating
//            val rating2 = ratingBar2.rating
//            val rating3 = ratingBar3.rating
//            val comment = commentEditText.text.toString()
//
//            // Aquí procesaré los datos en bd o servidor
//
//            // Muestra un AlertDialog con el mensaje de confirmación de recibimiento del rating
//
//
//        }

        return binding.root
    }

    private fun registrarObservadores() {
        viewModel.id.observe(viewLifecycleOwner, Observer {
            val alertDialog = AlertDialog.Builder(requireContext())
                .setTitle("Hemos recibido tus comentarios")
                .setMessage("Gracias por calificarnos y ayudarnos a mejorar para ti")
                .setPositiveButton("Aceptar") { _, _ ->
                    binding.ratingBar1.rating = 0.0F
                    binding.ratingBar2.rating = 0.0F
                    binding.ratingBar3.rating = 0.0F
                    binding.commentEditText.text.clear()

                    // Código a ejecutar cuando se hace clic en el botón "Aceptar" del popup
                }
                .create()

            alertDialog.show()
        })
    }

    private fun registrarEventos() {
        binding.sendButton.setOnClickListener {
            viewModel.insertarCalificacion(Singleton.id, binding.ratingBar1.rating.toInt(), binding.ratingBar2.rating.toInt(), binding.ratingBar3.rating.toInt(), binding.commentEditText.text.toString())
        }
        }


//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }
}
