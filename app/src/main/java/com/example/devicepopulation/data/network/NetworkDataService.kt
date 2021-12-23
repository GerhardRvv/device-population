package com.example.devicepopulation.data.network

import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.data.interfaces.IDataService
import javax.inject.Inject

class NetworkDataService @Inject constructor(
    private val devicesService: INetworkDevicesService
) : IDataService {

    override suspend fun fetchDevices(): List<DeviceModel> = devicesService.fetchDevices()

    override suspend fun insertDevices(devices: List<DeviceModel>) {
        throw NotImplementedError()
    }

    override suspend fun fetchDeviceByName(name_: String): DeviceModel {
        throw NotImplementedError()
    }
}