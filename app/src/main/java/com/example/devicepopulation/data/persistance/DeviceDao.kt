package com.example.devicepopulation.data.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.devicepopulation.data.models.DeviceModel

@Dao
interface DeviceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceList(devices: List<DeviceModel>)

    @Query("SELECT * FROM DeviceModel")
    suspend fun getDeviceList(): List<DeviceModel>

    @Query("SELECT * FROM DeviceModel WHERE id = :id")
    suspend fun fetchDeviceById(id: Long): DeviceModel?

    @Query("SELECT * FROM DeviceModel WHERE name LIKE '%' || :name || '%'")
    suspend fun fetchDeviceByName(name: String): List<DeviceModel>?

    @Query("UPDATE DeviceModel SET is_favourite = :favourite WHERE id = :deviceId")
    suspend fun setFavouriteStatus(favourite: Boolean, deviceId: Long)
}
