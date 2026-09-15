package com.sabeomnim.app.data.repository

import com.sabeomnim.app.data.models.BeltRank
import com.sabeomnim.app.data.models.Poomsae
import com.sabeomnim.app.data.models.PoomsaeStep

object PoomsaeRepository {

    // Default GitHub CDN base URL for video hosting
    // Can point to raw GitHub media, GitHub Releases, or a Git LFS repository
    private const val GITHUB_CDN_BASE = "https://raw.githubusercontent.com/sabeomnim/assets/main/videos"

    // Demo reliable video stream URLs for testing media playback immediately
    // These ensure Android MediaPlayer / ExoPlayer works even before custom footage is uploaded
    private const val SAMPLE_FRONT_URL = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    private const val SAMPLE_SIDE_URL = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"

    fun getAllPoomsae(): List<Poomsae> = listOf(
        poomsaeTaegeuk1,
        poomsaeTaegeuk2,
        poomsaeTaegeuk3,
        poomsaeTaegeuk4,
        poomsaeTaegeuk5,
        poomsaeTaegeuk6,
        poomsaeTaegeuk7,
        poomsaeTaegeuk8
    )

    fun getPoomsaeById(id: String): Poomsae? = getAllPoomsae().firstOrNull { it.id == id }

    fun getPoomsaeForBelt(rank: BeltRank): Poomsae? = getAllPoomsae().firstOrNull { it.beltRank == rank }

    val poomsaeTaegeuk1 = Poomsae(
        id = "taegeuk_1",
        number = 1,
        nameKorean = "태극 1장",
        nameRomanized = "Taegeuk 1 Il Jang",
        nameEnglish = "Taegeuk 1 (Heaven / Sky)",
        trigramSymbol = "☰",
        trigramMeaning = "Keon (Heaven, Creative, Pure Yang) - Origin of all creation",
        movementCount = 18,
        beltRank = BeltRank.YELLOW,
        description = "Taegeuk 1 Jang represents Keon (Heaven and Light). As heaven provides rain and sunlight for everything to grow, this form is the origin and foundation of all subsequent Poomsae.",
        frontVideoUrl = "$GITHUB_CDN_BASE/taegeuk_1_front.mp4",
        sideVideoUrl = "$GITHUB_CDN_BASE/taegeuk_1_side.mp4",
        steps = listOf(
            PoomsaeStep(1, 0, 3000, "왼 앞서기 아래막기", "Oen Ap-seogi Arae-makgi", "Turn left 90°, left walking stance, low block", "Ap-seogi", "Arae-makgi", "Keep fist one to two fist-widths above front thigh."),
            PoomsaeStep(2, 3000, 5500, "오른 앞서기 몸통 바로지르기", "Oreun Ap-seogi Momtong Baro-jireugi", "Step forward, right walking stance, middle punch", "Ap-seogi", "Momtong Baro-jireugi", "Punch directed at solar plexus height."),
            PoomsaeStep(3, 5500, 8000, "오른 앞서기 아래막기", "Oreun Ap-seogi Arae-makgi", "Turn right 180°, right walking stance, low block", "Ap-seogi", "Arae-makgi", "Pivot on balls of feet cleanly."),
            PoomsaeStep(4, 8000, 10500, "왼 앞서기 몸통 바로지르기", "Oen Ap-seogi Momtong Baro-jireugi", "Step forward, left walking stance, middle punch", "Ap-seogi", "Momtong Baro-jireugi", "Maintain upright posture."),
            PoomsaeStep(5, 10500, 13500, "왼 앞굽이 아래막기", "Oen Ap-kubi Arae-makgi", "Turn left 90° towards front, left forward stance, low block", "Ap-kubi", "Arae-makgi", "Front knee bent directly above ankle, rear leg straight."),
            PoomsaeStep(6, 13500, 16000, "왼 앞굽이 몸통 반대지르기", "Oen Ap-kubi Momtong Bandae-jireugi", "In place, middle reverse punch", "Ap-kubi", "Momtong Bandae-jireugi", "Strong hip twist without moving stance."),
            PoomsaeStep(7, 16000, 19000, "오른 앞서기 몸통 안막기", "Oreun Ap-seogi Momtong An-makgi", "Turn right 90°, right walking stance, inner middle block", "Ap-seogi", "Momtong An-makgi", "Fist at shoulder height."),
            PoomsaeStep(8, 19000, 21500, "왼 앞서기 몸통 바로지르기", "Oen Ap-seogi Momtong Baro-jireugi", "Step forward, left walking stance, middle punch", "Ap-seogi", "Momtong Baro-jireugi", "Deliver punch with continuous breath."),
            PoomsaeStep(9, 21500, 24500, "왼 앞서기 몸통 안막기", "Oen Ap-seogi Momtong An-makgi", "Turn left 180°, left walking stance, inner middle block", "Ap-seogi", "Momtong An-makgi", "Chamber across opposite hip."),
            PoomsaeStep(10, 24500, 27000, "오른 앞서기 몸통 바로지르기", "Oreun Ap-seogi Momtong Baro-jireugi", "Step forward, right walking stance, middle punch", "Ap-seogi", "Momtong Baro-jireugi", "Focus eye vision straight ahead."),
            PoomsaeStep(11, 27000, 30000, "오른 앞굽이 아래막기", "Oreun Ap-kubi Arae-makgi", "Turn right 90° towards front, right forward stance, low block", "Ap-kubi", "Arae-makgi", "Front foot pointed forward, rear foot angled 30°."),
            PoomsaeStep(12, 30000, 32500, "오른 앞굽이 몸통 반대지르기", "Oreun Ap-kubi Momtong Bandae-jireugi", "In place, middle reverse punch", "Ap-kubi", "Momtong Bandae-jireugi", "Explosive torque from the core."),
            PoomsaeStep(13, 32500, 35500, "왼 앞서기 얼굴막기", "Oen Ap-seogi Olgul-makgi", "Turn left 90°, left walking stance, high block", "Ap-seogi", "Olgul-makgi", "Forearm angled upwards, one fist above forehead."),
            PoomsaeStep(14, 35500, 38500, "오른 앞차기 + 오른 앞서기 몸통 바로지르기", "Oreun Ap-chagi + Oreun Ap-seogi Momtong Baro-jireugi", "Right front kick, land forward in right walking stance, middle punch", "Ap-seogi", "Ap-chagi + Jireugi", "Snap kick with ball of the foot (Ap-chuk)."),
            PoomsaeStep(15, 38500, 41500, "오른 앞서기 얼굴막기", "Oreun Ap-seogi Olgul-makgi", "Turn right 180°, right walking stance, high block", "Ap-seogi", "Olgul-makgi", "Keep shoulders level."),
            PoomsaeStep(16, 41500, 44500, "왼 앞차기 + 왼 앞서기 몸통 바로지르기", "Oen Ap-chagi + Oen Ap-seogi Momtong Baro-jireugi", "Left front kick, land in left walking stance, middle punch", "Ap-seogi", "Ap-chagi + Jireugi", "Chamber knee high before kicking."),
            PoomsaeStep(17, 44500, 47500, "왼 앞굽이 아래막기", "Oen Ap-kubi Arae-makgi", "Turn left 90° towards back line, left forward stance, low block", "Ap-kubi", "Arae-makgi", "Firm solid grounding."),
            PoomsaeStep(18, 47500, 52000, "오른 앞굽이 몸통 반대지르기 (기합)", "Oreun Ap-kubi Momtong Bandae-jireugi (Kihap)", "Step forward, right forward stance, middle reverse punch with loud Kihap!", "Ap-kubi", "Momtong Bandae-jireugi", "Decisive final punch with spirited shout!", isKihap = true)
        )
    )

    val poomsaeTaegeuk2 = Poomsae(
        id = "taegeuk_2",
        number = 2,
        nameKorean = "태극 2장",
        nameRomanized = "Taegeuk 2 Ee Jang",
        nameEnglish = "Taegeuk 2 (Lake / Joy)",
        trigramSymbol = "☱",
        trigramMeaning = "Tae (Lake, Joyfulness) - Inner firmness with external gentleness",
        movementCount = 18,
        beltRank = BeltRank.ORANGE,
        description = "Taegeuk 2 Jang symbolizes Tae (Lake). It teaches the practitioner that power should be exercised calmly, with inner fortitude covered by serene and controlled outward actions.",
        frontVideoUrl = "$GITHUB_CDN_BASE/taegeuk_2_front.mp4",
        sideVideoUrl = "$GITHUB_CDN_BASE/taegeuk_2_side.mp4",
        steps = listOf(
            PoomsaeStep(1, 0, 3000, "왼 앞서기 아래막기", "Oen Ap-seogi Arae-makgi", "Turn left 90°, left walking stance, low block", "Ap-seogi", "Arae-makgi", "Clear downward block trajectory."),
            PoomsaeStep(2, 3000, 5500, "오른 앞굽이 몸통 반대지르기", "Oreun Ap-kubi Momtong Bandae-jireugi", "Step forward into right forward stance, middle reverse punch", "Ap-kubi", "Momtong Bandae-jireugi", "Deep stance with solid base."),
            PoomsaeStep(3, 5500, 8000, "오른 앞서기 아래막기", "Oreun Ap-seogi Arae-makgi", "Turn right 180°, right walking stance, low block", "Ap-seogi", "Arae-makgi", "Smooth pivot."),
            PoomsaeStep(4, 8000, 10500, "왼 앞굽이 몸통 반대지르기", "Oen Ap-kubi Momtong Bandae-jireugi", "Step forward into left forward stance, middle reverse punch", "Ap-kubi", "Momtong Bandae-jireugi", "Lock rear heel down."),
            PoomsaeStep(5, 10500, 13500, "왼 앞서기 몸통 안막기", "Oen Ap-seogi Momtong An-makgi", "Turn left 90°, left walking stance, inner middle block", "Ap-seogi", "Momtong An-makgi", "Block from outside inward."),
            PoomsaeStep(6, 13500, 16000, "오른 앞서기 몸통 안막기", "Oreun Ap-seogi Momtong An-makgi", "Step forward, right walking stance, inner middle block", "Ap-seogi", "Momtong An-makgi", "Keep wrist straight."),
            PoomsaeStep(18, 47000, 52000, "왼 앞서기 몸통 바로지르기 (기합)", "Oen Ap-seogi Momtong Baro-jireugi (Kihap)", "Step forward, left walking stance, middle punch with Kihap!", "Ap-seogi", "Momtong Baro-jireugi", "Finish strong with clear yell.", isKihap = true)
        )
    )

    val poomsaeTaegeuk3 = Poomsae(
        id = "taegeuk_3",
        number = 3,
        nameKorean = "태극 3장",
        nameRomanized = "Taegeuk 3 Sam Jang",
        nameEnglish = "Taegeuk 3 (Fire / Sun)",
        trigramSymbol = "☲",
        trigramMeaning = "Ri (Fire, Sun) - Passion, brightness, and enthusiastic energy",
        movementCount = 20,
        beltRank = BeltRank.GREEN,
        description = "Taegeuk 3 Jang represents Ri (Fire). Fire produces warmth and light, but requires precise control to avoid destruction. Introduces Sonnal mok-chigi and Hansonnal bakkat-makgi.",
        frontVideoUrl = "$GITHUB_CDN_BASE/taegeuk_3_front.mp4",
        sideVideoUrl = "$GITHUB_CDN_BASE/taegeuk_3_side.mp4",
        steps = listOf(
            PoomsaeStep(1, 0, 3000, "왼 앞서기 아래막기", "Oen Ap-seogi Arae-makgi", "Turn left 90°, left walking stance, low block", "Ap-seogi", "Arae-makgi", "Sharp snap."),
            PoomsaeStep(2, 3000, 6000, "오른 앞차기 + 오른 앞굽이 두번지르기", "Oreun Ap-chagi + Oreun Ap-kubi Dubeon-jireugi", "Right front kick, right forward stance, double punch", "Ap-kubi", "Ap-chagi + Dubeon-jireugi", "Two punches executed in rapid cadence."),
            PoomsaeStep(20, 50000, 55000, "왼 앞굽이 두번지르기 (기합)", "Oen Ap-kubi Dubeon-jireugi (Kihap)", "Left front kick, land in left forward stance, double punch with Kihap!", "Ap-kubi", "Dubeon-jireugi", "Final spirited finish.", isKihap = true)
        )
    )

    val poomsaeTaegeuk4 = Poomsae(
        id = "taegeuk_4",
        number = 4,
        nameKorean = "태극 4장",
        nameRomanized = "Taegeuk 4 Sa Jang",
        nameEnglish = "Taegeuk 4 (Thunder)",
        trigramSymbol = "☳",
        trigramMeaning = "Jin (Thunder) - Great power and dignity",
        movementCount = 20,
        beltRank = BeltRank.BLUE,
        description = "Taegeuk 4 Jang represents Jin (Thunder), which commands awe and respect. Demonstrates calm poise before exploding with sudden, decisive force. Features Dwit-kubi and Sonnal-makgi.",
        frontVideoUrl = "$GITHUB_CDN_BASE/taegeuk_4_front.mp4",
        sideVideoUrl = "$GITHUB_CDN_BASE/taegeuk_4_side.mp4",
        steps = listOf(
            PoomsaeStep(1, 0, 3500, "오른 뒷굽이 손날 몸통막기", "Oreun Dwit-kubi Sonnal Momtong-makgi", "Turn left 90°, right back stance, double knife-hand block", "Dwit-kubi", "Sonnal Momtong-makgi", "70% weight on rear leg, fingertips at shoulder height."),
            PoomsaeStep(2, 3500, 6500, "오른 앞굽이 편손끝 찌르기", "Oreun Ap-kubi Pyeon-son-kkeut Jjireugi", "Step forward into right forward stance, spear hand thrust", "Ap-kubi", "Pyeon-son-kkeut Jjireugi", "Supported by downward pressing palm."),
            PoomsaeStep(20, 51000, 56000, "오른 앞굽이 몸통 반대지르기 (기합)", "Oreun Ap-kubi Momtong Bandae-jireugi (Kihap)", "Middle block followed by reverse punch with Kihap!", "Ap-kubi", "Momtong Bandae-jireugi", "Dynamic finish.", isKihap = true)
        )
    )

    val poomsaeTaegeuk5 = Poomsae(
        id = "taegeuk_5",
        number = 5,
        nameKorean = "태극 5장",
        nameRomanized = "Taegeuk 5 O Jang",
        nameEnglish = "Taegeuk 5 (Wind)",
        trigramSymbol = "☴",
        trigramMeaning = "Son (Wind) - Flexible and gentle, yet devastatingly powerful",
        movementCount = 20,
        beltRank = BeltRank.BLUE_RED_STRIPE,
        description = "Taegeuk 5 Jang represents Son (Wind). Like wind transitioning from a soothing breeze to a hurricane, movements alternate between calm transitions and devastating strikes. Features Me-jumeok and Palkup-chigi.",
        frontVideoUrl = "$GITHUB_CDN_BASE/taegeuk_5_front.mp4",
        sideVideoUrl = "$GITHUB_CDN_BASE/taegeuk_5_side.mp4",
        steps = listOf(
            PoomsaeStep(1, 0, 3000, "왼 앞굽이 아래막기", "Oen Ap-kubi Arae-makgi", "Turn left 90°, left forward stance, low block", "Ap-kubi", "Arae-makgi", "Strong opening forward stance."),
            PoomsaeStep(2, 3000, 6000, "왼 앞서기 메주먹 내려치기", "Oen Ap-seogi Me-jumeok Naeryo-chigi", "Pull left foot to walking stance, hammerfist downward strike", "Ap-seogi", "Me-jumeok Naeryo-chigi", "Strike with bottom padded edge of fist."),
            PoomsaeStep(20, 48000, 53000, "오른 앞굽이 팔굽 표적치기 (기합)", "Oreun Ap-kubi Palkup Pyojeok-chigi (Kihap)", "Jump to cross stance, step out to right forward stance, elbow target strike with Kihap!", "Ap-kubi", "Palkup Pyojeok-chigi", "Target hand grasps incoming strike as elbow drives forward.", isKihap = true)
        )
    )

    val poomsaeTaegeuk6 = Poomsae(
        id = "taegeuk_6",
        number = 6,
        nameKorean = "태극 6장",
        nameRomanized = "Taegeuk 6 Yuk Jang",
        nameEnglish = "Taegeuk 6 (Water)",
        trigramSymbol = "☵",
        trigramMeaning = "Gam (Water) - Continuous flow, overcoming obstacles by adaptability",
        movementCount = 19,
        beltRank = BeltRank.RED,
        description = "Taegeuk 6 Jang represents Gam (Water). Water always flows downhill, navigates around any stone, and erodes cliffs through persistence. Introduces Dwi-chagi, Batangson-makgi, and Hecheo-makgi.",
        frontVideoUrl = "$GITHUB_CDN_BASE/taegeuk_6_front.mp4",
        sideVideoUrl = "$GITHUB_CDN_BASE/taegeuk_6_side.mp4",
        steps = listOf(
            PoomsaeStep(1, 0, 3500, "왼 앞굽이 아래막기", "Oen Ap-kubi Arae-makgi", "Turn left 90°, left forward stance, low block", "Ap-kubi", "Arae-makgi", "Steady flow into opening move."),
            PoomsaeStep(2, 3500, 6500, "오른 앞차기 + 오른 뒷굽이 바탕손 몸통막기", "Oreun Ap-chagi + Oreun Dwit-kubi Batangson Momtong-makgi", "Right front kick, land back into back stance with palm heel block", "Dwit-kubi", "Batangson Momtong-makgi", "Deflect with palm heel."),
            PoomsaeStep(19, 46000, 51000, "오른 앞굽이 반대지르기 (기합)", "Oreun Ap-kubi Bandae-jireugi (Kihap)", "Low block followed by punch with Kihap!", "Ap-kubi", "Bandae-jireugi", "Continuous fluid motion.", isKihap = true)
        )
    )

    val poomsaeTaegeuk7 = Poomsae(
        id = "taegeuk_7",
        number = 7,
        nameKorean = "태극 7장",
        nameRomanized = "Taegeuk 7 Chil Jang",
        nameEnglish = "Taegeuk 7 (Mountain)",
        trigramSymbol = "☶",
        trigramMeaning = "Gan (Mountain) - Stability, immobility, and steadfast grandeur",
        movementCount = 25,
        beltRank = BeltRank.RED_BLACK_STRIPE_1,
        description = "Taegeuk 7 Jang represents Gan (Mountain). A mountain stands majestic and motionless, knowing when to stay grounded and when to stand tall. Features Beom-seogi (Tiger stance) and Gawi-makgi (Scissors block).",
        frontVideoUrl = "$GITHUB_CDN_BASE/taegeuk_7_front.mp4",
        sideVideoUrl = "$GITHUB_CDN_BASE/taegeuk_7_side.mp4",
        steps = listOf(
            PoomsaeStep(1, 0, 3500, "왼 범서기 바탕손 몸통 안막기", "Oen Beom-seogi Batangson Momtong An-makgi", "Turn left 90°, left tiger stance, palm heel inner block", "Beom-seogi", "Batangson An-makgi", "90% weight on rear leg, front heel raised."),
            PoomsaeStep(2, 3500, 6500, "오른 앞차기 + 왼 범서기 바탕손 몸통 안막기", "Oreun Ap-chagi + Oen Beom-seogi Batangson Momtong An-makgi", "Front kick, return foot to tiger stance with palm heel block", "Beom-seogi", "Batangson An-makgi", "Do not collapse stance upon landing."),
            PoomsaeStep(25, 52000, 58000, "오른 주춤서기 옆지르기 (기합)", "Oreun Joochoom-seogi Yeop-jireugi (Kihap)", "Horse stance side punch with thunderous Kihap!", "Joochoom-seogi", "Yeop-jireugi", "Firm stance like a mountain.", isKihap = true)
        )
    )

    val poomsaeTaegeuk8 = Poomsae(
        id = "taegeuk_8",
        number = 8,
        nameKorean = "태극 8장",
        nameRomanized = "Taegeuk 8 Pal Jang",
        nameEnglish = "Taegeuk 8 (Earth)",
        trigramSymbol = "☷",
        trigramMeaning = "Gon (Earth, Pure Yin) - Ultimate receptivity, mother of life, culmination of all geup ranks",
        movementCount = 27,
        beltRank = BeltRank.RED_BLACK_STRIPE_2,
        description = "Taegeuk 8 Jang represents Gon (Earth). Earth receives heaven's grace and nurtures all life to full fruition. As the final Taegeuk form before Black Belt, it synthesizes all fundamental techniques including Oe-santeul-makgi and Du-bal dangsang-chagi.",
        frontVideoUrl = "$GITHUB_CDN_BASE/taegeuk_8_front.mp4",
        sideVideoUrl = "$GITHUB_CDN_BASE/taegeuk_8_side.mp4",
        steps = listOf(
            PoomsaeStep(1, 0, 3500, "오른 뒷굽이 손날 몸통막기", "Oreun Dwit-kubi Sonnal Momtong-makgi", "Turn left 90°, right back stance, double knife-hand block", "Dwit-kubi", "Sonnal Momtong-makgi", "Crisp open-hand chambering."),
            PoomsaeStep(2, 3500, 6500, "오른 앞굽이 몸통 반대지르기", "Oreun Ap-kubi Momtong Bandae-jireugi", "Step forward into right forward stance, middle reverse punch", "Ap-kubi", "Momtong Bandae-jireugi", "Deep forward drive."),
            PoomsaeStep(3, 6500, 10500, "두발 당성 앞차기 (기합)", "Du-bal Dangsang Ap-chagi (Kihap)", "Jumping double front kick with first low and second high with Kihap!", "Air", "Du-bal Dangsang-chagi", "Rapid two-tempo kick in air.", isKihap = true),
            PoomsaeStep(27, 56000, 62000, "오른 앞굽이 몸통 반대지르기 (기합)", "Oreun Ap-kubi Momtong Bandae-jireugi (Kihap)", "Step forward into right forward stance, decisive reverse punch with final Dan-qualifying Kihap!", "Ap-kubi", "Momtong Bandae-jireugi", "Hold final stance firmly with unwavering composure.", isKihap = true)
        )
    )
}
