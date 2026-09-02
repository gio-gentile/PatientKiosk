package it.GiorgioGentile759951.patientkiosk.ui.screens

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import it.GiorgioGentile759951.patientkiosk.R
import it.GiorgioGentile759951.patientkiosk.data.model.GroupResult
import it.GiorgioGentile759951.patientkiosk.utils.PdfExporter.exportResult
import it.GiorgioGentile759951.patientkiosk.viewmodels.QuestionnaireViewModel
import kotlinx.coroutines.selects.select
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ResultScreen(
    score: Int,
    onNewQuestionnaireClick: () -> Unit,
    onFinishClick: () -> Unit,
    viewModel: QuestionnaireViewModel
) {
    val questionnaire = viewModel.questionnaire ?: return

    val patientCode = viewModel.patientCode
    val questionnaireName = questionnaire.name

    val maxScore = viewModel.getMaximumScore()
    val interpretation = viewModel.getScoreInterpretation(score)

    val groupResults = viewModel.getGroupResults()

    val context = LocalContext.current

    val formatterForFileName = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss")
    val formatterForPDF = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    val dateForFileName = LocalDateTime.now().format(formatterForFileName)

    val pdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument(
            "application/pdf"
        )
    ) { uri ->

        if (uri != null) {

            exportResult(
                context = context,
                uri = uri,
                patientCode = viewModel.patientCode,
                questionnaire = questionnaire,
                selectedAnswers = viewModel.selectedAnswers.toList(),
                score = score,
                maxScore = viewModel.getMaximumScore(),
                interpretation = viewModel.getScoreInterpretation(score),
                groupResults = viewModel.getGroupResults()
            )
        }
    }

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
                .widthIn(max = dimensionResource(R.dimen.content_max_width3)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.questionnaire_completed),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(dimensionResource(R.dimen.spacing_md))
            )

            Text(
                text = questionnaireName,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(dimensionResource(R.dimen.spacing_sm))
            )

            Text(
                text = stringResource(R.string.patient_code, patientCode),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(
                modifier = Modifier.height(dimensionResource(R.dimen.spacing_xxl))
            )

            if (groupResults.isEmpty()) {
                Text(
                    text = stringResource(R.string.score),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "$score / $maxScore",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                if (interpretation.isNotBlank()) {
                    Text(
                        text = interpretation,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

            } else {

                groupResults.forEach { result ->

                    Text(
                        text = result.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = "${result.score} / ${result.maxScore}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    if (result.interpretation.isNotBlank()) {
                        Text(
                            text = result.interpretation,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(dimensionResource(R.dimen.spacing_lg))
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(dimensionResource(R.dimen.spacing_xl))
            )

            Button(
                onClick = {
                    pdfLauncher.launch(
                        "${questionnaireName}_${patientCode}_${dateForFileName}.pdf"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = dimensionResource(
                            R.dimen.button_min_height
                        )
                    )
            ) {
                Text(
                    text = stringResource(
                        R.string.export_pdf_button
                    )
                )

                Spacer(
                    modifier = Modifier.width(dimensionResource(R.dimen.spacing_sm))
                )

                Icon(
                    imageVector = Icons.Default.PictureAsPdf,
                    contentDescription = null
                )
            }

            Spacer(
                modifier = Modifier.height(
                    dimensionResource(
                        R.dimen.spacing_m
                    )
                )
            )

            Button(
                onClick = onNewQuestionnaireClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = dimensionResource(R.dimen.button_min_height)
                    )
            ) {
                Text(
                    text = stringResource(R.string.next_questionnaire)
                )
            }

            Spacer(
                modifier = Modifier.height(dimensionResource(R.dimen.spacing_m))
            )

            OutlinedButton(
                onClick = onFinishClick,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(
                        min = dimensionResource(R.dimen.button_min_height)
                    )
            ) {
                Text(
                    text = stringResource(R.string.end_session_text)
                )
            }
        }
    }
}