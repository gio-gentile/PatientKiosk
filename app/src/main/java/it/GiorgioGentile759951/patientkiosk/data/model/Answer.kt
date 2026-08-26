package it.GiorgioGentile759951.patientkiosk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Answer(
    val text: String,
    val score: Int
)