package com.sabeomnim.app.data.models

import kotlinx.serialization.Serializable

@Serializable
enum class VideoAngle {
    FRONT,
    SIDE
}

@Serializable
data class PoomsaeStep(
    val stepIndex: Int,
    val startTimeMs: Long,
    val endTimeMs: Long,
    val korean: String,
    val romanized: String,
    val english: String,
    val danish: String? = null,
    val stance: String,
    val technique: String,
    val coachingTip: String,
    val coachingTipDanish: String? = null,
    val isKihap: Boolean = false
)

@Serializable
data class Poomsae(
    val id: String,
    val number: Int,
    val nameKorean: String,
    val nameRomanized: String,
    val nameEnglish: String,
    val nameDanish: String? = null,
    val trigramSymbol: String,
    val trigramMeaning: String,
    val trigramMeaningDanish: String? = null,
    val movementCount: Int,
    val beltRank: BeltRank,
    val description: String,
    val descriptionDanish: String? = null,
    val frontVideoUrl: String,
    val sideVideoUrl: String,
    val steps: List<PoomsaeStep>
)

