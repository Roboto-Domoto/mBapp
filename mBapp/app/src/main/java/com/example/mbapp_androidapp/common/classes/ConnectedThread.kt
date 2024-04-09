package com.example.mbapp_androidapp.common.classes

import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class ConnectedThread(socket: BluetoothSocket,private val handler: Handler):Thread() {

    private var tag = "ConnectedThread"
    private var mmInputStream: InputStream? = null
    private var mmOutputStream: OutputStream? = null
    private val handlerState = 0

    init {
        var tmpIn:InputStream? = null
        var tmpOut:OutputStream? = null
        try{
            tmpIn = socket.inputStream
            tmpOut = socket.outputStream
        }catch(e:IOException){
            Log.e(tag,"Error in socket recive")
        }
        mmInputStream = tmpIn
        mmOutputStream = tmpOut
    }

    fun execute(){
        if (mmInputStream==null||mmOutputStream==null){
            Log.e(tag,"Can't execute")
        }else{
            start()
        }
    }

    override fun run() {
        val buffer = ByteArray(256)
        var bytes:Int
        while(true){
            try{
                Log.e(tag,"HHH")
                bytes = mmInputStream!!.read(buffer)
                Log.e(tag,"HHH")
                val readMessage = String(buffer,0,bytes)
                handler.obtainMessage(handlerState,bytes,-1,readMessage).sendToTarget()
            }catch(e:IOException){
                Log.e(tag,"Error running: ${e.message}")
                break
            }
        }
    }

    fun write(input:String){
        try{
            mmOutputStream?.write(input.toByteArray())
        }catch (_:IOException){}
    }
}