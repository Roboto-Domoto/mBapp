package com.example.mbapp_androidapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mbapp_androidapp.data.daos.ItemDao
import com.example.mbapp_androidapp.data.daos.NutritionalInfoDao
import com.example.mbapp_androidapp.data.entities.ItemEntity
import com.example.mbapp_androidapp.data.entities.NutritionalInfoEntity

@Database(
    entities = [ItemEntity::class, NutritionalInfoEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun itemDao(): ItemDao

    abstract fun infoDao(): NutritionalInfoDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            Log.d("DB", "Getting database")
            return INSTANCE ?: synchronized(this) {
                Log.d("DB", "Building database")
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass =AppDatabase::class.java,
                    name = "mBapp_database"
                ).build()
                Log.d("DB", "Database built")
                INSTANCE = instance
                return instance
            }
        }
    }
}