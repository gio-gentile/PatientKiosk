package it.GiorgioGentile759951.patientkiosk.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import it.GiorgioGentile759951.patientkiosk.R

@Composable
fun AppTopBar(
    title: String,
    onInfoClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                horizontal = dimensionResource(R.dimen.screen_padding_s),
                vertical = dimensionResource(R.dimen.spacing_md)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(
                    R.drawable.patientkiosk_logo
                ),
                contentDescription = null,
                modifier = Modifier.size(dimensionResource(R.dimen.topbar_logo_size))
            )

            Spacer(
                modifier = Modifier.width(dimensionResource(R.dimen.spacing_sm))
            )

            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        IconButton(
            onClick = onInfoClick
        ) {
            Icon(
                imageVector = Icons.Default.HelpOutline,
                contentDescription = stringResource(R.string.info_title),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}