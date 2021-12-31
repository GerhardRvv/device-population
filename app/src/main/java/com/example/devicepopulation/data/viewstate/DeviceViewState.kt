package com.example.devicepopulation.data.viewstate

sealed class  DeviceViewState<out T>

data class Success<out T>(val data: T): DeviceViewState<T>()
data class Error(val errorMsg: String): DeviceViewState<Nothing>()
object Loading: DeviceViewState<Nothing>()
//
//sealed class DeviceViewState{
//    data class Success(val devices: Flow<List<DeviceModel>>): DeviceViewState()
//    data class Error(val errorMsg: String): DeviceViewState()
//    object Loading: DeviceViewState()
