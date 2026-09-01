package it.GiorgioGentile759951.patientkiosk.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import it.GiorgioGentile759951.patientkiosk.R
import it.GiorgioGentile759951.patientkiosk.data.model.Questionnaire

@Composable
fun QuestionnaireSelectionScreen(
    questionnaires: List<Questionnaire>,
    onQuestionnaireSelected: (Questionnaire) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(dimensionResource(R.dimen.screen_padding_s)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(
                    max = dimensionResource(R.dimen.questionnaire_selection_max_width)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.questionnaire_selection_text),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(dimensionResource(R.dimen.spacing_xl))
            )

            if (questionnaires.isEmpty()) {

                Text(
                    text = stringResource(R.string.no_questionnaires_available),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

            } else {

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(
                        minSize = dimensionResource(R.dimen.questionnaire_card_min_width)
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.questionnaire_grid_spacing)
                    ),
                    verticalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.questionnaire_grid_spacing)
                    )
                ) {

                    items(questionnaires) { questionnaire ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(
                                    min = dimensionResource(R.dimen.questionnaire_card_min_height)
                                )
                                .clickable {
                                    onQuestionnaireSelected(
                                        questionnaire
                                    )
                                },

                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            ),

                            shape = RoundedCornerShape(
                                dimensionResource(R.dimen.card_corner_radius)
                            ),

                            elevation = CardDefaults.cardElevation(
                                defaultElevation = dimensionResource(R.dimen.card_elevation)
                            )
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(dimensionResource(R.dimen.spacing_lg))
                            ) {

                                Text(
                                    text = questionnaire.name,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_sm)))

                                Text(
                                    text = questionnaire.description,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Spacer(
                                    modifier = Modifier.height(dimensionResource(R.dimen.spacing_md))
                                )

                                Text(
                                    text = stringResource(
                                        R.string.questionnaire_questions_count,
                                        questionnaire.questions.size
                                    ),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}