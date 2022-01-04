package com.example.devicepopulation.mockdata

import com.example.devicepopulation.data.models.DeviceDetailsModel
import com.example.devicepopulation.data.models.DeviceModel

class DeviceModuleDataMock {

    private val detailsModel1 = listOf(
        DeviceDetailsModel(
            name = "phoneDetailsName",
            current_price = "34",
            device_image_url = "https://dummyimage.com/200x200/000/fff.jpg",
            description = "Description with a long text",
            rating = 3
        )
    )
    private val detailsModel2 = listOf(
        DeviceDetailsModel(
            name = "tabletDetailsName",
            current_price = "34",
            device_image_url = "https://dummyimage.com/200x200/000/fff.jpg",
            description = "Description with a long text",
            rating = 3
        )
    )
    val deviceModel1 = DeviceModel(
        id = 1,
        type = "Type1",
        name = "phone",
        status = "status",
        image = "https://dummyimage.com/100x100/000/fff.jpg",
        is_favourite = true,
        details = detailsModel1,
    )
    val deviceModel2 = DeviceModel(
        id = 2,
        type = "Type2",
        name = "tablet",
        status = "status",
        image = "https://dummyimage.com/100x100/000/fff.jpg",
        is_favourite = true,
        details = detailsModel2,
    )
}
