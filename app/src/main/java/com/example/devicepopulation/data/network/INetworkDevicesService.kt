package com.example.devicepopulation.data.network

import com.example.devicepopulation.data.models.DeviceModel
import retrofit2.http.GET

interface INetworkDevicesService {
    @GET("6ea7ad0880805c331d8d67cff30a0fec/raw/f8bf740304bdabc07cd81a5eba165ba29b2f3a64/devices.json")
    suspend fun fetchDevices(): List<DeviceModel>
}
