package com.example.devicepopulation.ui.devicedetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.devicepopulation.R

import com.google.accompanist.insets.statusBarsPadding

import androidx.compose.ui.tooling.preview.Preview
import com.example.devicepopulation.data.models.DeviceDetailsModel
import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.ui.components.*
import com.example.devicepopulation.ui.theme.DeviceAppTheme
import com.example.devicepopulation.ui.utils.mirroringBackIcon
import kotlinx.coroutines.launch

@Composable
fun DeviceDetailsScreen(
    viewModel: DeviceDetailsViewModel,
    deviceId : Long?,
    upPress: () -> Unit
){
    viewModel.fetchDeviceById(deviceId)
    val device = viewModel.deviceDetails.value
    val scroll = rememberScrollState(0)
    device?.let {
        DeviceDetailsBody(
            device = it,
            scroll,
            upPress
        )
    }
}

@Composable
fun DeviceDetailsBody(
    device : DeviceModel,
    scroll: ScrollState,
    upPress: () -> Unit = {}
){
    val deviceDetails = device.details.first()
    DevicePopulationScaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopAppBar(
                backgroundColor = DeviceAppTheme.colors.uiBackground,
                contentColor = DeviceAppTheme.colors.textPrimary,
                elevation = 4.dp
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Up(upPress)

                    Text( //Device Name
                        text = deviceDetails.name,
                        style = MaterialTheme.typography.h6,
                        color = DeviceAppTheme.colors.textPrimary,
                        modifier = Modifier.align(alignment = Center)
                    )
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.align(alignment = CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite")
                    }
                }
            }
        },
    ) {
        DevicePopulationSurface(
            modifier = Modifier.padding(6.dp)
                .background(DeviceAppTheme.colors.uiBackground)
        ) {
        Column(modifier = Modifier.verticalScroll(scroll)) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .height(10.dp)
                )
                Image(
                    imageUrl = deviceDetails.device_image_url,
                    modifier = Modifier
                        .size(200.dp)
                        .align(CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
                DevicePopulationDivider()

                Title("Device Name: ", deviceDetails.name)
                DeviceDetailText(infoTitle = "Status: ",   detailInfo = device.status)
                DeviceDetailText(infoTitle = "Price: ", detailInfo = deviceDetails.current_price)
                DeviceDetailText(infoTitle = "Description: ", detailInfo = deviceDetails.description)
            }

        }
    }
}

@Composable
private fun Header(
) {
    Spacer(
        modifier = Modifier
            .height(20.dp)
            .fillMaxWidth()
            .background(DeviceAppTheme.colors.uiBackground)

    )
}

private val MinTitleOffset = 56.dp

@Composable
private fun Up(
    upPress: () -> Unit
) {
    IconButton(
        onClick = upPress
    ) {
        Icon(
            imageVector = mirroringBackIcon(),
            tint = DeviceAppTheme.colors.textLink,
            contentDescription = stringResource(R.string.label_back)
        )
    }
}

private val TitleHeight = 128.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)


@Composable
private fun DeviceDetailText(
    infoTitle: String,
    detailInfo: String
) {
    Spacer(Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .background(color = DeviceAppTheme.colors.uiBackground).fillMaxWidth()
    ) {

        Text(
            text = infoTitle,
            style = MaterialTheme.typography.subtitle2,
            color = DeviceAppTheme.colors.textPrimary,
        )
        Text(
            text = detailInfo,
            style = MaterialTheme.typography.subtitle1,
            color = DeviceAppTheme.colors.textPrimary,
        )
    }
    DevicePopulationDivider()
}

@Composable
private fun Title(
    infoTitle: String,
    deviceTitle: String
) {
    Spacer(Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .background(color = DeviceAppTheme.colors.uiBackground)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = infoTitle,
            style = MaterialTheme.typography.subtitle2,
            color = DeviceAppTheme.colors.textPrimary,
        )
        Text(
            text = deviceTitle,
            style = MaterialTheme.typography.subtitle1,
            color = DeviceAppTheme.colors.textPrimary,
        )
    }
    DevicePopulationDivider()
}

@Composable
private fun Image(
    imageUrl: String,
    modifier: Modifier
) {
    DeviceImage(
        imageUrl = imageUrl,
        modifier = modifier
    )
}

@Preview
@Composable
fun DeviceDetailsPreview(){
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
    DeviceDetailsBody(
        device = device,
        scroll = rememberScrollState(0),
        upPress = {}
    )
}