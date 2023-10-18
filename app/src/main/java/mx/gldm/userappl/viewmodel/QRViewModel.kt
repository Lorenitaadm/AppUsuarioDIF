package mx.gldm.userappl.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.graphics.Bitmap

class QRViewModel : ViewModel() {
    private val _qrCode = MutableLiveData<Bitmap>()
    val qrCode: LiveData<Bitmap>
        get() = _qrCode

    fun setQRCode(qrCodeBitmap: Bitmap) {
        _qrCode.value = qrCodeBitmap
    }
}