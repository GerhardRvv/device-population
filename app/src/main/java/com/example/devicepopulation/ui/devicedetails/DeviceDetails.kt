package com.example.devicepopulation.ui.devicedetails

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.devicepopulation.R
import com.example.devicepopulation.data.models.DeviceDetailsModel
import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.ui.components.DeviceImage
import com.example.devicepopulation.ui.components.DevicePopulationDivider
import com.example.devicepopulation.ui.components.DevicePopulationScaffold
import com.example.devicepopulation.ui.components.DevicePopulationSurface
import com.example.devicepopulation.ui.components.RatingBar
import com.example.devicepopulation.ui.theme.DeviceAppTheme
import com.example.devicepopulation.ui.utils.mirroringBackIcon
import com.google.accompanist.insets.statusBarsPadding

val descriptionTextPadding = 12.dp

@Composable
fun DeviceDetailsScreen(
    viewModel: DeviceDetailsViewModel,
    deviceId: Long?,
    upPress: () -> Unit
) {
    viewModel.fetchDeviceById(deviceId)
    val device by remember { mutableStateOf(viewModel.deviceDetails) }
    val scrollState = rememberScrollState(0)

    device.value?.let {
        DeviceDetailsBody(
            device = it,
            scrollState = scrollState,
            upPress = upPress,
            onFavoriteClick = {
                viewModel.updateDeviceFavoriteStatus(!it.is_favourite, it.id)
            }
        )
    }
}

@Composable
fun DeviceDetailsBody(
    device: DeviceModel,
    scrollState: ScrollState,
    upPress: () -> Unit = {},
    onFavoriteClick: () -> Unit = {}
) {
    val deviceDetails = device.details.first()
    DevicePopulationScaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            TopAppBar(
                backgroundColor = DeviceAppTheme.colors.uiBackground,
                contentColor = DeviceAppTheme.colors.textPrimary,
                elevation = 4.dp
            ) {
                Box(modifier = Modifier.fillMaxSize().align(alignment = Alignment.CenterVertically)) {
                    Up(upPress = upPress)
                    Text(
                        text = deviceDetails.name,
                        style = MaterialTheme.typography.h6,
                        color = DeviceAppTheme.colors.textPrimary,
                        modifier = Modifier.align(alignment = Center)
                    )
                    IconButton(
                        onClick = onFavoriteClick,
                        modifier = Modifier.align(alignment = CenterEnd).padding(end = 4.dp),
                    ) {
                        Icon(
                            imageVector = if (device.is_favourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = stringResource(id = R.string.detail_button_favourite),
                            tint = DeviceAppTheme.colors.error
                        )
                    }
                }
            }
        },
    ) {
        DevicePopulationSurface(
            modifier = Modifier
                .padding(6.dp)
                .background(DeviceAppTheme.colors.uiBackground)
        ) {
            Column(modifier = Modifier.verticalScroll(scrollState)) {
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
                Title(
                    infoTitle = stringResource(
                        id = R.string.detail_header_name
                    ),
                    deviceDetails.name
                )
                DevicePopulationDivider()
                DeviceDetailText(
                    infoTitle = stringResource(
                        id = R.string.detail_header_status
                    ),
                    detailInfo = device.status
                )
                DevicePopulationDivider()
                DeviceDetailText(
                    infoTitle = stringResource(
                        id = R.string.detail_header_price
                    ),
                    detailInfo = deviceDetails.current_price
                )
                DevicePopulationDivider()
                RatingBar(
                    rating = deviceDetails.rating
                )
                DevicePopulationDivider()
                DeviceDetailText(
                    infoTitle = stringResource(
                        id = R.string.detail_header_description
                    ),
                    detailInfo = deviceDetails.description
                )
            }
        }
    }
}

@Composable
private fun Up(
    upPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier.padding(top = 4.dp),
        onClick = upPress
    ) {
        Icon(
            imageVector = mirroringBackIcon(),
            tint = DeviceAppTheme.colors.iconPrimary,
            contentDescription = stringResource(R.string.label_back)
        )
    }
}

@Composable
private fun DeviceDetailText(
    infoTitle: String,
    detailInfo: String
) {
    Spacer(Modifier.height(16.dp))
    Row(
        modifier = Modifier
            .background(color = DeviceAppTheme.colors.uiBackground)
            .fillMaxWidth()
    ) {

        Text(
            modifier = Modifier.padding(start = 2.dp),
            text = infoTitle,
            style = MaterialTheme.typography.subtitle2,
            color = DeviceAppTheme.colors.textPrimary,
        )
        Text(
            modifier = Modifier.padding(start = descriptionTextPadding),
            text = detailInfo,
            style = MaterialTheme.typography.subtitle1,
            color = DeviceAppTheme.colors.textPrimary,
        )
    }
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
            modifier = Modifier.padding(start = 2.dp),
            text = infoTitle,
            style = MaterialTheme.typography.subtitle2,
            color = DeviceAppTheme.colors.textPrimary,
        )
        Text(
            modifier = Modifier.padding(start = descriptionTextPadding),
            text = deviceTitle,
            style = MaterialTheme.typography.subtitle1,
            color = DeviceAppTheme.colors.textPrimary,
        )
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
fun DeviceDetailsPreview() {
    val detailsModel = listOf(
        DeviceDetailsModel(
            name = "name",
            current_price = "34",
            device_image_url = "https://dummyimage.com/200x200/000/fff.jpg",
            description = "Description with a long text",
            rating = 4
        )
    )
    val device = DeviceModel(
        id = 1,
        type = "Type",
        name = "Name",
        status = "status",
        image = "https://dummyimage.com/100x100/000/fff.jpg",
        is_favourite = true,
        details = detailsModel
    )
    DeviceDetailsBody(
        device = device,
        scrollState = rememberScrollState(0),
        upPress = {},
        onFavoriteClick = {}
    )
}
