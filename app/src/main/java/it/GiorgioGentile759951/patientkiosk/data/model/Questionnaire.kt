package it.GiorgioGentile759951.patientkiosk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Questionnaire(
    val id: String,
    val name: String,
    val description: String,
    val questions: List<Question>
)