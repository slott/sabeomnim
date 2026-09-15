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
            meaningDanish = "Symboliserer uskyld og rent potentiale. Eleven har ingen forudgående kendskab til Taekwondo; det er den rene begyndelse på kampsportens vej.",
            poomsaeTitle = "Basic Movements (Kibon Dongjak)",
            poomsaeTitleDanish = "Grundlæggende bevægelser (Kibon Dongjak)",
            poomsaeId = null,
            minimumTrainingMonths = 2,
            techniques = listOf(
                BeltTechnique("모아서기 / 나란히서기", "Moa-seogi / Naranhi-seogi", "Attention & Parallel Stance", "Samlet stand / Parallelstand", "Stance", "Stand", "Foundational ready stances.", "Grundlæggende klarstande."),
                BeltTechnique("앞서기", "Ap-seogi", "Walking Stance", "Gå-stand", "Stance", "Stand", "Natural one-step length stance with weight distributed 50/50.", "Naturlig stand med ét skridts længde og vægten ligeligt fordelt 50/50."),
                BeltTechnique("앞굽이", "Ap-kubi", "Forward Stance", "Lang stand / Fremadstand", "Stance", "Stand", "Deep stance (3.5 foot lengths), 65% weight on front bent leg.", "Dyb fremadstand (3,5 fods længde), 65% af vægten på forreste bøjede ben."),
                BeltTechnique("아래막기", "Arae-makgi", "Low Block", "Lav blokade", "Block", "Blokade", "Forearm sweeps downward 2 fists above front thigh.", "Underarm svinges nedad, standser to knytnævebredder over forreste lår."),
                BeltTechnique("몸통막기", "Momtong-makgi", "Middle Block", "Midter blokade", "Block", "Blokade", "Forearm guards torso from outside to center.", "Underarm afværger mod kroppen udefra og ind til centrum."),
                BeltTechnique("몸통 바로지르기", "Momtong Baro-jireugi", "Straight Middle Punch", "Ligestød i kropshøjde", "Strike", "Stød", "Punch targeting the solar plexus.", "Stød rettet mod solar plexus med samme hånd som forreste ben."),
                BeltTechnique("앞차기", "Ap-chagi", "Front Snap Kick", "Frontspark", "Kick", "Spark", "Knee chambers high; strike with ball of the foot (Ap-chuk).", "Knæet løftes højt; rammer med fodbalden (Ap-chuk).")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.YELLOW,
            meaning = "Signifies the earth from which a plant sprouts and takes root as the foundation of Taekwondo is laid.",
            meaningDanish = "Symboliserer jorden, hvorfra en plante spirer og slår rødder, idet fundamentet for Taekwondo lægges.",
            poomsaeTitle = "Taegeuk 1 Jang (Il Jang)",
            poomsaeTitleDanish = "Taegeuk 1 Jang (Il Jang)",
            poomsaeId = "taegeuk_1",
            minimumTrainingMonths = 3,
            techniques = listOf(
                BeltTechnique("태극 1장", "Taegeuk Il Jang", "Form 1 (Keon / Heaven)", "Form 1 (Keon / Himlen)", "Poomsae", "Poomsae", "18 movements symbolizing the origin of all creation.", "18 bevægelser, der symboliserer oprindelsen til alt skabt."),
                BeltTechnique("몸통 반대지르기", "Momtong Bandae-jireugi", "Reverse Middle Punch", "Modsat stød i kropshøjde", "Strike", "Stød", "Punch delivered with opposite hand to lead leg in walking or forward stance.", "Stød udført med modsat hånd i forhold til forreste ben."),
                BeltTechnique("돌려차기 기초", "Dollyo-chagi (Basics)", "Roundhouse Kick Prep", "Cirkelspark grundform", "Kick", "Spark", "Hip rotation with instep (Bal-deung) impact surface.", "Hofterotation med vristen (Bal-deung) som træfpunkt."),
                BeltTechnique("얼굴막기", "Olgul-makgi", "High Block", "Høj blokade", "Block", "Blokade", "Forearm rises to deflect strikes angled upward above forehead.", "Underarm løftes skråt opad for at afværge angreb over pandehøjde.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.ORANGE,
            meaning = "Represents the warmth of the sun nourishing the newly sprouted seedling as it grows towards the sky.",
            meaningDanish = "Symboliserer solens varme, der nærer den nyspirede plante, mens den vokser mod himlen.",
            poomsaeTitle = "Taegeuk 2 Jang (Ee Jang)",
            poomsaeTitleDanish = "Taegeuk 2 Jang (Ee Jang)",
            poomsaeId = "taegeuk_2",
            minimumTrainingMonths = 3,
            techniques = listOf(
                BeltTechnique("태극 2장", "Taegeuk Ee Jang", "Form 2 (Tae / Lake)", "Form 2 (Tae / Søen)", "Poomsae", "Poomsae", "18 movements emphasizing internal calm and outward joy.", "18 bevægelser, der understreger indre ro og ydre glæde."),
                BeltTechnique("얼굴지르기", "Olgul-jireugi", "High Punch", "Højt stød", "Strike", "Stød", "Punch directed at the philtrum/nose level.", "Stød rettet mod næse- og ansigtshøjde."),
                BeltTechnique("몸통 안막기", "Momtong An-makgi", "Inner Middle Block", "Indadgående midter blokade", "Block", "Blokade", "Blocking inward from outside to deflect torso attacks.", "Blokade udefra og ind for at afværge angreb mod overkroppen."),
                BeltTechnique("뒷굽이 기초", "Dwit-kubi (Intro)", "Back Stance Introduction", "Sidestand grundform", "Stance", "Stand", "L-shape stance with 70% weight on rear leg.", "L-formet stand med 70% af vægten hvilende på bagerste ben.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.GREEN,
            meaning = "Represents the green plant growing vigorously and developing strength and coordination.",
            meaningDanish = "Symboliserer den grønne plante, der vokser kraftigt og udvikler styrke, balance og koordination.",
            poomsaeTitle = "Taegeuk 3 Jang (Sam Jang)",
            poomsaeTitleDanish = "Taegeuk 3 Jang (Sam Jang)",
            poomsaeId = "taegeuk_3",
            minimumTrainingMonths = 3,
            techniques = listOf(
                BeltTechnique("태극 3장", "Taegeuk Sam Jang", "Form 3 (Ri / Fire)", "Form 3 (Ri / Ild)", "Poomsae", "Poomsae", "20 movements full of enthusiasm and spirited focus.", "20 bevægelser fyldt med dynamik og intensitet."),
                BeltTechnique("손날 목치기", "Sonnal Mok-chigi", "Knife-Hand Neck Strike", "Knivhåndsslag mod hals", "Strike", "Slag", "Striking the carotid artery with open knife-hand blade.", "Slag mod halspulsåren med åben knivhånd."),
                BeltTechnique("한손날 바깥막기", "Hansonnal Bakkat-makgi", "Single Knife-Hand Outer Block", "Enkelt knivhånds udadgående blokade", "Block", "Blokade", "Deflecting middle attacks with outer knife-hand.", "Afværgelse af midterangreb med udadgående knivhånd."),
                BeltTechnique("옆차기", "Yeop-chagi", "Side Kick", "Sideværts spark", "Kick", "Spark", "Chamber to chest, pivot base foot 180°, strike with heel / foot blade.", "Knæ trækkes op mod brystet, standfoden roterer 180°, rammer med hæl/fodkant.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.BLUE,
            meaning = "Represents the blue sky towards which the plant matures into a towering tree.",
            meaningDanish = "Symboliserer den blå himmel, mod hvilken planten modnes til et højt og stærkt træ.",
            poomsaeTitle = "Taegeuk 4 Jang (Sa Jang)",
            poomsaeTitleDanish = "Taegeuk 4 Jang (Sa Jang)",
            poomsaeId = "taegeuk_4",
            minimumTrainingMonths = 3,
            techniques = listOf(
                BeltTechnique("태극 4장", "Taegeuk Sa Jang", "Form 4 (Jin / Thunder)", "Form 4 (Jin / Torden)", "Poomsae", "Poomsae", "20 movements showing dignity and explosive sudden power.", "20 bevægelser, der viser ro og eksplosiv styrke."),
                BeltTechnique("손날 몸통막기", "Sonnal Momtong-makgi", "Double Knife-Hand Middle Block", "Dobbelt knivhånds midter blokade", "Block", "Blokade", "Guarding middle section with coordinated twin open hands.", "Beskytter kroppen med koordinerede dobbelte åbne hænder."),
                BeltTechnique("편손끝 찌르기", "Pyeon-son-kkeut Jjireugi", "Spear-Hand Thrust", "Spydhåndsstød", "Strike", "Stød", "Targeting solar plexus supported with underlying palm block.", "Stød mod solar plexus understøttet af en nedadpressende håndflade."),
                BeltTechnique("제비품 목치기", "Jebipoom Mok-chigi", "Swallow Neck Strike", "Svalehals slag", "Strike", "Slag", "Simultaneous high knife-hand block and neck strike.", "Samtidig høj knivhåndsblokade og knivhåndsslag mod halsen.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.BLUE_RED_STRIPE,
            meaning = "The transition towards danger and high technique; power begins to blend with tactical versatility.",
            meaningDanish = "Overgangen mod det røde bælte (fare og avanceret teknik); rå styrke kombineres med taktisk alsidighed.",
            poomsaeTitle = "Taegeuk 5 Jang (O Jang)",
            poomsaeTitleDanish = "Taegeuk 5 Jang (O Jang)",
            poomsaeId = "taegeuk_5",
            minimumTrainingMonths = 3,
            techniques = listOf(
                BeltTechnique("태극 5장", "Taegeuk O Jang", "Form 5 (Son / Wind)", "Form 5 (Son / Vind)", "Poomsae", "Poomsae", "20 movements balancing gentle movement with stormy force.", "20 bevægelser, der balancerer blide overgange med stormfuld kraft."),
                BeltTechnique("꼬아서기", "Koa-seogi", "Cross Stance", "Krydsstand", "Stance", "Stand", "Crossing legs while moving or landing dynamically.", "Krydsede ben under dynamisk bevægelse eller landing."),
                BeltTechnique("메주먹 내려치기", "Me-jumeok Naeryo-chigi", "Hammerfist Downward Strike", "Hammerknytnæve nedadgående slag", "Strike", "Slag", "Striking downward with bottom fist base.", "Nedadgående slag med knytnævens underside."),
                BeltTechnique("팔굽 돌려치기 / 표적치기", "Palkup-chigi", "Elbow Strikes", "Albuestød", "Strike", "Stød", "Close-range elbow turning and target impacts.", "Nærkamps albuerotation og stød mod målhåndflade."),
                BeltTechnique("내려차기", "Naeryeo-chagi", "Axe Kick", "Øksespark", "Kick", "Spark", "High leg swing descending with heel onto collarbone/face.", "Højt bensving, der accelererer nedad med hælen mod kraveben/ansigt.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.RED,
            meaning = "Signifies danger. The practitioner possesses high physical technique; utmost discipline, caution, and self-control are vital.",
            meaningDanish = "Betyder fare. Eleven behersker høj teknisk kunnen; maksimal disciplin, omtanke og selvkontrol er afgørende.",
            poomsaeTitle = "Taegeuk 6 Jang (Yuk Jang)",
            poomsaeTitleDanish = "Taegeuk 6 Jang (Yuk Jang)",
            poomsaeId = "taegeuk_6",
            minimumTrainingMonths = 4,
            techniques = listOf(
                BeltTechnique("태극 6장", "Taegeuk Yuk Jang", "Form 6 (Gam / Water)", "Form 6 (Gam / Vand)", "Poomsae", "Poomsae", "19 movements flowing continuously around obstacles.", "19 bevægelser, der flyder ubesværet rundt om enhver forhindring."),
                BeltTechnique("바탕손 몸통막기", "Batangson Momtong-makgi", "Palm-Heel Middle Block", "Håndrods midter blokade", "Block", "Blokade", "Gentle redirection using the padded heel of palm.", "Blød afledning af indkommende angreb med håndroden."),
                BeltTechnique("아래 헤쳐막기", "Arae Hecheo-makgi", "Low Spreading Block", "Lav adskillelsesblokade", "Block", "Blokade", "Dual forearms spreading low to clear kicks.", "Begge underarme spredes udad i lav højde for at afværge spark."),
                BeltTechnique("뒤차기", "Dwi-chagi", "Back Kick", "Bagudspark", "Kick", "Spark", "Linear straight back mule kick driving heel into torso.", "Lineært bagudgående spark, der drives direkte ind i kroppen med hælen.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.RED_BLACK_STRIPE_1,
            meaning = "First stripe on red signifies the approach of the black belt; internal stability and immovable resolve.",
            meaningDanish = "Første snip på det røde bælte markerer det sorte bæltes nærhed; indre balance og urokkelig stabilitet.",
            poomsaeTitle = "Taegeuk 7 Jang (Chil Jang)",
            poomsaeTitleDanish = "Taegeuk 7 Jang (Chil Jang)",
            poomsaeId = "taegeuk_7",
            minimumTrainingMonths = 4,
            techniques = listOf(
                BeltTechnique("태극 7장", "Taegeuk Chil Jang", "Form 7 (Gan / Mountain)", "Form 7 (Gan / Bjerget)", "Poomsae", "Poomsae", "25 movements showing unwavering stability.", "25 bevægelser, der udstråler urokkelig stabilitet som et bjerg."),
                BeltTechnique("범서기", "Beom-seogi", "Tiger Stance", "Tigerstand", "Stance", "Stand", "Short compressed stance with front heel off floor.", "Kort komprimeret stand med forreste hæl løftet fra underlaget."),
                BeltTechnique("가위막기", "Gawi-makgi", "Scissors Block", "Sakseblokade", "Block", "Blokade", "Simultaneous low block and middle outer block.", "Samtidig lav blokade og udadgående midter blokade i saksebevægelse."),
                BeltTechnique("무릎치기", "Mureup-chigi", "Knee Strike", "Knæstød", "Strike", "Stød", "Clinching target and driving knee into ribcage.", "Fastholdelse af modstander og kraftfuldt knæstød mod ribben."),
                BeltTechnique("등주먹 바깥치기", "Deung-jumeok Bakkat-chigi", "Backfist Outer Strike", "Overside knytnæveslag", "Strike", "Slag", "Snapping backfist to jaw.", "Hurtigt snærtende slag med håndryggens knoer mod kæben.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.RED_BLACK_STRIPE_2,
            meaning = "Second stripe on red signifies mastering the ultimate geup form; pure receptivity and full ground coordination.",
            meaningDanish = "Anden snip på det røde bælte markerer mestring af den ultimative kup-form; fuld modtagelighed og perfekt kropskontrol.",
            poomsaeTitle = "Taegeuk 8 Jang (Pal Jang)",
            poomsaeTitleDanish = "Taegeuk 8 Jang (Pal Jang)",
            poomsaeId = "taegeuk_8",
            minimumTrainingMonths = 5,
            techniques = listOf(
                BeltTechnique("태극 8장", "Taegeuk Pal Jang", "Form 8 (Gon / Earth)", "Form 8 (Gon / Jorden)", "Poomsae", "Poomsae", "27 movements synthesizing all geup techniques.", "27 bevægelser, der samler og fuldender alle kup-teknikker."),
                BeltTechnique("외산틀막기", "Oe-santeul-makgi", "Single Mountain Block", "Enkelt bjergblokade", "Block", "Blokade", "High reverse block combined with lower trunk guard.", "Kombineret høj blokade og lav blokade i bjergform."),
                BeltTechnique("두발 당성 앞차기", "Du-bal Dangsang Ap-chagi", "Jumping Double Front Kick", "Flyvende dobbelt frontspark", "Kick", "Spark", "Rapid double kick delivered in mid-air.", "Hurtigt dobbelt frontspark udført i luften (først lavt, så højt)."),
                BeltTechnique("뒤후리기", "Dwi-hurigi", "Spin Hook Kick", "Bagudgående svingspark / Hælspark", "Kick", "Spark", "360° turning heel hook sweep.", "360° roterende spark med hælen som træfpunkt.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.RED_BLACK_STRIPE_3,
            meaning = "Candidate for 1st Dan (Cho Dan Bo). Complete demonstration and mastery of all fundamental techniques and 8 Taegeuk forms.",
            meaningDanish = "Kandidat til 1. Dan (Cho Dan Bo). Fuldstændig beherskelse og demonstration af alle grundteknikker samt de 8 Taegeuk-former.",
            poomsaeTitle = "Taegeuks 1-8 Comprehensive Review",
            poomsaeTitleDanish = "Samlet repetition af Taegeuk 1-8",
            poomsaeId = null,
            minimumTrainingMonths = 6,
            techniques = listOf(
                BeltTechnique("태극 1장~8장 전편 시연", "Taegeuk 1-8 Mastery", "Complete 8 Forms Review", "Mestring af Taegeuk 1-8", "Poomsae", "Poomsae", "Random selection and flawless execution of any Taegeuk form.", "Vilkaarlig udtrækning og fejlfri fremvisning af samtlige Taegeuk-former."),
                BeltTechnique("겨루기 전략 & 반격", "Gyeorugi Strategy", "Advanced Sparring", "Avanceret kamp og kontra", "Sparring", "Kamp", "Tactical footwork, cut kicks, counter-roundhouse scoring.", "Taktisk fodarbejde, stopspark og kontrateknikker."),
                BeltTechnique("격파 시험 준비", "Gyeokpa Readiness", "Board Breaking", "Gennembrydning", "Breaking", "Gennembrydning", "Turning kick and hand strike breaking tests.", "Præcision og gennembrydning af brædder med spark og håndteknikker."),
                BeltTechnique("단 심사 구술 시험", "Dan Exam Oral Prep", "Oral Exam Prep", "Mundtlig teoriprøve", "Theory", "Teori", "Kukkiwon history, belt philosophy, referee hand signals.", "Kukkiwon historie, bælteteori, dommersignaler og koreanske termer.")
            )
        ),
        BeltCurriculum(
            rank = BeltRank.BLACK,
            meaning = "Opposite of white: signifies mastery of basics, imperviousness to fear, and the true beginning of the martial journey.",
            meaningDanish = "Modsat hvid: symboliserer modenhed, mestring af grundteknikker, frygtløshed og den sande begyndelse på Taekwondo-rejsen.",
            poomsaeTitle = "Mastery of Taegeuks 1-8 + Koryo",
            poomsaeTitleDanish = "Mestring af Taegeuk 1-8 + Koryo",
            poomsaeId = null,
            minimumTrainingMonths = 12,
            techniques = listOf(
                BeltTechnique("고려 품새 준비", "Koryo Poomsae Prep", "Koryo Form Basics", "Koryo Poomsae introduktion", "Poomsae", "Poomsae", "The first Dan form honoring the ancient Goryeo dynasty.", "Den første Dan-form, opkaldt efter det historiske Goryeo-dynasti."),
                BeltTechnique("품새 1장~8장 완벽 시연", "Taegeuk 1-8 Mastery", "Complete 8 Taegeuk Exam", "Fejlfri Taegeuk 1-8 eksamen", "Poomsae", "Poomsae", "Flawless demonstration of all 8 forms under test conditions.", "Fejlfri fremvisning af alle 8 Taegeuk-former under eksamensforhold."),
                BeltTechnique("겨루기 전술", "Gyeorugi Tactics", "Competition Sparring", "Konkurrencekamp", "Sparring", "Kamp", "Electronic scoring rules, cut kicks, counter clinches.", "Regler for elektronisk scoring, stopspark og klinch-kontra."),
                BeltTechnique("격파", "Gyeokpa", "Breaking Technique", "Avanceret gennembrydning", "Breaking", "Gennembrydning", "Power & jumping aerial breaking demonstrating focus.", "Kraft- og springgennembrydning med maksimalt fokus.")
            )
        )
    )
}
