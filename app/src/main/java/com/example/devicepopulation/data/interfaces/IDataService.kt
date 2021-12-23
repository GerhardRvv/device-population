package com.example.devicepopulation.data.interfaces

import com.example.devicepopulation.data.models.DeviceModel

interface IDataService {

    suspend fun fetchDevices() : List<DeviceModel>

    suspend fun insertDevices(devices: List<DeviceModel>)

    suspend fun fetchDeviceByName(name_: String) : DeviceModel
}