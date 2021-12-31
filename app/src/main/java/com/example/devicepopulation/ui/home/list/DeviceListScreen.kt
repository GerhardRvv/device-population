package com.example.devicepopulation.ui.home.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.devicepopulation.data.models.DeviceDetailsModel

import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.ui.components.DeviceCardComponent
import com.example.devicepopulation.ui.components.DevicePopulationSurface
import com.example.devicepopulation.viewmodel.MainActivityViewModel

@Composable
fun DeviceListView(
    viewModel: MainActivityViewModel,
    onDeviceClick: (Long) -> Unit,
    modifier: Modifier = Modifier
){
    val deviceCollection = viewModel.devices

    DeviceListView(
        devices = deviceCollection.value,
        onDeviceClick = onDeviceClick
    )
}
/**
 *  Device List Body
 */

@Composable
private fun DeviceListView(
    devices: List<DeviceModel>,
    onDeviceClick: (Long) -> Unit = {},
    modifier: Modifier = Modifier
) {
    DevicePopulationSurface(modifier = modifier.fillMaxSize()) {
        DeviceCollectionList(devices = devices, onDeviceClick = onDeviceClick)
//        SearchBar()
    }
}

@Composable
private fun DeviceCollectionList(
    devices: List<DeviceModel>,
    onDeviceClick: (Long) -> Unit
){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .semantics { contentDescription = "Device Screen List" }
    ) {
        LazyColumn {
            itemsIndexed(devices) { index, device ->
                DeviceCardComponent(device, onDeviceClick, index != 0)
            }
        }
    }
}

@Preview
@Composable
private fun DeviceListViewPreview(){
    val detailsModel = listOf(
        DeviceDetailsModel(
            name = "name",
            current_price = "34",
            device_image_url = "https://dummyimage.com/200x200/000/fff.jpg",
            is_favourite = true,
            description = "Description with a long text"
        )
    )
    val device = DeviceModel(
        id = 1,
        type = "Type",
        name = "Name",
        status = "status",
        image = "https://dummyimage.com/100x100/000/fff.jpg",
        details = detailsModel
    )
    val collection = listOf(device)
    DeviceCollectionList(
        devices = collection,
        onDeviceClick = {}
    )
}
