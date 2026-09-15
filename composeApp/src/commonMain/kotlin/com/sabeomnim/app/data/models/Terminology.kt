package com.sabeomnim.app.data.models

import kotlinx.serialization.Serializable

@Serializable
enum class TermCategory(val title: String, val titleDanish: String, val hangul: String) {
    GENERAL("General & Etiquette", "Kommandoer & Etikette", "기본 용어 및 도장 예절"),
    DIRECTIONS("Directions & Zones", "Retninger & Zoner", "방향 및 신체 구역"),
    STANCES("Stances (Seogi)", "Stande (Seogi)", "서기"),
    BLOCKS("Blocks (Makgi)", "Blokader (Makgi)", "막기"),
    STRIKES("Strikes (Chigi)", "Slag/Håndkantslag (Chigi)", "치기"),
    PUNCHES("Punches (Jireugi)", "Stød/Slag (Jireugi)", "지르기"),
    KICKS("Kicks (Chagi)", "Spark (Chagi)", "차기"),
    ANATOMY("Anatomy & Body Parts", "Anatomi & Kropsdele", "신체 부위"),
    NUMBERS_NATIVE("Native Numbers (1-100)", "Koreanske Tal (1-100)", "순우리말 숫자"),
    NUMBERS_SINO("Sino Numbers (1st-50th)", "Sino-koreanske Tal", "한자어 숫자 / 서수")
}

@Serializable
data class TerminologyEntry(
    val id: String,
    val category: TermCategory,
    val hangul: String,
    val romanized: String,
    val phoneticSpelling: String? = null,
    val english: String,
    val danish: String? = null,
    val explanation: String,
    val danishExplanation: String? = null,
    val beltRank: BeltRank = BeltRank.WHITE,
    val audioKey: String? = null
)
