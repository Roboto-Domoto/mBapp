package com.example.mbapp_androidapp.common.classes

class Customer private constructor() {
    private val shopping: MutableList<ItemClass> = mutableListOf()

    companion object {
        private var instance: Customer? = null
        fun getInstance(): Customer {
            if (instance == null) instance = Customer()
            return instance!!
        }

        fun deleteInstance() {
            instance = null
        }
    }

    //Vaciar la lista de productos comprados
    fun resetList() {
        shopping.clear()
    }

    //Obtener la cesta
    fun getShoppingList(): MutableList<ItemClass> {
        return shopping
    }

    //Añadir un producto a la cesta
    fun addProduct(item: ItemClass) {
        shopping.add(item)
    }

    //Obtener el precio a pagar por los productos comprados
    fun getAccount(): Float {
        var total: Float = 0f
        for (item in shopping) {
            total += item.price
        }
        return total
    }
}