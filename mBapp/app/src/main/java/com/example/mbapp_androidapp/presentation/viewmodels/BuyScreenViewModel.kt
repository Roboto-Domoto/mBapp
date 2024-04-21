package com.example.mbapp_androidapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mbapp_androidapp.common.classes.System

class BuyScreenViewModel : ViewModel() {
    private val system = System.getInstance()

    private var _topWeightNow = MutableLiveData(0)
    private var _botWeightNow = MutableLiveData(0)
    val topWeightNow: LiveData<Int> = _topWeightNow
    val botWeightNow: LiveData<Int> = _botWeightNow

    init {
        system.weightBot.observeForever{ bW -> _botWeightNow.value = bW }
        system.weightTop.observeForever{ tW -> _topWeightNow.value = tW }
    }
}