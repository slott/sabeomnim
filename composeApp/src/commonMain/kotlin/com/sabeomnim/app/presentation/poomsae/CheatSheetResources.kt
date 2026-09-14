package com.sabeomnim.app.presentation.poomsae

import org.jetbrains.compose.resources.DrawableResource
import sabeomnim.composeapp.generated.resources.*

fun getTaegeukCheatSheetResource(number: Int): DrawableResource = when (number) {
    1 -> Res.drawable.taegeuk_1_cheat_sheet
    2 -> Res.drawable.taegeuk_2_cheat_sheet
    3 -> Res.drawable.taegeuk_3_cheat_sheet
    4 -> Res.drawable.taegeuk_4_cheat_sheet
    5 -> Res.drawable.taegeuk_5_cheat_sheet
    6 -> Res.drawable.taegeuk_6_cheat_sheet
    7 -> Res.drawable.taegeuk_7_cheat_sheet
    8 -> Res.drawable.taegeuk_8_cheat_sheet
    else -> Res.drawable.taegeuk_1_cheat_sheet
}
