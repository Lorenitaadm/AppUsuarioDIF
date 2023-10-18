package mx.gldm.userappl.ui.home

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.gldm.userappl.model.QR

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    val bit= MutableLiveData<Bitmap>()

    val qr = QR()

    fun genQR(codigo : Int){
        bit.value = qr.generarQR(codigo.toString())
    }

}