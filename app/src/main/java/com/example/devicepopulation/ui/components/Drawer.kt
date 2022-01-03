package com.example.devicepopulation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.devicepopulation.R
import com.example.devicepopulation.ui.theme.DeviceAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val screens = listOf("Home", "My Devices", "Settings")

@Composable
fun DeviceAppDrawer(
    modifier: Modifier = Modifier,
    onDrawerOptionClicked: (option: String) -> Unit,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope
) {
    DevicePopulationSurface(color = DeviceAppTheme.colors.uiFloated) {
        Column(
            modifier
                .fillMaxSize()
                .padding(start = 24.dp, top = 48.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = stringResource(R.string.app_name)
            )
            for (screen in screens) {
                Spacer(Modifier.height(24.dp))
                Row(
                    Modifier.clickable {
                        onDrawerOptionClicked(screen)
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(top = 4.dp, end = 24.dp),
                        imageVector = when (screen) {
                            stringResource(id = R.string.drawer_option_home) -> Icons.Outlined.Home
                            stringResource(id = R.string.drawer_option_my_devices) -> Icons.Outlined.List
                            stringResource(id = R.string.drawer_option_my_settings) -> Icons.Outlined.Settings
                            else -> Icons.Outlined.Warning
                        },
                        tint = DeviceAppTheme.colors.textLink,
                        contentDescription = stringResource(id = R.string.drawer_option_button),
                    )
                    Text(
                        text = screen,
                        color = DeviceAppTheme.colors.textPrimary,
                        style = MaterialTheme.typography.h5
                    )
                }
                DevicePopulationDivider()
            }
        }
    }
}
