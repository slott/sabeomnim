package com.sabeomnim.app.data.repository

import com.sabeomnim.app.data.models.BeltRank
import com.sabeomnim.app.data.models.QuizQuestion

object QuizRepository {

    fun getQuestionsForBelt(rank: BeltRank): List<QuizQuestion> =
        questionList.filter { it.beltRank == rank }

    fun getAllQuestions(): List<QuizQuestion> = questionList

    private val questionList: List<QuizQuestion> by lazy {
        curatedQuestions + MasterNimQuizQuestions.allQuestions
    }

    private val curatedQuestions = listOf(
        // WHITE BELT (10th Geup)
        QuizQuestion(
            id = "w1",
            beltRank = BeltRank.WHITE,
            question = "What is the literal translation of the word 'Taekwondo'?",
            questionDanish = "Hvad er den bogstavelige oversættelse af ordet 'Taekwondo'?",
            koreanTerm = "Taekwondo",
            options = listOf(
                "The way of the foot and the hand",
                "The art of internal peace",
                "The ancient way of the spear",
                "The sport of kicks and strikes"
            ),
            optionsDanish = listOf(
                "Fodens og håndens vej",
                "Den indre freds kunst",
                "Spydets ældgamle vej",
                "Sporten med spark og slag"
            ),
            correctIndex = 0,
            explanation = "'Tae' means foot/kicking, 'Kwon' means hand/punching, and 'Do' means the art, path, or way of life.",
            explanationDanish = "'Tae' betyder fod/spark, 'Kwon' betyder hånd/slag, og 'Do' betyder kunsten, vejen eller livsvejen.",
            category = "Theory & Commands"
        ),
        QuizQuestion(
            id = "w2",
            beltRank = BeltRank.WHITE,
            question = "What does the White Belt symbolize?",
            questionDanish = "Hvad symboliserer det hvide bælte?",
            koreanTerm = "Baek-tti (White Belt)",
            options = listOf(
                "Innocence and having no prior knowledge; a blank canvas",
                "The danger of the journey ahead",
                "The tree reaching for the sky",
                "The master of the training hall"
            ),
            optionsDanish = listOf(
                "Uskyld og mangel på forudgående viden; et tomt lærred",
                "Faren ved rejsen forude",
                "Træet der rækker mod himlen",
                "Mesteren i træningssalen"
            ),
            correctIndex = 0,
            explanation = "White signifies innocence and purity—the student has no prior knowledge of Taekwondo.",
            explanationDanish = "Hvidt bælte betyder uskyld og renhed – eleven har ingen forudgående viden om Taekwondo.",
            category = "Theory & Commands"
        ),
        QuizQuestion(
            id = "w3",
            beltRank = BeltRank.WHITE,
            question = "Which Korean command means 'Bow'?",
            questionDanish = "Hvilken koreansk kommando betyder 'Buk' / 'Hils'?",
            koreanTerm = "Gyeong-nye",
            options = listOf("Charyeot", "Gyeong-nye", "Junbi", "Sijak"),
            optionsDanish = listOf("Charyeot", "Gyeong-nye", "Junbi", "Sijak"),
            correctIndex = 1,
            explanation = "Gyeong-nye is the command to bow to show respect.",
            explanationDanish = "Gyeong-nye er kommandoen til at bukke for at vise respekt.",
            category = "Theory & Commands"
        ),
        QuizQuestion(
            id = "w4",
            beltRank = BeltRank.WHITE,
            question = "Which striking surface is used in an Ap-chagi (Front Snap Kick)?",
            questionDanish = "Hvilken træfflade bruges i et Ap-chagi (Frontspark)?",
            koreanTerm = "Ap-chuk",
            options = listOf("The ball of the foot (Ap-chuk)", "The heel (Dwichuk)", "The blade of the foot (Bal-nal)", "The knee (Mureup)"),
            optionsDanish = listOf("Fodbalden (Ap-chuk)", "Hælen (Dwichuk)", "Fodkniven (Bal-nal)", "Knæet (Mureup)"),
            correctIndex = 0,
            explanation = "Ap-chagi primarily strikes using the ball of the foot (Ap-chuk) with toes pulled back.",
            explanationDanish = "Ap-chagi rammer primært med fodbalden (Ap-chuk) med tæerne trukket tilbage.",
            category = "Kicking Techniques"
        ),

        // YELLOW BELT (9th Geup)
        QuizQuestion(
            id = "y1",
            beltRank = BeltRank.YELLOW,
            question = "What does Taegeuk 1 Jang symbolize?",
            questionDanish = "Hvad symboliserer Taegeuk 1 Jang?",
            koreanTerm = "Taegeuk 1 Jang (Keon)",
            options = listOf(
                "Keon (Heaven/Sky) - the origin of all creation",
                "Gam (Water) - flow and continuous motion",
                "Jin (Thunder) - immense sudden power",
                "Gon (Earth) - pure receptivity"
            ),
            optionsDanish = listOf(
                "Keon (Himlen) - oprindelsen til al skabelse",
                "Gam (Vand) - flow og kontinuerlig bevægelse",
                "Jin (Torden) - enorm pludselig kraft",
                "Gon (Jorden) - ren modtagelighed"
            ),
            correctIndex = 0,
            explanation = "Taegeuk 1 Jang represents 'Keon', symbolizing Heaven and the origin of life.",
            explanationDanish = "Taegeuk 1 Jang repræsenterer 'Keon', der symboliserer Himlen og livets oprindelse.",
            category = "Theory & Commands"
        ),
        QuizQuestion(
            id = "y2",
            beltRank = BeltRank.YELLOW,
            question = "How many movements are in Taegeuk 1 Jang?",
            questionDanish = "Hvor mange bevægelser er der i Taegeuk 1 Jang?",
            koreanTerm = "18 Movements",
            options = listOf("16", "18", "20", "22"),
            optionsDanish = listOf("16", "18", "20", "22"),
            correctIndex = 1,
            explanation = "Taegeuk 1 Jang has exactly 18 movements ending in a right forward stance middle punch with Kihap.",
            explanationDanish = "Taegeuk 1 Jang har præcis 18 bevægelser og slutter med et højre lang stand midterslag med Kihap.",
            category = "Theory & Commands"
        ),
        QuizQuestion(
            id = "y3",
            beltRank = BeltRank.YELLOW,
            question = "What is the difference between Baro-jireugi and Bandae-jireugi?",
            questionDanish = "Hvad er forskellen mellem Baro-jireugi og Bandae-jireugi?",
            koreanTerm = "Baro vs Bandae-jireugi",
            options = listOf(
                "Baro uses the hand matching the front leg in walking stance; Bandae uses the opposite hand",
                "Baro is high punch; Bandae is low punch",
                "Baro is open hand; Bandae is clenched fist",
                "Baro uses both hands simultaneously"
            ),
            optionsDanish = listOf(
                "Baro bruger hånden svarende til forreste ben; Bandae bruger modsat hånd",
                "Baro er højt slag; Bandae er lavt slag",
                "Baro er åben hånd; Bandae er knyttet hånd",
                "Baro bruger begge hænder samtidigt"
            ),
            correctIndex = 0,
            explanation = "Baro-jireugi punches with the same side hand as the forward foot in walking stance; Bandae-jireugi punches with the opposite side hand (reverse punch).",
            explanationDanish = "Baro-jireugi slår med samme sides arm som forreste ben; Bandae-jireugi slår med modsat sides arm (omvendt slag).",
            category = "Hand Techniques"
        ),

        // ORANGE BELT (8th Geup)
        QuizQuestion(
            id = "o1",
            beltRank = BeltRank.ORANGE,
            question = "What does Taegeuk 2 Jang (Ee Jang) represent?",
            questionDanish = "Hvad repræsenterer Taegeuk 2 Jang (Ee Jang)?",
            koreanTerm = "Taegeuk 2 Jang (Tae)",
            options = listOf(
                "Tae (Lake / Joy) - inner firmness with external calmness",
                "Ri (Fire / Sun) - passion and brightness",
                "Jin (Thunder) - sudden shocking power",
                "Keon (Heaven) - creation"
            ),
            optionsDanish = listOf(
                "Tae (Søen / Glæde) - indre fasthed med ydre ro",
                "Ri (Ild / Sol) - passion og lysstyrke",
                "Jin (Torden) - pludselig kraft",
                "Keon (Himlen) - skabelse"
            ),
            correctIndex = 0,
            explanation = "Taegeuk 2 Jang represents Tae (Lake), symbolizing internal firmness expressed through calm, joyful movement.",
            explanationDanish = "Taegeuk 2 Jang repræsenterer Tae (Søen), som symboliserer indre fasthed udtrykt gennem rolig bevægelse.",
            category = "Theory & Commands"
        ),
        QuizQuestion(
            id = "o2",
            beltRank = BeltRank.ORANGE,
            question = "What stance is introduced in the orange belt curriculum?",
            questionDanish = "Hvilken stand introduceres i 8. Kup (orange bælte) pensum?",
            koreanTerm = "Dwit-kubi",
            options = listOf("Dwit-kubi (Back Stance)", "Beom-seogi (Tiger Stance)", "Koa-seogi (Cross Stance)", "Hakdari-seogi (Crane Stance)"),
            optionsDanish = listOf("Dwit-kubi (Back Stance)", "Beom-seogi (Tigerstand)", "Koa-seogi (Krydsstand)", "Hakdari-seogi (Tranestand)"),
            correctIndex = 0,
            explanation = "Dwit-kubi (Back Stance) is introduced, placing 70% of the body weight on the bent rear leg.",
            explanationDanish = "Dwit-kubi introduceres, hvor 70% af kropsvægten placeres på det bøjede bagerste ben.",
            category = "Stances"
        ),
        QuizQuestion(
            id = "o3",
            beltRank = BeltRank.ORANGE,
            question = "Where is the target for an Olgul-jireugi (High Punch)?",
            questionDanish = "Hvor er målet for et Olgul-jireugi (Højt slag)?",
            koreanTerm = "Olgul-jireugi",
            options = listOf("The philtrum / nose area", "The solar plexus", "The lower abdomen", "The collarbone"),
            optionsDanish = listOf("Næseroden / philtrum-området", "Solplexus (Momtong)", "Underlivet (Arae)", "Kravebenet"),
            correctIndex = 0,
            explanation = "Olgul attacks target between the philtrum and the bridge of the nose.",
            explanationDanish = "Olgul-angreb sigter mod området mellem næseroden og overlæben (philtrum).",
            category = "Hand Techniques"
        ),

        // GREEN BELT (7th Geup)
        QuizQuestion(
            id = "g1",
            beltRank = BeltRank.GREEN,
            question = "What does Taegeuk 3 Jang represent in the Eight Trigrams?",
            questionDanish = "Hvad repræsenterer Taegeuk 3 Jang i de otte trigrammer?",
            koreanTerm = "Taegeuk 3 Jang (Ri)",
            options = listOf("Ri (Fire and Sun)", "Tae (Lake)", "Son (Wind)", "Gan (Mountain)"),
            optionsDanish = listOf("Ri (Ild og Sol)", "Tae (Søen)", "Son (Vinden)", "Gan (Bjerget)"),
            correctIndex = 0,
            explanation = "Taegeuk 3 Jang symbolizes 'Ri' (Fire), representing enthusiasm, warmth, and focused intensity.",
            explanationDanish = "Taegeuk 3 Jang symboliserer 'Ri' (Ild), der repræsenterer entusiasme, varme og fokuseret intensitet.",
            category = "Theory & Commands"
        ),
        QuizQuestion(
            id = "g2",
            beltRank = BeltRank.GREEN,
            question = "In Dwit-kubi (Back Stance), what is the weight distribution between rear and front legs?",
            questionDanish = "Hvad er vægtfordelingen mellem bageste og forreste ben i Dwit-kubi?",
            koreanTerm = "Dwit-kubi",
            options = listOf("70% rear leg, 30% front leg", "50% rear, 50% front", "90% rear, 10% front", "60% front, 40% rear"),
            optionsDanish = listOf("70% på bagerste ben, 30% på forreste ben", "50% bagerst, 50% forrest", "90% bagerst, 10% forrest", "60% forrest, 40% bagerst"),
            correctIndex = 0,
            explanation = "Dwit-kubi places approximately 70% of the body weight on the rear leg and 30% on the front leg.",
            explanationDanish = "Dwit-kubi placerer cirka 70% af kropsvægten på det bagerste ben og 30% på det forreste ben.",
            category = "Stances"
        ),
        QuizQuestion(
            id = "g3",
            beltRank = BeltRank.GREEN,
            question = "What part of the hand is used in Sonnal Mok-chigi?",
            questionDanish = "Hvilken del af hånden bruges i Sonnal Mok-chigi?",
            koreanTerm = "Sonnal",
            options = listOf(
                "The knife-edge (outer blade) of the open hand",
                "The palm heel (Batangson)",
                "The knuckles of the backfist",
                "The tips of the fingers"
            ),
            optionsDanish = listOf(
                "Fodkniven / håndkanten af den åbne hånd",
                "Håndroden (Batangson)",
                "Knoerne på bagsiden af hånden",
                "Fingerspidserne"
            ),
            correctIndex = 0,
            explanation = "Sonnal refers to the knife-hand blade, striking the opponent's neck.",
            explanationDanish = "Sonnal refererer til håndkanten, der rammer modstanderens hals.",
            category = "Hand Techniques"
        ),

        // BLUE BELT (6th Geup)
        QuizQuestion(
            id = "b1",
            beltRank = BeltRank.BLUE,
            question = "What does Taegeuk 4 Jang symbolize?",
            questionDanish = "Hvad symboliserer Taegeuk 4 Jang?",
            koreanTerm = "Taegeuk 4 Jang (Jin)",
            options = listOf("Jin (Thunder) - great dignity and explosive power", "Son (Wind)", "Gam (Water)", "Keon (Heaven)"),
            optionsDanish = listOf("Jin (Torden) - stor værdighed og eksplosiv kraft", "Son (Vind)", "Gam (Vand)", "Keon (Himmel)"),
            correctIndex = 0,
            explanation = "Taegeuk 4 Jang symbolizes 'Jin' (Thunder), demonstrating calm poise that erupts into sudden, decisive power.",
            explanationDanish = "Taegeuk 4 Jang symboliserer 'Jin' (Torden), der viser rolig balance, som bryder ud i pludselig kraft.",
            category = "Theory & Commands"
        ),
        QuizQuestion(
            id = "b2",
            beltRank = BeltRank.BLUE,
            question = "What technique is Pyeon-son-kkeut Jjireugi?",
            questionDanish = "Hvilken teknik er Pyeon-son-kkeut Jjireugi?",
            koreanTerm = "Pyeon-son-kkeut Jjireugi",
            options = listOf("Spear-hand thrust", "Hammerfist downward strike", "Elbow turning strike", "Scissors block"),
            optionsDanish = listOf("Spydhåndsstik", "Nedafgående hammerhåndsslag", "Albuedrejeslag", "Sakseblokering"),
            correctIndex = 0,
            explanation = "Pyeon-son-kkeut Jjireugi is a spear-hand thrust targeting the solar plexus, supported with an underlying palm.",
            explanationDanish = "Pyeon-son-kkeut Jjireugi er et spydhåndsstik mod solar plexus, støttet med en underliggende håndflade.",
            category = "Hand Techniques"
        ),

        // BLUE BELT W. RED STRIPE (5th Geup)
        QuizQuestion(
            id = "br1",
            beltRank = BeltRank.BLUE_RED_STRIPE,
            question = "What does Taegeuk 5 Jang symbolize?",
            questionDanish = "Hvad symboliserer Taegeuk 5 Jang?",
            koreanTerm = "Taegeuk 5 Jang (Son)",
            options = listOf("Son (Wind) - combining gentle breeze with devastating hurricane", "Jin (Thunder)", "Gam (Water)", "Gon (Earth)"),
            optionsDanish = listOf("Son (Vind) - kombination af blid brise og ødelæggende orkan", "Jin (Torden)", "Gam (Vand)", "Gon (Jorden)"),
            correctIndex = 0,
            explanation = "Taegeuk 5 Jang represents Son (Wind), transitioning smoothly between calm movements and stormy, powerful strikes.",
            explanationDanish = "Taegeuk 5 Jang repræsenterer Son (Vind), som bevæger sig mellem rolige bevægelser og stormfulde slag.",
            category = "Theory & Commands"
        ),
        QuizQuestion(
            id = "br2",
            beltRank = BeltRank.BLUE_RED_STRIPE,
            question = "Which stance is introduced in Taegeuk 5 Jang?",
            questionDanish = "Hvilken stand introduceres i Taegeuk 5 Jang?",
            koreanTerm = "Koa-seogi",
            options = listOf("Koa-seogi (Cross Stance)", "Moa-seogi (Close Stance)", "Beom-seogi (Tiger Stance)", "Hakdari-seogi (Crane Stance)"),
            optionsDanish = listOf("Koa-seogi (Krydsstand)", "Moa-seogi (Samlet stand)", "Beom-seogi (Tigerstand)", "Hakdari-seogi (Tranestand)"),
            correctIndex = 0,
            explanation = "Taegeuk 5 Jang introduces Koa-seogi (Cross Stance) before stepping into the final elbow target strike.",
            explanationDanish = "Taegeuk 5 Jang introducerer Koa-seogi (Krydsstand) forud for det afsluttende albuemålslag.",
            category = "Stances"
        ),

        // RED BELT (4th Geup)
        QuizQuestion(
            id = "r1",
            beltRank = BeltRank.RED,
            question = "Why does Red Belt symbolize 'Danger'?",
            questionDanish = "Hvorfor symboliserer det røde bælte 'Fare'?",
            koreanTerm = "Hong-tti",
            options = listOf(
                "The student has high technical skill and power, requiring utmost self-control and caution",
                "The student is likely to injure themselves",
                "The student has failed testing",
                "Sparring is prohibited for red belts"
            ),
            optionsDanish = listOf(
                "Eleven har høj teknisk kunnen og kraft, hvilket kræver stor selvkontrol og forsigtighed",
                "Eleven risikerer at skade sig selv",
                "Eleven er dumpet til graduering",
                "Frikamp er forbudt for rødbælter"
            ),
            correctIndex = 0,
            explanation = "Red signifies danger: the practitioner has powerful skills, warning opponents to stay away and urging the practitioner to practice self-restraint.",
            explanationDanish = "Rød farve betyder fare: udøveren besidder stærke teknikker og må udvise selvkontrol.",
            category = "Theory & Commands"
        ),
        QuizQuestion(
            id = "r2",
            beltRank = BeltRank.RED,
            question = "What trigram represents Taegeuk 6 Jang (Yuk Jang)?",
            questionDanish = "Hvilket trigram repræsenterer Taegeuk 6 Jang (Yuk Jang)?",
            koreanTerm = "Taegeuk 6 Jang (Gam)",
            options = listOf("Gam (Water) - flowing continuously around obstacles", "Ri (Fire)", "Gan (Mountain)", "Son (Wind)"),
            optionsDanish = listOf("Gam (Vand) - flyder uafbrudt udenom forhindringer", "Ri (Ild)", "Gan (Bjerget)", "Son (Vinden)"),
            correctIndex = 0,
            explanation = "Taegeuk 6 Jang symbolizes Gam (Water), teaching that continuous persistence erodes all barriers.",
            explanationDanish = "Taegeuk 6 Jang symboliserer Gam (Vand), som lærer at vedholdenhed overvinder alle barrierer.",
            category = "Theory & Commands"
        ),

        // RED BELT W. 1 BLACK STRIPE (3rd Geup)
        QuizQuestion(
            id = "r1b1",
            beltRank = BeltRank.RED_BLACK_STRIPE_1,
            question = "What does Taegeuk 7 Jang (Chil Jang) represent?",
            questionDanish = "Hvad repræsenterer Taegeuk 7 Jang (Chil Jang)?",
            koreanTerm = "Taegeuk 7 Jang (Gan)",
            options = listOf("Gan (Mountain) - immovable stability and majestic poise", "Gon (Earth)", "Keon (Heaven)", "Tae (Lake)"),
            optionsDanish = listOf("Gan (Bjerget) - urokkelig stabilitet og majestætisk ro", "Gon (Jorden)", "Keon (Himlen)", "Tae (Søen)"),
            correctIndex = 0,
            explanation = "Taegeuk 7 Jang represents Gan (Mountain), demanding steadfast balance and rock-solid stances.",
            explanationDanish = "Taegeuk 7 Jang repræsenterer Gan (Bjerget) og kræver klippefast balance og stabile stande.",
            category = "Theory & Commands"
        ),
        QuizQuestion(
            id = "r1b2",
            beltRank = BeltRank.RED_BLACK_STRIPE_1,
            question = "Describe the stance 'Beom-seogi' (Tiger Stance).",
            questionDanish = "Beskriv standen 'Beom-seogi' (Tigerstand).",
            koreanTerm = "Beom-seogi",
            options = listOf(
                "90% weight on bent rear leg, front heel raised with only ball touching floor lightly",
                "Feet shoulder-width apart with toes turned outward",
                "L-shape with 70% weight on back foot",
                "Feet together in attention stance"
            ),
            optionsDanish = listOf(
                "90% vægt på det bøjede bagerste ben, forreste hæl løftet med kun balden i gulvet",
                "Fødderne i skulderbredde med tæerne udad",
                "L-form med 70% vægt på bagerste fod",
                "Fødderne samlet i giv-agt stand"
            ),
            correctIndex = 0,
            explanation = "Beom-seogi places 90-100% of weight on the bent back leg, allowing the front leg to kick instantly without transferring weight.",
            explanationDanish = "Beom-seogi placerer 90-100% af vægten på det bagerste ben, så forreste ben kan sparke øjeblikkeligt.",
            category = "Stances"
        ),

        // RED BELT W. 2 BLACK STRIPES (2nd Geup)
        QuizQuestion(
            id = "r2b1",
            beltRank = BeltRank.RED_BLACK_STRIPE_2,
            question = "How many movements are in Taegeuk 8 Jang (Pal Jang)?",
            questionDanish = "Hvor mange bevægelser er der i Taegeuk 8 Jang (Pal Jang)?",
            koreanTerm = "Taegeuk 8 Jang (Gon)",
            options = listOf("27", "20", "25", "18"),
            optionsDanish = listOf("27", "20", "25", "18"),
            correctIndex = 0,
            explanation = "Taegeuk 8 Jang is the longest Taegeuk form with 27 movements, synthesizing all Geup techniques.",
            explanationDanish = "Taegeuk 8 Jang er den længste Taegeuk-form med 27 bevægelser.",
            category = "Theory & Commands"
        ),
        QuizQuestion(
            id = "r2b2",
            beltRank = BeltRank.RED_BLACK_STRIPE_2,
            question = "What is the jumping kick executed in Taegeuk 8 Jang?",
            questionDanish = "Hvilket flyvespark udføres i Taegeuk 8 Jang?",
            koreanTerm = "Du-bal Dangsang Ap-chagi",
            options = listOf("Du-bal Dangsang Ap-chagi (Jumping Double Front Kick)", "Dwi-hurigi (Spin Hook Kick)", "Dolgae-chagi (Tornado Kick)", "Mondollyo-chagi"),
            optionsDanish = listOf("Du-bal Dangsang Ap-chagi (Dobbelt flyvende frontspark)", "Dwi-hurigi (Krogspark med spin)", "Dolgae-chagi (Tornado-spark)", "Mondollyo-chagi"),
            correctIndex = 0,
            explanation = "Move 3 is Du-bal Dangsang Ap-chagi, a rapid two-tempo jumping double front kick with Kihap.",
            explanationDanish = "Bevægelse 3 er Du-bal Dangsang Ap-chagi, et hurtigt dobbelt flyvende frontspark med Kihap.",
            category = "Kicking Techniques"
        ),

        // RED BELT W. 3 BLACK STRIPES (1st Geup)
        QuizQuestion(
            id = "r3b1",
            beltRank = BeltRank.RED_BLACK_STRIPE_3,
            question = "What is the primary requirement for the 1st Geup (Cho Dan Bo) promotion exam?",
            questionDanish = "Hvad er hovedkravet til graduering til 1. Geup (Cho Dan Bo)?",
            koreanTerm = "Cho Dan Bo Promotion",
            options = listOf(
                "Comprehensive demonstration of all 8 Taegeuk forms and sparring readiness",
                "Only learning Taegeuk 1 Jang",
                "Sparring with an Olympic referee",
                "A 10-mile marathon"
            ),
            optionsDanish = listOf(
                "Omfattende demonstration af alle 8 Taegeuk-former samt kampfærdigheder",
                "Kun at lære Taegeuk 1 Jang",
                "Kæmpe mod en OL-dommer",
                "Løbe 10 kilometer"
            ),
            correctIndex = 0,
            explanation = "Cho Dan Bo (pre-Dan) candidates must demonstrate flawless execution of any randomly drawn Taegeuk form from 1 to 8, alongside sparring tactics, breaking, and theory.",
            explanationDanish = "Cho Dan Bo-kandidater skal kunne udføre enhver tilfældigt udtrukket Taegeuk-form fra 1 til 8 foruden kamp og teori.",
            category = "Theory & Commands"
        ),
        QuizQuestion(
            id = "r3b2",
            beltRank = BeltRank.RED_BLACK_STRIPE_3,
            question = "What does the 3rd black stripe on the red belt represent?",
            questionDanish = "Hvad repræsenterer den 3. sorte snip på det røde bælte?",
            koreanTerm = "3rd Black Stripe",
            options = listOf(
                "The threshold of Black Belt, representing the final polishing of mind, body, and technique",
                "Permission to stop training",
                "A junior instructor license",
                "Exemption from promotion exams"
            ),
            optionsDanish = listOf(
                "Tærsklen til det sorte bælte; den sidste finpudsning af sind, krop og teknik",
                "Tilladelse til at stoppe træningen",
                "En juniorinstruktørlicens",
                "Fritagelse for graduering"
            ),
            correctIndex = 0,
            explanation = "The 3rd black stripe marks the final pre-black belt candidate, embodying rigorous discipline and readiness for 1st Dan.",
            explanationDanish = "Den 3. sorte snip markerer den afsluttende kandidat før 1. Dan og symboliserer høj disciplin.",
            category = "Theory & Commands"
        ),

        // 1ST DAN (Black Belt / Il Dan)
        QuizQuestion(
            id = "d1",
            beltRank = BeltRank.BLACK,
            question = "In what year was the Kukkiwon (World Taekwondo Headquarters) founded?",
            questionDanish = "I hvilket år blev Kukkiwon (World Taekwondo Headquarters) grundlagt?",
            koreanTerm = "Kukkiwon",
            options = listOf("1972", "1988", "1965", "2000"),
            optionsDanish = listOf("1972", "1988", "1965", "2000"),
            correctIndex = 0,
            explanation = "The Kukkiwon was founded in November 1972 in Gangnam, Seoul, South Korea.",
            explanationDanish = "Kukkiwon blev grundlagt i november 1972 i Gangnam, Seoul, Sydkorea.",
            category = "Theory & Commands"
        ),
        QuizQuestion(
            id = "d2",
            beltRank = BeltRank.BLACK,
            question = "What is the meaning of Black Belt (Il Dan)?",
            questionDanish = "Hvad er betydningen af det sorte bælte (1. Dan)?",
            koreanTerm = "Heuk-tti (1st Dan)",
            options = listOf(
                "The opposite of white: mastery of the basics, imperviousness to fear, and the beginning of true study",
                "The final completion of all martial learning",
                "An indication that no more training is necessary",
                "Solely an honorary title"
            ),
            optionsDanish = listOf(
                "Det modsatte af hvid: mestring af det basale, frygtløshed og begyndelsen på sand fordybelse",
                "Den endelige afslutning på al kampsportslæring",
                "Et tegn på at videre træning er unødvendig",
                "Udelukkende en ærestitel"
            ),
            correctIndex = 0,
            explanation = "Black belt is not the end, but the beginning. It signifies mastery of the fundamentals and readiness to embark on the true martial way.",
            explanationDanish = "Det sorte bælte er ikke enden, men begyndelsen på den sande vej inden for kampsport.",
            category = "Theory & Commands"
        )
    )
}
