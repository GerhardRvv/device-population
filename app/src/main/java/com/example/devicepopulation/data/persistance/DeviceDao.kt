package com.example.devicepopulation.data.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.devicepopulation.data.models.DeviceDetailsModel
import com.example.devicepopulation.data.models.DeviceModel

@Dao
interface DeviceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceList(devices: List<DeviceModel>)

    @Query("SELECT * FROM DeviceModel")
    suspend fun getDeviceList(): List<DeviceModel>

    @Query("SELECT * FROM DeviceModel WHERE id = :id")
    suspend fun fetchDeviceById(id: Long) : DeviceModel?
}