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
            (it.phoneticSpelling?.lowercase()?.contains(q) == true) ||
            it.english.lowercase().contains(q) ||
            it.explanation.lowercase().contains(q)
        }
    }

    private val allTerms = listOf(
        TerminologyEntry(
            id = "term_1_gen_taekwond",
            category = TermCategory.GENERAL,
            hangul = "태권도",
            romanized = "Taekwondo",
            phoneticSpelling = "Foot-Hand-Art/Mind",
            english = "Taekwondo",
            explanation = "The way of the foot and the fist; Korean martial art developed through centuries of tradition.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_2_gen_studio_o",
            category = TermCategory.GENERAL,
            hangul = "도장",
            romanized = "Dojang",
            phoneticSpelling = "Do Jang",
            english = "Studio or training facility",
            explanation = "Martial arts training hall; a place of discipline and respect.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_3_gen_uniform",
            category = TermCategory.GENERAL,
            hangul = "도복",
            romanized = "Dobok",
            phoneticSpelling = "Do Boak or Dobok",
            english = "Uniform",
            explanation = "White martial arts training uniform representing purity and beginning of practice.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_4_gen_belt",
            category = TermCategory.GENERAL,
            hangul = "띠",
            romanized = "Dti (Dhee)",
            phoneticSpelling = "Dhee",
            english = "Belt",
            explanation = "Colored or black belt tied around the waist indicating rank and dedication.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_5_gen_grade,ra",
            category = TermCategory.GENERAL,
            hangul = "급",
            romanized = "Geup",
            phoneticSpelling = "Geup",
            english = "Grade,Rank or Color Belt",
            explanation = "Student grade level, starting from 10th Geup (White) progressing to 1st Geup (Red/Black).",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_6_gen_degree_",
            category = TermCategory.GENERAL,
            hangul = "단",
            romanized = "Dan",
            phoneticSpelling = "Dan",
            english = "Degree (Black Belt)",
            explanation = "Degree holder rank for black belts, from 1st Dan (Cho Dan) to 9th Dan.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_7_gen_flag",
            category = TermCategory.GENERAL,
            hangul = "국기",
            romanized = "Gukgi (Kook Ki)",
            phoneticSpelling = "Kook Ki",
            english = "Flag",
            explanation = "National flag (Taegeukgi), saluted at the beginning and close of each training session.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_8_gen_bow",
            category = TermCategory.GENERAL,
            hangul = "경례",
            romanized = "Gyeongnye (Kyung Neh)",
            phoneticSpelling = "Kyung Neh",
            english = "Bow",
            explanation = "Formal bow showing humility, gratitude, and mutual respect.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_9_gen_yell",
            category = TermCategory.GENERAL,
            hangul = "기합",
            romanized = "Gihap (Keup / Kihap)",
            phoneticSpelling = "Keup",
            english = "Yell",
            explanation = "Vocal focus shout coordinating breath with technique to amplify power and deter an adversary.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_10_gen_form",
            category = TermCategory.GENERAL,
            hangul = "품새",
            romanized = "Poomsae (Poomse)",
            phoneticSpelling = "Poomse",
            english = "Form",
            explanation = "Pre-arranged pattern of defense and attack movements against imaginary opponents.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_11_gen_red",
            category = TermCategory.GENERAL,
            hangul = "홍",
            romanized = "Hong",
            phoneticSpelling = "Hong",
            english = "Red",
            explanation = "Red competitor color, wearing red hogu (chest protector) and headgear in Olympic sparring.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_12_gen_blue",
            category = TermCategory.GENERAL,
            hangul = "청",
            romanized = "Cheong (Chung)",
            phoneticSpelling = "Chung",
            english = "Blue",
            explanation = "Blue competitor color, wearing blue hogu and headgear in Olympic sparring.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_13_gen_sparring",
            category = TermCategory.GENERAL,
            hangul = "겨루기",
            romanized = "Gyeorugi (Gyoroogi)",
            phoneticSpelling = "Gyoroogi",
            english = "Sparring",
            explanation = "Free or regulated combat sparring applying techniques under referee supervision.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_14_gen_breaking",
            category = TermCategory.GENERAL,
            hangul = "격파",
            romanized = "Gyeokpa (Kyopka)",
            phoneticSpelling = "Kyopka",
            english = "Breaking",
            explanation = "Demonstration of power, speed, and accuracy by breaking pine boards, tiles, or bricks.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_15_gen_student",
            category = TermCategory.GENERAL,
            hangul = "학생 / 수련생",
            romanized = "Haksaeng (Koo Ga)",
            phoneticSpelling = "Hak Saeng, Koo Ga",
            english = "Student",
            explanation = "Student or practitioner attending martial arts class.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_16_gen_senior_s",
            category = TermCategory.GENERAL,
            hangul = "선배님",
            romanized = "Seonbaenim (Sun Bae Nim)",
            phoneticSpelling = "Sun Bae Nim",
            english = "Senior Student",
            explanation = "Senior practitioner or higher belt mentor worthy of polite address.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_17_gen_assistan",
            category = TermCategory.GENERAL,
            hangul = "조교님",
            romanized = "Chogyonim (Chokyo Nim)",
            phoneticSpelling = "Chokyo Nim",
            english = "Assistant Instructor (1 Dan)",
            explanation = "1st Dan apprentice assistant instructor aiding the class.",
            beltRank = BeltRank.BLACK
        ),
        TerminologyEntry(
            id = "term_18_gen_assistan",
            category = TermCategory.GENERAL,
            hangul = "교사님",
            romanized = "Gyosanim (Keo Sa Nim)",
            phoneticSpelling = "Keo Sa Nim",
            english = "Assistant Instructor (2 Dan)",
            explanation = "2nd Dan certified instructor assisting in curriculum delivery.",
            beltRank = BeltRank.BLACK
        ),
        TerminologyEntry(
            id = "term_19_gen_assistan",
            category = TermCategory.GENERAL,
            hangul = "교범님",
            romanized = "Gyobeomnim (Keo Bum Nim)",
            phoneticSpelling = "Keo Bum Nim",
            english = "Assistant Instructor (3 Dan)",
            explanation = "3rd Dan senior instructor helping manage dojang testing.",
            beltRank = BeltRank.BLACK
        ),
        TerminologyEntry(
            id = "term_20_gen_assistan",
            category = TermCategory.GENERAL,
            hangul = "사범님",
            romanized = "Sabeomnim (Sa Bum Nim)",
            phoneticSpelling = "Sa Bum Nim",
            english = "Assistant Instructor (4 Dan or higher)",
            explanation = "Master instructor (4th Dan or higher), the namesake of our app.",
            beltRank = BeltRank.BLACK
        ),
        TerminologyEntry(
            id = "term_21_gen_head_of_",
            category = TermCategory.GENERAL,
            hangul = "관장님",
            romanized = "Gwanjangnim (Kwan Jang Nim)",
            phoneticSpelling = "Kwan Jang Nim",
            english = "Head of organization or GrandMaster",
            explanation = "Grandmaster and director/headmaster of the school or dojang network.",
            beltRank = BeltRank.BLACK
        ),
        TerminologyEntry(
            id = "term_22_gen_bow_to_f",
            category = TermCategory.GENERAL,
            hangul = "국기에 대한 경례",
            romanized = "Gukgie Daehan Gyeongnye",
            phoneticSpelling = "Koo Ki Eh, Kyung Neh",
            english = "Bow to Flag",
            explanation = "Ceremonial bow facing the national flag to open/close practice.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_23_gen_bow_to_m",
            category = TermCategory.GENERAL,
            hangul = "관장님께 경례",
            romanized = "Gwanjangnimkke Gyeongnye",
            phoneticSpelling = "Kwan Jang Nim, Kyung Neh",
            english = "Bow to Master",
            explanation = "Formal bow of honor to the Grandmaster at the start or end of training.",
            beltRank = BeltRank.BLACK
        ),
        TerminologyEntry(
            id = "term_24_gen_friend",
            category = TermCategory.GENERAL,
            hangul = "친구",
            romanized = "Chingu (Chin Goo)",
            phoneticSpelling = "Chin Goo",
            english = "Friend",
            explanation = "Friend, comrade, fellow classmate.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_25_gen_hello",
            category = TermCategory.GENERAL,
            hangul = "안녕하십니까",
            romanized = "Annyeonghasimnikka",
            phoneticSpelling = "Annyong Hashimnigga",
            english = "Hello",
            explanation = "Formal polite Korean greeting used upon entering the dojang.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_26_gen_goodbye",
            category = TermCategory.GENERAL,
            hangul = "안녕히 계세요",
            romanized = "Annyeonghi Kyeseyo",
            phoneticSpelling = "Annyonghi Kyeseyo",
            english = "Goodbye",
            explanation = "Respectful farewell spoken to those remaining in the hall when you depart.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_27_gen_please",
            category = TermCategory.GENERAL,
            hangul = "부탁합니다",
            romanized = "Butakhamnida (Put'ak Hamnida)",
            phoneticSpelling = "Put’ak Hamnida",
            english = "Please",
            explanation = "Polite expression of request: please assist or instruct me.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_28_gen_thank_yo",
            category = TermCategory.GENERAL,
            hangul = "감사합니다",
            romanized = "Gamsahamnida (Kamsa Hamnida)",
            phoneticSpelling = "Kamsa Hamnida",
            english = "Thank you",
            explanation = "Formal heartfelt gratitude expressed to master, seniors, and partners.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_29_gen_you_are_",
            category = TermCategory.GENERAL,
            hangul = "천만에요",
            romanized = "Cheonman-eyo (Ch'un Man E Yo)",
            phoneticSpelling = "Ch’un Man E Yo",
            english = "You are Welcome",
            explanation = "Formal reply to thank you: you are welcome / think nothing of it.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_30_gen_good_luc",
            category = TermCategory.GENERAL,
            hangul = "행운을 빕니다",
            romanized = "Haeng-un-eul Bimnida",
            phoneticSpelling = "Haeng Oon Eul Bil Gett U Yo",
            english = "Good Luck",
            explanation = "Wishing good luck, blessing, and fortitude for testing or tournaments.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_31_gen_congratu",
            category = TermCategory.GENERAL,
            hangul = "축하합니다",
            romanized = "Chukha-hamnida (Ch'ook Ha)",
            phoneticSpelling = "Ch’ook Ha",
            english = "Congratulations",
            explanation = "Warm congratulations offered on promotion exam success.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_32_dir_front",
            category = TermCategory.DIRECTIONS,
            hangul = "앞",
            romanized = "Ap (Up)",
            phoneticSpelling = "Up (Ap)",
            english = "Front",
            explanation = "Forward direction or anterior aspect.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_33_dir_back",
            category = TermCategory.DIRECTIONS,
            hangul = "뒤",
            romanized = "Dwi",
            phoneticSpelling = "Dwi",
            english = "Back",
            explanation = "Rear direction or posterior aspect.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_34_dir_side",
            category = TermCategory.DIRECTIONS,
            hangul = "옆",
            romanized = "Yeop (Yop)",
            phoneticSpelling = "Yop",
            english = "Side",
            explanation = "Lateral or sideways direction.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_35_dir_high",
            category = TermCategory.DIRECTIONS,
            hangul = "얼굴",
            romanized = "Olgul (Eulgool)",
            phoneticSpelling = "Eulgool",
            english = "High",
            explanation = "Upper target zone; collarbone up to top of head.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_36_dir_middle",
            category = TermCategory.DIRECTIONS,
            hangul = "몸통",
            romanized = "Momtong",
            phoneticSpelling = "Momtong",
            english = "Middle",
            explanation = "Middle body target zone; solar plexus down to belt.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_37_dir_low",
            category = TermCategory.DIRECTIONS,
            hangul = "아래",
            romanized = "Arae",
            phoneticSpelling = "Arae",
            english = "Low",
            explanation = "Lower target area; waistline down to feet.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_38_dir_left",
            category = TermCategory.DIRECTIONS,
            hangul = "왼",
            romanized = "Wen",
            phoneticSpelling = "Wen",
            english = "Left",
            explanation = "Left side / left foot lead.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_39_dir_right",
            category = TermCategory.DIRECTIONS,
            hangul = "오른",
            romanized = "Oreun (Oreum)",
            phoneticSpelling = "Oreum",
            english = "Right",
            explanation = "Right side / right foot lead.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_40_dir_outward",
            category = TermCategory.DIRECTIONS,
            hangul = "바깥",
            romanized = "Bakkat",
            phoneticSpelling = "Bakkat",
            english = "Outward",
            explanation = "Motion travelling from center body outwards.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_41_dir_inward",
            category = TermCategory.DIRECTIONS,
            hangul = "안",
            romanized = "An",
            phoneticSpelling = "An",
            english = "Inward",
            explanation = "Motion travelling from outside inward toward centerline.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_42_dir_downward",
            category = TermCategory.DIRECTIONS,
            hangul = "내려",
            romanized = "Naeryeo",
            phoneticSpelling = "Naeryo",
            english = "Downward",
            explanation = "Striking or blocking motion descending from high to low.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_43_dir_upward",
            category = TermCategory.DIRECTIONS,
            hangul = "올려",
            romanized = "Ollyeo (Allyo)",
            phoneticSpelling = "Allyo",
            english = "Upward",
            explanation = "Ascending technique rising upward from low to high.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_44_dir_sideways",
            category = TermCategory.DIRECTIONS,
            hangul = "돌려",
            romanized = "Dollyo",
            phoneticSpelling = "Dollyo",
            english = "Sideways (Turning)",
            explanation = "Rotational circular arc movement.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_45_sta_ready_st",
            category = TermCategory.STANCES,
            hangul = "차렷서기",
            romanized = "Charyeot-seogi",
            phoneticSpelling = "Cha Ryot Seogi",
            english = "Ready Stance",
            explanation = "Attention stance with feet closed and hands pinned flat beside thighs.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_46_sta_ready_mo",
            category = TermCategory.STANCES,
            hangul = "준비서기",
            romanized = "Junbi-seogi (Joonbe)",
            phoneticSpelling = "Joonbe Seogi",
            english = "Ready Motion",
            explanation = "Parallel ready stance with fists raised smoothly in front of solar plexus.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_47_sta_parallel",
            category = TermCategory.STANCES,
            hangul = "나란히서기",
            romanized = "Naranhi-seogi",
            phoneticSpelling = "Naranhi Seogi",
            english = "Parallel Stance",
            explanation = "Feet parallel, exactly one shoulder-width apart from outer edges.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_48_sta_front_st",
            category = TermCategory.STANCES,
            hangul = "앞굽이",
            romanized = "Ap-gubi (Ap Koo Bi)",
            phoneticSpelling = "Ap Koo Bi Seogi",
            english = "Front Stance",
            explanation = "Deep forward stance (3.5 foot lengths), 65% weight on bent front leg.",
            beltRank = BeltRank.YELLOW
        ),
        TerminologyEntry(
            id = "term_49_sta_walking_",
            category = TermCategory.STANCES,
            hangul = "앞서기",
            romanized = "Ap-seogi",
            phoneticSpelling = "Ap Seogi",
            english = "Walking Stance",
            explanation = "Natural walking stride stance (1 foot length), weight distributed 50/50.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_50_sta_back_sta",
            category = TermCategory.STANCES,
            hangul = "뒷굽이",
            romanized = "Dwit-gubi (Dwitkoobi)",
            phoneticSpelling = "Dwitkoobi Seogi",
            english = "Back Stance",
            explanation = "L-stance with heels aligned at 90 degrees; 70% weight on rear leg.",
            beltRank = BeltRank.GREEN_STRIPE
        ),
        TerminologyEntry(
            id = "term_51_sta_horse_ri",
            category = TermCategory.STANCES,
            hangul = "주춤서기",
            romanized = "Juchum-seogi (Joochoom)",
            phoneticSpelling = "Joochoom Seogi",
            english = "Horse Ridding Stance",
            explanation = "Sitting stance twice shoulder-width, knees pushed out, back erect.",
            beltRank = BeltRank.YELLOW_STRIPE
        ),
        TerminologyEntry(
            id = "term_52_sta_cross_st",
            category = TermCategory.STANCES,
            hangul = "앞꼬아서기",
            romanized = "Ap-kkoa-seogi (Up-kkoa)",
            phoneticSpelling = "Up-kkoa Seogi",
            english = "Cross Stance (Front)",
            explanation = "Front cross stance where one foot crosses over in front of the other.",
            beltRank = BeltRank.BLUE
        ),
        TerminologyEntry(
            id = "term_53_sta_cross_st",
            category = TermCategory.STANCES,
            hangul = "뒤꼬아서기",
            romanized = "Dwi-kkoa-seogi",
            phoneticSpelling = "Dwi-kkoa Seogi",
            english = "Cross Stance (Back)",
            explanation = "Rear cross stance stepping behind the front foot, heels raised.",
            beltRank = BeltRank.BLUE
        ),
        TerminologyEntry(
            id = "term_54_sta_crane_st",
            category = TermCategory.STANCES,
            hangul = "학다리서기",
            romanized = "Hakdari-seogi",
            phoneticSpelling = "Hakdari Seogi",
            english = "Crane Stance",
            explanation = "Single leg balance stance, other foot resting against knee joint.",
            beltRank = BeltRank.BLACK
        ),
        TerminologyEntry(
            id = "term_55_sta_lef_hand",
            category = TermCategory.STANCES,
            hangul = "왼서기",
            romanized = "Wen-seogi",
            phoneticSpelling = "Wen Seogi",
            english = "Lef-hand Stance",
            explanation = "Left-oriented stance where left foot points sideways 90 degrees.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_56_sta_right_ha",
            category = TermCategory.STANCES,
            hangul = "오른서기",
            romanized = "Oreun-seogi (Oreum)",
            phoneticSpelling = "Oreum Seogi",
            english = "Right-hand Stance",
            explanation = "Right-oriented stance where right foot points sideways 90 degrees.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_57_sta_tiger_st",
            category = TermCategory.STANCES,
            hangul = "범서기",
            romanized = "Beom-seogi",
            phoneticSpelling = "Beom Seogi",
            english = "Tiger Stance",
            explanation = "Compact cat stance with front heel lifted, 90% bodyweight on rear bent leg.",
            beltRank = BeltRank.RED
        ),
        TerminologyEntry(
            id = "term_58_blo_down_low",
            category = TermCategory.BLOCKS,
            hangul = "아래막기",
            romanized = "Arae-makgi (Arae Makki)",
            phoneticSpelling = "Arae Makki",
            english = "Down/Low Block",
            explanation = "Defending against groin and lower body kicks with downward forearm sweep.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_59_blo_high_blo",
            category = TermCategory.BLOCKS,
            hangul = "얼굴막기",
            romanized = "Olgul-makgi (Eulgool Makki)",
            phoneticSpelling = "Eulgool Makki",
            english = "High Block",
            explanation = "Forearm angled upwards 45 degrees over forehead to deflect head strikes.",
            beltRank = BeltRank.GREEN_STRIPE
        ),
        TerminologyEntry(
            id = "term_60_blo_inward_b",
            category = TermCategory.BLOCKS,
            hangul = "안막기",
            romanized = "An-makgi (An Makki)",
            phoneticSpelling = "An Makki",
            english = "Inward Block",
            explanation = "Forearm sweeping inwards across body to deflect attacks to the solar plexus.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_61_blo_knife_ha",
            category = TermCategory.BLOCKS,
            hangul = "손날막기",
            romanized = "Sonnal-makgi",
            phoneticSpelling = "Sonnal Makki",
            english = "Knife Hand Block (Double)",
            explanation = "Dual knife-hand guard in back stance protecting solar plexus and chamber.",
            beltRank = BeltRank.GREEN
        ),
        TerminologyEntry(
            id = "term_62_blo_knife_ha",
            category = TermCategory.BLOCKS,
            hangul = "한손날막기",
            romanized = "Hansonnal-makgi",
            phoneticSpelling = "Han-Sonnal Makki",
            english = "Knife Hand Block (Single)",
            explanation = "Single knife hand deflecting middle attack with opposite fist at hip.",
            beltRank = BeltRank.GREEN
        ),
        TerminologyEntry(
            id = "term_63_blo_middle_b",
            category = TermCategory.BLOCKS,
            hangul = "몸통막기",
            romanized = "Momtong-makgi",
            phoneticSpelling = "Momtong Makki",
            english = "Middle Block",
            explanation = "Outer forearm middle block deflecting chest attacks from inside out.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_64_blo_outward_",
            category = TermCategory.BLOCKS,
            hangul = "바깥막기",
            romanized = "Bakkat-makgi",
            phoneticSpelling = "Bakkat Makki",
            english = "Outward Block",
            explanation = "Forearm deflecting outward from opposite ear across torso.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_65_blo_palm_hel",
            category = TermCategory.BLOCKS,
            hangul = "바탕손막기",
            romanized = "Batangson-makgi",
            phoneticSpelling = "Batangson Makki",
            english = "Palm Hell Block",
            explanation = "Pressing or deflecting with palm heel to cushion incoming strikes.",
            beltRank = BeltRank.RED_STRIPE
        ),
        TerminologyEntry(
            id = "term_66_blo_scissors",
            category = TermCategory.BLOCKS,
            hangul = "가위막기",
            romanized = "Gawi-makgi (Kawi Makki)",
            phoneticSpelling = "Kawi Makki",
            english = "Scissors Block",
            explanation = "Simultaneous low block and inside middle block resembling open shears.",
            beltRank = BeltRank.RED
        ),
        TerminologyEntry(
            id = "term_67_blo_twist_bl",
            category = TermCategory.BLOCKS,
            hangul = "비틀어막기",
            romanized = "Biteureo-makgi (Bituro)",
            phoneticSpelling = "Bituro Makki",
            english = "Twist Block",
            explanation = "Block executed by twisting torso opposite to the lead stepping leg.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_68_blo_wide_ope",
            category = TermCategory.BLOCKS,
            hangul = "산틀막기",
            romanized = "Santeul-makgi",
            phoneticSpelling = "Santeul Makki",
            english = "Wide Open Block",
            explanation = "Mountain block: simultaneous high outer forearm block and low block.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_69_blo_spreadin",
            category = TermCategory.BLOCKS,
            hangul = "헤쳐막기",
            romanized = "Hechyeo-makgi (Hechyo)",
            phoneticSpelling = "Hechyo Makki",
            english = "Spreading Block",
            explanation = "Both arms opening simultaneously outwards to break dual chest grabs.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_70_blo_x_block",
            category = TermCategory.BLOCKS,
            hangul = "엇걸어막기",
            romanized = "Eotgeoreo-makgi (Otgoreo)",
            phoneticSpelling = "Otgoreo Makki",
            english = "X-Block",
            explanation = "Wrists crossed in an 'X' to capture or reinforce high or low blocks.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_71_str_strike",
            category = TermCategory.STRIKES,
            hangul = "치기",
            romanized = "Chigi",
            phoneticSpelling = "Chigi",
            english = "Strike",
            explanation = "Striking technique utilizing snapping impact rather than straight thrust.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_72_str_knife_ha",
            category = TermCategory.STRIKES,
            hangul = "손날 안치기",
            romanized = "Sonnal An-chigi",
            phoneticSpelling = "Sonnal An-Chigi",
            english = "Knife-hand Strike (Inward)",
            explanation = "Knife-hand strike aimed inward targeting opponent's carotid neck artery.",
            beltRank = BeltRank.GREEN
        ),
        TerminologyEntry(
            id = "term_73_str_knife_ha",
            category = TermCategory.STRIKES,
            hangul = "손날 바깥치기",
            romanized = "Sonnal Bakkat-chigi",
            phoneticSpelling = "Sonnal Bakkat-Chigi",
            english = "Knife-hand Strike (Outward)",
            explanation = "Knife-hand strike snapping outward toward neck or temple.",
            beltRank = BeltRank.GREEN
        ),
        TerminologyEntry(
            id = "term_74_str_hammer_f",
            category = TermCategory.STRIKES,
            hangul = "메주먹 내려치기",
            romanized = "Me-jumeok Naeryeo-chigi",
            phoneticSpelling = "Meori-Naeryo Chigi",
            english = "Hammer Fist (Down)",
            explanation = "Hammer fist striking downward on bridge of nose, collarbone, or ribs.",
            beltRank = BeltRank.BLUE
        ),
        TerminologyEntry(
            id = "term_75_str_palm_hee",
            category = TermCategory.STRIKES,
            hangul = "바탕손 턱치기",
            romanized = "Batangson Teok-chigi",
            phoneticSpelling = "Batangson Teok-Chigi",
            english = "Palm Heel Strike",
            explanation = "Palm heel thrust driving upward into opponent's jaw or chin.",
            beltRank = BeltRank.RED_STRIPE
        ),
        TerminologyEntry(
            id = "term_76_str_elbow_st",
            category = TermCategory.STRIKES,
            hangul = "팔굽치기",
            romanized = "Palkup-chigi",
            phoneticSpelling = "Palkup Chigi",
            english = "Elbow Strike",
            explanation = "Short range catastrophic strike utilizing the sharp bony point of the elbow.",
            beltRank = BeltRank.BLUE
        ),
        TerminologyEntry(
            id = "term_77_str_knee_str",
            category = TermCategory.STRIKES,
            hangul = "무릎치기",
            romanized = "Mureup-chigi",
            phoneticSpelling = "Mureup Chigi",
            english = "Knee Strike",
            explanation = "Upward or driving knee strike targeting ribs, abdomen, or clinched head.",
            beltRank = BeltRank.RED
        ),
        TerminologyEntry(
            id = "term_78_str_back_fis",
            category = TermCategory.STRIKES,
            hangul = "등주먹치기",
            romanized = "Deung-jumeok-chigi",
            phoneticSpelling = "Deung Jumeok Chigi",
            english = "Back Fist Strike",
            explanation = "Back of clenched fist snapping horizontally or vertically into face.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_79_str_fingerti",
            category = TermCategory.STRIKES,
            hangul = "가위손끝 찌르기",
            romanized = "Kawinsonkkeut Tzireuki",
            phoneticSpelling = "Kawinsonkkeut Tzireuki",
            english = "Fingertips (2) Thrust",
            explanation = "Two-finger scissor thrust targeting eyes or pressure points.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_80_pun_punch",
            category = TermCategory.PUNCHES,
            hangul = "지르기",
            romanized = "Jireugi (Jireuki)",
            phoneticSpelling = "Jireuki",
            english = "Punch",
            explanation = "Straight punch rotating 180 degrees through centerline at moment of impact.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_81_pun_reverse_",
            category = TermCategory.PUNCHES,
            hangul = "바로지르기",
            romanized = "Baro-jireugi (Bahro)",
            phoneticSpelling = "Bahro Jireuki",
            english = "Reverse Punch",
            explanation = "Punch delivered with hand on same side as front lead leg.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_82_pun_straight",
            category = TermCategory.PUNCHES,
            hangul = "반대지르기",
            romanized = "Bandae-jireugi",
            phoneticSpelling = "Bandae Jireuki",
            english = "Straight Punch",
            explanation = "Punch delivered with hand opposite to the front lead leg (opposite side).",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_83_pun_middle_p",
            category = TermCategory.PUNCHES,
            hangul = "몸통지르기",
            romanized = "Momtong-jireugi",
            phoneticSpelling = "Momtong Jireuki",
            english = "Middle Punch",
            explanation = "Straight punch driven into opponent's solar plexus.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_84_pun_upper_cu",
            category = TermCategory.PUNCHES,
            hangul = "치지르기",
            romanized = "Chi-jireugi",
            phoneticSpelling = "Chi Jireuki",
            english = "Upper Cut Punch",
            explanation = "Uppercut fist rising vertically underneath adversary's jaw.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_85_pun_side_pun",
            category = TermCategory.PUNCHES,
            hangul = "옆지르기",
            romanized = "Yeop-jireugi (Yop)",
            phoneticSpelling = "Yop Jireuki",
            english = "Side Punch",
            explanation = "Punch delivered sideways across torso while in horse stance.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_86_pun_hook_pun",
            category = TermCategory.PUNCHES,
            hangul = "돌려지르기",
            romanized = "Dollyo-jireugi",
            phoneticSpelling = "Dollyo Jireuki",
            english = "Hook Punch",
            explanation = "Round hook punch travelling horizontally around opponent's guard.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_87_pun_downward",
            category = TermCategory.PUNCHES,
            hangul = "내려지르기",
            romanized = "Naeryeo-jireugi",
            phoneticSpelling = "Naeryo Jireuki",
            english = "Downward Punch",
            explanation = "Downward driving punch targeting fallen or low opponent.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_88_pun_erected_",
            category = TermCategory.PUNCHES,
            hangul = "세워지르기",
            romanized = "Sewo-jireugi",
            phoneticSpelling = "Sewo Jireuki",
            english = "Erected Fist Punch",
            explanation = "Vertical fist punch delivered without rotating forearm to fit between guard.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_89_kic_front_ki",
            category = TermCategory.KICKS,
            hangul = "앞차기",
            romanized = "Ap-chagi",
            phoneticSpelling = "Ap Chagi",
            english = "Front Kick",
            explanation = "Snapping kick driven with ball of foot (Apchuk) directly into midsection or chin.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_90_kic_roundhou",
            category = TermCategory.KICKS,
            hangul = "돌려차기",
            romanized = "Dollyo-chagi (Ap Dollyo)",
            phoneticSpelling = "Ap Dollyo Chagi",
            english = "Roundhouse Kick",
            explanation = "Rotational kick pivoting on ball of support foot, striking with instep or ball of foot.",
            beltRank = BeltRank.YELLOW_STRIPE
        ),
        TerminologyEntry(
            id = "term_91_kic_side_kic",
            category = TermCategory.KICKS,
            hangul = "옆차기",
            romanized = "Yeop-chagi (Yop)",
            phoneticSpelling = "Yop Chagi",
            english = "Side Kick",
            explanation = "Linear thrust driven with heel and knife-edge of foot (Balnal) through centerline.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_92_kic_half_moo",
            category = TermCategory.KICKS,
            hangul = "반달차기",
            romanized = "Bandal-chagi (Badal)",
            phoneticSpelling = "Badal Chagi",
            english = "Half Moon kick",
            explanation = "Diagonal kick midway between front kick and roundhouse kick targeting solar plexus.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_93_kic_axe_kick",
            category = TermCategory.KICKS,
            hangul = "내려차기 / 찍어차기",
            romanized = "Naeryeo-chagi / Chiko-chagi",
            phoneticSpelling = "Nareyo or Chiko Chagi",
            english = "Axe kick",
            explanation = "Leg raised high overhead and brought crashing down with heel onto face or collarbone.",
            beltRank = BeltRank.BLUE
        ),
        TerminologyEntry(
            id = "term_94_kic_high  st",
            category = TermCategory.KICKS,
            hangul = "앞올리기",
            romanized = "Ap-olligi (Krohlligi)",
            phoneticSpelling = "Ap-Olligi or Krohlligi",
            english = "High  Stretch Kick (straight leg)",
            explanation = "Straight leg dynamic flexibility warm-up kick swung high toward ceiling.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_95_kic_back_kic",
            category = TermCategory.KICKS,
            hangul = "뒤차기",
            romanized = "Dwi-chagi",
            phoneticSpelling = "Dwi Chagi",
            english = "Back Kick",
            explanation = "Straight linear mule kick driven directly backwards with heel into opponent's ribs.",
            beltRank = BeltRank.RED_STRIPE
        ),
        TerminologyEntry(
            id = "term_96_kic_hook_kic",
            category = TermCategory.KICKS,
            hangul = "낚아차기 / 후려차기",
            romanized = "Nakka-chagi",
            phoneticSpelling = "Nakka Chagi",
            english = "Hook Kick",
            explanation = "Whip-like hook kick bending knee across opponent's temple with heel or sole.",
            beltRank = BeltRank.BLACK_STRIPE
        ),
        TerminologyEntry(
            id = "term_97_kic_thrashin",
            category = TermCategory.KICKS,
            hangul = "회오리차기 / 뒤후려차기",
            romanized = "Huryo-chagi (Dwi-hurigi)",
            phoneticSpelling = "Huryo Chagi",
            english = "Thrashing or Spinning Kick",
            explanation = "360-degree spinning hook or whip kick generating massive rotational force.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_98_kic_push_kic",
            category = TermCategory.KICKS,
            hangul = "밀어차기",
            romanized = "Mireo-chagi",
            phoneticSpelling = "Mireo Chagi",
            english = "Push Kick",
            explanation = "Teep/push kick stomping sole of foot into opponent's torso to create distance.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_99_kic_stretch_",
            category = TermCategory.KICKS,
            hangul = "앞올려차기",
            romanized = "Ap-ollyo-chagi",
            phoneticSpelling = "Ap-Ollyo Chagi",
            english = "Stretch kick",
            explanation = "Upward stretching front kick to loosen hamstrings and develop high chamber.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_100_kic_swing_ki",
            category = TermCategory.KICKS,
            hangul = "바깥차기",
            romanized = "Bakkat-chagi",
            phoneticSpelling = "Bakkat Chagi",
            english = "Swing Kick (Outward)",
            explanation = "Crescent kick sweeping from inside across outwards with blade of foot.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_101_kic_swing_ki",
            category = TermCategory.KICKS,
            hangul = "안차기",
            romanized = "An-chagi",
            phoneticSpelling = "An Chagi",
            english = "Swing Kick (Inward)",
            explanation = "Inside crescent kick sweeping from outside inward with inside arch of foot.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_102_kic_target_k",
            category = TermCategory.KICKS,
            hangul = "표적차기",
            romanized = "Pyojeok-chagi",
            phoneticSpelling = "Pyojeok Chagi",
            english = "Target Kick",
            explanation = "Kick slapping target palm or focus pad held in stationary position.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_103_kic_twist_ki",
            category = TermCategory.KICKS,
            hangul = "비틀어차기",
            romanized = "Bitureo-chagi",
            phoneticSpelling = "Bitureo Chagi",
            english = "Twist Kick",
            explanation = "Deceptive kick originating like front kick but twisting outward toward target.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_104_ana_ball_of_",
            category = TermCategory.ANATOMY,
            hangul = "앞축",
            romanized = "Apchuk (Apchook)",
            phoneticSpelling = "Apchook",
            english = "Ball of Foot",
            explanation = "The ball of the foot directly under toes, primary striking tool for Ap-chagi and Dollyo-chagi.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_105_ana_foot",
            category = TermCategory.ANATOMY,
            hangul = "발",
            romanized = "Bal",
            phoneticSpelling = "Bal",
            english = "Foot",
            explanation = "The foot in Korean, fundamental root of all kicking arts.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_106_ana_top_of_f",
            category = TermCategory.ANATOMY,
            hangul = "발등",
            romanized = "Baldeung",
            phoneticSpelling = "Baldeung",
            english = "Top of Foot",
            explanation = "Instep or top of foot used for scoring points with electronic hogu in modern WT sparring.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_107_ana_foot_edg",
            category = TermCategory.ANATOMY,
            hangul = "발날",
            romanized = "Balnal",
            phoneticSpelling = "Balnal",
            english = "Foot Edge",
            explanation = "Outer blade / knife edge of the foot, primary contact point for Yeop-chagi (Side kick).",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_108_ana_joint_of",
            category = TermCategory.ANATOMY,
            hangul = "밤주먹",
            romanized = "Bam-jumeok (Bam Joomeok)",
            phoneticSpelling = "Bam Joomeok",
            english = "Joint of one finger, or strike using a joint of one finger",
            explanation = "Chestnut fist; middle finger knuckle protruded for concentrated point striking.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_109_ana_palm",
            category = TermCategory.ANATOMY,
            hangul = "바탕손",
            romanized = "Batangson (Batang Son)",
            phoneticSpelling = "Batang Son",
            english = "Palm",
            explanation = "Heel/base of palm utilized for gentle deflection or bone-jarring strikes.",
            beltRank = BeltRank.RED_STRIPE
        ),
        TerminologyEntry(
            id = "term_110_ana_leg",
            category = TermCategory.ANATOMY,
            hangul = "다리",
            romanized = "Dari",
            phoneticSpelling = "Dari",
            english = "Leg",
            explanation = "Leg, lower limb.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_111_ana_back_fis",
            category = TermCategory.ANATOMY,
            hangul = "등주먹",
            romanized = "Deung-jumeok (Deung Joomeok)",
            phoneticSpelling = "Deung Joomeok",
            english = "Back Fist",
            explanation = "Back of fist, top knuckles used in snapping facial strikes.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_112_ana_heel_bac",
            category = TermCategory.ANATOMY,
            hangul = "뒤꿈치",
            romanized = "Dwikgumchi (Dwikoomchi)",
            phoneticSpelling = "Dwikoomchi",
            english = "Heel Back",
            explanation = "Back of the heel, used for devastating axe kicks and hook kicks.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_113_ana_heel_bot",
            category = TermCategory.ANATOMY,
            hangul = "뒤축",
            romanized = "Dwichuk (Dwichook)",
            phoneticSpelling = "Dwichook",
            english = "Heel Bottom",
            explanation = "Bottom base of heel used in pushing kicks and back kicks.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_114_ana_fist",
            category = TermCategory.ANATOMY,
            hangul = "주먹",
            romanized = "Jumeok (Joomeok)",
            phoneticSpelling = "Joomeok",
            english = "Fist",
            explanation = "Clenched fist.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_115_ana_neck",
            category = TermCategory.ANATOMY,
            hangul = "목",
            romanized = "Mok",
            phoneticSpelling = "Mok",
            english = "Neck",
            explanation = "Neck / throat target zone.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_116_ana_body",
            category = TermCategory.ANATOMY,
            hangul = "몸",
            romanized = "Mom",
            phoneticSpelling = "Mom",
            english = "Body",
            explanation = "Body, torso.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_117_ana_knee",
            category = TermCategory.ANATOMY,
            hangul = "무릎",
            romanized = "Mureup (Mooreup)",
            phoneticSpelling = "Mooreup",
            english = "Knee",
            explanation = "Knee joint used in devastating close quarters strikes.",
            beltRank = BeltRank.RED
        ),
        TerminologyEntry(
            id = "term_118_ana_arm",
            category = TermCategory.ANATOMY,
            hangul = "팔",
            romanized = "Pal",
            phoneticSpelling = "Pal",
            english = "Arm",
            explanation = "Arm.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_119_ana_elbow",
            category = TermCategory.ANATOMY,
            hangul = "팔굽",
            romanized = "Palkup (Palkoop)",
            phoneticSpelling = "Palkoop",
            english = "Elbow",
            explanation = "Elbow joint point.",
            beltRank = BeltRank.BLUE
        ),
        TerminologyEntry(
            id = "term_120_ana_forearm",
            category = TermCategory.ANATOMY,
            hangul = "팔목",
            romanized = "Palmok",
            phoneticSpelling = "Palmok",
            english = "Forearm",
            explanation = "Forearm utilized for interior and exterior blocking surfaces.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_121_ana_finger_j",
            category = TermCategory.ANATOMY,
            hangul = "편주먹",
            romanized = "Pyeon-jumeok (Pyon Joomeok)",
            phoneticSpelling = "Pyon Joomeok",
            english = "Finger Joints, or strike using finger joints",
            explanation = "Flat fist with four secondary finger knuckles extended.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_122_ana_hand",
            category = TermCategory.ANATOMY,
            hangul = "손",
            romanized = "Son",
            phoneticSpelling = "Son",
            english = "Hand",
            explanation = "Hand.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_123_ana_finger_t",
            category = TermCategory.ANATOMY,
            hangul = "손끝",
            romanized = "Sonkkeut (Sonkeut)",
            phoneticSpelling = "Sonkeut",
            english = "Finger Tips",
            explanation = "Fingertips used in spear-hand thrusting (Pyeon-son-kkeut jjireugi).",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_124_ana_wrist",
            category = TermCategory.ANATOMY,
            hangul = "손목",
            romanized = "Sonmok (Sonmoke)",
            phoneticSpelling = "Sonmoke",
            english = "Wrist",
            explanation = "Wrist joint.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_125_ana_knife_ha",
            category = TermCategory.ANATOMY,
            hangul = "손날",
            romanized = "Sonnal",
            phoneticSpelling = "Sonnal",
            english = "Knife Hand",
            explanation = "Knife hand edge from base of pinky finger to wrist.",
            beltRank = BeltRank.GREEN
        ),
        TerminologyEntry(
            id = "term_126_ana_ridge_ha",
            category = TermCategory.ANATOMY,
            hangul = "손날등",
            romanized = "Sonnal-deung (Sonnal Deung)",
            phoneticSpelling = "Sonnal Deung",
            english = "Ridge Hand",
            explanation = "Reverse knife hand edge running between thumb and index finger knuckle.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_127_ana_chin",
            category = TermCategory.ANATOMY,
            hangul = "턱",
            romanized = "Teok",
            phoneticSpelling = "Teok",
            english = "Chin",
            explanation = "Chin / lower jaw target area.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_128_num_1",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "하나",
            romanized = "Hana (Hanna)",
            phoneticSpelling = "Hanna",
            english = "1",
            explanation = "Native Korean number 1.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_129_num_2",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "둘",
            romanized = "Dul (Dool)",
            phoneticSpelling = "Dool",
            english = "2",
            explanation = "Native Korean number 2.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_130_num_3",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "셋",
            romanized = "Set",
            phoneticSpelling = "Set",
            english = "3",
            explanation = "Native Korean number 3.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_131_num_4",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "넷",
            romanized = "Net",
            phoneticSpelling = "Net",
            english = "4",
            explanation = "Native Korean number 4.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_132_num_5",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "다섯",
            romanized = "Daseot (Dasut)",
            phoneticSpelling = "Dasut",
            english = "5",
            explanation = "Native Korean number 5.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_133_num_6",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "여섯",
            romanized = "Yeoseot (Yosut)",
            phoneticSpelling = "Yosut",
            english = "6",
            explanation = "Native Korean number 6.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_134_num_7",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "일곱",
            romanized = "Ilgop (Ill Gop)",
            phoneticSpelling = "Ill Gop",
            english = "7",
            explanation = "Native Korean number 7.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_135_num_8",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "여덟",
            romanized = "Yeodeol (Yo Dol)",
            phoneticSpelling = "Yo Dol",
            english = "8",
            explanation = "Native Korean number 8.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_136_num_9",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "아홉",
            romanized = "Ahop (A-Hoop)",
            phoneticSpelling = "A-Hoop",
            english = "9",
            explanation = "Native Korean number 9.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_137_num_10",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "열",
            romanized = "Yeol (Yoal)",
            phoneticSpelling = "Yoal",
            english = "10",
            explanation = "Native Korean number 10.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_138_num_11",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "열하나",
            romanized = "Yeol-hana",
            phoneticSpelling = "Yoal-Hana…Etc",
            english = "11",
            explanation = "Native Korean number 11 (10 + 1).",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_139_num_20",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "스물",
            romanized = "Seumul (Soo Mool)",
            phoneticSpelling = "Soo Mool",
            english = "20",
            explanation = "Native Korean number 20.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_140_num_21",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "스물하나",
            romanized = "Seumul-hana (Soo Mool Hana)",
            phoneticSpelling = "Soo Mool Hana…Etc",
            english = "21",
            explanation = "Native Korean number 21 (20 + 1).",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_141_num_30",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "서른",
            romanized = "Seoreun (Saroon)",
            phoneticSpelling = "Saroon",
            english = "30",
            explanation = "Native Korean number 30.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_142_num_31",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "서른하나",
            romanized = "Seoreun-hana (Saroon Hana)",
            phoneticSpelling = "Saroon Hana…Etc",
            english = "31",
            explanation = "Native Korean number 31 (30 + 1).",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_143_num_40",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "마흔",
            romanized = "Maheun (Mahoon)",
            phoneticSpelling = "Mahoon",
            english = "40",
            explanation = "Native Korean number 40.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_144_num_41",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "마흔하나",
            romanized = "Maheun-hana (Mahoon Hana)",
            phoneticSpelling = "Mahoon Hana…Etc",
            english = "41",
            explanation = "Native Korean number 41 (40 + 1).",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_145_num_50",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "쉰",
            romanized = "Swin (Shioon)",
            phoneticSpelling = "Shioon",
            english = "50",
            explanation = "Native Korean number 50.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_146_num_60",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "예순",
            romanized = "Yesun (Yesoon)",
            phoneticSpelling = "Yesoon",
            english = "60",
            explanation = "Native Korean number 60.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_147_num_70",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "일흔",
            romanized = "Ilheun",
            phoneticSpelling = "Ilheun",
            english = "70",
            explanation = "Native Korean number 70.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_148_num_80",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "여든",
            romanized = "Yeodeun (Yudoon)",
            phoneticSpelling = "Yudoon",
            english = "80",
            explanation = "Native Korean number 80.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_149_num_90",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "아흔",
            romanized = "Aheun",
            phoneticSpelling = "Aheun",
            english = "90",
            explanation = "Native Korean number 90.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_150_num_100",
            category = TermCategory.NUMBERS_NATIVE,
            hangul = "백",
            romanized = "Baek (Bak)",
            phoneticSpelling = "Bak",
            english = "100",
            explanation = "Korean number 100.",
            beltRank = BeltRank.WHITE
        ),
        TerminologyEntry(
            id = "term_151_num_1rst",
            category = TermCategory.NUMBERS_SINO,
            hangul = "일 (제일)",
            romanized = "Il (Je-il / Ill)",
            phoneticSpelling = "Ill",
            english = "1rst",
            explanation = "Sino-Korean 1st; used in Taegeuk Il Jang and 1st Dan (Il Dan).",
            beltRank = BeltRank.YELLOW
        ),
        TerminologyEntry(
            id = "term_152_num_2nd",
            category = TermCategory.NUMBERS_SINO,
            hangul = "이 (제이)",
            romanized = "Ee (Je-ee)",
            phoneticSpelling = "Ee",
            english = "2nd",
            explanation = "Sino-Korean 2nd; used in Taegeuk Ee Jang and 2nd Dan (Ee Dan).",
            beltRank = BeltRank.GREEN_STRIPE
        ),
        TerminologyEntry(
            id = "term_153_num_3rd",
            category = TermCategory.NUMBERS_SINO,
            hangul = "삼 (제삼)",
            romanized = "Sam (Je-sam)",
            phoneticSpelling = "Sam",
            english = "3rd",
            explanation = "Sino-Korean 3rd; used in Taegeuk Sam Jang and 3rd Dan (Sam Dan).",
            beltRank = BeltRank.GREEN
        ),
        TerminologyEntry(
            id = "term_154_num_4th",
            category = TermCategory.NUMBERS_SINO,
            hangul = "사 (제사)",
            romanized = "Sa (Je-sa / Sah)",
            phoneticSpelling = "Sah",
            english = "4th",
            explanation = "Sino-Korean 4th; used in Taegeuk Sa Jang and 4th Dan (Sa Dan).",
            beltRank = BeltRank.BLUE_STRIPE
        ),
        TerminologyEntry(
            id = "term_155_num_5th",
            category = TermCategory.NUMBERS_SINO,
            hangul = "오 (제오)",
            romanized = "Oh (Je-oh)",
            phoneticSpelling = "Oh",
            english = "5th",
            explanation = "Sino-Korean 5th; used in Taegeuk Oh Jang and 5th Dan (Oh Dan).",
            beltRank = BeltRank.BLUE
        ),
        TerminologyEntry(
            id = "term_156_num_6th",
            category = TermCategory.NUMBERS_SINO,
            hangul = "육 (제육)",
            romanized = "Yuk (Je-yuk / Yook)",
            phoneticSpelling = "Yook",
            english = "6th",
            explanation = "Sino-Korean 6th; used in Taegeuk Yuk Jang and 6th Dan (Yuk Dan).",
            beltRank = BeltRank.RED_STRIPE
        ),
        TerminologyEntry(
            id = "term_157_num_7th",
            category = TermCategory.NUMBERS_SINO,
            hangul = "칠 (제칠)",
            romanized = "Chil (Je-chil)",
            phoneticSpelling = "Chil",
            english = "7th",
            explanation = "Sino-Korean 7th; used in Taegeuk Chil Jang and 7th Dan (Chil Dan).",
            beltRank = BeltRank.RED
        ),
        TerminologyEntry(
            id = "term_158_num_8th",
            category = TermCategory.NUMBERS_SINO,
            hangul = "팔 (제팔)",
            romanized = "Pal (Je-pal / Pahl)",
            phoneticSpelling = "Pahl",
            english = "8th",
            explanation = "Sino-Korean 8th; used in Taegeuk Pal Jang and 8th Dan (Pal Dan).",
            beltRank = BeltRank.BLACK_STRIPE
        ),
        TerminologyEntry(
            id = "term_159_num_9th",
            category = TermCategory.NUMBERS_SINO,
            hangul = "구 (제구)",
            romanized = "Gu (Je-gu / Koo)",
            phoneticSpelling = "Koo",
            english = "9th",
            explanation = "Sino-Korean 9th; used for 9th Dan (Gu Dan).",
            beltRank = BeltRank.BLACK
        ),
        TerminologyEntry(
            id = "term_160_num_10th",
            category = TermCategory.NUMBERS_SINO,
            hangul = "십 (제십)",
            romanized = "Sip (Je-sip / Ship)",
            phoneticSpelling = "Ship",
            english = "10th",
            explanation = "Sino-Korean 10th.",
            beltRank = BeltRank.YELLOW
        ),
        TerminologyEntry(
            id = "term_161_num_20th",
            category = TermCategory.NUMBERS_SINO,
            hangul = "이십 (제이십)",
            romanized = "Isip (Je-isip / Eeship)",
            phoneticSpelling = "Eeship",
            english = "20th",
            explanation = "Sino-Korean 20th.",
            beltRank = BeltRank.GREEN_STRIPE
        ),
        TerminologyEntry(
            id = "term_162_num_30th",
            category = TermCategory.NUMBERS_SINO,
            hangul = "삼십 (제삼십)",
            romanized = "Samsip (Je-samsip / Samship)",
            phoneticSpelling = "Samship",
            english = "30th",
            explanation = "Sino-Korean 30th.",
            beltRank = BeltRank.GREEN
        ),
        TerminologyEntry(
            id = "term_163_num_40th",
            category = TermCategory.NUMBERS_SINO,
            hangul = "사십 (제사십)",
            romanized = "Sasip (Je-sasip / Sahship)",
            phoneticSpelling = "Sahship",
            english = "40th",
            explanation = "Sino-Korean 40th.",
            beltRank = BeltRank.BLUE_STRIPE
        ),
        TerminologyEntry(
            id = "term_164_num_50th",
            category = TermCategory.NUMBERS_SINO,
            hangul = "오십 (제오십)",
            romanized = "Osip (Je-osip / Ohship)",
            phoneticSpelling = "Ohship",
            english = "50th",
            explanation = "Sino-Korean 50th.",
            beltRank = BeltRank.BLUE
        )
    )
}
