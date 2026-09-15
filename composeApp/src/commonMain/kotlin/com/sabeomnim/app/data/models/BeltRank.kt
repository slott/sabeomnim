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
    val stripeCount: Int = 0,
    val stripeColorHex: Long? = null,
    val order: Int
) {
    WHITE(
        title = "White Belt",
        hangul = "백띠",
        romanized = "Baek-tti",
        gradeText = "10th Geup",
        colorHex = 0xFFF5F5F5,
        accentColorHex = 0xFFCCCCCC,
        stripeCount = 0,
        stripeColorHex = null,
        order = 0
    ),
    YELLOW(
        title = "Yellow Belt",
        hangul = "노랑띠",
        romanized = "No-rang-tti",
        gradeText = "9th Geup",
        colorHex = 0xFFFFD54F,
        accentColorHex = 0xFFF57F17,
        stripeCount = 0,
        stripeColorHex = null,
        order = 1
    ),
    ORANGE(
        title = "Orange Belt",
        hangul = "주황띠",
        romanized = "Ju-hwang-tti",
        gradeText = "8th Geup",
        colorHex = 0xFFFF9800,
        accentColorHex = 0xFFE65100,
        stripeCount = 0,
        stripeColorHex = null,
        order = 2
    ),
    GREEN(
        title = "Green Belt",
        hangul = "초록띠",
        romanized = "Cho-rok-tti",
        gradeText = "7th Geup",
        colorHex = 0xFF4CAF50,
        accentColorHex = 0xFF1B5E20,
        stripeCount = 0,
        stripeColorHex = null,
        order = 3
    ),
    BLUE(
        title = "Blue Belt",
        hangul = "파란띠",
        romanized = "Cheong-tti",
        gradeText = "6th Geup",
        colorHex = 0xFF2196F3,
        accentColorHex = 0xFF0D47A1,
        stripeCount = 0,
        stripeColorHex = null,
        order = 4
    ),
    BLUE_RED_STRIPE(
        title = "Blue Belt (Red Stripe)",
        hangul = "빨간줄 파란띠",
        romanized = "Ppal-gan-jul Cheong-tti",
        gradeText = "5th Geup",
        colorHex = 0xFF2196F3,
        accentColorHex = 0xFFE53935,
        stripeCount = 1,
        stripeColorHex = 0xFFE53935,
        order = 5
    ),
    RED(
        title = "Red Belt",
        hangul = "빨간띠",
        romanized = "Hong-tti",
        gradeText = "4th Geup",
        colorHex = 0xFFE53935,
        accentColorHex = 0xFFB71C1C,
        stripeCount = 0,
        stripeColorHex = null,
        order = 6
    ),
    RED_BLACK_STRIPE_1(
        title = "Red Belt (1 Black Stripe)",
        hangul = "검은 한 줄 빨간띠",
        romanized = "Il-geom-jul Hong-tti",
        gradeText = "3rd Geup",
        colorHex = 0xFFE53935,
        accentColorHex = 0xFF212121,
        stripeCount = 1,
        stripeColorHex = 0xFF212121,
        order = 7
    ),
    RED_BLACK_STRIPE_2(
        title = "Red Belt (2 Black Stripes)",
        hangul = "검은 두 줄 빨간띠",
        romanized = "Ee-geom-jul Hong-tti",
        gradeText = "2nd Geup",
        colorHex = 0xFFE53935,
        accentColorHex = 0xFF212121,
        stripeCount = 2,
        stripeColorHex = 0xFF212121,
        order = 8
    ),
    RED_BLACK_STRIPE_3(
        title = "Red Belt (3 Black Stripes)",
        hangul = "검은 세 줄 빨간띠",
        romanized = "Sam-geom-jul Hong-tti",
        gradeText = "1st Geup",
        colorHex = 0xFFE53935,
        accentColorHex = 0xFF212121,
        stripeCount = 3,
        stripeColorHex = 0xFF212121,
        order = 9
    ),
    BLACK(
        title = "Black Belt",
        hangul = "검은띠 / 1단",
        romanized = "Heuk-tti / Il Dan",
        gradeText = "1st Dan",
        colorHex = 0xFF212121,
        accentColorHex = 0xFFFFD700,
        stripeCount = 0,
        stripeColorHex = null,
        order = 10
    );

    companion object {
        fun fromOrder(order: Int): BeltRank = entries.firstOrNull { it.order == order } ?: WHITE
    }
}
