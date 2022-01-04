package com.example.devicepopulation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
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
        Surface(
            shape = MaterialTheme.shapes.small,
            color = DeviceAppTheme.colors.brand,
            elevation = 4.dp,
        ) {
            ConstraintLayout(
                modifier = modifier
                    .padding(horizontal = 24.dp).fillMaxWidth()
            ) {
                val (image, name) = createRefs()
                createVerticalChain(name, chainStyle = ChainStyle.Packed)

                DevicePopulationSurface(
                    shape = CircleShape,
                    modifier = modifier.padding(all = 8.dp)
                        .constrainAs(image) {
                            linkTo(
                                top = parent.top,
                                topMargin = 4.dp,
                                bottom = parent.bottom,
                                bottomMargin = 4.dp
                            )
                            start.linkTo(parent.start)
                        }
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = childModifier.size(60.dp),
                        alignment = Alignment.Center
                    )
                }
                Image(
                    painter = painter,
                    contentDescription = null,
                    modifier = childModifier.height(30.dp).width(200.dp).size(20.dp)
                        .constrainAs(name) {
                            linkTo(
                                top = parent.top,
                                topMargin = 4.dp,
                                bottom = parent.bottom,
                                bottomMargin = 4.dp
                            )
                            start.linkTo(image.end)
                            top.linkTo(image.top)
                        }
                )
            }
        }
    }
}

@Preview("default", showBackground = true)
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun PlaceHolderPreview() {
    Placeholder(
        painter = rememberImagePainter(R.drawable.ic_launcher_foreground)
    )
}
