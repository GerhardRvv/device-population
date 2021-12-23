package com.example.devicepopulation.data.persistance

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.devicepopulation.data.models.DeviceModel

@Database(entities = [DeviceModel::class], version = 1)
@TypeConverters(value = [DeviceDetailsConverter::class])
abstract class AppDatabase: RoomDatabase() {

    abstract fun deviceDao(): DeviceDao
}