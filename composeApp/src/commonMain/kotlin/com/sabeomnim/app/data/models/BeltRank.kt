package com.sabeomnim.app.data.models

import kotlinx.serialization.Serializable

@Serializable
enum class BeltRank(
    val title: String,
    val hangul: String,
    val romanized: String,
    val gradeText: String,
    val colorHex: Long,
    val accentColorHex: Long,
    val order: Int
) {
    WHITE(
        title = "White Belt",
        hangul = "백띠",
        romanized = "Baek-tti",
        gradeText = "10th Geup",
        colorHex = 0xFFF5F5F5,
        accentColorHex = 0xFF333333,
        order = 0
    ),
    YELLOW_STRIPE(
        title = "Yellow Stripe",
        hangul = "노랑줄 백띠",
        romanized = "No-rang-jul Baek-tti",
        gradeText = "9th Geup",
        colorHex = 0xFFFFF9C4,
        accentColorHex = 0xFFFBC02D,
        order = 1
    ),
    YELLOW(
        title = "Yellow Belt",
        hangul = "노랑띠",
        romanized = "No-rang-tti",
        gradeText = "8th Geup",
        colorHex = 0xFFFFD54F,
        accentColorHex = 0xFFF57F17,
        order = 2
    ),
    GREEN_STRIPE(
        title = "Green Stripe",
        hangul = "초록줄 노랑띠",
        romanized = "Cho-rok-jul No-rang-tti",
        gradeText = "7th Geup",
        colorHex = 0xFFFFF176,
        accentColorHex = 0xFF388E3C,
        order = 3
    ),
    GREEN(
        title = "Green Belt",
        hangul = "초록띠",
        romanized = "Cho-rok-tti",
        gradeText = "6th Geup",
        colorHex = 0xFF4CAF50,
        accentColorHex = 0xFF1B5E20,
        order = 4
    ),
    BLUE_STRIPE(
        title = "Blue Stripe",
        hangul = "파란줄 초록띠",
        romanized = "Pa-ran-jul Cho-rok-tti",
        gradeText = "5th Geup",
        colorHex = 0xFF81C784,
        accentColorHex = 0xFF1976D2,
        order = 5
    ),
    BLUE(
        title = "Blue Belt",
        hangul = "파란띠",
        romanized = "Cheong-tti",
        gradeText = "4th Geup",
        colorHex = 0xFF2196F3,
        accentColorHex = 0xFF0D47A1,
        order = 6
    ),
    RED_STRIPE(
        title = "Red Stripe",
        hangul = "빨간줄 파란띠",
        romanized = "Ppal-gan-jul Cheong-tti",
        gradeText = "3rd Geup",
        colorHex = 0xFF64B5F6,
        accentColorHex = 0xFFD32F2F,
        order = 7
    ),
    RED(
        title = "Red Belt",
        hangul = "빨간띠",
        romanized = "Hong-tti",
        gradeText = "2nd Geup",
        colorHex = 0xFFE53935,
        accentColorHex = 0xFFB71C1C,
        order = 8
    ),
    BLACK_STRIPE(
        title = "Black Stripe",
        hangul = "검은줄 빨간띠",
        romanized = "Geom-eun-jul Hong-tti",
        gradeText = "1st Geup",
        colorHex = 0xFFEF5350,
        accentColorHex = 0xFF212121,
        order = 9
    ),
    BLACK(
        title = "Black Belt",
        hangul = "검은띠 / 1단",
        romanized = "Heuk-tti / Il Dan",
        gradeText = "1st Dan",
        colorHex = 0xFF212121,
        accentColorHex = 0xFFFFD700,
        order = 10
    );

    companion object {
        fun fromOrder(order: Int): BeltRank = entries.firstOrNull { it.order == order } ?: WHITE
    }
}
