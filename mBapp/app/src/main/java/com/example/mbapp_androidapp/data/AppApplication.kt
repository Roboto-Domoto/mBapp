package com.example.mbapp_androidapp.data

import android.app.Application

class AppApplication: Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
}