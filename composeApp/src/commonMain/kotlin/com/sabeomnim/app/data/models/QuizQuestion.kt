package com.sabeomnim.app.data.models

import kotlinx.serialization.Serializable

@Serializable
data class QuizQuestion(
    val id: String,
    val beltRank: BeltRank,
    val question: String,
    val questionDanish: String? = null,
    val koreanTerm: String? = null,
    val options: List<String>,
    val optionsDanish: List<String>? = null,
    val correctIndex: Int,
    val explanation: String,
    val explanationDanish: String? = null,
    val category: String? = null
) {
    fun localizedQuestion(lang: com.sabeomnim.app.core.i18n.AppLanguage): String =
        if (lang == com.sabeomnim.app.core.i18n.AppLanguage.DANISH && !questionDanish.isNullOrBlank()) questionDanish else question

    fun localizedOptions(lang: com.sabeomnim.app.core.i18n.AppLanguage): List<String> =
        if (lang == com.sabeomnim.app.core.i18n.AppLanguage.DANISH && optionsDanish != null && optionsDanish.size == options.size) optionsDanish else options

    fun localizedExplanation(lang: com.sabeomnim.app.core.i18n.AppLanguage): String =
        if (lang == com.sabeomnim.app.core.i18n.AppLanguage.DANISH && !explanationDanish.isNullOrBlank()) explanationDanish else explanation
}

@Serializable
data class BeltTechnique(
    val nameHangul: String,
    val nameRomanized: String,
    val nameEnglish: String,
    val nameDanish: String? = null,
    val type: String, // Stance, Block, Strike, Kick, Poomsae, Sparring, Breaking, Theory
    val typeDanish: String? = null,
    val description: String,
    val descriptionDanish: String? = null
) {
    fun localizedName(lang: com.sabeomnim.app.core.i18n.AppLanguage): String =
        if (lang == com.sabeomnim.app.core.i18n.AppLanguage.DANISH && nameDanish != null) nameDanish else nameEnglish

    fun localizedType(lang: com.sabeomnim.app.core.i18n.AppLanguage): String =
        if (lang == com.sabeomnim.app.core.i18n.AppLanguage.DANISH && typeDanish != null) typeDanish else type

    fun localizedDescription(lang: com.sabeomnim.app.core.i18n.AppLanguage): String =
        if (lang == com.sabeomnim.app.core.i18n.AppLanguage.DANISH && descriptionDanish != null) descriptionDanish else description
}

@Serializable
data class BeltCurriculum(
    val rank: BeltRank,
    val meaning: String,
    val meaningDanish: String? = null,
    val poomsaeTitle: String?,
    val poomsaeTitleDanish: String? = null,
    val poomsaeId: String?,
    val techniques: List<BeltTechnique>,
    val minimumTrainingMonths: Int
) {
    fun localizedMeaning(lang: com.sabeomnim.app.core.i18n.AppLanguage): String =
        if (lang == com.sabeomnim.app.core.i18n.AppLanguage.DANISH && meaningDanish != null) meaningDanish else meaning

    fun localizedPoomsaeTitle(lang: com.sabeomnim.app.core.i18n.AppLanguage): String? =
        if (lang == com.sabeomnim.app.core.i18n.AppLanguage.DANISH && poomsaeTitleDanish != null) poomsaeTitleDanish else poomsaeTitle
}
