package com.example.devicepopulation.viewmodel

import android.app.Application

import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devicepopulation.data.models.DeviceDetailsModel
import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.data.repository.DeviceRepository
import com.example.devicepopulation.data.viewstate.DeviceViewState
import com.example.devicepopulation.data.viewstate.Loading
import com.example.devicepopulation.data.viewstate.Success
import com.example.devicepopulation.data.viewstate.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    application: Application,
    private val repository: DeviceRepository
) : AndroidViewModel(application) {

    companion object{
        private const val ERROR_MESSAGE = "Error fetching devices"
    }

    val devices : MutableState<List<DeviceModel>> = mutableStateOf(listOf())

    val deviceDetails : MutableState<DeviceModel?>  = mutableStateOf(null)

    val loading = mutableStateOf(false)

    init {
        fetchDevices(false)
    }

    fun fetchDevices(forceFetch : Boolean){
        viewModelScope.launch {
            loading.value = true
            delay(2000)
            val result = if (forceFetch) repository.forceFetchDevices() else repository.fetchDevices()
            if (result != null) {
                devices.value = result
                loading.value = false
            }
            loading.value = false
        }
    }

    fun fetchDeviceById(deviceId: Long?){
        viewModelScope.launch {
            val result = repository.fetchDeviceDetails(deviceId)
            if (result != null) deviceDetails.value = result
        }
    }

//    private fun fetchDevices(forceFetch : Boolean){
//        if (_deviceLiveData.value is Success) return
//
//        _deviceLiveData.value = Loading
//        viewModelScope.launch {
//            try {
//                val devices = if (forceFetch) repository.forceFetchDevices() else repository.fetchDevices()
//                if (devices.isNullOrEmpty()){
//                    _deviceLiveData.postValue(Error(ERROR_MESSAGE))
//                    return@launch
//                }
//                _deviceLiveData.postValue(Success(devices))
//            } catch (e: Exception) {
//                _deviceLiveData.postValue(Error(ERROR_MESSAGE))
//            }
//        }
//    }

//    fun fetchDeviceDetails(deviceId : Long?){
//        if(deviceId == null) return
//
//        if (_deviceDetailsData.value is Success) return
//
//        _deviceDetailsData.value = Loading
//        viewModelScope.launch {
//            try {
//                val devices = repository.fetchDeviceDetails(deviceId)
//                if (devices == null ){
//                    _deviceDetailsData.postValue(Error(ERROR_MESSAGE))
//                    return@launch
//                }
//                _deviceDetailsData.postValue(Success(devices))
//            } catch (e: Exception) {
//                _deviceDetailsData.postValue(Error(ERROR_MESSAGE))
//            }
//        }
//    }
}

