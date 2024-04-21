package com.example.mbapp_androidapp.common.classes

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.core.app.ActivityCompat
import com.example.mbapp_androidapp.MainActivity
import java.io.IOException
import java.util.UUID


class BluetoothTerminal(private var activity: MainActivity){

    private var tag = "BluetoothTerminal"
    private var requestCode = 0
    private var uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var device: BluetoothDevice? = null
    private var manager: BluetoothManager = activity.getSystemService(BluetoothManager::class.java)
    private var adapter: BluetoothAdapter = manager.adapter
    private var socket: BluetoothSocket? = null


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

    fun checkBluetooth(){
        if(!adapter.isEnabled){
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            checkConnectPermission()
            activity.startActivityForResult(intent,requestCode)
        }
    }

    fun requestConnectPermission(){
        if(ActivityCompat.checkSelfPermission(activity,Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT>=31){
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                    0
                )
            }else makeToast("Can't connect bluetooth")
        }
    }

    private fun checkConnectPermission(){
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestConnectPermission()
            return
        }
    }

    fun getDeviceByName(name:String){
        checkConnectPermission()
        for(dev in adapter.bondedDevices){
            if(dev.name.equals(name)){
                //makeToast("Device get it")
                device = dev
            }
        }
    }

    fun connect(){
        checkConnectPermission()
        if(device!=null){
            try{
                socket = device!!.createRfcommSocketToServiceRecord(uuid)
            }catch (e:IOException){
                makeToast("Can't create socket: ${e.message}")
            }
            try{
                socket!!.connect()
            }catch (e:IOException){
                makeToast("Can't connect socket: ${e.message}")
                try{
                    socket!!.close()
                }catch (e2:IOException){
                    makeToast("Can't close socket: ${e2.message}")
                }
            }
        }
        if(socket!!.isConnected) makeToast("Connected")
    }

    fun disconnect(){
        try{
            socket!!.close()
            makeToast("Disconnect successful")
        }catch(e:IOException){
            e.printStackTrace()
        }
    }

    fun createConnectedThread() : ConnectedThread{
        return ConnectedThread.getActualThread(socket!!,activity)
    }

    fun makeToast(message:String){
        activity.runOnUiThread{
            Toast.makeText(activity,message,Toast.LENGTH_SHORT).show()
        }
    }
}