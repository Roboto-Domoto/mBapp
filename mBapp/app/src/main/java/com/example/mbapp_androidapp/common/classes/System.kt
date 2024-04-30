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
    var lastItemAdd: ItemClass?=null
    private var invHourStart: Array<String> = arrayOf("00", "00")
    private var invHourEnd: Array<String> = arrayOf("23", "59")
    //Min stocks items
    private val stockList: MutableList<ItemStock> = mutableListOf()
    /*** Communication variables with ESP32 ***/
    //Temperature
    private val _temperature = MutableLiveData(6)
    var temperature: LiveData<Int> = _temperature
    //Door
    private val _doorIsOpen = MutableLiveData(false)
    var doorIsOpen: LiveData<Boolean> = _doorIsOpen
    //Top weight
    private val _weightTop = MutableLiveData(0)
    val weightTop: LiveData<Int> = _weightTop
    //Bot weight
    private var _weightBot = MutableLiveData(0)
    val weightBot: LiveData<Int> = _weightBot

    fun updateTemperature(t: Int) {
        _temperature.value = t
    }

    fun updateDoorIsOpen(f: Boolean) {
        _doorIsOpen.value = f
    }

    fun updateWeights(weightTop:Int,weightBot:Int) {
        this._weightBot.value = weightBot
        this._weightTop.value = weightTop
    }

    fun getTopWeight(): Int {
        return this.weightTop.value ?: 0
    }

    fun getBotWeight(): Int {
        return this.weightBot.value ?: 0
    }

    fun getStartHour(): String {
        return "${invHourStart[0]}:${invHourStart[1]}"
    }
    fun getEndHour(): String {
        return "${invHourEnd[0]}:${invHourEnd[1]}"
    }
    fun setStartHour(hour: String) {
        invHourStart = if (hour.length == 5) arrayOf(hour.substring(0,2), hour.substring(3))
        else arrayOf(hour.substring(0,1), hour.substring(2))
    }
    fun setEndHour(hour: String) {
        invHourEnd = if (hour.length == 5) arrayOf(hour.substring(0,2), hour.substring(3))
        else arrayOf(hour.substring(0,1), hour.substring(2))
    }

    companion object {
        private var instance: System? = null
        fun getInstance(): System {
            if (instance == null) instance = System()
            return instance!!
        }

        const val barId = 300202
        const val pressureErrorTop = 20
        const val pressureErrorBot = 20
    }
    private fun getDate(): String {
        val hourFormat = DateTimeFormatter.ofPattern("hh:mm:ss - dd/MM/yyyy")
        return LocalDateTime.now().format(hourFormat)
    }
    fun addLog(event: String) {
        val log = MBappLog(getDate(), event)
        mBappLogs.add(log)
    }

    fun getLogs(): List<MBappLog> {
        return mBappLogs
    }

    fun addItemToInventory(item: ItemClass) {
        val inventoryItem = InventoryItem(getDate(), item)
        dailyInventory.add(inventoryItem)
    }

    fun getInventory(): List<InventoryItem> {
        return dailyInventory
    }

    fun getStockList(): List<ItemStock> {
        return stockList
    }

    fun getMinStock(item: ItemClass): Int {
        val product = stockList.firstOrNull { it.item.name == item.name }
        return product?.minStock ?: 0
    }

    fun addMinStock(item: ItemClass, stock: Int) {
        stockList.add(ItemStock(item, stock))
    }

    fun changeStock(item: ItemClass, stock: Int) {
        if (stock > 0) stockList.first { it.item.name == item.name }.minStock = stock
        else stockList.remove(stockList.first { it.item.name == item.name })
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