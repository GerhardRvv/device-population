package com.example.devicepopulation.ui.devices

import android.app.Application

import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.data.repository.DeviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceListViewModel @Inject constructor(
    application: Application,
    private val repository: DeviceRepository
) : AndroidViewModel(application) {

    val devices : MutableState<List<DeviceModel>> = mutableStateOf(listOf())
    val devicesSearch : MutableState<List<DeviceModel>> = mutableStateOf(listOf())
    val loading = mutableStateOf(false)

    init {
        fetchDevices(false)
    }

    fun fetchDevices(forceFetch : Boolean){
        viewModelScope.launch {
            loading.value = true
            delay(2000) //Simulate Network waiting call to display Shimmer
            val result = if (forceFetch) repository.forceFetchDevices() else repository.fetchDevices()
            if (result != null) {
                devices.value = result
                loading.value = false
            }
            loading.value = false
        }
    }

    fun fetchDevicesByName(name: String?){
        viewModelScope.launch {
            val result = repository.fetchDeviceByName(name)
            if (result != null) devicesSearch.value = result
        }
    }
}

