package it.GiorgioGentile759951.patientkiosk.data.repository

import android.content.Context
import it.GiorgioGentile759951.patientkiosk.data.model.Questionnaire
import kotlinx.serialization.json.Json

class QuestionnaireRepository(
    private val context: Context
) {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun loadQuestionnaire(fileName: String): Questionnaire {

        val jsonString = context.assets
            .open("questionnaires/$fileName")
            .bufferedReader()
            .use { it.readText() }

        return json.decodeFromString<Questionnaire>(jsonString)
    }

    fun getAvailableQuestionnaires(): List<Questionnaire> {

        val files = context.assets.list("questionnaires")
            ?: return emptyList()

        return files
            .filter { it.endsWith(".json") }
            .mapNotNull { file ->

                try {

                    val questionnaire = loadQuestionnaire(file)

                    if (isValidQuestionnaire(questionnaire)) {
                        questionnaire
                    } else {
                        null
                    }

                } catch (e: Exception) {

                    e.printStackTrace()
                    null
                }
            }
            .sortedBy { it.name }
    }

    private fun isValidQuestionnaire(
        questionnaire: Questionnaire
    ): Boolean {

        if (questionnaire.id.isBlank()) {
            return false
        }

        if (questionnaire.name.isBlank()) {
            return false
        }

        if (questionnaire.questions.isEmpty()) {
            return false
        }

        for (question in questionnaire.questions) {

            if (question.text.isBlank()) {
                return false
            }

            if (question.answers.isEmpty()) {
                return false
            }

            for (answer in question.answers) {

                if (answer.text.isBlank()) {
                    return false
                }

                if (answer.score < 0) {
                    return false
                }
            }
        }

        return true
    }
}
