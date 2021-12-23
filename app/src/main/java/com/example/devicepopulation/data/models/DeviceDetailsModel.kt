package com.example.devicepopulation.data.models

data class DeviceDetailsModel(
    val name : String,
    val current_price : String,
    val device_image_url : String,
    val is_favourite : Boolean,
    val description : String
)