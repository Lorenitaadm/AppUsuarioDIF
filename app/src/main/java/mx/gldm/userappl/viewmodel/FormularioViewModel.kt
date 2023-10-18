package mx.gldm.userappl.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.gldm.userappl.model.Retro
import mx.gldm.userappl.model.dataClass.Condiciones
import mx.gldm.userappl.model.dataClass.idUsuario
import mx.gldm.userappl.model.dataClass.nuevoUsuario
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FormularioViewModel: ViewModel() {
    val descargaAPI = Retro().createRetrofit()
    val id = MutableLiveData<Int>()
    val error = MutableLiveData<String>()
    val lst = MutableLiveData<List<String>>()


    fun solicitarListaVulneravilidades(){
        val call = descargaAPI.listaCondiciones()
        call.enqueue(object : Callback<Condiciones>{
            override fun onResponse(call: Call<Condiciones>, response: Response<Condiciones>) {
                lst.value = response.body()?.user
            }

            override fun onFailure(call: Call<Condiciones>, t: Throwable) {
                error.value = "Error al conectar con el servidor"
            }
        })
    }



    fun registrarUsuario(
        nombre: String, apellido: String,
        curp: String, sexo: String,
        fecha: String
    ) {

        val usuario = nuevoUsuario(nombre, apellido, curp, sexo, fecha)
        val call = descargaAPI.agregarUsuario(usuario)

        call.enqueue(object : Callback<idUsuario> {
            override fun onResponse(call: Call<idUsuario>, response: Response<idUsuario>) {
                if (response.isSuccessful) {
                    id.value = response.body()?.idusuario
                    Log.d("RESPUESTAAAAAAAA", response.body()?.idusuario.toString())
                } else {
                    Log.d("TAG", "Código de respuesta: ${response.code()}")
                    Log.d("TAG", "Código de mensaje AAAA: ${response.message()}")
                    error.value =
                        "Error AL conectar con el servidor" + response.message() + ": " + response.code()
                }
            }

            override fun onFailure(call: Call<idUsuario>, t: Throwable) {
                error.value = "Error al conectar con el servidor"
            }
        })
    }


}