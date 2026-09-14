package com.sabeomnim.app.data.models

import kotlinx.serialization.Serializable

@Serializable
enum class TermCategory(val title: String, val hangul: String) {
    GENERAL("General & Etiquette", "기본 용어 및 도장 예절"),
    DIRECTIONS("Directions & Zones", "방향 및 신체 구역"),
    STANCES("Stances (Seogi)", "서기"),
    BLOCKS("Blocks (Makgi)", "막기"),
    STRIKES("Strikes (Chigi)", "치기"),
    PUNCHES("Punches (Jireugi)", "지르기"),
    KICKS("Kicks (Chagi)", "차기"),
    ANATOMY("Anatomy & Body Parts", "신체 부위"),
    NUMBERS_NATIVE("Native Numbers (1-100)", "순우리말 숫자"),
    NUMBERS_SINO("Sino Numbers (1st-50th)", "한자어 숫자 / 서수")
}

@Serializable
data class TerminologyEntry(
    val id: String,
    val category: TermCategory,
    val hangul: String,
    val romanized: String,
    val phoneticSpelling: String? = null,
    val english: String,
    val explanation: String,
    val beltRank: BeltRank = BeltRank.WHITE,
    val audioKey: String? = null
)
