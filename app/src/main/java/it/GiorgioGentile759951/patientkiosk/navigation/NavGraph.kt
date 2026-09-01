package it.GiorgioGentile759951.patientkiosk.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import it.GiorgioGentile759951.patientkiosk.ui.screens.PatientIdentificationScreen
import it.GiorgioGentile759951.patientkiosk.ui.screens.QuestionScreen
import it.GiorgioGentile759951.patientkiosk.ui.screens.QuestionnaireSelectionScreen
import it.GiorgioGentile759951.patientkiosk.ui.screens.ResultScreen
import it.GiorgioGentile759951.patientkiosk.ui.screens.WelcomeScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import it.GiorgioGentile759951.patientkiosk.R
import it.GiorgioGentile759951.patientkiosk.data.repository.QuestionnaireRepository
import it.GiorgioGentile759951.patientkiosk.ui.screens.AppTopBar
import it.GiorgioGentile759951.patientkiosk.ui.screens.InfoScreen
import it.GiorgioGentile759951.patientkiosk.viewmodels.QuestionnaireViewModel

@Composable
fun PatientKioskApp() {

    val navController = rememberNavController()
    val context = LocalContext.current

    val repository = remember {
        QuestionnaireRepository(context)
    }

    val questionnaires = remember {
        repository.getAvailableQuestionnaires()
    }

    val questionnaireViewModel: QuestionnaireViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {

        composable("welcome") {
            WelcomeScreen(
                onStartClick = {
                    navController.navigate("patient")
                }
            )
        }

        composable("patient") {
            PatientIdentificationScreen(
                onContinue = { code ->
                    questionnaireViewModel.updatePatientCode(code)
                    navController.navigate("questionnaires")
                }
            )
        }

        composable("questionnaires") {

            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                AppTopBar(
                    title = stringResource(R.string.app_name),
                    onInfoClick = {
                        navController.navigate("info")
                    }
                )

                QuestionnaireSelectionScreen(
                    questionnaires = questionnaires,
                    onQuestionnaireSelected = { questionnaire ->

                        val started =
                            questionnaireViewModel.startQuestionnaire(questionnaire)

                        if (started) {
                            navController.navigate("question")
                        }
                    }
                )
            }
        }

        composable("question") {
            QuestionScreen(
                viewModel = questionnaireViewModel,
                onFinished = { score ->
                    navController.navigate("result/$score")
                },
                onExitQuestionnaire = {
                    questionnaireViewModel.resetQuestionnaire()

                    navController.navigate("questionnaires"){
                        popUpTo("questionnaires"){
                            inclusive = false
                        }
                    }
                }
            )
        }

        composable("result/{score}") { backStackEntry ->

            val score = backStackEntry
                .arguments
                ?.getString("score")
                ?.toIntOrNull()
                ?: 0

            val groupResults = questionnaireViewModel.getGroupResults()

            ResultScreen(
                questionnaireName = questionnaireViewModel.questionnaire?.name ?: "",
                patientCode = questionnaireViewModel.patientCode,
                score = score,
                maxScore = questionnaireViewModel.getMaximumScore(),
                interpretation = questionnaireViewModel.getScoreInterpretation(score),
                groupResults = groupResults,

                onNewQuestionnaireClick = {

                    questionnaireViewModel.resetQuestionnaire()

                    navController.navigate("questionnaires") {
                        popUpTo("questionnaires") {
                            inclusive = false
                        }
                    }
                },

                onFinishClick = {
                    questionnaireViewModel.resetSession()

                    navController.navigate("welcome") {
                        popUpTo("welcome") {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable("info") {
            InfoScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}