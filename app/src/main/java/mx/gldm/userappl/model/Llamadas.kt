package mx.gldm.userappl.model
import mx.gldm.userappl.model.dataClass.CURP
import mx.gldm.userappl.model.dataClass.Calificacion
import retrofit2.Call
import mx.gldm.userappl.model.dataClass.Codigo
import mx.gldm.userappl.model.dataClass.Condicion
import mx.gldm.userappl.model.dataClass.Condiciones
import mx.gldm.userappl.model.dataClass.IdCalificacion
import mx.gldm.userappl.model.dataClass.IdCondicion
import mx.gldm.userappl.model.dataClass.idUsuario
import mx.gldm.userappl.model.dataClass.nuevoUsuario
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface Llamadas {
    @Headers("Content-Type: application/json")
    @POST("/verificarCodigo")
    fun verificarCodigo(@Body codigo: Codigo): Call<Codigo>

    @Headers("Content-Type: application/json")
    @POST("/verificarCURP")
    fun verificarCURP(@Body curp: CURP): Call<Codigo>

    @Headers("Content-Type: application/json")
    @POST("/agregarUsuario")
    fun agregarUsuario(@Body user: nuevoUsuario): Call<idUsuario>

    @Headers("Content-Type: application/json")
    @GET("/listaCondiciones")
    fun listaCondiciones() : Call<Condiciones>

    @Headers("Content-Type: application/json")
    @POST("/insertarVulnerabilidades")
    fun insertarVulnerabilidades(@Body condicion: Condicion) : Call<IdCondicion>

    @Headers("Content-Type: application/json")
    @POST("/agregarCalificacion")
    fun agregarCalificacion(@Body calificacion: Calificacion) : Call<IdCalificacion>
}