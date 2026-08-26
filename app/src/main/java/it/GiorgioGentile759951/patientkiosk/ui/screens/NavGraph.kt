package it.GiorgioGentile759951.patientkiosk.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import it.GiorgioGentile759951.patientkiosk.data.model.Answer
import it.GiorgioGentile759951.patientkiosk.data.model.Question
import it.GiorgioGentile759951.patientkiosk.data.model.Questionnaire
import it.GiorgioGentile759951.patientkiosk.data.repository.QuestionnaireRepository

@Composable
fun PatientKioskApp() {

    val navController = rememberNavController()
    val context = LocalContext.current

    val repository = remember {
        QuestionnaireRepository(context)
    }

    val questionnaire = remember {
        repository.loadQuestionnaire("dlqi.json")
    }

    val questionnaires = remember {
        repository.getAvailableQuestionnaires()
    }

    var selectedQuestionnaire by remember {
        mutableStateOf<Questionnaire?>(null)
    }

    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {

        composable("welcome") {
            WelcomeScreen(
                onStartClick = {
                    navController.navigate("questionnaires")
                }
            )
        }

        composable("questionnaires") {

            QuestionnaireSelectionScreen(
                questionnaires = questionnaires,
                onQuestionnaireSelected = { questionnaire ->

                    selectedQuestionnaire = questionnaire
                    navController.navigate("question")
                }
            )
        }

        composable("questionnaire") {
            QuestionScreen(
                questionnaire = questionnaire,
                onFinished = { score ->
                    navController.navigate("result/$score")
                }
            )
        }

        composable("question") {

            selectedQuestionnaire?.let { questionnaire ->

                QuestionScreen(
                    questionnaire = questionnaire,
                    onFinished = { score ->
                        navController.navigate("result/$score")
                    }
                )
            }
        }

        composable("result/{score}") { backStackEntry ->
            val score = backStackEntry
                .arguments
                ?.getString("score")
                ?.toIntOrNull()
                ?: 0

            ResultScreen(
                score = score,
                onHomeClick = {
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