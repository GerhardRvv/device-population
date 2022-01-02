package com.example.devicepopulation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.example.devicepopulation.R

import com.example.devicepopulation.ui.theme.DeviceAppTheme

/**
 * Simple list item row which displays an image and text.
 */
@Composable
fun Placeholder(
    painter: Painter,
    modifier: Modifier = Modifier,
    childModifier: Modifier = Modifier,
) {
    Column(Modifier.padding(4.dp)) {
    DevicePopulationSurface(
        shape = MaterialTheme.shapes.small,
        color = DeviceAppTheme.colors.uiBackground,
        elevation = 2.dp,
        modifier = modifier
        ) {
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {
            val (image, name, button) = createRefs()
            createVerticalChain(name, chainStyle = ChainStyle.Packed)

            DevicePopulationSurface(
                shape = CircleShape,
                modifier = modifier
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = childModifier
                        .size(100.dp)
                        .constrainAs(image) {
                            linkTo(
                                top = parent.top,
                                topMargin = 16.dp,
                                bottom = parent.bottom,
                                bottomMargin = 16.dp
                            )
                            start.linkTo(parent.start)
                        },
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = childModifier.constrainAs(name) {
                        linkTo(
                            start = image.end,
                            end = parent.end,
                            startMargin = 16.dp
                        )
                    }
                )
            }
        }
    }
  }
}

@Preview
@Composable
fun PlaceHolderPreview(){
    Placeholder(
        painter =  rememberImagePainter(R.drawable.ic_launcher_foreground)
    )
}


