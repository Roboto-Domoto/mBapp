package com.example.mbapp_androidapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nutritional_info_table")
data class NutritionalInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val info_id: Int = 0,
    var calories: Float = 0f,
    var fat: Float = 0f,
    var cholesterol: Float = 0f,
    var carbohydrate: Float = 0f,
    var sugar: Float = 0f,
    var protein: Float = 0f
)
