package mx.gldm.userappl.model
import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
class QR {
    fun generarQR(codigo: String?): Bitmap {
        val barcodeEncoder = BarcodeEncoder()
        val qrCode = barcodeEncoder.encodeBitmap(codigo, BarcodeFormat.QR_CODE, 900, 900)

        // Establece el fondo transparente
        for (x in 0 until qrCode.width) {
            for (y in 0 until qrCode.height) {
                if (qrCode.getPixel(x, y) == Color.WHITE) {
                    qrCode.setPixel(x, y, Color.TRANSPARENT)
                }
            }
        }

        return qrCode
    }


//    fun generarQR(codigo: String?): Bitmap {
//        val barcodeEncoder = BarcodeEncoder()
//        val bitMap = barcodeEncoder.encodeBitmap(codigo, BarcodeFormat.QR_CODE, 800, 800)
//        return bitMap
//    }
}