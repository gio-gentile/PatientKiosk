package it.GiorgioGentile759951.patientkiosk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ScoreRange(
    val min: Int,
    val max: Int,
    val label: String
)