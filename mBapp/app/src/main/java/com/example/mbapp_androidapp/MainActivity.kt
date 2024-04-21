package com.example.mbapp_androidapp

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.mbapp_androidapp.common.classes.BarcodeScanner
import com.example.mbapp_androidapp.common.classes.BluetoothTerminal
import com.example.mbapp_androidapp.presentation.navigation.AppNavigation
import com.example.mbapp_androidapp.ui.theme.MBapp_androidAppTheme



class MainActivity : ComponentActivity() {

    private val tag: String = "MainActivity"
    private lateinit var bt: BluetoothTerminal

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bt = BluetoothTerminal(this)
        bt.getDeviceByName("ESP32-BT-MINIBAR")
        bt.connect()
        
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
}



