package com.example.devicepopulation.data.network

import com.example.devicepopulation.data.models.DeviceModel
import retrofit2.http.GET

interface INetworkDevicesService {

    @GET("6ea7ad0880805c331d8d67cff30a0fec/raw/74fb2456e9e192f7dfee6d6bf602cef3e3064c09/devices.json")
    suspend fun fetchDevices() : List<DeviceModel>
}