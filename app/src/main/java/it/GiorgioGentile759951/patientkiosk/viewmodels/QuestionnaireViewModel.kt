package it.GiorgioGentile759951.patientkiosk.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import it.GiorgioGentile759951.patientkiosk.data.model.Answer
import it.GiorgioGentile759951.patientkiosk.data.model.Questionnaire

class QuestionnaireViewModel : ViewModel() {

    var questionnaire by mutableStateOf<Questionnaire?>(null)
        private set

    var currentQuestionIndex by mutableStateOf(0)
        private set

    val selectedAnswers = mutableStateListOf<Answer?>()

    var patientCode by mutableStateOf("")
        private set

    fun startQuestionnaire(questionnaire: Questionnaire): Boolean {
        if(questionnaire.questions.isEmpty())
        {
            return false
        }

        this.questionnaire = questionnaire
        currentQuestionIndex = 0

        selectedAnswers.clear()

        repeat(questionnaire.questions.size) {
            selectedAnswers.add(null)
        }

        return true;
    }

    fun selectAnswer(answer: Answer) {
        selectedAnswers[currentQuestionIndex] = answer
    }

    fun nextQuestion(): Boolean {
        val questionnaire = questionnaire ?: return false

        return if (currentQuestionIndex < questionnaire.questions.lastIndex) {
            currentQuestionIndex++
            false
        } else {
            true
        }
    }

    fun previousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--
        }
    }

    fun calculateScore(): Int {
        return selectedAnswers
            .filterNotNull()
            .sumOf { it.score }
    }

    fun getScoreInterpretation(score: Int): String {

        val questionnaire = questionnaire
            ?: return "Interpretazione non disponibile"

        return questionnaire.scoreRanges
            .firstOrNull { range ->
                score in range.min..range.max
            }
            ?.label
            ?: "Interpretazione non disponibile"
    }

    fun updatePatientCode(code: String) {
        patientCode = code
    }
}