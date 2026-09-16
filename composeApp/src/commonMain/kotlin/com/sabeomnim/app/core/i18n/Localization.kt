package com.sabeomnim.app.core.i18n

import androidx.compose.runtime.compositionLocalOf

enum class AppLanguage(val code: String, val displayName: String, val flag: String) {
    DANISH("da", "Dansk", "🇩🇰"),
    ENGLISH("en", "English", "🇬🇧")
}

val LocalAppLanguage = compositionLocalOf { AppLanguage.DANISH }

object AppStrings {
    // Navigation Tabs
    fun tabCurriculum(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Pensum" else "Curriculum"
    fun tabTaegeuk(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Taegeuk" else "Taegeuks"
    fun tabPoomsae(lang: AppLanguage) = tabTaegeuk(lang)
    fun tabGlossary(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Ordbog" else "Glossary"
    fun tabQuiz(lang: AppLanguage) = "Quiz"

    // Video Player
    fun btnFront(lang: AppLanguage) = "Front"
    fun btnSide(lang: AppLanguage) = "Side"
    fun viewFront(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "📷 FORFRA" else "📷 FRONT VIEW"
    fun viewSide(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "📷 FRA SIDEN" else "📷 SIDE VIEW"
    fun modeVideo(lang: AppLanguage) = "Video"
    fun modeCheatSheet(lang: AppLanguage) = "Diagram"
    fun stepLabel(lang: AppLanguage, index: Int, total: Int) =
        if (lang == AppLanguage.DANISH) "TRIN ${index.toString().padStart(2, '0')} / $total"
        else "STEP ${index.toString().padStart(2, '0')} / $total"
    fun stepLoopActive(lang: AppLanguage) =
        if (lang == AppLanguage.DANISH) "⟲ Gentag trin aktiv" else "⟲ Step Loop Active"
    fun stanceLabel(lang: AppLanguage, stance: String) =
        if (lang == AppLanguage.DANISH) "Stand: $stance" else "Stance: $stance"
    fun moveLabel(lang: AppLanguage, move: String) =
        if (lang == AppLanguage.DANISH) "Teknik: $move" else "Move: $move"
    fun movementChecklist(lang: AppLanguage, count: Int) =
        if (lang == AppLanguage.DANISH) "Komplet teknikliste ($count trin)" else "Full Movement Checklist ($count steps)"
    fun moves(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "trin" else "Moves"
    fun movementsCount(lang: AppLanguage, count: Int) = if (lang == AppLanguage.DANISH) "$count bevægelser" else "$count Movements"
    fun dualAngleVideoBadge(lang: AppLanguage) =
        if (lang == AppLanguage.DANISH) "Dobbeltvinkel HD Video + Tekster" else "Dual-Angle HD Video + Subtitles"
    fun cheatSheetTitle(lang: AppLanguage, number: Int) =
        if (lang == AppLanguage.DANISH) "Taegeuk $number Jang oversigt" else "Taegeuk $number Jang Cheat Sheet"
    fun kihapStep(lang: AppLanguage, step: Int) =
        if (lang == AppLanguage.DANISH) "⚡ Kihap: Trin $step" else "⚡ Kihap: Step $step"
    fun diagramTitle(lang: AppLanguage) =
        if (lang == AppLanguage.DANISH) "Officielt bevægelsesdiagram" else "Official Movement Diagram"
    fun diagramTip(lang: AppLanguage) =
        if (lang == AppLanguage.DANISH) "💡 Knib for at zoome • Træk for at panorere • Tryk ⛶ for fuld skærm"
        else "💡 Pinch to zoom • Drag to pan • Tap ⛶ for full-screen view"
    fun stepDirectoryTitle(lang: AppLanguage) =
        if (lang == AppLanguage.DANISH) "Trinvis bevægelsesliste" else "Choreography Step Directory"
    fun readyBadge(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "KLAR" else "READY"
    fun readyTitle(lang: AppLanguage) =
        if (lang == AppLanguage.DANISH) "Kibon Junbi-seogi (Klarstand)" else "Kibon Junbi-seogi (Ready Stance)"
    fun readyDescription(lang: AppLanguage) =
        if (lang == AppLanguage.DANISH) "Grundlæggende klarstand • Vendt mod frontlinje A, parallelstand, knytnæver ved solar plexus"
        else "Basic Ready Stance • Facing front line A, parallel stance, fists at solar plexus level"
    fun baroBadge(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "AFSLUT" else "BARO"
    fun baroTitle(lang: AppLanguage) =
        if (lang == AppLanguage.DANISH) "Baro / Shwieo (Afslut & Hvil)" else "Baro / Shwieo (Return & Rest)"
    fun baroDescription(lang: AppLanguage) =
        if (lang == AppLanguage.DANISH) "Vend tilbage til klarstand ved at føre venstre fod tilbage til udgangspositionen. Hils og hvil."
        else "Return to ready stance by drawing left foot back to original position. Bow and rest."

    // Belt Dashboard
    fun selectBeltGrade(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Vælg bæltegrad" else "Select Belt Grade"
    fun trainingLabel(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Træning" else "Training"
    fun monthsSuffix(lang: AppLanguage, mos: Int) = if (lang == AppLanguage.DANISH) "$mos mdr" else "$mos Mos"
    fun techniquesLabel(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Teknikker" else "Techniques"
    fun requiredSuffix(lang: AppLanguage, count: Int) = if (lang == AppLanguage.DANISH) "$count krav" else "$count Req"
    fun gradingExamTitle(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Teoriprøve til graduering" else "Grading Theory Exam"
    fun examQuestionsSubtitle(lang: AppLanguage, count: Int, grade: String) =
        if (lang == AppLanguage.DANISH) "$count spørgsmål til $grade" else "$count Questions for $grade"
    fun questionsCount(lang: AppLanguage, grade: String) = examQuestionsSubtitle(lang, 0, grade).substringAfter("0 ")
    fun requiredTechniquesHeader(lang: AppLanguage, grade: String) =
        if (lang == AppLanguage.DANISH) "Pensumteknikker til $grade" else "Required Techniques for $grade"
    fun requiredTechniques(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Pensumteknikker" else "Required Techniques"

    // Dictionary
    fun searchPlaceholder(lang: AppLanguage) =
        if (lang == AppLanguage.DANISH) "Søg på dansk, engelsk eller romanisering..."
        else "Search English, Danish, or Romanization..."
    fun slowAudio(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "🐢 Langsom" else "🐢 Slow"
    fun normalSpeed(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "🐰 Normal" else "🐰 Normal"
    fun allCategory(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Alle" else "All"
    fun allCategories(lang: AppLanguage) = allCategory(lang)
    fun termsCount(lang: AppLanguage, total: Int) =
        if (lang == AppLanguage.DANISH) "af $total termer" else "of $total terms"
    fun tapToHearAudio(lang: AppLanguage) =
        if (lang == AppLanguage.DANISH) "Tryk 🔊 for koreansk udtale" else "Tap 🔊 to hear Korean"

    // Quiz
    fun quizTitle(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Gradueringsprøve" else "Grading Theory Exam"
    fun questionCounter(lang: AppLanguage, current: Int, total: Int) =
        if (lang == AppLanguage.DANISH) "Spørgsmål $current af $total" else "Question $current of $total"
    fun submitAnswer(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Bekræft svar" else "Submit Answer"
    fun nextQuestion(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Næste" else "Next"
    fun finishQuiz(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Afslut prøve" else "Finish Exam"
    fun scoreLabel(lang: AppLanguage, score: Int, total: Int) =
        if (lang == AppLanguage.DANISH) "Din score: $score / $total" else "Your Score: $score / $total"
    fun passedPromotion(lang: AppLanguage) =
        if (lang == AppLanguage.DANISH) "🎉 KLAR TIL GRADUERING!" else "🎉 PROMOTION READY!"
    fun needsStudy(lang: AppLanguage) =
        if (lang == AppLanguage.DANISH) "📚 KRÆVER MERE TRÆNING (Prøv igen)" else "📚 NEEDS STUDY (Retake)"
    fun retakeQuiz(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Tag prøve igen" else "Retake Exam"

    // Settings
    fun settingsTitle(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Indstillinger" else "Settings"
    fun settingsLanguage(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Sprog / Language" else "Language"
    fun settingsTheme(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Udseende & Mørk tilstand" else "Appearance & Dark Mode"
    fun themeSystem(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Systemstandard" else "System default"
    fun themeLight(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Lys tilstand" else "Light mode"
    fun themeDark(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Mørk tilstand" else "Dark mode"
    fun settingsAbout(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Om Sabeomnim" else "About Sabeomnim"
    fun settingsAboutDesc(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Taekwondo pensum fra 10. Kup til 1. Dan (Kukkiwon / WT)" else "Taekwondo Curriculum from 10th Geup to 1st Dan (Kukkiwon / WT)"
    fun settingsClose(lang: AppLanguage) = if (lang == AppLanguage.DANISH) "Luk" else "Close"
}
