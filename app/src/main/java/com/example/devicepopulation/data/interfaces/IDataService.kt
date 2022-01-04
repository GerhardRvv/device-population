package com.example.devicepopulation.data.interfaces

import com.example.devicepopulation.data.models.DeviceModel

/**
 * Main entry point for accessing devices data.
 */
interface IDataService {

    suspend fun clearDb()

    suspend fun fetchDevices(): MutableList<DeviceModel>?

    suspend fun insertDevices(devices: MutableList<DeviceModel>)

    suspend fun fetchDeviceById(id: Long): DeviceModel?

    suspend fun fetchDeviceByName(name: String): MutableList<DeviceModel>?

    suspend fun updateFavouriteStatus(isFavourite: Boolean, devicesId: Long)
}
