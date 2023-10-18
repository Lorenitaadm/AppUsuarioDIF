package mx.gldm.userappl.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.gldm.userappl.model.QR
import mx.gldm.userappl.model.Retro
import mx.gldm.userappl.model.dataClass.Condicion
import mx.gldm.userappl.model.dataClass.IdCondicion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroExitosoViewModel : ViewModel(){
    val bit= MutableLiveData<Bitmap>()
    val qr = QR()
    val id = MutableLiveData<Int>()
    val descargaAPI = Retro().createRetrofit()
    val error = MutableLiveData<String>()

    fun genQR(codigo : Int){
        bit.value = qr.generarQR(codigo.toString())
    }
    fun insertarVul(codigo : Int, condicion: String){
        val cond = Condicion(codigo, condicion)
        val call = descargaAPI.insertarVulnerabilidades(cond)

        call.enqueue(object : Callback<IdCondicion> {
            override fun onResponse(call: Call<IdCondicion>, response: Response<IdCondicion>) {
                id.value = response.body()?.idCondicion
            }

            override fun onFailure(call: Call<IdCondicion>, t: Throwable) {
                error.value = "Error al conectar con el servidor"
            }
        })
    }
}