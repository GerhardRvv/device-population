package com.example.devicepopulation.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DeviceModel(
    @PrimaryKey
    val id: Long,
    val type: String,
    val name: String,
    val status: String,
    val image: String,
    val is_favourite: Boolean,
    val details: List<DeviceDetailsModel>
)
