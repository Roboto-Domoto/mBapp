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
                bytes = mmInputStream!!.read(buffer)
                val readMessage = String(buffer,0,bytes)
                val handler = object : Handler(Looper.getMainLooper()) {
                    override fun handleMessage(msg: Message) {
                        when (msg.what) {
                            0 -> {
                                val sys = System.getInstance()
                                val data = filterMsg(msg.obj.toString())
                                sys.temperature=data[0]
                                sys.doorIsOpen=data[1]!="1"
                                sys.weightTop=data[2].toInt()
                                sys.weightTop=data[3].toInt()
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

    private fun filterMsg(msg:String):List<String>{
        val listData = mutableListOf<String>()
        val listOfSliceData = msg.split("|")
        var temperatureMed = 0.00
        var i = 0
        for (temp in listOfSliceData[0].substring(2).split(",")){
            if(temp!="nan"){
                i++
                temperatureMed+=temp.toDouble()
            }
        }
        listData.add((temperatureMed/i).toString())
        listData.add(listOfSliceData[1][2].toString())
        val pesos = listOfSliceData[2].split(",")
        listData.add(pesos[0].substring(2))
        listData.add(pesos[1].substring(0,pesos[1].length-1))
        return listData
    }

    fun write(input:String){
        try{
            mmOutputStream?.write(input.toByteArray())
        }catch (_:IOException){}
    }
}