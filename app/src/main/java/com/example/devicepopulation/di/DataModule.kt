package com.example.devicepopulation.di

import com.example.devicepopulation.data.interfaces.IDataService
import com.example.devicepopulation.data.network.INetworkDevicesService
import com.example.devicepopulation.data.network.NetworkDataService
import com.example.devicepopulation.data.persistance.DeviceDao
import com.example.devicepopulation.data.persistance.PersistenceDataService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Persistence
    @Provides
    fun providesPersistenceDataService(
        deviceDao: DeviceDao
    ): IDataService {
        return PersistenceDataService(deviceDao)
    }

    @Network
    @Provides
    fun providesNetworkDataService(
        deviceNetworkService: INetworkDevicesService
    ): IDataService {
        return NetworkDataService(deviceNetworkService)
    }
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class Network

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class Persistence
