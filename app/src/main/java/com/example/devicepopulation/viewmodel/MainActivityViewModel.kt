package com.example.devicepopulation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.data.repository.DeviceRepository
import com.example.devicepopulation.data.viewstate.DeviceViewState
import com.example.devicepopulation.data.viewstate.Error
import com.example.devicepopulation.data.viewstate.Loading
import com.example.devicepopulation.data.viewstate.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: DeviceRepository
) : ViewModel() {

    companion object{
        private const val ERROR_MESSAGE = "Error fetching devices"
    }

    private val _deviceLiveData: MutableLiveData<DeviceViewState<List<DeviceModel>>>
    by lazy { MutableLiveData<DeviceViewState<List<DeviceModel>>>() }
    val deviceLiveData: MutableLiveData<DeviceViewState<List<DeviceModel>>>
    get() = _deviceLiveData

    init {
        fetchDevices()
    }

    private fun fetchDevices(){
        if (_deviceLiveData.value is Success) return

        _deviceLiveData.value = Loading
        viewModelScope.launch {
            try {
                val devices = repository.fetchDevices()
                if (devices.isNullOrEmpty()){
                    _deviceLiveData.postValue(Error(ERROR_MESSAGE))
                    return@launch
                }
                _deviceLiveData.postValue(Success(devices))
            } catch (e: Exception) {
                _deviceLiveData.postValue(Error(ERROR_MESSAGE))
            }
        }
    }
}