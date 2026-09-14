package com.sabeomnim.app.data.models

import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestion(
    val id: String,
    val beltRank: BeltRank,
    val question: String,
    val koreanTerm: String? = null,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

@Serializable
data class BeltTechnique(
    val nameHangul: String,
    val nameRomanized: String,
    val nameEnglish: String,
    val type: String, // Stance, Block, Strike, Kick
    val description: String
)

@Serializable
data class BeltCurriculum(
    val rank: BeltRank,
    val meaning: String,
    val poomsaeTitle: String?,
    val poomsaeId: String?,
    val techniques: List<BeltTechnique>,
    val minimumTrainingMonths: Int
)
