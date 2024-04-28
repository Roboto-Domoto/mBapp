package com.example.mbapp_androidapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mbapp_androidapp.R
import com.example.mbapp_androidapp.common.classes.ItemClass
import com.example.mbapp_androidapp.common.classes.NutritionInfoClass
import com.example.mbapp_androidapp.data.daos.ItemDao
import com.example.mbapp_androidapp.data.entities.ItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class ItemsViewModel(private val itemDao: ItemDao) : ViewModel() {
    val allItems: LiveData<List<ItemEntity>> = itemDao.getAll().asLiveData()

    fun getStock(name: String): Int = allItems.value?.filter { it.barcode != null && it.name == name }?.size ?: 0
    //fun getItem(barcode: String): ItemEntity? = itemDao.getByCodebar(barcode).asLiveData().value

    fun getItem(barcode: Long): ItemEntity? = allItems.value?.first{it.barcode==barcode}

    fun addItem(item: ItemEntity) {
        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    fun deleteItem(item:ItemEntity){
        viewModelScope.launch {
            itemDao.delete(item)
        }
    }

    fun updateItem(item:ItemEntity){
        viewModelScope.launch {
            itemDao.update(item)
        }
    }
}

class ItemsViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItemsViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}