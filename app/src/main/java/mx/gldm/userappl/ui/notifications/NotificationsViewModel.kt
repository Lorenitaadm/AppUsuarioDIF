package mx.gldm.userappl.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.gldm.userappl.model.Retro
import mx.gldm.userappl.model.dataClass.Calificacion
import mx.gldm.userappl.model.dataClass.IdCalificacion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    val descargaAPI = Retro().createRetrofit()
    val id = MutableLiveData<Int>()
    val error = MutableLiveData<String>()

    fun insertarCalificacion(codigo: Int, califcomida: Int, califservicio: Int, califHigiene: Int, comentario: String){
        val calificacion = Calificacion(codigo, califcomida, califservicio, califHigiene, comentario)
        val call = descargaAPI.agregarCalificacion(calificacion)
        call.enqueue(object: Callback<IdCalificacion>{
            override fun onResponse(
                call: Call<IdCalificacion>,
                response: Response<IdCalificacion>
            ) {
                id.value = response.body()?.idcalificacion
            }
            override fun onFailure(call: Call<IdCalificacion>, t: Throwable) {
                error.value = "Error al conectar con el servidor"
            }
        })
    }
}
