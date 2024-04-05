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
import com.example.mbapp_androidapp.presentation.navigation.AppNavigation
import com.example.mbapp_androidapp.ui.theme.MBapp_androidAppTheme
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID


class MainActivity : ComponentActivity() {

    //Variables para el bluetooth
    private val tag: String = "MainActivity" //Log tag
    private val btModuleUuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") //UUID module bluetooth (no tocar)
    private val requestEnableBt = 1 //Indices permisos
    private val requestBluetoothConnectPermission = 2
    private val requestFineLocationPermission = 3
    private lateinit var mBtAdapter: BluetoothAdapter //Adaptador bluetooth
    private lateinit var btSocket: BluetoothSocket //Socket de comunicación
    private var selectDevice: BluetoothDevice? = null //Dispositivo seleccionado
    private lateinit var myConnectionBT: ConnectedThread //Hilo de conexion
    private var nameDevice = "ESP32-BT-Slave" //Nombre del dispositivo

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*//Pedir permisos
        requestBluetoothConnectPermission()
        requestLocationPermission()
        //Crear conexion
        selectedDevices()
        connectBtDevice()
        //Avisar
        myConnectionBT.writeln("Succesfull connection")*/

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

    override fun onDestroy() {
        super.onDestroy()
        //Cerrar socket en caso de destruccion de activity
        /*try {
            btSocket.close()
        } catch (e: IOException) {
            showToast("Error disconnecting..: ${e.message}")
        }
        finish()*/
    }

    //Launcher del scanner (no se puede sacar de una actividad ni meter en un companion object)
    private val barcodeLauncher = registerForActivityResult(ScanContract()) {
        if (it.contents != null) {
            Toast.makeText(applicationContext,"Reader: " + it.contents,Toast.LENGTH_SHORT).show()
            myConnectionBT.writeln(it.contents)
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

    //Registro resultado actividad(opcional y tiene que estar en el mainActivitu
    private var someActivityResultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == requestEnableBt) {
                Log.d(tag, "ACTIVITY REGISTER")
            }
        }

    //Selector dispositivos
    private fun selectedDevices() {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter() //Deprecado me da igual (funciona)
        if (!mBtAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) return
            someActivityResultLauncher.launch(enableBtIntent)
        }

        /*val pairedDevices = mBtAdapter.bondedDevices
        if (pairedDevices.isNotEmpty()) {
            for (device in pairedDevices)
                mNameDevices.add(device.name)
            deviceAdapter.notifyDataSetChanged()
        } else showToast("No one device connected")*/
        selectDevice = getBluetoothDeviceByName(nameDevice)
    }

    //Pedir permiso (localizacion)
    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            requestFineLocationPermission
        )
    }

    //Pedir permiso (bluetooth)
    @RequiresApi(Build.VERSION_CODES.S)
    private fun requestBluetoothConnectPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.BLUETOOTH_CONNECT),
            requestBluetoothConnectPermission
        )
    }

    //Pedir permiso (Bluetooth connect
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestBluetoothConnectPermission) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Log.d(tag, "BLUETOOTH_CONNECT successful")
            else Log.d(tag, "BLUETOOTH_CONNECT denied")
        }
    }

    //Get device by name
    private fun getBluetoothDeviceByName(name: String): BluetoothDevice? {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) Log.d(tag, "---->>>>> ActivityCompat.checkSelfPermission")
        val pairedDevices = mBtAdapter.bondedDevices
        for (pairedDevice in pairedDevices)
            if (pairedDevice.name.equals(name))
                return pairedDevice
        return null
    }

    //Conectar y crear el hilo de conecxion
    private fun connectBtDevice() {
        if (selectDevice == null) {
            showToast("Select a device to connect")
            return
        }
        try {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT)
                != PackageManager.PERMISSION_GRANTED
            ) return
            btSocket = selectDevice!!.createRfcommSocketToServiceRecord(btModuleUuid)
            btSocket.connect()
            myConnectionBT = ConnectedThread(btSocket, this)
            myConnectionBT.start()
            showToast("Connection successful")
        } catch (e: IOException) {
            showToast("Error to connect with device: ${e.message}")
        }
    }

    //Hilo de conexion
    private class ConnectedThread(socket: BluetoothSocket, private val context: Context) :
        Thread() {
        private var mmOutputStream: OutputStream?
        private var mmInputStream: InputStream?

        init {
            var tmpOut: OutputStream? = null
            var tmpIn: InputStream? = null
            try {
                tmpOut = socket.outputStream
                tmpIn = socket.inputStream
            } catch (e: IOException) {
                Toast.makeText(
                    context,
                    "Error to create data buffer: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
            mmOutputStream = tmpOut
            mmInputStream = tmpIn
        }

        //Falta el read

        fun write(input: String) {
            try {
                for (c in input) mmOutputStream?.write(c.code)
            } catch (e: IOException) {
                Toast.makeText(
                    context,
                    "Error to write in mmOutStream: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        fun writeln(input: String) {
            write(input + "\n")
        }
    }

    private fun showToast(msg: String) {
        runOnUiThread {
            Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
        }
    }
}



