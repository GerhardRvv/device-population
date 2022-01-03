package com.example.devicepopulation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val LightColorPalette = DevicePopulationColors(
    brand = lightCard,
    brandSecondary = Ocean3,
    uiBackground = lightBackground,
    uiBorder = Neutral4,
    uiFloated = lightBackground,
    textPrimary = Neutral1,
    textSecondary = Neutral7,
    textHelp = Neutral0,
    textInteractive = Neutral0,
    textLink = Ocean11,
    iconPrimary = Neutral0,
    iconSecondary = Neutral7,
    iconInteractive = Neutral0,
    iconInteractiveInactive = Neutral1,
    error = FunctionalRed,
    online = FunctionalGreen,
    rating = gold,
    isDark = false
)

val DarkColorPalette = DevicePopulationColors(
    brand = cards,
    brandSecondary = Ocean2,
    uiBackground = FunctionalDarkGrey,
    uiBorder = Neutral3,
    uiFloated = FunctionalDarkGrey,
    textPrimary = Neutral1,
    textSecondary = Neutral0,
    textHelp = Neutral1,
    textInteractive = Neutral7,
    textLink = Ocean2,
    iconPrimary = Shadow1,
    iconSecondary = Neutral0,
    iconInteractive = Neutral7,
    iconInteractiveInactive = Neutral6,
    error = FunctionalRed,
    online = FunctionalGreen,
    rating = gold,
    isDark = true
)

@Composable
fun DeviceAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    val sysUiController = rememberSystemUiController()
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = colors.uiBackground.copy(alpha = AlphaNearOpaque)
        )
    }

    ProvideDevicePopulationColors(colors) {
        MaterialTheme(
            colors = MaterialTheme.colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object DeviceAppTheme {
    val colors: DevicePopulationColors
        @Composable
        get() = LocalDevicePopulationColors.current
}

/**
 * DevicePopulationColors custom Color Palette
 */
@Stable
class DevicePopulationColors(
    brand: Color,
    brandSecondary: Color,
    uiBackground: Color,
    uiBorder: Color,
    uiFloated: Color,
    textPrimary: Color = brand,
    textSecondary: Color,
    textHelp: Color,
    textInteractive: Color,
    textLink: Color,
    iconPrimary: Color = brand,
    iconSecondary: Color,
    iconInteractive: Color,
    iconInteractiveInactive: Color,
    error: Color,
    online: Color,
    rating: Color,
    notificationBadge: Color = error,
    isDark: Boolean
) {
    var brand by mutableStateOf(brand)
        private set
    var brandSecondary by mutableStateOf(brandSecondary)
        private set
    var uiBackground by mutableStateOf(uiBackground)
        private set
    var uiBorder by mutableStateOf(uiBorder)
        private set
    var uiFloated by mutableStateOf(uiFloated)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var textSecondary by mutableStateOf(textSecondary)
        private set
    var textHelp by mutableStateOf(textHelp)
        private set
    var textInteractive by mutableStateOf(textInteractive)
        private set
    var textLink by mutableStateOf(textLink)
        private set
    var iconPrimary by mutableStateOf(iconPrimary)
        private set
    var iconSecondary by mutableStateOf(iconSecondary)
        private set
    var iconInteractive by mutableStateOf(iconInteractive)
        private set
    var iconInteractiveInactive by mutableStateOf(iconInteractiveInactive)
        private set
    var error by mutableStateOf(error)
        private set
    var online by mutableStateOf(online)
        private set
    var rating by mutableStateOf(rating)
        private set
    var notificationBadge by mutableStateOf(notificationBadge)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun update(other: DevicePopulationColors) {
        brand = other.brand
        brandSecondary = other.brandSecondary
        uiBackground = other.uiBackground
        uiBorder = other.uiBorder
        uiFloated = other.uiFloated
        textPrimary = other.textPrimary
        textSecondary = other.textSecondary
        textHelp = other.textHelp
        textInteractive = other.textInteractive
        textLink = other.textLink
        iconPrimary = other.iconPrimary
        iconSecondary = other.iconSecondary
        iconInteractive = other.iconInteractive
        iconInteractiveInactive = other.iconInteractiveInactive
        error = other.error
        online = other.online
        rating = other.rating
        notificationBadge = other.notificationBadge
        isDark = other.isDark
    }

    fun copy(): DevicePopulationColors = DevicePopulationColors(
        brand = brand,
        brandSecondary = brandSecondary,
        uiBackground = uiBackground,
        uiBorder = uiBorder,
        uiFloated = uiFloated,
        textPrimary = textPrimary,
        textSecondary = textSecondary,
        textHelp = textHelp,
        textInteractive = textInteractive,
        textLink = textLink,
        iconPrimary = iconPrimary,
        iconSecondary = iconSecondary,
        iconInteractive = iconInteractive,
        iconInteractiveInactive = iconInteractiveInactive,
        error = error,
        online = online,
        rating = gold,
        notificationBadge = notificationBadge,
        isDark = isDark
    )
}

@Composable
fun ProvideDevicePopulationColors(
    colors: DevicePopulationColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        colors.copy()
    }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalDevicePopulationColors provides colorPalette, content = content)
}

private val LocalDevicePopulationColors = staticCompositionLocalOf<DevicePopulationColors> {
    error("Color not found")
}
