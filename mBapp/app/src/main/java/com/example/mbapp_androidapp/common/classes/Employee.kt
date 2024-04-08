package com.example.mbapp_androidapp.common.classes

class Employee private constructor() {
    private var adminTl = ""

    companion object {
        private var instance: Employee? = null
        fun getInstance(): Employee {
            if (instance == null) instance = Employee()
            return instance!!
        }
    }

    fun changeAdminTl(number: String) {
        adminTl = number
    }

    fun getAdminTl(): String {
        return adminTl
    }
}