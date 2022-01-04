package com.example.devicepopulation.data.network

import com.example.devicepopulation.data.interfaces.IDataService
import com.example.devicepopulation.data.models.DeviceModel
import javax.inject.Inject

class NetworkDataService @Inject constructor(
    private val devicesService: INetworkDevicesService
) : IDataService {

    override suspend fun clearDb() {
        throw NotImplementedError()
    }

    override suspend fun fetchDevices(): MutableList<DeviceModel>? = devicesService.fetchDevices()

    override suspend fun insertDevices(devices: MutableList<DeviceModel>) {
        throw NotImplementedError()
    }

    override suspend fun fetchDeviceById(id: Long): DeviceModel? {
        throw NotImplementedError()
    }

    override suspend fun fetchDeviceByName(name: String): MutableList<DeviceModel>? {
        throw NotImplementedError()
    }

    override suspend fun updateFavouriteStatus(isFavourite: Boolean, devicesId: Long) {
        throw NotImplementedError()
    }
}
