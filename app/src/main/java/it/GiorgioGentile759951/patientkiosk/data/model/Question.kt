package it.GiorgioGentile759951.patientkiosk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val text: String,
    val answers: List<Answer>
)