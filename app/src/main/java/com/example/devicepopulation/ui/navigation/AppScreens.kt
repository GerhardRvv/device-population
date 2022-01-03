package com.example.devicepopulation.ui.navigation

enum class AppScreens() {
    DeviceList,
    Details;

    companion object {
        fun fromRoute(route: String?): AppScreens =
            when (route?.substringBefore("/")) {
                DeviceList.name -> DeviceList
                Details.name -> Details
                null -> DeviceList
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}
