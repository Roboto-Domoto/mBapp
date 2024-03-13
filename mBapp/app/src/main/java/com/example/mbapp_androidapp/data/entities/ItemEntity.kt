package com.example.mbapp_androidapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mbapp_androidapp.common.classes.ItemClass

@Entity(tableName = "items_table")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val name: String,
    val pictureId: Int,
    val quantity: Float,
    val price: Float,
    val type: String
) {
    fun toItemClass(): ItemClass {
        return ItemClass(
            pictureId = pictureId,
            name = name,
            price = price,
            quantity = quantity,
            type = type
        )
    }
}