package com.example.mbapp_androidapp.common.classes

import android.widget.Toast
import com.example.mbapp_androidapp.CaptureActivityPortrait
import com.example.mbapp_androidapp.MainActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class BarcodeScanner private constructor(private val activity: MainActivity) {

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
        } else {
            Toast.makeText(activity.applicationContext,"Error in reader", Toast.LENGTH_SHORT).show()
        }
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