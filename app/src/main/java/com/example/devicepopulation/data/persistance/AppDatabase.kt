package com.example.devicepopulation.data.persistance

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.devicepopulation.data.models.DeviceModel

@Database(
    entities = [DeviceModel::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(value = [DeviceDetailsConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun deviceDao(): DeviceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context, AppDatabase::class.java, "deviceAppDb").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
