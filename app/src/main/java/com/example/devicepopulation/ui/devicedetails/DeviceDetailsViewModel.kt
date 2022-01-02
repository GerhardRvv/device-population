package com.example.devicepopulation.ui.devicedetails

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.data.repository.DeviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceDetailsViewModel @Inject constructor(
    application: Application,
    private val repository: DeviceRepository
) : AndroidViewModel(application) {

    companion object{
        private const val ERROR_MESSAGE = "Error fetching devices"
    }

    val deviceDetails : MutableState<DeviceModel?> = mutableStateOf(null)

    fun fetchDeviceById(deviceId: Long?){
        viewModelScope.launch {
            val result = repository.fetchDeviceDetails(deviceId)
            if (result != null) deviceDetails.value = result
        }
    }
}