package com.example.mbapp_androidapp.common.classes

import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.mbapp_androidapp.MainActivity
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class ConnectedThread(socket: BluetoothSocket,private val mutableState: MutableState<String>):Thread() {

    private var tag = "ConnectedThread"
    private var mmInputStream: InputStream? = null
    private var mmOutputStream: OutputStream? = null
    private val handlerState = 0

    private var _temp1 = 0.0
    private var _temp2 = 0.0
    private var _temp3 = 0.0
    private var _isOpen = false


    companion object{
        @Volatile private var INSTANCE: ConnectedThread? = null
        fun getActualThread(socket: BluetoothSocket?,mutableState: MutableState<String>?): ConnectedThread {
            return INSTANCE ?: synchronized(this) {
                val instance = ConnectedThread(socket!!,mutableState!!)
                INSTANCE = instance
                return instance
            }
        }
    }

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
                val handler = object : Handler(Looper.getMainLooper()) {
                    override fun handleMessage(msg: Message) {
                        when (msg.what) {
                            0 -> {
                                mutableState.value = msg.obj.toString()
                            }
                        }
                    }
                }
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