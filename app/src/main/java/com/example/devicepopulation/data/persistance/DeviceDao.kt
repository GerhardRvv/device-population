package com.example.devicepopulation.data.persistance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.devicepopulation.data.models.DeviceModel

/**
 * Data Access Object for the DeviceModel table.
 */
@Dao
interface DeviceDao {

    /**
     * Delete list of DeviceModel content.
     */
    @Query("DELETE FROM DeviceModel")
    suspend fun clearDb()

    /**
     * Insert list of Devices into DeviceModel table.
     *
     * @param MutableList<DeviceModel>: the task id.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeviceList(devices: MutableList<DeviceModel>)

    /**
     * Fetch all DeviceModels.
     *
     * @return MutableList<DeviceModel>?: list of DeviceModel.
     */
    @Query("SELECT * FROM DeviceModel")
    suspend fun getDeviceList(): MutableList<DeviceModel>?

    /**
     * Select a DeviceModel by id.
     *
     * @param deviceId Long: the device id.
     * @return DeviceModel: the device with devceId.
     */
    @Query("SELECT * FROM DeviceModel WHERE id = :id")
    suspend fun fetchDeviceById(id: Long): DeviceModel?

    /**
     * Fetch DeviceModels by name.
     *
     * @param deviceName String: the device name.
     * @return MutableList<DeviceModel>?: list of DeviceModel.
     */
    @Query("SELECT * FROM DeviceModel WHERE name LIKE '%' || :name || '%'")
    suspend fun fetchDeviceByName(name: String): MutableList<DeviceModel>?

    /**
     * Update the Favourite status of a device
     *
     * @param favourite Boolean: value to update to in DeviceModel table
     * @param deviceId Long: Device id to update
     */
    @Query("UPDATE DeviceModel SET is_favourite = :favourite WHERE id = :deviceId")
    suspend fun setFavouriteStatus(favourite: Boolean, deviceId: Long)
}
