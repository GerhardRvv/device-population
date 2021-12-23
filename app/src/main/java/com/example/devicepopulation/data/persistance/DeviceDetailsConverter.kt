package com.example.devicepopulation.data.persistance

import androidx.room.TypeConverter
import com.example.devicepopulation.data.models.DeviceDetailsModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class DeviceDetailsConverter {

    @TypeConverter
    fun fromStringToList(value: String): DeviceDetailsModel?{
        return getMoshiAdapter().fromJson(value)
    }

    @TypeConverter
    fun fromListToString(details: DeviceDetailsModel): String{
        return getMoshiAdapter().toJson(details)
    }

    private fun getMoshiAdapter() : JsonAdapter<DeviceDetailsModel> {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(
            MutableList::class.java,
            DeviceDetailsModel::class.java
        )
        return moshi.adapter(type)
    }
}