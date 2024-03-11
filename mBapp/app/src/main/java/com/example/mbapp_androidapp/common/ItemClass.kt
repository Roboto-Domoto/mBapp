package com.example.mbapp_androidapp.common

abstract class ItemClass(
    var pictureId: Int,
    var name: String,
    var price: Float,
    var quantity: Float,
    var nutritionInfo: NutritionInfoClass = NutritionInfoClass()
) {
    abstract fun getQuantityString(): String
}

class Drink(
    pictureId: Int,
    name: String,
    price: Float,
    quantity: Float,
    nutritionInfo: NutritionInfoClass = NutritionInfoClass()
) : ItemClass(pictureId, name, price, quantity, nutritionInfo) {

    override fun getQuantityString(): String {
        return "$quantity ml"
    }
}

class Snack(
    pictureId: Int,
    name: String,
    price: Float,
    quantity: Float,
    nutritionInfo: NutritionInfoClass = NutritionInfoClass()
) : ItemClass(pictureId, name, price, quantity, nutritionInfo) {

    override fun getQuantityString(): String {
        return "$quantity g"
    }
}