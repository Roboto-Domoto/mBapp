package com.example.mbapp_androidapp.common.classes

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat

class PhotoManager private constructor(private var activity: ComponentActivity){

    companion object {
        private var instance: PhotoManager? = null
        fun getInstance(activity: ComponentActivity? = null): PhotoManager {
            if (instance == null) instance = activity?.let { PhotoManager(it) }
            return instance!!
        }
    }

    private var uri: Uri? = null

    private val selectImageLauncher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                this.uri = uri
            }
        }
    }

    fun requestConnectPermission(){
        if(ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) !=
            PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT>=31){
                ActivityCompat.requestPermissions(
                    activity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    0
                )
            }
        }
    }

    private fun checkConnectPermission(){
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestConnectPermission()
            return
        }
    }

    fun selectPhotoFromDevice() {
        checkConnectPermission()
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        selectImageLauncher.launch(intent)
    }

    fun getUri(): Uri? {
        return uri
    }

}