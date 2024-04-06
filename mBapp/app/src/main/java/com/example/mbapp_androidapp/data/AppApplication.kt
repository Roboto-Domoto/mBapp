package com.example.mbapp_androidapp.data

import android.app.Application
import com.example.mbapp_androidapp.MainActivity
import com.example.mbapp_androidapp.common.ConnectedThread

class AppApplication: Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
}