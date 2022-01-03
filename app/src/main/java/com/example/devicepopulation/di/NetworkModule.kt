package com.example.devicepopulation.di

import com.example.devicepopulation.data.network.INetworkDevicesService
import com.example.devicepopulation.data.network.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private var httpClient = OkHttpClient.Builder().addInterceptor(logging)

    @Singleton
    @Provides
    fun providesDeviceService(): INetworkDevicesService {
        return Retrofit.Builder()
            .baseUrl("https://gist.githubusercontent.com/GerhardRvv/")
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .client(httpClient.build())
            .build()
            .create(INetworkDevicesService::class.java)
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(RequestInterceptor())
            .build()
    }
}
