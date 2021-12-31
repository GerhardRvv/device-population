package com.example.devicepopulation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.devicepopulation.R

@OptIn(ExperimentalCoilApi::class)
@Composable
fun DeviceImage(
    imageUrl: String,
    contentDescription: String? = "",
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    DevicePopulationSurface(
        color = Color.LightGray,
        elevation = elevation,
        shape = CircleShape,
        modifier = modifier
    ) {
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = {
                    crossfade(true)
                    placeholder(drawableResId = R.drawable.ic_launcher_foreground)
                }
            ),
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
    }
}

@Preview
@Composable
fun ImageCardPreview(){
    MaterialTheme{
        DeviceImage(
            imageUrl = "https://dummyimage.com/200x200/000/fff.jpg",
            contentDescription = "Description Text",
            modifier = Modifier
                .size(100.dp)
        )
    }
}