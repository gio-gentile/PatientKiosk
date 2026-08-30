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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.OutlinedButton
import it.GiorgioGentile759951.patientkiosk.data.model.GroupResult

@Composable
fun ResultScreen(
    questionnaireName: String,
    patientCode: String,
    score: Int,
    maxScore: Int,
    interpretation: String,
    groupResults: List<GroupResult>,
    onNewQuestionnaireClick: () -> Unit,
    onFinishClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 700.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Questionario completato",
                fontSize = 36.sp
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = questionnaireName,
                fontSize = 26.sp
            )

            Text(
                text = "Paziente: $patientCode",
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            if (groupResults.isEmpty()) {
                Text(
                    text = "Punteggio",
                    fontSize = 20.sp
                )

                Text(
                    text = "$score / $maxScore",
                    fontSize = 56.sp
                )

                Text(
                    text = interpretation,
                    fontSize = 24.sp
                )

            } else {

                groupResults.forEach { result ->

                    Text(
                        text = result.name,
                        fontSize = 24.sp
                    )

                    Text(
                        text = "${result.score} / ${result.maxScore}",
                        fontSize = 42.sp
                    )

                    Text(
                        text = result.interpretation,
                        fontSize = 20.sp
                    )

                    Spacer(
                        modifier = Modifier.height(24.dp)
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(48.dp)
            )

            Button(
                onClick = onNewQuestionnaireClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Compila un altro questionario",
                    fontSize = 18.sp
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            OutlinedButton(
                onClick = onFinishClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Termina sessione",
                    fontSize = 18.sp
                )
            }
        }
    }
}