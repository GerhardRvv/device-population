//package com.example.devicepopulation.data.repository
//
//import com.example.devicepopulation.data.models.DeviceModel
//import com.example.devicepopulation.data.interfaces.IDataService
//import com.example.devicepopulation.data.persistance.PersistenceDataService
//import com.example.devicepopulation.di.Persistence
//import javax.inject.Inject
//
//class DeviceDetailsRepository @Inject constructor(
//    @Persistence private val persistenceDataService: IDataService
//) {
//
//    suspend fun fetchDevice(id: Long) : DeviceModel?{
//        return try {
//            return persistenceDataService.fetch
//        }
//    }
//}