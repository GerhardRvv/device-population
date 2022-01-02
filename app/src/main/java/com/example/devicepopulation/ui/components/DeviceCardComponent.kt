package com.example.devicepopulation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.devicepopulation.R
import com.example.devicepopulation.data.models.DeviceDetailsModel
import com.example.devicepopulation.data.models.DeviceModel
import com.example.devicepopulation.ui.theme.DeviceAppTheme

@Composable
fun DeviceCardComponent(
    device: DeviceModel,
    onDeviceClick: (Long) -> Unit,
    showDivider: Boolean,
    modifier: Modifier = Modifier
) {
    Box(Modifier.padding(4.dp)) {
        DevicePopulationSurface(
            shape = MaterialTheme.shapes.small,
            color = DeviceAppTheme.colors.uiBackground,
            contentColor = DeviceAppTheme.colors.textPrimary,
            elevation = 4.dp
        ) {
            ConstraintLayout(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { onDeviceClick(device.id) }
                    .padding(horizontal = 24.dp)
            ) {
                val (divider, image, name, tag, priceSpacer, price, add) = createRefs()
                createVerticalChain(name, tag, priceSpacer, price, chainStyle = ChainStyle.Packed)
                if (showDivider) {
                    DevicePopulationDivider(
                        Modifier.constrainAs(divider) {
                            linkTo(start = parent.start, end = parent.end)
                            top.linkTo(parent.top)
                        }
                    )
                }
                DeviceImage(
                    imageUrl = device.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .constrainAs(image) {
                            linkTo(
                                top = parent.top,
                                topMargin = 16.dp,
                                bottom = parent.bottom,
                                bottomMargin = 16.dp
                            )
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    text = device.name,
                    style = MaterialTheme.typography.subtitle1,
                    color = DeviceAppTheme.colors.textPrimary,
                    modifier = Modifier.constrainAs(name) {
                        linkTo(
                            start = image.end,
                            startMargin = 16.dp,
                            end = add.start,
                            endMargin = 16.dp,
                            bias = 0f
                        )
                    }
                )
                Text(
                    text = device.type,
                    style = MaterialTheme.typography.body1,
                    color = DeviceAppTheme.colors.textPrimary,
                    modifier = Modifier.constrainAs(tag) {
                        linkTo(
                            start = image.end,
                            startMargin = 16.dp,
                            end = add.start,
                            endMargin = 16.dp,
                            bias = 0f
                        )
                    }
                )
                Spacer(
                    Modifier
                        .height(8.dp)
                        .constrainAs(priceSpacer) {
                            linkTo(top = tag.bottom, bottom = price.top)
                        }
                )
                Text(
                    text = device.status,
                    style = MaterialTheme.typography.subtitle1,
                    color = DeviceAppTheme.colors.textPrimary,
                    modifier = Modifier.constrainAs(price) {
                        linkTo(
                            start = image.end,
                            startMargin = 16.dp,
                            end = add.start,
                            endMargin = 16.dp,
                            bias = 0f
                        )
                    }
                )
                GeneralButton(
                    onClick = { /* todo */ },
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(36.dp)
                        .constrainAs(add) {
                            linkTo(top = parent.top, bottom = parent.bottom)
                            end.linkTo(parent.end)
                        }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = stringResource(R.string.label_add)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DeviceCardPreview(){
    val detailsModel = listOf(DeviceDetailsModel(
        name = "name",
        current_price = "34",
        device_image_url = "https://dummyimage.com/200x200/000/fff.jpg",
        is_favourite = true,
        description = "Description with a long text"
    ))
    val device = DeviceModel(
        id = 1,
        type = "Type",
        name = "Name",
        status = "status",
        image = "https://dummyimage.com/100x100/000/fff.jpg",
        details = detailsModel
    )
        DeviceCardComponent(
            device = device,
            onDeviceClick = {},
            showDivider = true
        )
}
