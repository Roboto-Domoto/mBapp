package com.example.mbapp_androidapp.common.classes

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import com.example.mbapp_androidapp.CaptureActivityPortrait
import com.example.mbapp_androidapp.MainActivity
import com.journeyapps.barcodescanner.ScanOptions

class BarcodeScanner private constructor(private val activity: MainActivity) {
    private var lastCode: String = ""
    private val codeList: MutableList<String> = mutableListOf()
    private var navController: NavController? = null
    private var route: String? = null

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
    var barcodeResult: String? = null

    // Contrato de resultado de actividad
    private val scannerLauncher =
        activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val barcode = data?.getStringExtra("SCAN_RESULT")
                barcodeResult = barcode
                Toast.makeText(activity.applicationContext, "Reader: $barcode", Toast.LENGTH_SHORT).show()
                barcode?.let { codeList.add(it) }
                if(route==null) navController?.navigateUp()
                else navController?.navigate(route!!)
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
    fun getLastCodeRead(): String {
        val code = lastCode
        lastCode = ""
        return code
    }

    // Método para obtener la lista de códigos leídos
    fun getCodeList(): List<String> {
        return codeList
    }

    // Método para limpiar la lista de códigos leídos
    fun cleanList() {
        codeList.clear()
    }

    fun setNav(navController: NavController, route: String?=null){
        this.navController = navController
        this.route = route
    }
}