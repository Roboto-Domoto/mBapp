package com.example.mbapp_androidapp.common.classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class System private constructor() {
    private val mBappLogs: MutableList<MBappLog> = mutableListOf()
    private val dailyInventory: MutableList<InventoryItem> = mutableListOf()
    var employee: Employee = Employee.getInstance()
    var customer: Customer = Customer.getInstance()
    //Communication variables with ESP32
    //Temperature
    private val _temperature = MutableLiveData("0")
    var temperature: LiveData<String> = _temperature

    var doorIsOpen: Boolean = false
    private val _weightTop = MutableLiveData(0)
    val weightTop: LiveData<Int> = _weightTop
    private var _weightBot = MutableLiveData(0)
    val weightBot: LiveData<Int> = _weightBot

    fun updateTemperature(t: String) {
        _temperature.value = t
    }

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

    fun setWeights(weightTop:Int,weightBot:Int){
        this._weightBot.value=weightBot
        this._weightTop.value=weightTop
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