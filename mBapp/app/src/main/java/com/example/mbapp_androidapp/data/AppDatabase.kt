package com.example.mbapp_androidapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mbapp_androidapp.R
import com.example.mbapp_androidapp.common.classes.NutritionInfoClass
import com.example.mbapp_androidapp.data.daos.ItemDao
import com.example.mbapp_androidapp.data.entities.ItemEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(
    entities = [ItemEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun itemDao(): ItemDao

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
            val ccNutritionInfo = NutritionInfoClass(
                calories = 45f ,
                fat = 0f ,
                cholesterol = 0f,
                carbohydrate = 10f,
                sugar = 9f,
                protein = 0.1f
            )
            val cocaCola = ItemEntity(
                name = "Coca-Cola",
                pictureId = R.drawable.cocacola_lata,
                price = 1.5f,
                type = "Drink",
                quantity = 330f,
                nutritionInfo = ccNutritionInfo
            )
            val cocaColaD = ItemEntity(
                name = "Coca-Cola",
                pictureId = R.drawable.cocacola_lata,
                price = 1.5f,
                type = "Drink",
                quantity = 330f,
                barcode = "12345",
                nutritionInfo = ccNutritionInfo
            )
            INSTANCE?.itemDao()?.insert(cocaColaD)

            val ccZeroNutritionInfo = NutritionInfoClass(
                calories = 0.2f ,
                fat = 0f ,
                cholesterol = 0f,
                carbohydrate = 10f,
                sugar = 0f,
                protein = 0f
            )
            val cocaColaZero = ItemEntity(
                name = "Coca-Cola Zero",
                pictureId = R.drawable.coca_cola_zero_lata,
                price = 1.5f,
                type = "Drink",
                quantity = 330f,
                nutritionInfo = ccZeroNutritionInfo
            )

            val water = ItemEntity(
                name = "Agua",
                pictureId = R.drawable.botella_agua,
                price = 1.5f,
                type = "Drink",
                quantity = 500f
            )
            INSTANCE?.itemDao()?.insert(cocaCola)
            Log.d("DB", "Coca cola inserted")
            INSTANCE?.itemDao()?.insert(cocaColaZero)
            Log.d("DB", "Coca cola zero inserted")
            INSTANCE?.itemDao()?.insert(water)
            Log.d("DB", "Water inserted")
        }

        private suspend fun insertSnacks() {
            val snickerNutritionInfo = NutritionInfoClass(
                calories = 248f ,
                fat = 11.6f ,
                cholesterol = 3f,
                carbohydrate = 31.5f,
                sugar = 27.10f,
                protein = 4.4f
            )
            val snicker = ItemEntity(
                name = "Snicker",
                pictureId = R.drawable.twix,
                price = 1.25f,
                type = "Snack",
                quantity = 50f,
                nutritionInfo = snickerNutritionInfo
            )

            val kitKatNutritionInfo = NutritionInfoClass(
                calories = 530f ,
                fat = 29.86f ,
                cholesterol = 0f,
                carbohydrate = 58.69f,
                sugar = 47.11f,
                protein = 0f
            )
            val kitkat = ItemEntity(
                name = "KitKat",
                pictureId = R.drawable.kitkat,
                price = 1.10f,
                type = "Snack",
                quantity = 41.5f,
                nutritionInfo = kitKatNutritionInfo
            )
            INSTANCE?.itemDao()?.insert(snicker)
            INSTANCE?.itemDao()?.insert(kitkat)
        }
    }
}