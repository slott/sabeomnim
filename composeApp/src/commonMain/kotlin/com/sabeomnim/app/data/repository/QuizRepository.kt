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
            koreanTerm = "태권도 (Taekwondo)",
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
            koreanTerm = "백띠 (Baek-tti)",
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
            koreanTerm = "경례 (Gyeong-nye)",
            options = listOf("Charyeot", "Gyeong-nye", "Junbi", "Sijak"),
            correctIndex = 1,
            explanation = "Gyeong-nye (경례) is the command to bow to show respect."
        ),
        QuizQuestion(
            id = "w4",
            beltRank = BeltRank.WHITE,
            question = "Which striking surface is used in an Ap-chagi (Front Snap Kick)?",
            koreanTerm = "앞축 (Ap-chuk)",
            options = listOf("The ball of the foot (Ap-chuk)", "The heel (Dwichuk)", "The blade of the foot (Bal-nal)", "The knee (Mureup)"),
            correctIndex = 0,
            explanation = "Ap-chagi primarily strikes using the ball of the foot (Ap-chuk) with toes pulled back."
        ),

        // YELLOW STRIPE & YELLOW BELT (9th & 8th Geup)
        QuizQuestion(
            id = "y1",
            beltRank = BeltRank.YELLOW,
            question = "What does Taegeuk 1 Jang symbolize?",
            koreanTerm = "태극 1장 - 건 (Keon)",
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
            koreanTerm = "18개 동작",
            options = listOf("16", "18", "20", "22"),
            correctIndex = 1,
            explanation = "Taegeuk 1 Jang has exactly 18 movements ending in a right forward stance middle punch with Kihap."
        ),
        QuizQuestion(
            id = "y3",
            beltRank = BeltRank.YELLOW,
            question = "What is the difference between Baro-jireugi and Bandae-jireugi?",
            koreanTerm = "바로지르기 vs 반대지르기",
            options = listOf(
                "Baro uses the hand matching the front leg in walking stance; Bandae uses the opposite hand",
                "Baro is high punch; Bandae is low punch",
                "Baro is open hand; Bandae is clenched fist",
                "Baro uses both hands simultaneously"
            ),
            correctIndex = 0,
            explanation = "Baro-jireugi punches with the same side hand as the forward foot in walking stance; Bandae-jireugi punches with the opposite side hand (reverse punch)."
        ),

        // GREEN STRIPE & GREEN BELT (7th & 6th Geup)
        QuizQuestion(
            id = "g1",
            beltRank = BeltRank.GREEN,
            question = "What does Taegeuk 3 Jang represent in the Eight Trigrams?",
            koreanTerm = "태극 3장 - 리 (Ri)",
            options = listOf("Ri (Fire and Sun)", "Tae (Lake)", "Son (Wind)", "Gan (Mountain)"),
            correctIndex = 0,
            explanation = "Taegeuk 3 Jang symbolizes 'Ri' (Fire), representing enthusiasm, warmth, and focused intensity."
        ),
        QuizQuestion(
            id = "g2",
            beltRank = BeltRank.GREEN,
            question = "In Dwit-kubi (Back Stance), what is the weight distribution between rear and front legs?",
            koreanTerm = "뒷굽이 (Dwit-kubi)",
            options = listOf("70% rear leg, 30% front leg", "50% rear, 50% front", "90% rear, 10% front", "60% front, 40% rear"),
            correctIndex = 0,
            explanation = "Dwit-kubi places approximately 70% of the body weight on the rear leg and 30% on the front leg."
        ),
        QuizQuestion(
            id = "g3",
            beltRank = BeltRank.GREEN,
            question = "What part of the hand is used in Sonnal Mok-chigi?",
            koreanTerm = "손날 (Sonnal)",
            options = listOf(
                "The knife-edge (outer blade) of the open hand",
                "The palm heel (Batangson)",
                "The knuckles of the backfist",
                "The tips of the fingers"
            ),
            correctIndex = 0,
            explanation = "Sonnal refers to the knife-hand blade, striking the opponent's neck."
        ),

        // BLUE STRIPE & BLUE BELT (5th & 4th Geup)
        QuizQuestion(
            id = "b1",
            beltRank = BeltRank.BLUE,
            question = "What does the Blue Belt symbolize?",
            koreanTerm = "파란띠 (Cheong-tti)",
            options = listOf(
                "The blue sky towards which the plant grows into a tall tree",
                "The ocean of peace and serenity",
                "The fire of destruction",
                "The earth from which roots grow"
            ),
            correctIndex = 0,
            explanation = "Blue represents the sky towards which the plant matures as Taekwondo technique ripens."
        ),
        QuizQuestion(
            id = "b2",
            beltRank = BeltRank.BLUE,
            question = "What does Taegeuk 5 Jang symbolize?",
            koreanTerm = "태극 5장 - 손 (Son)",
            options = listOf("Son (Wind)", "Jin (Thunder)", "Gam (Water)", "Keon (Heaven)"),
            correctIndex = 0,
            explanation = "Taegeuk 5 Jang symbolizes 'Son' (Wind), combining flexible gentle transitions with destructive sudden gusts."
        ),

        // RED STRIPE & RED BELT (3rd & 2nd Geup)
        QuizQuestion(
            id = "r1",
            beltRank = BeltRank.RED,
            question = "Why does Red Belt symbolize 'Danger'?",
            koreanTerm = "빨간띠 (Hong-tti)",
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
            question = "Describe the stance 'Beom-seogi' (Tiger Stance).",
            koreanTerm = "범서기 (Beom-seogi)",
            options = listOf(
                "90% weight on bent rear leg, front heel raised with only ball touching floor lightly",
                "Feet shoulder-width apart with toes turned outward",
                "L-shape with 70% weight on back foot",
                "Feet together in attention stance"
            ),
            correctIndex = 0,
            explanation = "Beom-seogi places 90-100% of weight on the bent back leg, allowing the front leg to kick instantly without transferring weight."
        ),

        // BLACK STRIPE & 1ST DAN (1st Geup & Il Dan)
        QuizQuestion(
            id = "d1",
            beltRank = BeltRank.BLACK_STRIPE,
            question = "How many movements are in Taegeuk 8 Jang (Pal Jang)?",
            koreanTerm = "태극 8장",
            options = listOf("27", "20", "25", "18"),
            correctIndex = 0,
            explanation = "Taegeuk 8 Jang is the longest Taegeuk form with 27 movements, culminating in Dan-level readiness."
        ),
        QuizQuestion(
            id = "d2",
            beltRank = BeltRank.BLACK,
            question = "In what year was the Kukkiwon (World Taekwondo Headquarters) founded?",
            koreanTerm = "국기원 (Kukkiwon)",
            options = listOf("1972", "1988", "1965", "2000"),
            correctIndex = 0,
            explanation = "The Kukkiwon was founded in November 1972 in Gangnam, Seoul, South Korea."
        ),
        QuizQuestion(
            id = "d3",
            beltRank = BeltRank.BLACK,
            question = "What is the meaning of Black Belt (Il Dan)?",
            koreanTerm = "검은띠 / 1단",
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
