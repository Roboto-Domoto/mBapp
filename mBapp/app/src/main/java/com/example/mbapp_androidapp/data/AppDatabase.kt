package com.example.mbapp_androidapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mbapp_androidapp.R
import com.example.mbapp_androidapp.data.daos.ItemDao
import com.example.mbapp_androidapp.data.daos.NutritionalInfoDao
import com.example.mbapp_androidapp.data.entities.ItemEntity
import com.example.mbapp_androidapp.data.entities.NutritionalInfoEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
                )
                    .addCallback(appCallback)
                    .build()
                Log.d("DB", "Database built")
                INSTANCE = instance
                return instance
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        private val appCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d("DB", "Callback Started")
                GlobalScope.launch(Dispatchers.IO) {
                    Log.d("DB", "Insert drinks Started")
                    insertDrinks()
                    Log.d("DB", "Insert drinks Finished")
                    Log.d("DB", "Insert snacks Started")
                    insertSnacks()
                    Log.d("DB", "Insert snacks Finished")
                }
                Log.d("DB", "Callback Finished")
            }
        }

        private suspend fun insertDrinks() {
            val cocaCola = ItemEntity(
                name = "Coca-Cola",
                pictureId = R.drawable.cocacola_lata,
                price = 1.5f,
                type = "Drink",
                quantity = 330f
            )
            val cocaColaZero = ItemEntity(
                name = "Coca-Cola Zero",
                pictureId = R.drawable.coca_cola_zero_lata,
                price = 1.5f,
                type = "Drink",
                quantity = 330f
            )
            val water = ItemEntity(
                name = "Agua",
                pictureId = R.drawable.botella_agua,
                price = 1.5f,
                type = "Drink",
                quantity = 330f
            )
            INSTANCE?.itemDao()?.insert(cocaCola)
            Log.d("DB", "Coca cola inserted")
            INSTANCE?.itemDao()?.insert(cocaColaZero)
            Log.d("DB", "Coca cola zero inserted")
            INSTANCE?.itemDao()?.insert(water)
            Log.d("DB", "Water inserted")
        }

        private suspend fun insertSnacks() {
            val snicker = ItemEntity(
                name = "Snicker",
                pictureId = R.drawable.twix,
                price = 1.25f,
                type = "Snack",
                quantity = 50f
            )
            val kitkat = ItemEntity(
                name = "KitKat",
                pictureId = R.drawable.kitkat,
                price = 1.20f,
                type = "Snack",
                quantity = 45f
            )
            INSTANCE?.itemDao()?.insert(snicker)
            INSTANCE?.itemDao()?.insert(kitkat)
        }
    }
}