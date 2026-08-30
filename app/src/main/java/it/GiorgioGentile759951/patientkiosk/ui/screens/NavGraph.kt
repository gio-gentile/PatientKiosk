package it.GiorgioGentile759951.patientkiosk.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import it.GiorgioGentile759951.patientkiosk.data.repository.QuestionnaireRepository
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
            QuestionnaireSelectionScreen(
                questionnaires = questionnaires,
                onQuestionnaireSelected = { questionnaire ->
                    val started = questionnaireViewModel.startQuestionnaire(questionnaire)

                    if (started) {
                        navController.navigate("question")
                    }
                }
            )
        }

        composable("question") {
            QuestionScreen(
                viewModel = questionnaireViewModel,
                onFinished = { score ->
                    navController.navigate("result/$score")
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
    }
}