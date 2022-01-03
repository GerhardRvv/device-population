package com.example.devicepopulation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.devicepopulation.R
import com.example.devicepopulation.ui.customIcons.StarBorder
import com.example.devicepopulation.ui.theme.DeviceAppTheme

@Composable
fun RatingBar(
    rating: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        Text(
            modifier = Modifier.padding(end = 24.dp),
            text = stringResource(id = R.string.detail_header_rating),
            style = MaterialTheme.typography.subtitle2,
            color = DeviceAppTheme.colors.textPrimary
        )
        for (i in 1..5) {
            Icon(
                tint = DeviceAppTheme.colors.rating,
                contentDescription = stringResource(id = R.string.rating),
                imageVector = if (i <= rating) Icons.Default.Star else Icons.Default.StarBorder,
            )
        }
    }
    DevicePopulationDivider()
}
