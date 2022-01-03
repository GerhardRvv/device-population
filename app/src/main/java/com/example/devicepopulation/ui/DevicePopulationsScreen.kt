package com.example.devicepopulation.ui

/**
 * Screen metadata for DevicePopulation.
 */
enum class DevicePopulationsScreen() {
    DevicesList(),
    DeviceDetails();

    companion object {
        fun fromRoute(route: String?): DevicePopulationsScreen =
            when (route?.substringBefore("/")) {
                DevicesList.name -> DevicesList
                DeviceDetails.name -> DeviceDetails
                null -> DevicesList
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}
