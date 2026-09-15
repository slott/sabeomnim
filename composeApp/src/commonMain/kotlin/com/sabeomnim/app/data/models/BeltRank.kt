package com.sabeomnim.app.data.models

import kotlinx.serialization.Serializable

@Serializable
enum class BeltRank(
    val title: String,
    val titleDanish: String,
    val hangul: String,
    val romanized: String,
    val gradeText: String,
    val gradeTextDanish: String,
    val colorHex: Long,
    val accentColorHex: Long,
    val stripeCount: Int = 0,
    val stripeColorHex: Long? = null,
    val order: Int
) {
    WHITE(
        title = "White Belt",
        titleDanish = "Hvidt bælte",
        hangul = "백띠",
        romanized = "Baek-tti",
        gradeText = "10th Geup",
        gradeTextDanish = "10. Kup",
        colorHex = 0xFFF5F5F5,
        accentColorHex = 0xFFCCCCCC,
        stripeCount = 0,
        stripeColorHex = null,
        order = 0
    ),
    YELLOW(
        title = "Yellow Belt",
        titleDanish = "Gult bælte",
        hangul = "노랑띠",
        romanized = "No-rang-tti",
        gradeText = "9th Geup",
        gradeTextDanish = "9. Kup",
        colorHex = 0xFFFFD54F,
        accentColorHex = 0xFFF57F17,
        stripeCount = 0,
        stripeColorHex = null,
        order = 1
    ),
    ORANGE(
        title = "Orange Belt",
        titleDanish = "Orange bælte",
        hangul = "주황띠",
        romanized = "Ju-hwang-tti",
        gradeText = "8th Geup",
        gradeTextDanish = "8. Kup",
        colorHex = 0xFFFF9800,
        accentColorHex = 0xFFE65100,
        stripeCount = 0,
        stripeColorHex = null,
        order = 2
    ),
    GREEN(
        title = "Green Belt",
        titleDanish = "Grønt bælte",
        hangul = "초록띠",
        romanized = "Cho-rok-tti",
        gradeText = "7th Geup",
        gradeTextDanish = "7. Kup",
        colorHex = 0xFF4CAF50,
        accentColorHex = 0xFF1B5E20,
        stripeCount = 0,
        stripeColorHex = null,
        order = 3
    ),
    BLUE(
        title = "Blue Belt",
        titleDanish = "Blåt bælte",
        hangul = "파란띠",
        romanized = "Cheong-tti",
        gradeText = "6th Geup",
        gradeTextDanish = "6. Kup",
        colorHex = 0xFF2196F3,
        accentColorHex = 0xFF0D47A1,
        stripeCount = 0,
        stripeColorHex = null,
        order = 4
    ),
    BLUE_RED_STRIPE(
        title = "Blue Belt (Red Stripe)",
        titleDanish = "Blåt bælte (Rød snip)",
        hangul = "빨간줄 파란띠",
        romanized = "Ppal-gan-jul Cheong-tti",
        gradeText = "5th Geup",
        gradeTextDanish = "5. Kup",
        colorHex = 0xFF2196F3,
        accentColorHex = 0xFFE53935,
        stripeCount = 1,
        stripeColorHex = 0xFFE53935,
        order = 5
    ),
    RED(
        title = "Red Belt",
        titleDanish = "Rødt bælte",
        hangul = "빨간띠",
        romanized = "Hong-tti",
        gradeText = "4th Geup",
        gradeTextDanish = "4. Kup",
        colorHex = 0xFFE53935,
        accentColorHex = 0xFFB71C1C,
        stripeCount = 0,
        stripeColorHex = null,
        order = 6
    ),
    RED_BLACK_STRIPE_1(
        title = "Red Belt (1 Black Stripe)",
        titleDanish = "Rødt bælte (1 sort snip)",
        hangul = "검은 한 줄 빨간띠",
        romanized = "Il-geom-jul Hong-tti",
        gradeText = "3rd Geup",
        gradeTextDanish = "3. Kup",
        colorHex = 0xFFE53935,
        accentColorHex = 0xFF212121,
        stripeCount = 1,
        stripeColorHex = 0xFF212121,
        order = 7
    ),
    RED_BLACK_STRIPE_2(
        title = "Red Belt (2 Black Stripes)",
        titleDanish = "Rødt bælte (2 sorte snipper)",
        hangul = "검은 두 줄 빨간띠",
        romanized = "Ee-geom-jul Hong-tti",
        gradeText = "2nd Geup",
        gradeTextDanish = "2. Kup",
        colorHex = 0xFFE53935,
        accentColorHex = 0xFF212121,
        stripeCount = 2,
        stripeColorHex = 0xFF212121,
        order = 8
    ),
    RED_BLACK_STRIPE_3(
        title = "Red Belt (3 Black Stripes)",
        titleDanish = "Rødt bælte (3 sorte snipper)",
        hangul = "검은 세 줄 빨간띠",
        romanized = "Sam-geom-jul Hong-tti",
        gradeText = "1st Geup",
        gradeTextDanish = "1. Kup",
        colorHex = 0xFFE53935,
        accentColorHex = 0xFF212121,
        stripeCount = 3,
        stripeColorHex = 0xFF212121,
        order = 9
    ),
    BLACK(
        title = "Black Belt",
        titleDanish = "Sort bælte",
        hangul = "검은띠 / 1단",
        romanized = "Heuk-tti / Il Dan",
        gradeText = "1st Dan",
        gradeTextDanish = "1. Dan",
        colorHex = 0xFF212121,
        accentColorHex = 0xFFFFD700,
        stripeCount = 0,
        stripeColorHex = null,
        order = 10
    );

    fun localizedTitle(isDanish: Boolean): String = if (isDanish) titleDanish else title
    fun localizedGrade(isDanish: Boolean): String = if (isDanish) gradeTextDanish else gradeText
    fun localizedTitle(lang: com.sabeomnim.app.core.i18n.AppLanguage): String = if (lang == com.sabeomnim.app.core.i18n.AppLanguage.DANISH) titleDanish else title
    fun localizedGrade(lang: com.sabeomnim.app.core.i18n.AppLanguage): String = if (lang == com.sabeomnim.app.core.i18n.AppLanguage.DANISH) gradeTextDanish else gradeText

    companion object {
        fun fromOrder(order: Int): BeltRank = entries.firstOrNull { it.order == order } ?: WHITE
    }
}
