package it.GiorgioGentile759951.patientkiosk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import it.GiorgioGentile759951.patientkiosk.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.ui.res.painterResource

@Composable
fun WelcomeScreen(
    onStartClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                dimensionResource(R.dimen.screen_padding_s)
            ),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    R.drawable.patientkiosk_logo
                ),
                contentDescription = stringResource(
                    R.string.app_name
                ),
                modifier = Modifier.size(
                    dimensionResource(R.dimen.welcome_logo_size)
                )
            )

            Spacer(
                modifier = Modifier.height(dimensionResource(R.dimen.spacing_lg))
            )

            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(
                    dimensionResource(R.dimen.spacing_sm)
                )
            )

            Text(
                text = stringResource(R.string.patientkiosk),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(
                    dimensionResource(R.dimen.spacing_xl)
                )
            )

            Button(
                onClick = onStartClick,
                modifier = Modifier.heightIn(
                    min = dimensionResource(
                        R.dimen.button_min_height
                    )
                )
            ) {
                Text(
                    text = stringResource(
                        R.string.start_text
                    )
                )
            }
        }
    }
}