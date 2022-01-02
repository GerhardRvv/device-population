package com.example.devicepopulation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import com.example.devicepopulation.ui.theme.DeviceAppTheme

@Composable
fun GeneralButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = ButtonShape,
    border: BorderStroke? = null,
//    backgroundGradient: List<Color> =  DevicePopulationTheme.colors.interactivePrimary,
//    disabledBackgroundGradient: List<Color> = DevicePopulationTheme.colors.interactiveSecondary,
    contentColor: Color = MaterialTheme.colors.onError,
    disabledContentColor: Color = MaterialTheme.colors.error,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    DevicePopulationSurface(
        shape = shape,
        color = Color.Transparent,
        contentColor = if (enabled) contentColor else disabledContentColor,
        border = border,
        modifier = modifier
            .clip(shape)
//            .background(
//                Brush.horizontalGradient(
////                    colors = if (enabled) backgroundGradient else disabledBackgroundGradient
//                )
//            )
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null
            )
    ) {
        ProvideTextStyle(
            value = MaterialTheme.typography.button
        ) {
            Row(
                Modifier
                    .defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth,
                        minHeight = ButtonDefaults.MinHeight
                    )
                    .indication(interactionSource, rememberRipple())
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

private val ButtonShape = RoundedCornerShape(percent = 50)

@Preview("default", "round")
@Preview("dark theme", "round", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", "round", fontScale = 2f)
@Composable
private fun ButtonPreview() {
    DeviceAppTheme {
        GeneralButton(onClick = {}) {
            Text(text = "Demo")
        }
    }
}

@Preview("default", "rectangle")
@Preview("dark theme", "rectangle", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview("large font", "rectangle", fontScale = 2f)
@Composable
private fun RectangleButtonPreview() {
    DeviceAppTheme {
        GeneralButton(
            onClick = {}, shape = RectangleShape
        ) {
            Text(text = "Demo")
        }
    }
}