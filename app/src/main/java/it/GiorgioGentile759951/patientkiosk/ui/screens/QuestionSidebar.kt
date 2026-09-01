package it.GiorgioGentile759951.patientkiosk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import it.GiorgioGentile759951.patientkiosk.viewmodels.QuestionnaireViewModel
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import it.GiorgioGentile759951.patientkiosk.R

@Composable
fun QuestionSidebar(
    viewModel: QuestionnaireViewModel
) {

    val questionnaire = viewModel.questionnaire ?: return

    val sidebarWidth = dimensionResource(R.dimen.question_sidebar_width)

    val sidebarPadding = dimensionResource(R.dimen.question_sidebar_padding)

    Column(
        modifier = Modifier
            .width(sidebarWidth)
            .fillMaxHeight()
            .background(
                MaterialTheme.colorScheme.surface
            )
            .padding(sidebarPadding)
    ) {

        Text(
            text = questionnaire.name,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = stringResource(
                R.string.questions_completed,
                viewModel.getAnsweredQuestionsCount(),
                questionnaire.questions.size
            ),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(
            modifier = Modifier.height(dimensionResource(R.dimen.spacing_lg))
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.question_sidebar_item_spacing)
            )
        ) {

            itemsIndexed(questionnaire.questions) { index, _ ->

                val isCurrent = index == viewModel.currentQuestionIndex
                val isAnswered = viewModel.selectedAnswers.getOrNull(index) != null
                val isAccessible = index <= viewModel.maxReachedQuestionIndex

                val containerColor = when {
                    isCurrent -> MaterialTheme.colorScheme.primaryContainer
                    isAnswered -> MaterialTheme.colorScheme.secondaryContainer
                    else -> MaterialTheme.colorScheme.surface
                }

                val symbol = when {
                    isCurrent -> "▶"
                    isAnswered -> "✓"
                    else -> "○"
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(if (isAccessible) 1f else 0.45f)
                        .clickable(enabled = isAccessible) { viewModel.goToQuestion(index) },
                    colors = CardDefaults.cardColors(containerColor = containerColor)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.spacing_md)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = symbol,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(
                            modifier = Modifier.width(dimensionResource(R.dimen.spacing_sm))
                        )

                        Text(
                            text = stringResource(
                                R.string.question_number,
                                index + 1
                            ),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}