package it.GiorgioGentile759951.patientkiosk.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultScreen(
    score: Int,
    onHomeClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Questionario completato",
            fontSize = 36.sp
        )

        Text(
            text = "Punteggio totale",
            fontSize = 22.sp,
            modifier = Modifier.padding(top = 32.dp)
        )

        Text(
            text = score.toString(),
            fontSize = 56.sp,
            modifier = Modifier.padding(top = 8.dp)
        )

        Button(
            onClick = onHomeClick,
            modifier = Modifier.padding(top = 40.dp)
        ) {
            Text("Torna alla Home")
        }
    }
}