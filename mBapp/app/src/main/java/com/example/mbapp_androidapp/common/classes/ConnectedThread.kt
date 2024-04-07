package com.example.mbapp_androidapp.common.classes

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
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
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

class ConnectedThread private constructor(private val activity: MainActivity) : Thread() {

    private var isOpen: Boolean = false
    private var temperature: Double = 20.0


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
        @Volatile private var INSTANCE: ConnectedThread? = null
        fun getConnectedThread(activity: MainActivity): ConnectedThread {
            return INSTANCE ?: synchronized(this) {
                val instance = ConnectedThread(activity)
                INSTANCE = instance
                return instance
            }
        }
    }

    fun read():Char{
        var c: Char = 'E'
        try{
            c = mmInputStream.read().toChar()
        }catch(e: IOException){
            Toast.makeText(
                activity,
                "Error to read from mmInputStream: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
        return c
    }

    fun readLine():String{
        var line: String = ""
        try{
            var c: Char = mmInputStream.read().toChar()
            if(c=='E') return "Error"
            while(c!='\n'){
                line+=c
                c = mmInputStream.read().toChar()
            }
        }catch(e: IOException){
            Toast.makeText(
                activity,
                "Error to read line from mmInputStream: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
        return line
    }

    fun write(input: String) {
        try {
            for (c in input) mmOutputStream.write(c.code)
        } catch (e: IOException) {
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

    fun selectedDevice() {
        adapter = BluetoothAdapter.getDefaultAdapter() //Deprecate me da igual (funciona)
        if (!adapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            if (ActivityCompat.checkSelfPermission(
                    activity.applicationContext,
                    android.Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) return
            someActivityResultLauncher.launch(enableBtIntent)
        }
        selectDevice = getBluetoothDeviceByName(nameDevice)
    }

    private fun getBluetoothDeviceByName(name: String): BluetoothDevice? {
        if (ActivityCompat.checkSelfPermission(
                activity.applicationContext,
                android.Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) Log.d(tag, "---->>>>> ActivityCompat.checkSelfPermission")
        val pairedDevices = adapter.bondedDevices
        for (pairedDevice in pairedDevices)
            if (pairedDevice.name.equals(name))
                return pairedDevice
        showToast("No encontrado el dispositivo")
        return null
    }

    fun connectBtDevice() {
        if (selectDevice == null) {
            showToast("Select a device to connect")
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
            start()
            showToast("Connection successful")
            writeln("Connection great")
        } catch (e: IOException) {
            showToast("Error to connect with device: ${e.message}")
            socket.close()
        }
    }

    private fun showToast(msg: String) {
        activity.runOnUiThread {
            Toast.makeText(activity.applicationContext, msg, Toast.LENGTH_LONG).show()
        }
    }
}