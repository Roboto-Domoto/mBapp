package com.example.mbapp_androidapp.common.classes

class Employee private constructor() {
    private var adminMail = "sebssgarcia502580@gmail.com"

    companion object {
        private var instance: Employee? = null
        fun getInstance(): Employee {
            if (instance == null) instance = Employee()
            return instance!!
        }

        fun deleteInstance() {
            instance = null
        }
    }

    fun changeAdminEmail(email: String) {
        adminMail = email
    }

    fun getAdminEmail(): String {
        return adminMail
    }
}