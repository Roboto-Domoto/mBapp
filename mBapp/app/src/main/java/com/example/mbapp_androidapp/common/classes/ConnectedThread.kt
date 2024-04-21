package com.example.mbapp_androidapp.common.classes

import android.bluetooth.BluetoothSocket
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.mbapp_androidapp.MainActivity
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

class ConnectedThread(socket: BluetoothSocket,private val activity: MainActivity):Thread() {

    private var tag = "ConnectedThread"
    private var mmInputStream: InputStream? = null
    private var mmOutputStream: OutputStream? = null
    private val handlerState = 0


    companion object{
        @Volatile private var INSTANCE: ConnectedThread? = null
        fun getActualThread(socket: BluetoothSocket?,activity: MainActivity?): ConnectedThread {
            return INSTANCE ?: synchronized(this) {
                val instance = ConnectedThread(socket!!,activity!!)
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
            Toast.makeText(activity,"iniciado hilo",Toast.LENGTH_SHORT).show()
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
                                val msgString = msg.obj.toString()
                                val type = filterMsg(msgString)
                                when(type){
                                    "Puerta Abierta" -> Toast.makeText(activity,"Puerta abierta",Toast.LENGTH_LONG).show()
                                    "Temperaturas" -> Toast.makeText(activity,"Temperaturas: ${msgString.substring(2,msgString.length-5)}",Toast.LENGTH_LONG).show()
                                    "Peso" -> Toast.makeText(activity,"Peso: $msgString",Toast.LENGTH_LONG).show()
                                }
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

    private fun filterMsg(msg:String):String{
        if(msg[0] =='T'){
            if(msg[msg.length-2] =='1'){
                return "Puerta Abierta"
            }
            return "Temperaturas"
        }
        return "Peso"
    }

    fun write(input:String){
        try{
            mmOutputStream?.write(input.toByteArray())
        }catch (_:IOException){}
    }
}