package com.example.mbapp_androidapp.common

class NutritionInfoClass(
    var calories: Float = 0f,
    var fat: Float = 0f,
    var cholesterol: Float = 0f,
    var carbohydrate: Float = 0f,
    var sugar: Float = 0f,
    var protein: Float = 0f
) {
    fun getFatText(): String {
        return "$fat g"
    }

    fun getCholesterolText(): String {
        return "$cholesterol mg"
    }

    fun getCarboHydrateText(): String {
        return "$carbohydrate g"
    }

    fun getSugarText(): String {
        return "$sugar g"
    }

    fun getProteinText(): String {
        return "$protein g"
    }

}
