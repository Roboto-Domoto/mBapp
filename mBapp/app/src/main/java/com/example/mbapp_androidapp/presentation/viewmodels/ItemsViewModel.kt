package com.example.mbapp_androidapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mbapp_androidapp.data.daos.ItemDao
import com.example.mbapp_androidapp.data.entities.ItemEntity

class ItemsViewModel(itemDao: ItemDao) : ViewModel() {
    val allItems: LiveData<List<ItemEntity>> = itemDao.getAll().asLiveData()
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