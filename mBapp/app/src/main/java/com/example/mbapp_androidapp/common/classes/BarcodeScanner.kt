package com.example.mbapp_androidapp.common.classes

import android.widget.Toast
import com.example.mbapp_androidapp.CaptureActivityPortrait
import com.example.mbapp_androidapp.MainActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class BarcodeScanner private constructor(private val activity: MainActivity) {
    private var lastCode: String = ""
    private val codeList: MutableList<String> = mutableListOf()
    companion object{
        @Volatile private var INSTANCE: BarcodeScanner? = null
        fun getBarcodeScanner(activity: MainActivity?): BarcodeScanner {
            return INSTANCE ?: synchronized(this) {
                val instance = BarcodeScanner(activity!!)
                INSTANCE = instance
                return instance
            }
        }
    }


    //Launcher del scanner (no se puede sacar de una actividad ni meter en un companion object)
    private val barcodeLauncher = activity.registerForActivityResult(ScanContract()) {
        if (it.contents != null) {
            Toast.makeText(activity.applicationContext,"Reader: " + it.contents, Toast.LENGTH_SHORT).show()
            lastCode = it.contents
            codeList.add(lastCode)
        } else {
            Toast.makeText(activity.applicationContext,"Error in reader", Toast.LENGTH_SHORT).show()
        }
    }

    //Retornar el último código de barras almacenado
    fun getLastCodeRead(): String {
        val code = this.lastCode
        //Vaciamos el último código de barras leido
        this.lastCode = ""
        return code
    }

    fun getCodeList(): List<String> {
        return codeList
    }

    fun cleanList() {
        codeList.clear()
    }

    //Funcion para modificar y lanzar scanner
    fun scan():Unit{
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
        options.setPrompt("Scan a bar code!")
        options.setCameraId(0)
        options.setOrientationLocked(false)
        options.setBeepEnabled(true)
        options.captureActivity = CaptureActivityPortrait::class.java
        options.setBarcodeImageEnabled(false)
        barcodeLauncher.launch(options)
    }
}