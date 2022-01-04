package com.example.devicepopulation.data

import com.example.devicepopulation.data.interfaces.IDataService
import com.example.devicepopulation.data.models.DeviceModel

class MockDataService(
    private var devices: MutableList<DeviceModel> = mutableListOf()
) : IDataService {

    override suspend fun clearDb() {
        devices.clear()
    }

    override suspend fun fetchDevices(): MutableList<DeviceModel>? {
        devices.let { return it }
        return null
    }

    override suspend fun insertDevices(newDevices: MutableList<DeviceModel>) {
        devices = newDevices
    }

    override suspend fun fetchDeviceById(deviceId: Long): DeviceModel? {
        var queryMock: DeviceModel? = null
        devices.let {
            for (item in it) {
                if (item.id == deviceId) {
                    queryMock = item
                }
            }
            return queryMock
        }
    }

    override suspend fun fetchDeviceByName(name: String): MutableList<DeviceModel>? {
        val queryMock: MutableList<DeviceModel> = mutableListOf()
        devices.let {
            for (item in it) {
                if (item.name == name) {
                    queryMock.add(item)
                }
            }
            return queryMock
        }
        return null
    }

    override suspend fun updateFavouriteStatus(isFavourite: Boolean, devicesId: Long) {
        throw NotImplementedError()
    }
}
