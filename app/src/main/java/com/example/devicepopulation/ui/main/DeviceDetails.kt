package com.example.devicepopulation.ui.main

import android.icu.text.CaseMap
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.devicepopulation.viewmodel.MainActivityViewModel

import com.example.devicepopulation.R

import com.example.devicepopulation.ui.components.DevicePopulationDivider
import com.google.accompanist.insets.statusBarsPadding

import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.devicepopulation.data.models.DeviceDetailsModel
import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.ui.components.DeviceCardComponent
import com.example.devicepopulation.ui.components.DeviceImage
import com.example.devicepopulation.ui.components.DevicePopulationSurface
import com.example.devicepopulation.ui.theme.DevicePopulationTheme
import com.example.devicepopulation.ui.theme.Neutral8
import com.example.devicepopulation.ui.utils.mirroringBackIcon
import retrofit2.http.Body

@Composable
fun DeviceDetailsScreen(
    viewModel: MainActivityViewModel,
    deviceId : Long?,
//    upPress: () -> Unit
){
    viewModel.fetchDeviceById(deviceId)
    val device = viewModel.deviceDetails.value
    val scroll = rememberScrollState(0)
    device?.let {
        DeviceDetailsBody(
            deviceDetails = it,
            scroll
//            upPress
        )
    }
}

@Composable
fun DeviceDetailsBody(
    deviceDetails : DeviceModel,
    scroll: ScrollState,
//    upPress: () -> Unit
){
    DevicePopulationSurface(
        color = MaterialTheme.colors.surface
    ) {
        Column(modifier = Modifier.verticalScroll(scroll)) {

            Box(modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally)) {

                Up(
//                upPress
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .height(10.dp)
            )
            deviceDetails.details.first().device_image_url.let {
                Image(
                    imageUrl =  it,
                    modifier = Modifier
                        .size(200.dp)
                        .align(CenterHorizontally)
                )
            }
            Title(
                deviceDetails.name
            )
            Text( //Device Name
                text = deviceDetails.name,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground,
                modifier = HzPadding
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text( //Device Details
                text = deviceDetails.details.first().description,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground,
                maxLines = 5,
                overflow = TextOverflow.Ellipsis,
                modifier = HzPadding
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .height(MinTitleOffset)
            )
        }
    }
}

@Composable
private fun Header(
) {
    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)

    )
}

private val MinTitleOffset = 56.dp

@Composable
private fun Up(
//    upPress: () -> Unit
) {
    IconButton(
        onClick = { Log.d("GERHARD", "Click Back to be implemented") },
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
            .background(
                color = Neutral8.copy(alpha = 0.32f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = mirroringBackIcon(),
            tint = MaterialTheme.colors.onSurface,
            contentDescription = stringResource(R.string.label_back)
        )
    }
}

private val TitleHeight = 128.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)

@Composable
private fun Title(
    deviceTitle: String
) {
    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .statusBarsPadding()
            .background(color = MaterialTheme.colors.background)
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = deviceTitle,
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.onBackground,
            modifier = HzPadding
        )
        Spacer(Modifier.height(8.dp))
        DevicePopulationDivider()
    }
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
        deviceDetails = device,
        scroll = rememberScrollState(0)
//        upPress = {}
    )
}