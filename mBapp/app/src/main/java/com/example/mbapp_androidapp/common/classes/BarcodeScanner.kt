package com.example.mbapp_androidapp.common.classes

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import com.example.mbapp_androidapp.CaptureActivityPortrait
import com.example.mbapp_androidapp.MainActivity
import com.journeyapps.barcodescanner.ScanOptions

class BarcodeScanner private constructor(private val activity: MainActivity) {
    private var lastCode: Long = 0
    private val codeList: MutableList<Long> = mutableListOf()

    companion object{
        @Volatile private var INSTANCE: BarcodeScanner? = null
        fun getBarcodeScanner(activity: MainActivity?=null): BarcodeScanner {
            return INSTANCE ?: synchronized(this) {
                val instance = BarcodeScanner(activity!!)
                INSTANCE = instance
                return instance
            }
        }
    }

    // Estado para almacenar el resultado del escaneo
    var barcodeResult: Long? = null

    // Contrato de resultado de actividad
    private val scannerLauncher =
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val barcode = data?.getStringExtra("SCAN_RESULT")?.toLong()
                barcodeResult = barcode
                Toast.makeText(activity.applicationContext, "Reader: $barcode", Toast.LENGTH_SHORT).show()
                Log.d("Tag","$barcode")
                barcode?.let { codeList.add(it) }
            } else {
                Toast.makeText(activity.applicationContext, "Error in reader", Toast.LENGTH_SHORT).show()
            }
        }

    // Método para lanzar el escáner
    fun scan() {
        val intent = Intent(activity.applicationContext, CaptureActivityPortrait::class.java)
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        options.setPrompt("Scan a bar code!")
        options.setCameraId(0)
        options.setOrientationLocked(false)
        options.setBeepEnabled(true)
        options.setBarcodeImageEnabled(false)
        intent.action = "com.google.zxing.client.android.SCAN"
        intent.putExtra("SCAN_MODE", "PRODUCT_MODE") // Puedes cambiar esto según el tipo de código de barras que deseas escanear
        scannerLauncher.launch(intent)
    }

    // Método para obtener el último código de barras leído
    fun getLastCodeRead(): Long {
        val code = lastCode
        lastCode = 0
        return code
    }

    // Método para obtener la lista de códigos leídos
    fun getCodeList(): List<Long> {
        return codeList
    }

    // Método para limpiar la lista de códigos leídos
    fun cleanList() {
        codeList.clear()
    }
}