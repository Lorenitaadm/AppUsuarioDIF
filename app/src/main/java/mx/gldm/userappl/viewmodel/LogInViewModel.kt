package mx.gldm.userappl.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.gldm.userappl.model.Retro
import mx.gldm.userappl.model.dataClass.CURP
import mx.gldm.userappl.model.dataClass.Codigo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogInViewModel: ViewModel() {
    val descargarAPI = Retro().createRetrofit()
    val id = MutableLiveData<Int>()
    val error = MutableLiveData<String>()


    fun verificarCodigo(codigo: Int){
        val credencial = Codigo(codigo)
        val call = descargarAPI.verificarCodigo(credencial)

        call.enqueue(object : Callback<Codigo>{
            override fun onResponse(call: Call<Codigo>, response: Response<Codigo>) {
                if(response.isSuccessful) {
                    Log.d("TAG", response.body()?.codigo.toString())
                    id.value = response.body()?.codigo

                }else{
                    error.value = "No te has registrado"
                }
            }

            override fun onFailure(call: Call<Codigo>, t: Throwable) {
            Log.d("tag", "ERROR AL CONECTAR AL SERVIDOR <CODIGO PERSONAL>")
            }
        })
    }

    fun verificarCURP(curp: String) {
        val curpData = CURP(curp)
        val call = descargarAPI.verificarCURP(curpData)

        call.enqueue(object : Callback<Codigo> {
            override fun onResponse(call: Call<Codigo>, response: Response<Codigo>) {
                if (response.isSuccessful) {
                    Log.d("TAG", response.body()?.codigo.toString())
                    id.value = response.body()?.codigo

                } else{
                    error.value = "No existe tu registro"
                }
            }

            override fun onFailure(call: Call<Codigo>, t: Throwable) {
                Log.d("tag", "ERROR AL CONECTAR AL SERVIDOR <CURP>")
            }
        })
    }




}