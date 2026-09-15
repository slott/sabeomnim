package com.sabeomnim.app.data.repository

import com.sabeomnim.app.data.models.BeltCurriculum
import com.sabeomnim.app.data.models.BeltRank
import com.sabeomnim.app.data.models.BeltTechnique

object BeltRepository {

    fun getCurriculum(rank: BeltRank): BeltCurriculum = allCurriculums.firstOrNull { it.rank == rank } ?: allCurriculums.first()

    fun getAllCurriculums(): List<BeltCurriculum> = allCurriculums

    private val allCurriculums = listOf(
        BeltCurriculum(
            rank = BeltRank.WHITE,
            meaning = "Represents innocence and pure potential. The student has no prior knowledge of Taekwondo; it is the clean beginning of the martial arts path.",
            poomsaeTitle = "Basic Movements (Kibon Dongjak)",
            poomsaeId = null,
            minimumTrainingMonths = 2,
            techniques = listOf(
                BeltTechnique("모아서기 / 나란히서기", "Moa-seogi / Naranhi-seogi", "Attention & Parallel Stance", "Stance", "Foundational ready stances."),
                BeltTechnique("앞서기", "Ap-seogi", "Walking Stance", "Stance", "Natural one-step length stance with weight distributed 50/50."),
                BeltTechnique("앞굽이", "Ap-kubi", "Forward Stance", "Stance", "Deep stance (3.5 foot lengths), 65% weight on front bent leg."),
                BeltTechnique("아래막기", "Arae-makgi", "Low Block", "Block", "Forearm sweeps downward 2 fists above front thigh."),
                BeltTechnique("몸통막기", "Momtong-makgi", "Middle Block", "Block", "Forearm guards torso from outside to center."),
                BeltTechnique("몸통 바로지르기", "Momtong Baro-jireugi", "Straight Middle Punch", "Strike", "Punch targeting the solar plexus."),
                BeltTechnique("앞차기", "Ap-chagi", "Front Snap Kick", "Kick", "Knee chambers high; strike with ball of the foot (Ap-chuk).")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.YELLOW,
            meaning = "Signifies the earth from which a plant sprouts and takes root as the foundation of Taekwondo is laid.",
            poomsaeTitle = "Taegeuk 1 Jang (Il Jang)",
            poomsaeId = "taegeuk_1",
            minimumTrainingMonths = 3,
            techniques = listOf(
                BeltTechnique("태극 1장", "Taegeuk Il Jang", "Form 1 (Keon / Heaven)", "Poomsae", "18 movements symbolizing the origin of all creation."),
                BeltTechnique("몸통 반대지르기", "Momtong Bandae-jireugi", "Reverse Middle Punch", "Strike", "Punch delivered with opposite hand to lead leg in walking or forward stance."),
                BeltTechnique("돌려차기 기초", "Dollyo-chagi (Basics)", "Roundhouse Kick Prep", "Kick", "Hip rotation with instep (Bal-deung) impact surface."),
                BeltTechnique("얼굴막기", "Olgul-makgi", "High Block", "Block", "Forearm rises to deflect strikes angled upward above forehead.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.ORANGE,
            meaning = "Represents the warmth of the sun nourishing the newly sprouted seedling as it grows towards the sky.",
            poomsaeTitle = "Taegeuk 2 Jang (Ee Jang)",
            poomsaeId = "taegeuk_2",
            minimumTrainingMonths = 3,
            techniques = listOf(
                BeltTechnique("태극 2장", "Taegeuk Ee Jang", "Form 2 (Tae / Lake)", "Poomsae", "18 movements emphasizing internal calm and outward joy."),
                BeltTechnique("얼굴지르기", "Olgul-jireugi", "High Punch", "Strike", "Punch directed at the philtrum/nose level."),
                BeltTechnique("몸통 안막기", "Momtong An-makgi", "Inner Middle Block", "Block", "Blocking inward from outside to deflect torso attacks."),
                BeltTechnique("뒷굽이 기초", "Dwit-kubi (Intro)", "Back Stance Introduction", "Stance", "L-shape stance with 70% weight on rear leg.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.GREEN,
            meaning = "Represents the green plant growing vigorously and developing strength and coordination.",
            poomsaeTitle = "Taegeuk 3 Jang (Sam Jang)",
            poomsaeId = "taegeuk_3",
            minimumTrainingMonths = 3,
            techniques = listOf(
                BeltTechnique("태극 3장", "Taegeuk Sam Jang", "Form 3 (Ri / Fire)", "Poomsae", "20 movements full of enthusiasm and spirited focus."),
                BeltTechnique("손날 목치기", "Sonnal Mok-chigi", "Knife-Hand Neck Strike", "Strike", "Striking the carotid artery with open knife-hand blade."),
                BeltTechnique("한손날 바깥막기", "Hansonnal Bakkat-makgi", "Single Knife-Hand Outer Block", "Block", "Deflecting middle attacks with outer knife-hand."),
                BeltTechnique("옆차기", "Yeop-chagi", "Side Kick", "Kick", "Chamber to chest, pivot base foot 180°, strike with heel / foot blade.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.BLUE,
            meaning = "Represents the blue sky towards which the plant matures into a towering tree.",
            poomsaeTitle = "Taegeuk 4 Jang (Sa Jang)",
            poomsaeId = "taegeuk_4",
            minimumTrainingMonths = 3,
            techniques = listOf(
                BeltTechnique("태극 4장", "Taegeuk Sa Jang", "Form 4 (Jin / Thunder)", "Poomsae", "20 movements showing dignity and explosive sudden power."),
                BeltTechnique("손날 몸통막기", "Sonnal Momtong-makgi", "Double Knife-Hand Middle Block", "Block", "Guarding middle section with coordinated twin open hands."),
                BeltTechnique("편손끝 찌르기", "Pyeon-son-kkeut Jjireugi", "Spear-Hand Thrust", "Strike", "Targeting solar plexus supported with underlying palm block."),
                BeltTechnique("제비품 목치기", "Jebipoom Mok-chigi", "Swallow Neck Strike", "Strike", "Simultaneous high knife-hand block and neck strike.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.BLUE_RED_STRIPE,
            meaning = "The transition towards danger and high technique; power begins to blend with tactical versatility.",
            poomsaeTitle = "Taegeuk 5 Jang (O Jang)",
            poomsaeId = "taegeuk_5",
            minimumTrainingMonths = 3,
            techniques = listOf(
                BeltTechnique("태극 5장", "Taegeuk O Jang", "Form 5 (Son / Wind)", "Poomsae", "20 movements balancing gentle movement with stormy force."),
                BeltTechnique("꼬아서기", "Koa-seogi", "Cross Stance", "Stance", "Crossing legs while moving or landing dynamically."),
                BeltTechnique("메주먹 내려치기", "Me-jumeok Naeryo-chigi", "Hammerfist Downward Strike", "Strike", "Striking downward with bottom fist base."),
                BeltTechnique("팔굽 돌려치기 / 표적치기", "Palkup-chigi", "Elbow Strikes", "Strike", "Close-range elbow turning and target impacts."),
                BeltTechnique("내려차기", "Naeryeo-chagi", "Axe Kick", "Kick", "High leg swing descending with heel onto collarbone/face.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.RED,
            meaning = "Signifies danger. The practitioner possesses high physical technique; utmost discipline, caution, and self-control are vital.",
            poomsaeTitle = "Taegeuk 6 Jang (Yuk Jang)",
            poomsaeId = "taegeuk_6",
            minimumTrainingMonths = 4,
            techniques = listOf(
                BeltTechnique("태극 6장", "Taegeuk Yuk Jang", "Form 6 (Gam / Water)", "Poomsae", "19 movements flowing continuously around obstacles."),
                BeltTechnique("바탕손 몸통막기", "Batangson Momtong-makgi", "Palm-Heel Middle Block", "Block", "Gentle redirection using the padded heel of palm."),
                BeltTechnique("아래 헤쳐막기", "Arae Hecheo-makgi", "Low Spreading Block", "Block", "Dual forearms spreading low to clear kicks."),
                BeltTechnique("뒤차기", "Dwi-chagi", "Back Kick", "Kick", "Linear straight back mule kick driving heel into torso.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.RED_BLACK_STRIPE_1,
            meaning = "First stripe on red signifies the approach of the black belt; internal stability and immovable resolve.",
            poomsaeTitle = "Taegeuk 7 Jang (Chil Jang)",
            poomsaeId = "taegeuk_7",
            minimumTrainingMonths = 4,
            techniques = listOf(
                BeltTechnique("태극 7장", "Taegeuk Chil Jang", "Form 7 (Gan / Mountain)", "Poomsae", "25 movements showing unwavering stability."),
                BeltTechnique("범서기", "Beom-seogi", "Tiger Stance", "Stance", "Short compressed stance with front heel off floor."),
                BeltTechnique("가위막기", "Gawi-makgi", "Scissors Block", "Block", "Simultaneous low block and middle outer block."),
                BeltTechnique("무릎치기", "Mureup-chigi", "Knee Strike", "Strike", "Clinching target and driving knee into ribcage."),
                BeltTechnique("등주먹 바깥치기", "Deung-jumeok Bakkat-chigi", "Backfist Outer Strike", "Strike", "Snapping backfist to jaw.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.RED_BLACK_STRIPE_2,
            meaning = "Second stripe on red signifies mastering the ultimate geup form; pure receptivity and full ground coordination.",
            poomsaeTitle = "Taegeuk 8 Jang (Pal Jang)",
            poomsaeId = "taegeuk_8",
            minimumTrainingMonths = 5,
            techniques = listOf(
                BeltTechnique("태극 8장", "Taegeuk Pal Jang", "Form 8 (Gon / Earth)", "Poomsae", "27 movements synthesizing all geup techniques."),
                BeltTechnique("외산틀막기", "Oe-santeul-makgi", "Single Mountain Block", "Block", "High reverse block combined with lower trunk guard."),
                BeltTechnique("두발 당성 앞차기", "Du-bal Dangsang Ap-chagi", "Jumping Double Front Kick", "Kick", "Rapid double kick delivered in mid-air."),
                BeltTechnique("뒤후리기", "Dwi-hurigi", "Spin Hook Kick", "Kick", "360° turning heel hook sweep.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.RED_BLACK_STRIPE_3,
            meaning = "Candidate for 1st Dan (Cho Dan Bo). Complete demonstration and mastery of all fundamental techniques and 8 Taegeuk forms.",
            poomsaeTitle = "Taegeuks 1-8 Comprehensive Review",
            poomsaeId = null,
            minimumTrainingMonths = 6,
            techniques = listOf(
                BeltTechnique("태극 1장~8장 전편 시연", "Taegeuk 1-8 Mastery", "Complete 8 Forms Review", "Poomsae", "Random selection and flawless execution of any Taegeuk form."),
                BeltTechnique("겨루기 전략 & 반격", "Gyeorugi Strategy", "Advanced Sparring", "Sparring", "Tactical footwork, cut kicks, counter-roundhouse scoring."),
                BeltTechnique("격파 시험 준비", "Gyeokpa Readiness", "Board Breaking", "Breaking", "Turning kick and hand strike breaking tests."),
                BeltTechnique("단 심사 구술 시험", "Dan Exam Oral Prep", "Oral Exam Prep", "Theory", "Kukkiwon history, belt philosophy, referee hand signals.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.BLACK,
            meaning = "Opposite of white: signifies mastery of basics, imperviousness to fear, and the true beginning of the martial journey.",
            poomsaeTitle = "Mastery of Taegeuks 1-8 + Koryo",
            poomsaeId = null,
            minimumTrainingMonths = 12,
            techniques = listOf(
                BeltTechnique("고려 품새 준비", "Koryo Poomsae Prep", "Koryo Form Basics", "Poomsae", "The first Dan form honoring the ancient Goryeo dynasty."),
                BeltTechnique("품새 1장~8장 완벽 시연", "Taegeuk 1-8 Mastery", "Complete 8 Taegeuk Exam", "Poomsae", "Flawless demonstration of all 8 forms under test conditions."),
                BeltTechnique("겨루기 전술", "Gyeorugi Tactics", "Competition Sparring", "Sparring", "Electronic scoring rules, cut kicks, counter clinches."),
                BeltTechnique("격파", "Gyeokpa", "Breaking Technique", "Breaking", "Power & jumping aerial breaking demonstrating focus.")
            )
        )
    )
}
