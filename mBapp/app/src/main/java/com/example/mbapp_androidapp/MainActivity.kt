package com.example.mbapp_androidapp

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.example.mbapp_androidapp.common.ConnectedThread
import com.example.mbapp_androidapp.presentation.navigation.AppNavigation
import com.example.mbapp_androidapp.ui.theme.MBapp_androidAppTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID


class MainActivity : ComponentActivity() {

    private val btThread = ConnectedThread.getConnectedThread(this)
    private val tag: String = "MainActivity"

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        //Pedir permisos
        //btThread.requestLocationPermission()
        btThread.requestBluetoothConnectPermission()
        //Crear conexion
        btThread.selectedDevice()
        btThread.connectBtDevice()

        // Lock the mobile screen orientation in vertical
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContent {
            MBapp_androidAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }

    //Launcher del scanner (no se puede sacar de una actividad ni meter en un companion object)
    private val barcodeLauncher = registerForActivityResult(ScanContract()) {
        if (it.contents != null) {
            Toast.makeText(applicationContext,"Reader: " + it.contents,Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext,"Error in reader",Toast.LENGTH_SHORT).show()
        }
    }

    //Funcion para modificar y lanzar scanner
    private fun scan():Unit{
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


    //Pedir permiso (Bluetooth connect)
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == btThread.requestBluetoothConnectPermission) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Log.d(tag, "BLUETOOTH_CONNECT successful")
            else Log.d(tag, "BLUETOOTH_CONNECT denied")
        }
    }
}



