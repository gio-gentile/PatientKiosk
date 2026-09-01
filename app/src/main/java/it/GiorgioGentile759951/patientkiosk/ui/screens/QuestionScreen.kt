package it.GiorgioGentile759951.patientkiosk.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import it.GiorgioGentile759951.patientkiosk.R

@Composable
fun QuestionScreen(
    viewModel: QuestionnaireViewModel,
    onFinished: (Int) -> Unit,
    onExitQuestionnaire: () -> Unit
) {
    val questionnaire = viewModel.questionnaire ?: return

    val currentQuestionIndex = viewModel.currentQuestionIndex
    val currentQuestion = questionnaire.questions[currentQuestionIndex]

    val progress = (currentQuestionIndex + 1).toFloat() / questionnaire.questions.size.toFloat()

    var showExitDialog by remember {
        mutableStateOf(false)
    }

    var showIncompleteMessage by remember {
        mutableStateOf(false)
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val incompleteMessage = stringResource(
        R.string.incomplete_questionnaire_message,
        viewModel.selectedAnswers.count { it == null }
    )

    LaunchedEffect(showIncompleteMessage) {
        if (showIncompleteMessage) {

            snackbarHostState.showSnackbar(
                message = incompleteMessage
            )

            showIncompleteMessage = false
        }
    }

    BackHandler {
        if (viewModel.currentQuestionIndex > 0) {
            viewModel.previousQuestion()
        } else {
            showExitDialog = true;
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        innerPadding ->

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            QuestionSidebar(
                viewModel = viewModel
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.screen_padding_s)),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = dimensionResource(R.dimen.content_max_width1)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(
                            R.string.question_index_text,
                            currentQuestionIndex + 1,
                            questionnaire.questions.size
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(
                        modifier = Modifier.height(
                            dimensionResource(R.dimen.spacing_sm)
                        )
                    )

                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(
                        modifier = Modifier.height(dimensionResource(R.dimen.spacing_xxl))
                    )

                    Text(
                        text = currentQuestion.text,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(
                        modifier = Modifier.height(dimensionResource(R.dimen.spacing_xl))
                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.spacing_md))
                    ) {
                        items(currentQuestion.answers) { answer ->

                            val isSelected = viewModel.selectedAnswers[currentQuestionIndex] == answer

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.selectAnswer(answer)
                                    },
                                shape = RoundedCornerShape(dimensionResource(R.dimen.card_corner_radius)),
                                border = if (isSelected) {
                                    BorderStroke(
                                        width = dimensionResource(R.dimen.selected_card_border_width),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                } else {
                                    null
                                },
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = dimensionResource(R.dimen.card_elevation)
                                )
                            ) {

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = dimensionResource(R.dimen.card_padding_horizontal),
                                            vertical = dimensionResource(R.dimen.card_padding_vertical)
                                        ),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    RadioButton(
                                        selected = isSelected,
                                        onClick = {
                                            viewModel.selectAnswer(answer)
                                        }
                                    )

                                    Text(
                                        text = answer.text,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(start = dimensionResource(R.dimen.spacing_md)),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier.height(dimensionResource(R.dimen.spacing_lg))
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
                                text = stringResource(R.string.back_text)
                            )
                        }

                        Button(
                            onClick = {
                                if (currentQuestionIndex == questionnaire.questions.lastIndex) {
                                    if (viewModel.areAllQuestionsAnswered()) {
                                        onFinished(
                                            viewModel.calculateScore()
                                        )

                                    } else {
                                        showIncompleteMessage = true

                                        val firstUnanswered =
                                            viewModel.getFirstUnansweredQuestionIndex()

                                        if (firstUnanswered != null) {
                                            viewModel.goToQuestion(firstUnanswered)
                                        }
                                    }

                                } else {
                                    viewModel.nextQuestion()
                                }
                            },
                            enabled = viewModel.hasAnsweredCurrentQuestion()
                        ) {

                            Text(
                                text =
                                    if (currentQuestionIndex == questionnaire.questions.lastIndex) {
                                        stringResource(R.string.end_text)
                                    } else {
                                        stringResource(R.string.next_text)
                                    }
                            )
                        }
                    }
                }
            }
        }
    }

    if (showExitDialog) {

        AlertDialog(
            onDismissRequest = {
                showExitDialog = false
            },

            title = {
                Text(stringResource(R.string.leave_questionnaire_text))
            },

            text = {
                Text(
                    stringResource(R.string.warning_questionnaire_text)
                )
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                        onExitQuestionnaire()
                    }
                ) {
                    Text(stringResource(R.string.leave_text))
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        showExitDialog = false
                    }
                ) {
                    Text(stringResource(R.string.continue_text))
                }
            }
        )
    }
}