package com.example.mbapp_androidapp.common.classes

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.mbapp_androidapp.MainActivity
import io.reactivex.Observable
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID
import io.reactivex.schedulers.Schedulers;

class BluetoothTerminal private constructor(private val activity: MainActivity){

    private var isOpen: Boolean = false
    var temperatures = arrayOf(20.0,20.0,20.0)
    var pressures = arrayOf(100,100)

    private var command = "";
    private lateinit var mmOutputStream: OutputStream
    private lateinit var mmInputStream: InputStream
    private val tag: String = "ConnectedThread" //Log tag
    private val btModuleUuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") //UUID module bluetooth (no tocar)
    private val requestEnableBt = 1 //Indices permisos
    val requestBluetoothConnectPermission = 2
    private val requestFineLocationPermission = 3
    private lateinit var adapter: BluetoothAdapter //Adaptador bluetooth
    private lateinit var socket: BluetoothSocket //Socket de comunicación
    private var selectDevice: BluetoothDevice? = null //Dispositivo seleccionado
    private var nameDevice = "ESP32-BT-MINIBAR" //Nombre del dispositivo

    companion object{
        @Volatile private var INSTANCE: BluetoothTerminal? = null
        fun getBluetoothTerminal(activity: MainActivity?): BluetoothTerminal {
            return INSTANCE ?: synchronized(this) {
                val instance = BluetoothTerminal(activity!!)
                INSTANCE = instance
                return instance
            }
        }
    }

    // Call this method from the main activity to shut down the connection.
    fun cancel() {
        try {
            socket.close()
        } catch (e: IOException) {
            Log.e(tag, "Could not close the connect socket", e)
        }
    }


    fun connectBtDevice() {
        adapter = activity.getSystemService(BluetoothManager::class.java).adapter
        val pairedDevices = adapter.bondedDevices
        for (pairedDevice in pairedDevices)
            if (pairedDevice.name.equals(nameDevice))
                selectDevice = pairedDevice
        if (selectDevice == null) {
            showToast("No encontrado el dispositivo")
            return
        }
        try {
            if (ActivityCompat.checkSelfPermission(activity.applicationContext, android.Manifest.permission.BLUETOOTH_CONNECT)
                != PackageManager.PERMISSION_GRANTED
            ) return
            socket = selectDevice!!.createRfcommSocketToServiceRecord(btModuleUuid)
            socket.connect()
            mmInputStream = socket.inputStream
            mmOutputStream = socket.outputStream
            showToast("Connection successful")
        } catch (e: IOException) {
            showToast("Error to connect with device: ${e.message}")
            socket.close()
        }
    }

    fun write(msg:String){
        try{
            for(c in msg)
                mmOutputStream.write(c.code)
        }catch (e:IOException){
            Toast.makeText(
                activity,
                "Error to write in mmOutStream: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun writeln(input: String) {
        write(input + "\n")
    }

    private fun showToast(msg: String) {
        activity.runOnUiThread {
            Toast.makeText(activity.applicationContext, msg, Toast.LENGTH_LONG).show()
        }
    }

    //Registro resultado actividad(opcional y tiene que estar en el mainActivitu
    private var someActivityResultLauncher: ActivityResultLauncher<Intent> =
        activity.registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == requestEnableBt) {
                Log.d(tag, "ACTIVITY REGISTER")
            }
        }

    //Pedir permiso (localizacion)
    fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            requestFineLocationPermission
        )
    }

    //Pedir permiso (bluetooth)
    @RequiresApi(Build.VERSION_CODES.S)
    fun requestBluetoothConnectPermission() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(android.Manifest.permission.BLUETOOTH_CONNECT),
            requestBluetoothConnectPermission

        )
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        if (ActivityCompat.checkSelfPermission(
                activity.applicationContext,
                android.Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) return
        someActivityResultLauncher.launch(enableBtIntent)
    }

}