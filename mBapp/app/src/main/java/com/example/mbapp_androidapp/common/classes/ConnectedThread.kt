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

class ConnectedThread private constructor(private val activity: MainActivity) : Thread() {

    private var message:String = ""
    private var isOpen: Boolean = false
    var temperature: Double = 20.0

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
        @Volatile private var INSTANCE: ConnectedThread? = null
        fun getConnectedThread(activity: MainActivity?): ConnectedThread {
            return INSTANCE ?: synchronized(this) {
                val instance = ConnectedThread(activity!!)
                INSTANCE = instance
                return instance
            }
        }
    }

    override fun run() {
        /*while(true){
            writeln(command)
            Log.d(tag,"Writed command")
            sleep(400)
            Log.d(tag,"Go to read")
            var data = readLine()
            Log.d(tag,"Readed")
            var temperatures = data.split("|")[0]
            var temp = temperatures.substring(2).split(",")[0]
            if(temp!="nan") temperature = 20.0
            else temperature = temp.toDouble()
            Log.d(tag, "Readed $temperature")
            sleep(1000)
        }*/

        val buffer = ByteArray(1024)
        var bytes = 0 // bytes returned from read()
        var numberOfReadings = 0 //to control the number of readings from the Arduino

        // Keep listening to the InputStream until an exception occurs.
        //We just want to get 1 temperature readings from the Arduino

        // Keep listening to the InputStream until an exception occurs.
        //We just want to get 1 temperature readings from the Arduino
        while (numberOfReadings < 1) {
            try {
                buffer[bytes] = mmInputStream.read().toByte()
                // If I detect a "\n" means I already read a full measurement
                if (buffer[bytes] == '\n'.code.toByte()) {
                    message = String(buffer,0,bytes);
                    Log.e(tag, message)
                    bytes = 0
                    numberOfReadings++
                } else {
                    bytes++
                }
            } catch (e: IOException) {
                Log.d(tag, "Input stream was disconnected", e)
                break
            }
        }
    }

    fun getMessage():String{
        return message
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
            if (pairedDevice.name.equals(name))
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

    val connectToBTObservable: Observable<String> = Observable.create{
        this.connectBtDevice()
        if(socket.isConnected){
            start()
            if(message!=""){
                it.onNext(message)
            }
            cancel()
        }
        it.onComplete()
    }

    @SuppressLint("CheckResult")
    fun getARead(toDo:(String)->Unit){
        if(selectDevice!=null){
            connectToBTObservable
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .subscribe{
                    toDo(it)
                }
        }
    }
}