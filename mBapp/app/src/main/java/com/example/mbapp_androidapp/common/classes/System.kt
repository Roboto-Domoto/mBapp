package com.example.mbapp_androidapp.common.classes

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class System private constructor() {
    private val mBappLogs: MutableList<MBappLog> = mutableListOf()
    private val dailyInventory: MutableList<InventoryItem> = mutableListOf()
    var employee: Employee = Employee.getInstance()
    var customer: Customer = Customer.getInstance()

    companion object {
        private var instance: System? = null
        fun getInstance(): System {
            if (instance == null) instance = System()
            return instance!!
        }
    }
    private fun getHour(): String {
        val hourFormat = DateTimeFormatter.ofPattern("hh:mm:ss")
        return LocalDateTime.now().format(hourFormat)
    }
    fun addLog(event: String) {
        val log = MBappLog(getHour(), event)
        mBappLogs.add(log)
    }

    fun getLogs(): List<MBappLog> {
        return mBappLogs
    }

    fun addItemToInventory(item: ItemClass) {
        val inventoryItem = InventoryItem(getHour(), item)
        dailyInventory.add(inventoryItem)
    }

    fun getInventory(): List<InventoryItem> {
        return dailyInventory
    }

    fun factoryReset() {
        mBappLogs.clear()
        dailyInventory.clear()
        Employee.deleteInstance()
        employee = Employee.getInstance()
        Customer.deleteInstance()
        customer = Customer.getInstance()
    }
}