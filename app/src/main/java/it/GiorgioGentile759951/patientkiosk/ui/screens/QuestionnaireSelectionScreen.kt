package it.GiorgioGentile759951.patientkiosk.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.GiorgioGentile759951.patientkiosk.data.model.Questionnaire
import it.GiorgioGentile759951.patientkiosk.viewmodels.QuestionnaireViewModel

@Composable
fun QuestionnaireSelectionScreen(
    patientCode: String,
    questionnaires: List<Questionnaire>,
    onQuestionnaireSelected: (Questionnaire) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (questionnaires.isEmpty()) {
            Text(
                text = "Nessun questionario disponibile",
                fontSize = 24.sp
            )

            return
        }

        Text(
            text = patientCode
        )

        Text(
            text = "Seleziona un questionario",
            fontSize = 36.sp
        )

        Column(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            questionnaires.forEach { questionnaire ->

                Button(
                    onClick = {
                        onQuestionnaireSelected(questionnaire)
                    },
                    modifier = Modifier.fillMaxWidth(0.7f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = questionnaire.name,
                            fontSize = 22.sp
                        )

                        Text(
                            text = questionnaire.description,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}