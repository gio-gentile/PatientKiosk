package it.GiorgioGentile759951.patientkiosk.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.GiorgioGentile759951.patientkiosk.viewmodels.QuestionnaireViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.LinearProgressIndicator

@Composable
fun QuestionScreen(
    viewModel: QuestionnaireViewModel,
    onFinished: (Int) -> Unit
) {
    val questionnaire = viewModel.questionnaire ?: return

    val currentQuestionIndex = viewModel.currentQuestionIndex
    val currentQuestion = questionnaire.questions[currentQuestionIndex]

    val progress = (currentQuestionIndex + 1).toFloat() / questionnaire.questions.size.toFloat()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 800.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = questionnaire.name,
                fontSize = 32.sp
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Domanda ${currentQuestionIndex + 1} di ${questionnaire.questions.size}",
                fontSize = 18.sp
            )

            LinearProgressIndicator(
                progress = {
                    progress
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            Text(
                text = currentQuestion.text,
                fontSize = 28.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                currentQuestion.answers.forEach { answer ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        RadioButton(
                            selected =
                                viewModel.selectedAnswers[currentQuestionIndex] == answer,

                            onClick = {
                                viewModel.selectAnswer(answer)
                            }
                        )

                        Text(
                            text = answer.text,
                            fontSize = 22.sp
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(40.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Button(
                    onClick = {
                        viewModel.previousQuestion()
                    },
                    enabled = currentQuestionIndex > 0
                ) {
                    Text(
                        text = "Indietro",
                        fontSize = 18.sp
                    )
                }

                Button(
                    onClick = {

                        val finished = viewModel.nextQuestion()

                        if (finished) {
                            onFinished(
                                viewModel.calculateScore()
                            )
                        }
                    },
                    enabled =
                        viewModel.selectedAnswers[currentQuestionIndex] != null
                ) {

                    Text(
                        text =
                            if (currentQuestionIndex == questionnaire.questions.lastIndex) {
                                "Termina"
                            } else {
                                "Avanti"
                            },
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}