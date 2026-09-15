package com.sabeomnim.app.data.repository

import com.sabeomnim.app.data.models.BeltRank
import com.sabeomnim.app.data.models.QuizQuestion

object QuizRepository {

    fun getQuestionsForBelt(rank: BeltRank): List<QuizQuestion> =
        allQuestions.filter { it.beltRank == rank }

    fun getAllQuestions(): List<QuizQuestion> = allQuestions

    private val allQuestions = listOf(
        // WHITE BELT (10th Geup)
        QuizQuestion(
            id = "w1",
            beltRank = BeltRank.WHITE,
            question = "What is the literal translation of the word 'Taekwondo'?",
            koreanTerm = "Taekwondo",
            options = listOf(
                "The way of the foot and the hand",
                "The art of internal peace",
                "The ancient way of the spear",
                "The sport of kicks and strikes"
            ),
            correctIndex = 0,
            explanation = "'Tae' means foot/kicking, 'Kwon' means hand/punching, and 'Do' means the art, path, or way of life."
        ),
        QuizQuestion(
            id = "w2",
            beltRank = BeltRank.WHITE,
            question = "What does the White Belt symbolize?",
            koreanTerm = "Baek-tti (White Belt)",
            options = listOf(
                "Innocence and having no prior knowledge; a blank canvas",
                "The danger of the journey ahead",
                "The tree reaching for the sky",
                "The master of the training hall"
            ),
            correctIndex = 0,
            explanation = "White signifies innocence and purity—the student has no prior knowledge of Taekwondo."
        ),
        QuizQuestion(
            id = "w3",
            beltRank = BeltRank.WHITE,
            question = "Which Korean command means 'Bow'?",
            koreanTerm = "Gyeong-nye (Bow)",
            options = listOf("Charyeot", "Gyeong-nye", "Junbi", "Sijak"),
            correctIndex = 1,
            explanation = "Gyeong-nye is the command to bow to show respect."
        ),
        QuizQuestion(
            id = "w4",
            beltRank = BeltRank.WHITE,
            question = "Which striking surface is used in an Ap-chagi (Front Snap Kick)?",
            koreanTerm = "Ap-chuk (Ball of Foot)",
            options = listOf("The ball of the foot (Ap-chuk)", "The heel (Dwichuk)", "The blade of the foot (Bal-nal)", "The knee (Mureup)"),
            correctIndex = 0,
            explanation = "Ap-chagi primarily strikes using the ball of the foot (Ap-chuk) with toes pulled back."
        ),

        // YELLOW BELT (9th Geup)
        QuizQuestion(
            id = "y1",
            beltRank = BeltRank.YELLOW,
            question = "What does Taegeuk 1 Jang symbolize?",
            koreanTerm = "Taegeuk 1 Jang (Keon)",
            options = listOf(
                "Keon (Heaven/Sky) - the origin of all creation",
                "Gam (Water) - flow and continuous motion",
                "Jin (Thunder) - immense sudden power",
                "Gon (Earth) - pure receptivity"
            ),
            correctIndex = 0,
            explanation = "Taegeuk 1 Jang represents 'Keon', symbolizing Heaven and the origin of life."
        ),
        QuizQuestion(
            id = "y2",
            beltRank = BeltRank.YELLOW,
            question = "How many movements are in Taegeuk 1 Jang?",
            koreanTerm = "18 Movements",
            options = listOf("16", "18", "20", "22"),
            correctIndex = 1,
            explanation = "Taegeuk 1 Jang has exactly 18 movements ending in a right forward stance middle punch with Kihap."
        ),
        QuizQuestion(
            id = "y3",
            beltRank = BeltRank.YELLOW,
            question = "What is the difference between Baro-jireugi and Bandae-jireugi?",
            koreanTerm = "Baro vs Bandae-jireugi",
            options = listOf(
                "Baro uses the hand matching the front leg in walking stance; Bandae uses the opposite hand",
                "Baro is high punch; Bandae is low punch",
                "Baro is open hand; Bandae is clenched fist",
                "Baro uses both hands simultaneously"
            ),
            correctIndex = 0,
            explanation = "Baro-jireugi punches with the same side hand as the forward foot in walking stance; Bandae-jireugi punches with the opposite side hand (reverse punch)."
        ),

        // ORANGE BELT (8th Geup)
        QuizQuestion(
            id = "o1",
            beltRank = BeltRank.ORANGE,
            question = "What does Taegeuk 2 Jang (Ee Jang) represent?",
            koreanTerm = "Taegeuk 2 Jang (Tae)",
            options = listOf(
                "Tae (Lake / Joy) - inner firmness with external calmness",
                "Ri (Fire / Sun) - passion and brightness",
                "Jin (Thunder) - sudden shocking power",
                "Keon (Heaven) - creation"
            ),
            correctIndex = 0,
            explanation = "Taegeuk 2 Jang represents Tae (Lake), symbolizing internal firmness expressed through calm, joyful movement."
        ),
        QuizQuestion(
            id = "o2",
            beltRank = BeltRank.ORANGE,
            question = "What stance is introduced in the orange belt curriculum?",
            koreanTerm = "Dwit-kubi (Back Stance)",
            options = listOf("Dwit-kubi (Back Stance)", "Beom-seogi (Tiger Stance)", "Koa-seogi (Cross Stance)", "Hakdari-seogi (Crane Stance)"),
            correctIndex = 0,
            explanation = "Dwit-kubi (Back Stance) is introduced, placing 70% of the body weight on the bent rear leg."
        ),
        QuizQuestion(
            id = "o3",
            beltRank = BeltRank.ORANGE,
            question = "Where is the target for an Olgul-jireugi (High Punch)?",
            koreanTerm = "Olgul-jireugi",
            options = listOf("The philtrum / nose area", "The solar plexus", "The lower abdomen", "The collarbone"),
            correctIndex = 0,
            explanation = "Olgul attacks target between the philtrum and the bridge of the nose."
        ),

        // GREEN BELT (7th Geup)
        QuizQuestion(
            id = "g1",
            beltRank = BeltRank.GREEN,
            question = "What does Taegeuk 3 Jang represent in the Eight Trigrams?",
            koreanTerm = "Taegeuk 3 Jang (Ri)",
            options = listOf("Ri (Fire and Sun)", "Tae (Lake)", "Son (Wind)", "Gan (Mountain)"),
            correctIndex = 0,
            explanation = "Taegeuk 3 Jang symbolizes 'Ri' (Fire), representing enthusiasm, warmth, and focused intensity."
        ),
        QuizQuestion(
            id = "g2",
            beltRank = BeltRank.GREEN,
            question = "In Dwit-kubi (Back Stance), what is the weight distribution between rear and front legs?",
            koreanTerm = "Dwit-kubi (Back Stance)",
            options = listOf("70% rear leg, 30% front leg", "50% rear, 50% front", "90% rear, 10% front", "60% front, 40% rear"),
            correctIndex = 0,
            explanation = "Dwit-kubi places approximately 70% of the body weight on the rear leg and 30% on the front leg."
        ),
        QuizQuestion(
            id = "g3",
            beltRank = BeltRank.GREEN,
            question = "What part of the hand is used in Sonnal Mok-chigi?",
            koreanTerm = "Sonnal (Knife-hand)",
            options = listOf(
                "The knife-edge (outer blade) of the open hand",
                "The palm heel (Batangson)",
                "The knuckles of the backfist",
                "The tips of the fingers"
            ),
            correctIndex = 0,
            explanation = "Sonnal refers to the knife-hand blade, striking the opponent's neck."
        ),

        // BLUE BELT (6th Geup)
        QuizQuestion(
            id = "b1",
            beltRank = BeltRank.BLUE,
            question = "What does Taegeuk 4 Jang symbolize?",
            koreanTerm = "Taegeuk 4 Jang (Jin)",
            options = listOf("Jin (Thunder) - great dignity and explosive power", "Son (Wind)", "Gam (Water)", "Keon (Heaven)"),
            correctIndex = 0,
            explanation = "Taegeuk 4 Jang symbolizes 'Jin' (Thunder), demonstrating calm poise that erupts into sudden, decisive power."
        ),
        QuizQuestion(
            id = "b2",
            beltRank = BeltRank.BLUE,
            question = "What technique is Pyeon-son-kkeut Jjireugi?",
            koreanTerm = "Pyeon-son-kkeut Jjireugi",
            options = listOf("Spear-hand thrust", "Hammerfist downward strike", "Elbow turning strike", "Scissors block"),
            correctIndex = 0,
            explanation = "Pyeon-son-kkeut Jjireugi is a spear-hand thrust targeting the solar plexus, supported with an underlying palm."
        ),

        // BLUE BELT W. RED STRIPE (5th Geup)
        QuizQuestion(
            id = "br1",
            beltRank = BeltRank.BLUE_RED_STRIPE,
            question = "What does Taegeuk 5 Jang symbolize?",
            koreanTerm = "Taegeuk 5 Jang (Son)",
            options = listOf("Son (Wind) - combining gentle breeze with devastating hurricane", "Jin (Thunder)", "Gam (Water)", "Gon (Earth)"),
            correctIndex = 0,
            explanation = "Taegeuk 5 Jang represents Son (Wind), transitioning smoothly between calm movements and stormy, powerful strikes."
        ),
        QuizQuestion(
            id = "br2",
            beltRank = BeltRank.BLUE_RED_STRIPE,
            question = "Which stance is introduced in Taegeuk 5 Jang?",
            koreanTerm = "Koa-seogi (Cross Stance)",
            options = listOf("Koa-seogi (Cross Stance)", "Moa-seogi (Close Stance)", "Beom-seogi (Tiger Stance)", "Hakdari-seogi (Crane Stance)"),
            correctIndex = 0,
            explanation = "Taegeuk 5 Jang introduces Koa-seogi (Cross Stance) before stepping into the final elbow target strike."
        ),

        // RED BELT (4th Geup)
        QuizQuestion(
            id = "r1",
            beltRank = BeltRank.RED,
            question = "Why does Red Belt symbolize 'Danger'?",
            koreanTerm = "Hong-tti (Red Belt)",
            options = listOf(
                "The student has high technical skill and power, requiring utmost self-control and caution",
                "The student is likely to injure themselves",
                "The student has failed testing",
                "Sparring is prohibited for red belts"
            ),
            correctIndex = 0,
            explanation = "Red signifies danger: the practitioner has powerful skills, warning opponents to stay away and urging the practitioner to practice self-restraint."
        ),
        QuizQuestion(
            id = "r2",
            beltRank = BeltRank.RED,
            question = "What trigram represents Taegeuk 6 Jang (Yuk Jang)?",
            koreanTerm = "Taegeuk 6 Jang (Gam)",
            options = listOf("Gam (Water) - flowing continuously around obstacles", "Ri (Fire)", "Gan (Mountain)", "Son (Wind)"),
            correctIndex = 0,
            explanation = "Taegeuk 6 Jang symbolizes Gam (Water), teaching that continuous persistence erodes all barriers."
        ),

        // RED BELT W. 1 BLACK STRIPE (3rd Geup)
        QuizQuestion(
            id = "r1b1",
            beltRank = BeltRank.RED_BLACK_STRIPE_1,
            question = "What does Taegeuk 7 Jang (Chil Jang) represent?",
            koreanTerm = "Taegeuk 7 Jang (Gan)",
            options = listOf("Gan (Mountain) - immovable stability and majestic poise", "Gon (Earth)", "Keon (Heaven)", "Tae (Lake)"),
            correctIndex = 0,
            explanation = "Taegeuk 7 Jang represents Gan (Mountain), demanding steadfast balance and rock-solid stances."
        ),
        QuizQuestion(
            id = "r1b2",
            beltRank = BeltRank.RED_BLACK_STRIPE_1,
            question = "Describe the stance 'Beom-seogi' (Tiger Stance).",
            koreanTerm = "Beom-seogi (Tiger Stance)",
            options = listOf(
                "90% weight on bent rear leg, front heel raised with only ball touching floor lightly",
                "Feet shoulder-width apart with toes turned outward",
                "L-shape with 70% weight on back foot",
                "Feet together in attention stance"
            ),
            correctIndex = 0,
            explanation = "Beom-seogi places 90-100% of weight on the bent back leg, allowing the front leg to kick instantly without transferring weight."
        ),

        // RED BELT W. 2 BLACK STRIPES (2nd Geup)
        QuizQuestion(
            id = "r2b1",
            beltRank = BeltRank.RED_BLACK_STRIPE_2,
            question = "How many movements are in Taegeuk 8 Jang (Pal Jang)?",
            koreanTerm = "Taegeuk 8 Jang (Gon)",
            options = listOf("27", "20", "25", "18"),
            correctIndex = 0,
            explanation = "Taegeuk 8 Jang is the longest Taegeuk form with 27 movements, synthesizing all Geup techniques."
        ),
        QuizQuestion(
            id = "r2b2",
            beltRank = BeltRank.RED_BLACK_STRIPE_2,
            question = "What is the jumping kick executed in Taegeuk 8 Jang?",
            koreanTerm = "Du-bal Dangsang Ap-chagi",
            options = listOf("Du-bal Dangsang Ap-chagi (Jumping Double Front Kick)", "Dwi-hurigi (Spin Hook Kick)", "Dolgae-chagi (Tornado Kick)", "Mondollyo-chagi"),
            correctIndex = 0,
            explanation = "Move 3 is Du-bal Dangsang Ap-chagi, a rapid two-tempo jumping double front kick with Kihap."
        ),

        // RED BELT W. 3 BLACK STRIPES (1st Geup)
        QuizQuestion(
            id = "r3b1",
            beltRank = BeltRank.RED_BLACK_STRIPE_3,
            question = "What is the primary requirement for the 1st Geup (Cho Dan Bo) promotion exam?",
            koreanTerm = "Cho Dan Bo Promotion",
            options = listOf(
                "Comprehensive demonstration of all 8 Taegeuk forms and sparring readiness",
                "Only learning Taegeuk 1 Jang",
                "Sparring with an Olympic referee",
                "A 10-mile marathon"
            ),
            correctIndex = 0,
            explanation = "Cho Dan Bo (pre-Dan) candidates must demonstrate flawless execution of any randomly drawn Taegeuk form from 1 to 8, alongside sparring tactics, breaking, and theory."
        ),
        QuizQuestion(
            id = "r3b2",
            beltRank = BeltRank.RED_BLACK_STRIPE_3,
            question = "What does the 3rd black stripe on the red belt represent?",
            koreanTerm = "3rd Black Stripe",
            options = listOf(
                "The threshold of Black Belt, representing the final polishing of mind, body, and technique",
                "Permission to stop training",
                "A junior instructor license",
                "Exemption from promotion exams"
            ),
            correctIndex = 0,
            explanation = "The 3rd black stripe marks the final pre-black belt candidate, embodying rigorous discipline and readiness for 1st Dan."
        ),

        // 1ST DAN (Black Belt / Il Dan)
        QuizQuestion(
            id = "d1",
            beltRank = BeltRank.BLACK,
            question = "In what year was the Kukkiwon (World Taekwondo Headquarters) founded?",
            koreanTerm = "Kukkiwon",
            options = listOf("1972", "1988", "1965", "2000"),
            correctIndex = 0,
            explanation = "The Kukkiwon was founded in November 1972 in Gangnam, Seoul, South Korea."
        ),
        QuizQuestion(
            id = "d2",
            beltRank = BeltRank.BLACK,
            question = "What is the meaning of Black Belt (Il Dan)?",
            koreanTerm = "Heuk-tti (1st Dan)",
            options = listOf(
                "The opposite of white: mastery of the basics, imperviousness to fear, and the beginning of true study",
                "The final completion of all martial learning",
                "An indication that no more training is necessary",
                "Solely an honorary title"
            ),
            correctIndex = 0,
            explanation = "Black belt is not the end, but the beginning. It signifies mastery of the fundamentals and readiness to embark on the true martial way."
        )
    )
}
