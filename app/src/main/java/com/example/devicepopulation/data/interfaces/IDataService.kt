package com.example.devicepopulation.data.interfaces

import com.example.devicepopulation.data.models.DeviceModel

interface IDataService {

    suspend fun fetchDevices(): List<DeviceModel>

    suspend fun insertDevices(devices: List<DeviceModel>)

    suspend fun fetchDeviceById(id: Long): DeviceModel?

    suspend fun fetchDeviceByName(name: String): List<DeviceModel>?

    suspend fun updateFavouriteStatus(isFavourite: Boolean, devicesId: Long)
}
