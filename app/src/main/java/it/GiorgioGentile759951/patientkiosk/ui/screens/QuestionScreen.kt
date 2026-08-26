package it.GiorgioGentile759951.patientkiosk.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import it.GiorgioGentile759951.patientkiosk.data.model.Answer
import it.GiorgioGentile759951.patientkiosk.data.model.Question
import it.GiorgioGentile759951.patientkiosk.data.model.Questionnaire

@Composable
fun QuestionScreen(
    questionnaire: Questionnaire,
    onFinished: (Int) -> Unit
) {
    var currentQuestionIndex by remember {
        mutableStateOf(0)
    }

    val selectedAnswers = remember {
        mutableStateListOf<Answer?>().apply {
            repeat(questionnaire.questions.size) {
                add(null)
            }
        }
    }

    val currentQuestion = questionnaire.questions[currentQuestionIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = questionnaire.name,
            fontSize = 32.sp
        )

        Text(
            text = "Domanda ${currentQuestionIndex + 1} di ${questionnaire.questions.size}",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = currentQuestion.text,
            fontSize = 26.sp,
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth(0.8f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            currentQuestion.answers.forEach { answer ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = selectedAnswers[currentQuestionIndex] == answer,
                        onClick = {
                            selectedAnswers[currentQuestionIndex] = answer
                        }
                    )

                    Text(
                        text = answer.text,
                        fontSize = 22.sp
                    )
                }
            }
        }

        Row(
            modifier = Modifier.padding(top = 40.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Button(
                onClick = { currentQuestionIndex-- },
                enabled = currentQuestionIndex > 0
            ) {
                Text(
                    text = "Indietro",
                    fontSize = 20.sp
                )
            }

            Button(
                onClick = {
                    if (currentQuestionIndex < questionnaire.questions.lastIndex) {
                        currentQuestionIndex++
                    } else {
                        val totalScore = selectedAnswers
                            .filterNotNull()
                            .sumOf { it.score }

                        onFinished(totalScore)
                    }
                },
                enabled = selectedAnswers[currentQuestionIndex] != null
            ) {
                Text(
                    if (currentQuestionIndex == questionnaire.questions.lastIndex) {
                        "Termina"
                    } else {
                        "Avanti"
                    }
                )
            }
        }
    }
}