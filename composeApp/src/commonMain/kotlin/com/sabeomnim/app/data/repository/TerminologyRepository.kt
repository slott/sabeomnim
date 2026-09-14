package com.sabeomnim.app.data.repository

import com.sabeomnim.app.data.models.BeltRank
import com.sabeomnim.app.data.models.TermCategory
import com.sabeomnim.app.data.models.TerminologyEntry

object TerminologyRepository {

    fun getAllTerms(): List<TerminologyEntry> = allTerms

    fun getTermsByCategory(category: TermCategory): List<TerminologyEntry> =
        allTerms.filter { it.category == category }

    fun getTermsByBelt(rank: BeltRank): List<TerminologyEntry> =
        allTerms.filter { it.beltRank == rank }

    fun searchTerms(query: String): List<TerminologyEntry> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return allTerms
        return allTerms.filter {
            it.hangul.contains(q) ||
            it.romanized.lowercase().contains(q) ||
            it.english.lowercase().contains(q) ||
            it.explanation.lowercase().contains(q)
        }
    }

    private val allTerms = listOf(
        // --- COMMANDS & ETIQUETTE ---
        TerminologyEntry("cmd_charyeot", TermCategory.COMMANDS, "차렷", "Charyeot", "Attention", "Stand straight with feet together, fists beside thighs, eyes looking forward.", BeltRank.WHITE),
        TerminologyEntry("cmd_gyeongnye", TermCategory.COMMANDS, "경례", "Gyeong-nye", "Bow", "Bend upper body 45 degrees forward to show respect to master and peers.", BeltRank.WHITE),
        TerminologyEntry("cmd_junbi", TermCategory.COMMANDS, "준비", "Junbi", "Ready Stance", "Step left foot out to shoulder width, slowly draw fists to solar plexus, then press down to belt level.", BeltRank.WHITE),
        TerminologyEntry("cmd_sijak", TermCategory.COMMANDS, "시작", "Sijak", "Start / Begin", "Command given by referee or instructor to initiate forms, sparring, or exercises.", BeltRank.WHITE),
        TerminologyEntry("cmd_kalyeo", TermCategory.COMMANDS, "갈려", "Kalyeo", "Break / Separate", "Referee command to pause sparring without stopping the match clock.", BeltRank.WHITE),
        TerminologyEntry("cmd_kyesok", TermCategory.COMMANDS, "계속", "Kye-sok", "Continue", "Referee command to resume action after Kalyeo.", BeltRank.WHITE),
        TerminologyEntry("cmd_guman", TermCategory.COMMANDS, "그만", "Guman", "Stop / End", "Command to stop all action and return to ready stance.", BeltRank.WHITE),
        TerminologyEntry("cmd_shwieo", TermCategory.COMMANDS, "쉬어", "Shwieo", "Relax / At Ease", "Relax posture while remaining attentive in place.", BeltRank.WHITE),
        TerminologyEntry("cmd_dwiro_dora", TermCategory.COMMANDS, "뒤로 돌아", "Dwiro Dora", "Turn Around", "Turn 180 degrees to adjust uniform or reverse direction.", BeltRank.WHITE),
        TerminologyEntry("cmd_kihap", TermCategory.COMMANDS, "기합", "Kihap", "Yell / Focus Shout", "Coordinated breath and abdominal contraction to project energy, focus power, and tighten core.", BeltRank.WHITE),

        // --- NUMBERS ---
        TerminologyEntry("num_hana", TermCategory.NUMBERS, "하나", "Hana", "One (1)", "Native Korean number 1.", BeltRank.WHITE),
        TerminologyEntry("num_dul", TermCategory.NUMBERS, "둘", "Dul", "Two (2)", "Native Korean number 2.", BeltRank.WHITE),
        TerminologyEntry("num_set", TermCategory.NUMBERS, "셋", "Set", "Three (3)", "Native Korean number 3.", BeltRank.WHITE),
        TerminologyEntry("num_net", TermCategory.NUMBERS, "넷", "Net", "Four (4)", "Native Korean number 4.", BeltRank.WHITE),
        TerminologyEntry("num_daseot", TermCategory.NUMBERS, "다섯", "Daseot", "Five (5)", "Native Korean number 5.", BeltRank.WHITE),
        TerminologyEntry("num_yeoseot", TermCategory.NUMBERS, "여섯", "Yeoseot", "Six (6)", "Native Korean number 6.", BeltRank.WHITE),
        TerminologyEntry("num_ilgop", TermCategory.NUMBERS, "일곱", "Ilgop", "Seven (7)", "Native Korean number 7.", BeltRank.WHITE),
        TerminologyEntry("num_yeodeol", TermCategory.NUMBERS, "여덟", "Yeodeol", "Eight (8)", "Native Korean number 8.", BeltRank.WHITE),
        TerminologyEntry("num_ahop", TermCategory.NUMBERS, "아홉", "Ahop", "Nine (9)", "Native Korean number 9.", BeltRank.WHITE),
        TerminologyEntry("num_yeol", TermCategory.NUMBERS, "열", "Yeol", "Ten (10)", "Native Korean number 10.", BeltRank.WHITE),
        TerminologyEntry("num_il", TermCategory.NUMBERS, "일", "Il", "First / 1 (Sino)", "Sino-Korean 1 used for ranks, e.g. 1st Geup or 1st Dan (Il Dan).", BeltRank.YELLOW),
        TerminologyEntry("num_ee", TermCategory.NUMBERS, "이", "Ee", "Second / 2 (Sino)", "Sino-Korean 2 used for Taegeuk 2 Jang or 2nd Dan (Ee Dan).", BeltRank.GREEN_STRIPE),

        // --- STANCES ---
        TerminologyEntry("stn_moa", TermCategory.STANCES, "모아서기", "Moa-seogi", "Closed / Attention Stance", "Feet touching together side by side.", BeltRank.WHITE),
        TerminologyEntry("stn_naranhi", TermCategory.STANCES, "나란히서기", "Naranhi-seogi", "Parallel Stance", "Feet parallel, exactly one shoulder-width apart.", BeltRank.WHITE),
        TerminologyEntry("stn_ap_seogi", TermCategory.STANCES, "앞서기", "Ap-seogi", "Walking Stance", "One walking step length, body weight evenly balanced 50/50.", BeltRank.WHITE),
        TerminologyEntry("stn_ap_kubi", TermCategory.STANCES, "앞굽이", "Ap-kubi", "Forward / Front Stance", "Long deep stance (3.5 foot lengths), front knee bent over ankle, rear leg straight, 65% weight on front.", BeltRank.WHITE),
        TerminologyEntry("stn_joochoom", TermCategory.STANCES, "주춤서기", "Joochoom-seogi", "Horse-riding Stance", "Two shoulder-widths wide, parallel feet, knees pushed outwards.", BeltRank.YELLOW_STRIPE),
        TerminologyEntry("stn_dwit_kubi", TermCategory.STANCES, "뒷굽이", "Dwit-kubi", "Back Stance", "L-shaped feet, 70% of weight settled on the rear bent leg, 30% on front leg.", BeltRank.GREEN_STRIPE),
        TerminologyEntry("stn_koa_seogi", TermCategory.STANCES, "꼬아서기", "Koa-seogi", "Cross Stance", "Legs crossed with one foot resting on the ball behind or in front of other foot.", BeltRank.BLUE),
        TerminologyEntry("stn_beom_seogi", TermCategory.STANCES, "범서기", "Beom-seogi", "Tiger Stance", "Very short stance, 90% weight on rear leg, front heel raised with only ball touching floor.", BeltRank.RED),
        TerminologyEntry("stn_hakdari", TermCategory.STANCES, "학다리서기", "Hakdari-seogi", "Crane Stance", "Standing on one leg while other foot rests on the inner side of the knee.", BeltRank.BLACK),

        // --- BLOCKS ---
        TerminologyEntry("blk_arae", TermCategory.BLOCKS, "아래막기", "Arae-makgi", "Low Block", "Forearm sweeps downward across front thigh to block low kicks or punches.", BeltRank.WHITE),
        TerminologyEntry("blk_momtong", TermCategory.BLOCKS, "몸통막기", "Momtong-makgi", "Middle Block", "Outer forearm deflects attacks targeting the chest or solar plexus.", BeltRank.WHITE),
        TerminologyEntry("blk_olgul", TermCategory.BLOCKS, "얼굴막기", "Olgul-makgi", "High Block", "Forearm rises at 45 degree angle above the forehead to guard the head.", BeltRank.YELLOW_STRIPE),
        TerminologyEntry("blk_hansonnal", TermCategory.BLOCKS, "한손날 바깥막기", "Hansonnal Bakkat-makgi", "Single Knife-Hand Outer Block", "Open hand knife-edge sweeps outward at chest level.", BeltRank.GREEN),
        TerminologyEntry("blk_sonnal_momtong", TermCategory.BLOCKS, "손날 몸통막기", "Sonnal Momtong-makgi", "Double Knife-Hand Middle Block", "Lead knife-hand blocks while rear knife-hand guards solar plexus.", BeltRank.BLUE_STRIPE),
        TerminologyEntry("blk_batangson", TermCategory.BLOCKS, "바탕손 몸통막기", "Batangson Momtong-makgi", "Palm Heel Middle Block", "Padded palm heel redirects incoming thrusts across the body.", BeltRank.RED_STRIPE),
        TerminologyEntry("blk_gawi", TermCategory.BLOCKS, "가위막기", "Gawi-makgi", "Scissors Block", "Simultaneous low block with one arm and middle outer block with other arm.", BeltRank.RED),
        TerminologyEntry("blk_santeul", TermCategory.BLOCKS, "외산틀막기", "Oe-santeul-makgi", "Single Mountain Block", "One arm performs high outer block while opposite arm performs low block looking sideways.", BeltRank.BLACK_STRIPE),

        // --- STRIKES & PUNCHES ---
        TerminologyEntry("strk_baro", TermCategory.STANCES, "바로지르기", "Baro-jireugi", "Straight / Matching Punch", "Punch using the hand on the same side as the forward foot in walking stance.", BeltRank.WHITE),
        TerminologyEntry("strk_bandae", TermCategory.STRIKES, "반대지르기", "Bandae-jireugi", "Reverse Punch", "Punch using the opposite hand to the lead leg with dynamic hip rotation.", BeltRank.WHITE),
        TerminologyEntry("strk_sonnal_mok", TermCategory.STRIKES, "손날 목치기", "Sonnal Mok-chigi", "Knife-Hand Neck Strike", "Inward chopping strike directed at the opponent's neck.", BeltRank.GREEN),
        TerminologyEntry("strk_pyeonson", TermCategory.STRIKES, "편손끝 찌르기", "Pyeon-son-kkeut Jjireugi", "Spear-Hand Thrust", "Fingertips thrust forward straight into solar plexus.", BeltRank.BLUE_STRIPE),
        TerminologyEntry("strk_mejumeok", TermCategory.STRIKES, "메주먹 내려치기", "Me-jumeok Naeryo-chigi", "Hammerfist Downward Strike", "Descending strike using the bottom muscular pad of the clenched fist.", BeltRank.BLUE),
        TerminologyEntry("strk_palkup", TermCategory.STRIKES, "팔굽 돌려치기", "Palkup Dollyeo-chigi", "Turning Elbow Strike", "Close-range circular elbow blow across opponent's jaw.", BeltRank.BLUE),
        TerminologyEntry("strk_deungjumeok", TermCategory.STRIKES, "등주먹 바깥치기", "Deung-jumeok Bakkat-chigi", "Backfist Outer Strike", "Snapping strike using the back knuckles to hit the face or temple.", BeltRank.RED),
        TerminologyEntry("strk_mureup", TermCategory.STRIKES, "무릎치기", "Mureup-chigi", "Knee Strike", "Driving knee upward into ribcage or solar plexus from close clinch.", BeltRank.RED),

        // --- KICKS ---
        TerminologyEntry("kick_ap", TermCategory.KICKS, "앞차기", "Ap-chagi", "Front Snap Kick", "Fold knee, thrust forward striking with ball of the foot (Ap-chuk), snap back.", BeltRank.WHITE),
        TerminologyEntry("kick_dollyo", TermCategory.KICKS, "돌려차기", "Dollyo-chagi", "Roundhouse Kick", "Pivot hips and base foot, whip shin and instep into opponent's torso or head.", BeltRank.YELLOW),
        TerminologyEntry("kick_yeop", TermCategory.KICKS, "옆차기", "Yeop-chagi", "Side Kick", "Chamber knee into chest, pivot standing foot 180°, thrust blade of foot (Bal-nal) or heel directly sideways.", BeltRank.GREEN),
        TerminologyEntry("kick_naeryeo", TermCategory.KICKS, "내려차기", "Naeryeo-chagi", "Axe / Downward Kick", "Swing leg high into air and drop heel sharply down onto clavicle or face.", BeltRank.BLUE),
        TerminologyEntry("kick_dwi", TermCategory.KICKS, "뒤차기", "Dwi-chagi", "Back Kick", "Look over shoulder, drive heel straight backward like a horse kick.", BeltRank.RED_STRIPE),
        TerminologyEntry("kick_dwihurigi", TermCategory.KICKS, "뒤후리기", "Dwi-hurigi", "Spinning Hook Kick", "360 degree spin driving heel in sweeping horizontal arc across opponent's head.", BeltRank.BLACK_STRIPE),

        // --- ANATOMY & TARGETS ---
        TerminologyEntry("ant_olgul", TermCategory.ANATOMY, "얼굴", "Olgul", "Face / High Section", "Target area above the collarbone including chin, nose, and eyes.", BeltRank.WHITE),
        TerminologyEntry("ant_momtong", TermCategory.ANATOMY, "몸통", "Momtong", "Torso / Middle Section", "Target area between collarbone and belt level (solar plexus, ribs).", BeltRank.WHITE),
        TerminologyEntry("ant_arae", TermCategory.ANATOMY, "아래", "Arae", "Low Section", "Area below the belt line.", BeltRank.WHITE),
        TerminologyEntry("ant_apchuk", TermCategory.ANATOMY, "앞축", "Ap-chuk", "Ball of the Foot", "Primary striking surface for Ap-chagi (Front Kick).", BeltRank.WHITE),
        TerminologyEntry("ant_baldeung", TermCategory.ANATOMY, "발등", "Bal-deung", "Instep of the Foot", "Top of the foot used for Dollyo-chagi (Roundhouse Kick).", BeltRank.YELLOW),
        TerminologyEntry("ant_balnal", TermCategory.ANATOMY, "발날", "Bal-nal", "Knife-Edge / Blade of Foot", "Outer blade of the foot used for Yeop-chagi (Side Kick).", BeltRank.GREEN),
        TerminologyEntry("ant_dwichuk", TermCategory.ANATOMY, "뒤축", "Dwichuk", "Heel", "Bottom surface of the heel used in Dwi-chagi and Naeryeo-chagi.", BeltRank.BLUE),
        TerminologyEntry("ant_sonnal", TermCategory.ANATOMY, "손날", "Sonnal", "Knife-Hand", "Outer fleshy blade of the open hand with fingers tightly pressed.", BeltRank.GREEN),

        // --- TITLES & EQUIPMENT ---
        TerminologyEntry("ttl_sabeomnim", TermCategory.TITLES, "사범님", "Sabeomnim", "Master / Instructor", "Honorific title for 4th Dan and above instructors.", BeltRank.WHITE),
        TerminologyEntry("ttl_kwanjangnim", TermCategory.TITLES, "관장님", "Kwanjangnim", "Grandmaster / School Director", "Head of the martial arts school / dojang.", BeltRank.WHITE),
        TerminologyEntry("ttl_sunbaenim", TermCategory.TITLES, "선배님", "Sunbaenim", "Senior Student", "Senior practitioner deserving respect and guidance.", BeltRank.WHITE),
        TerminologyEntry("ttl_dojang", TermCategory.TITLES, "도장", "Dojang", "Training Hall", "Sacred floor and gymnasium where the martial art is practiced.", BeltRank.WHITE),
        TerminologyEntry("ttl_dobok", TermCategory.TITLES, "도복", "Dobok", "Uniform", "The traditional white uniform representing purity and equality.", BeltRank.WHITE),
        TerminologyEntry("ttl_ti", TermCategory.TITLES, "띠", "Ti", "Belt", "The colored fabric wrapped around the waist representing progress and commitment.", BeltRank.WHITE),
        TerminologyEntry("ttl_kukkiwon", TermCategory.TITLES, "국기원", "Kukkiwon", "World Taekwondo Headquarters", "Official governing body in Seoul, South Korea certifying black belt Dan ranks.", BeltRank.WHITE)
    )
}
