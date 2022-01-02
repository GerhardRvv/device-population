package com.example.devicepopulation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
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
                Row() {
                    Icon(
                        modifier = Modifier
                            .padding( top = 4.dp, end = 10.dp),
                            imageVector = when(screen) {
                                "Home" -> Icons.Outlined.Home
                                "My Devices" -> Icons.Outlined.List
                                "Settings" -> Icons.Outlined.Settings
                                else -> Icons.Outlined.Warning
                            },

                        tint = DeviceAppTheme.colors.textHelp,
                        contentDescription = stringResource(id = R.string.cd_menu),
                    )
                    Text(
                        text = screen,
                        color = DeviceAppTheme.colors.textPrimary,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.clickable {
                            onDrawerOptionClicked(screen)
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        }
                    )

                }
                DevicePopulationDivider()
            }
        }
    }
}

//@Preview(showSystemUi = true, showBackground = true)
//@Composable
//fun DeviceDrawerPreview() {
//    DeviceAppTheme{
//        DeviceAppDrawer(onDrawerOptionClicked = {}, scaffoldState = ScaffoldState., scope = scope)
//    }
//}