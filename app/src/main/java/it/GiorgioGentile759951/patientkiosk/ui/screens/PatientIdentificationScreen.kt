package it.GiorgioGentile759951.patientkiosk.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PatientIdentificationScreen(
    onContinue: (String) -> Unit
) {

    var patientCode by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Identificazione paziente",
            fontSize = 36.sp
        )

        Text(
            text = "Inserisci il codice paziente",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        OutlinedTextField(
            value = patientCode,
            onValueChange = {
                patientCode = it
            },
            label = {
                Text("Codice paziente")
            },
            singleLine = true,
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(0.5f)
        )

        Button(
            onClick = {
                onContinue(patientCode.trim())
            },
            enabled = patientCode.isNotBlank(),
            modifier = Modifier.padding(top = 32.dp)
        ) {
            Text(
                text = "Continua",
                fontSize = 20.sp
            )
        }
    }
}