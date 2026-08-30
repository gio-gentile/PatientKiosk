package it.GiorgioGentile759951.patientkiosk.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import it.GiorgioGentile759951.patientkiosk.data.model.Answer
import it.GiorgioGentile759951.patientkiosk.data.model.GroupResult
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

        val questionnaire = questionnaire ?: return "Interpretazione non disponibile"

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

    fun getMaximumScore(): Int {
        val questionnaire = questionnaire ?: return 0

        return questionnaire.questions.sumOf { question ->
            question.answers.maxOfOrNull { answer ->
                answer.score
            } ?: 0
        }
    }

    fun resetSession() {
        patientCode = ""
        questionnaire = null
        currentQuestionIndex = 0
        selectedAnswers.clear()
    }

    fun resetQuestionnaire() {
        questionnaire = null
        currentQuestionIndex = 0
        selectedAnswers.clear()
    }

    fun hasAnsweredCurrentQuestion(): Boolean {
        return selectedAnswers.getOrNull(currentQuestionIndex) != null
    }

    fun calculateGroupScores(): Map<String, Int> {
        val questionnaire = questionnaire ?: return emptyMap()

        val scores = mutableMapOf<String, Int>()

        questionnaire.questions.forEachIndexed { index, question ->
            val group = question.group ?: return@forEachIndexed
            val answer = selectedAnswers.getOrNull(index) ?: return@forEachIndexed

            scores[group] = (scores[group] ?: 0) + answer.score
        }

        return scores
    }

    fun getGroupInterpretation(
        group: String,
        score: Int
    ): String {

        val questionnaire = questionnaire ?: return "Interpretazione non disponibile"

        return questionnaire.groupScoreRanges
            .firstOrNull { range ->
                range.group == group &&
                        score in range.min..range.max
            }
            ?.label
            ?: "Interpretazione non disponibile"
    }

    fun getGroupResults(): List<GroupResult> {

        val questionnaire = questionnaire ?: return emptyList()

        val scores = calculateGroupScores()

        return scores.map { (group, score) ->

            val maxScore = questionnaire.questions
                .filter { it.group == group }
                .sumOf { question ->
                    question.answers.maxOfOrNull { it.score } ?: 0
                }

            val displayName = when (group) {
                "anxiety" -> "Ansia"
                "depression" -> "Depressione"
                else -> group.replaceFirstChar { it.uppercase() }
            }

            GroupResult(
                name = displayName,
                score = score,
                maxScore = maxScore,
                interpretation = getGroupInterpretation(group, score)
            )
        }
    }
}