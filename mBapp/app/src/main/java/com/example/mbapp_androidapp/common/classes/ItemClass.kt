package com.example.mbapp_androidapp.common.classes

class ItemClass(
    var pictureId: Int?,
    var pictureUri: String?,
    var name: String,
    var price: Float,
    var quantity: Float,
    var nutritionInfo: NutritionInfoClass = NutritionInfoClass(),
    val type: String,
) {
    fun getQuantityString(): String {
        return if (type == "Drink") "$quantity ml"
        else "$quantity g"
    }
}