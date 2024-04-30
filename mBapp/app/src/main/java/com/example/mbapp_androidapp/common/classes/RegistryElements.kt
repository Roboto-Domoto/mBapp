package com.example.mbapp_androidapp.common.classes

data class InventoryItem(val hour: String, val item: ItemClass)

data class MBappLog(val date: String, val event: String)

data class ItemStock(val item: ItemClass, var minStock: Int = 0)