package com.example.mbapp_androidapp.common.classes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class BarcodeScannerActivity : AppCompatActivity() {
    private var lastCode: String? = null
    private val scannedList: MutableList<String> = mutableListOf()

    private val scannerContract = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val scannedBarcode = result.data?.getStringExtra("barcode")
            if (scannedBarcode != null) {
                //Guardar en System
                System.getInstance().codeScanned(scannedBarcode)
            }
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, BarcodeScannerActivity::class.java)
        scannerContract.launch(intent)
    }
}