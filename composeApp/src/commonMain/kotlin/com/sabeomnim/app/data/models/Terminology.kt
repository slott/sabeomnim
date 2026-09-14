package com.sabeomnim.app.data.models

import kotlinx.serialization.Serializable

@Serializable
enum class TermCategory(val title: String, val hangul: String) {
    COMMANDS("Commands & Etiquette", "도장 구호 및 예절"),
    NUMBERS("Numbers & Counting", "숫자 세기"),
    STANCES("Stances", "서기 (Seogi)"),
    BLOCKS("Blocks", "막기 (Makgi)"),
    STRIKES("Strikes & Punches", "지르기 / 치기"),
    KICKS("Kicks", "차기 (Chagi)"),
    ANATOMY("Anatomy & Targets", "신체 부위 및 목표"),
    TITLES("Titles & Equipment", "호칭 및 용품")
}

@Serializable
data class TerminologyEntry(
    val id: String,
    val category: TermCategory,
    val hangul: String,
    val romanized: String,
    val english: String,
    val explanation: String,
    val beltRank: BeltRank,
    val audioKey: String? = null
)
