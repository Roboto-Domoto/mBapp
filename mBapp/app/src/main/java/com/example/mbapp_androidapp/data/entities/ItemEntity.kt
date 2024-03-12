package com.example.mbapp_androidapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_table")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val item_id: Int = 0,
    val name: String,
    val picture_id: String,
    val quantity: Float,
    val price: Float,
    val type: String
)