package com.example.mbapp_androidapp.common.classes

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

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

    fun selectPhotoFromDevice() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        selectImageLauncher.launch(intent)
    }

    fun getUri(): Uri? {
        return uri
    }

}