package it.GiorgioGentile759951.patientkiosk.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GroupScoreRange(
    val group: String,
    val min: Int,
    val max: Int,
    val label: String
)