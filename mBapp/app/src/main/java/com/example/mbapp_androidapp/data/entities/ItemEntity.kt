package com.example.mbapp_androidapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mbapp_androidapp.common.classes.ItemClass
import com.example.mbapp_androidapp.common.classes.NutritionInfoClass

@Entity(tableName = "items_table")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val name: String,
    val pictureId: Int,
    var quantity: Float,
    val price: Float,
    val type: String,
    val barcode: Long? = null,
    var nutritionInfo: NutritionInfoClass = NutritionInfoClass()
) {
    fun toItemClass(): ItemClass {
        return ItemClass(
            pictureId = pictureId,
            name = name,
            price = price,
            quantity = quantity,
            type = type,
            nutritionInfo = nutritionInfo
        )
    }
}