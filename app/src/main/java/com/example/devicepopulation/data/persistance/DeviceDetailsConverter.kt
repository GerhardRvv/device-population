package com.example.devicepopulation.data.persistance

import androidx.room.TypeConverter
import com.example.devicepopulation.data.models.DeviceDetailsModel
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class DeviceDetailsConverter {

    @TypeConverter
    fun fromStringToList(value: String): List<DeviceDetailsModel>?{
        return getMoshiAdapter().fromJson(value)
    }

    @TypeConverter
    fun fromListToString(list: List<DeviceDetailsModel>): String{
        return getMoshiAdapter().toJson(list)
    }

    private fun getMoshiAdapter() : JsonAdapter<List<DeviceDetailsModel>> {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(
            MutableList::class.java,
            DeviceDetailsModel::class.java
        )
        return moshi.adapter(type)
    }
}