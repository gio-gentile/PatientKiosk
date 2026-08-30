package it.GiorgioGentile759951.patientkiosk.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.GiorgioGentile759951.patientkiosk.data.model.Questionnaire
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

@Composable
fun QuestionnaireSelectionScreen(
    questionnaires: List<Questionnaire>,
    onQuestionnaireSelected: (Questionnaire) -> Unit
) {

    if (questionnaires.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Nessun questionario disponibile",
                fontSize = 24.sp
            )
        }

        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 900.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Seleziona un questionario",
                fontSize = 36.sp
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                questionnaires.forEach { questionnaire ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onQuestionnaireSelected(questionnaire)
                            },
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 2.dp
                        )
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp)
                        ) {

                            Text(
                                text = questionnaire.name,
                                fontSize = 26.sp
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text = questionnaire.description,
                                fontSize = 18.sp
                            )

                            Spacer(
                                modifier = Modifier.height(12.dp)
                            )

                            Text(
                                text = "${questionnaire.questions.size} domande",
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}