package com.example.devicepopulation.data.repository

import android.util.Log
import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.data.interfaces.IDataService
import com.example.devicepopulation.di.Network
import com.example.devicepopulation.di.Persistence
import javax.inject.Inject

class DeviceRepository @Inject constructor(
   @Persistence private val persistenceDataService: IDataService,
   @Network private val networkDataService: IDataService
) {

    suspend fun fetchDevices(): List<DeviceModel>? {
        return try {
            var devices = persistenceDataService.fetchDevices()
                if (devices.isNullOrEmpty()){
                    devices = networkDataService.fetchDevices()
                    persistenceDataService.insertDevices(devices)
                    persistenceDataService.fetchDevices()
                } else {
                    devices
                }
        } catch (e: Exception) {
            Log.d("Error", "$e")
            null
        }
    }
}


