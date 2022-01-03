package com.example.devicepopulation.data.persistance

import com.example.devicepopulation.data.interfaces.IDataService
import com.example.devicepopulation.data.models.DeviceModel
import javax.inject.Inject

class PersistenceDataService @Inject constructor(
    private val deviceDao: DeviceDao
) : IDataService {

    override suspend fun fetchDevices() = deviceDao.getDeviceList()

    override suspend fun insertDevices(devices: List<DeviceModel>) =
        deviceDao.insertDeviceList(devices)

    override suspend fun fetchDeviceById(id: Long) =
        deviceDao.fetchDeviceById(id)

    override suspend fun fetchDeviceByName(name: String): List<DeviceModel>? =
        deviceDao.fetchDeviceByName(name)

    override suspend fun updateFavouriteStatus(isFavourite: Boolean, deviceId: Long) =
        deviceDao.setFavouriteStatus(isFavourite, deviceId)
}
