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

        val files = context.assets.list("questionnaires") ?: return emptyList()

        return files.map { file ->
            loadQuestionnaire(file)
        }
    }
}