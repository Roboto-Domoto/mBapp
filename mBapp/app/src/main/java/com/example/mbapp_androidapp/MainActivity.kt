package com.example.mbapp_androidapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.mbapp_androidapp.common.classes.BarcodeScanner
import com.example.mbapp_androidapp.common.classes.BluetoothTerminal
import com.example.mbapp_androidapp.presentation.navigation.AppNavigation
import com.example.mbapp_androidapp.ui.theme.MBapp_androidAppTheme



class MainActivity : ComponentActivity() {

    private val tag: String = "MainActivity"
    private lateinit var bt: BluetoothTerminal

    // Declaración del launcher para la solicitud de permisos
    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // Permiso concedido
            Toast.makeText(this.applicationContext,"Permiso concedido", Toast.LENGTH_SHORT).show()
        } else {
            // Permiso denegado
            Toast.makeText(this.applicationContext,"Permiso denegado", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bt = BluetoothTerminal(this)
        bt.getDeviceByName("ESP32-BT-MINIBAR")
        bt.connect()

        val hilo = bt.createConnectedThread()
        hilo.execute()

        //Instance of barcode scanner
        BarcodeScanner.getBarcodeScanner(this)

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

    override fun onStart() {
        super.onStart()
        // Verifico permiso
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Si no se ha concedido, solicitar de nuevo
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El usuario concedió el permiso de la cámara
                Toast.makeText(this.applicationContext,"CONCEDIDO", Toast.LENGTH_SHORT).show()
            } else {
                // El usuario denegó el permiso de la cámara
                Toast.makeText(this.applicationContext,"DENEGADO", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CAMERA = 123 // Código de solicitud de permisos
    }
}



