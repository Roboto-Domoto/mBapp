package com.example.mbapp_androidapp.data

import androidx.room.TypeConverter
import com.example.mbapp_androidapp.common.classes.NutritionInfoClass
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    //Gson para las conversiones Json
    private val gson = Gson()

    @TypeConverter
    fun fromNutritionalInfo(value: String?): NutritionInfoClass {
        return gson.fromJson(value, object : TypeToken<NutritionInfoClass>() {}.type)
            ?: NutritionInfoClass()
    }

    @TypeConverter
    fun toNutritionalInfo(nutritionInfo: NutritionInfoClass): String {
        return gson.toJson(nutritionInfo)
    }
}