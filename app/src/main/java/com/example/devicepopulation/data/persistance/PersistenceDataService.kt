package com.example.devicepopulation.data.persistance

import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.data.interfaces.IDataService
import javax.inject.Inject

class PersistenceDataService @Inject constructor(
    private val deviceDao: DeviceDao
) : IDataService {

    override suspend fun insertDevices(devices: List<DeviceModel>) =
        deviceDao.insertDeviceList(devices)

    override suspend fun fetchDeviceByName(name_: String) = deviceDao.fetchDeviceByName(name_)

    override suspend fun fetchDevices() = deviceDao.getDeviceList()


}