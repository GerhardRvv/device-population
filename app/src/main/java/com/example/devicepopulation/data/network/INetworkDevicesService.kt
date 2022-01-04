package com.example.devicepopulation.data.network

import com.example.devicepopulation.data.models.DeviceModel
import retrofit2.http.GET

interface INetworkDevicesService {
    @GET("6ea7ad0880805c331d8d67cff30a0fec/raw/7f9856dfd0c7b38869d4620d6cdf02e84ee26144/devices.json")
    suspend fun fetchDevices(): MutableList<DeviceModel>
}
