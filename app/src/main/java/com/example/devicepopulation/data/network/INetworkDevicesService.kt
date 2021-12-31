package com.example.devicepopulation.data.network

import com.example.devicepopulation.data.models.DeviceModel
import retrofit2.http.GET

interface INetworkDevicesService {

    @GET("6ea7ad0880805c331d8d67cff30a0fec/raw/55f176c1e21feb5f6d9123a0e759089ac7900de4/devices.json")
    suspend fun fetchDevices() : List<DeviceModel>
}