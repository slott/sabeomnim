# Sabeomnim — Romanization UI & Slower Belt Unfolding Report

**Author**: Agent Smith  
**Target Platform**: Android & iOS (Compose Multiplatform)  
**Date**: September 2026  

---

## 1. Summary of Changes

In response to your directions:
1. **Stick with Romanization & English (leave out Korean Hangul writing)**:
   - All user-facing UI labels, titles, badges, subtitles, explanations, and navigation elements have been switched strictly to Revised Romanization and English translations.
   - Internal Hangul text is retained strictly in background data models so the native Text-to-Speech (TTS) engine can synthesize pronunciation accurately when users tap the speaker icons.
2. **Slower, More Graceful Belt Unfolding Animation**:
   - Replaced fast bounce physics (`dampingRatio = 0.62f, stiffness = Spring.StiffnessLow`) with a smooth 1,350 ms cloth drape tween (`FastOutSlowInEasing`).
   - The martial arts belt now unrolls and unfolds gently down the side with its colored rank stripe, embroidered knot, stitch lines, and pendulum motion.

---

## 2. Updated Components

### 2.1 [UnfoldingBeltView.kt](file:///Users/msh/git/sabeomnim/composeApp/src/commonMain/kotlin/com/sabeomnim/app/core/ui/belt/UnfoldingBeltView.kt)
- **Animation timing**: Slowed down to `tween<Float>(durationMillis = 1350, easing = FastOutSlowInEasing)`.
- **Interactivity**: Belt triggers the 1.35s roll animation both on belt grade selection and on direct tap (`Tap to unfold`).

### 2.2 [BeltDashboardScreen.kt](file:///Users/msh/git/sabeomnim/composeApp/src/commonMain/kotlin/com/sabeomnim/app/presentation/dashboard/BeltDashboardScreen.kt)
- **Top Bar**: Changed from Korean subtitle to clean English `"Sabeomnim"`.
- **Belt Hero Card**: Displays belt grade in Romanization (`"Baek-tti"`, `"No-rang-tti"`, etc.).
- **Poomsae Card**: Shows Romanized title and trigram symbol (`"Taegeuk 1 Il Jang (☰)"`).
- **Techniques List**: Primary bold header is the Romanized Korean technique name (`"Moa-seogi / Naranhi-seogi"`, `"Arae-makgi"`, etc.), followed by the English subtitle (`"Attention & Parallel Stance"`).

### 2.3 [TaegeukCheatSheetView.kt](file:///Users/msh/git/sabeomnim/composeApp/src/commonMain/kotlin/com/sabeomnim/app/presentation/poomsae/TaegeukCheatSheetView.kt)
- **Cheat Sheet Title**: `"Taegeuk X Jang Cheat Sheet"`.
- **Ready Card**: `"READY"` avatar badge, `"Kibon Junbi-seogi (Ready Stance)"`.
- **Return Card**: `"BARO"` avatar badge, `"Baro / Shwieo (Return & Rest)"`.
- **Step Cards**: Romanized primary title (e.g. `"Oreun Ap-seogi Momtong Baro-jireugi"`), English translation, stance and action chips, coaching tip, and Kihap indicators (`⚡ KIHAP`).

### 2.4 [AudioDictionaryScreen.kt](file:///Users/msh/git/sabeomnim/composeApp/src/commonMain/kotlin/com/sabeomnim/app/presentation/dictionary/AudioDictionaryScreen.kt)
- **Top Bar**: `"Glossary & Audio"`.
- **Search Bar**: `"Search English or Romanization..."`.
- **Vocabulary Cards**: Bold Romanized title (`Taekwondo`, `Dojang`, `Dobok`, `Charyeot`, `Kyeong-rye`), category badge, English translation, and audio playback button.

### 2.5 [QuizScreen.kt](file:///Users/msh/git/sabeomnim/composeApp/src/commonMain/kotlin/com/sabeomnim/app/presentation/quiz/QuizScreen.kt) & [QuizRepository.kt](file:///Users/msh/git/sabeomnim/composeApp/src/commonMain/kotlin/com/sabeomnim/app/data/repository/QuizRepository.kt)
- **Top Bar**: `"Grading Theory Exam"`.
- **Question Badges & Explanations**: 100% Romanized and English (e.g. `"Taegeuk 1 Jang (Keon)"`, `"Dwit-kubi (Back Stance)"`).
- **Pass / Fail Banner**: `"🎉 PROMOTION READY!"` and `"📚 NEEDS STUDY (Retake)"`.

---

## 3. Verification & Emulator Testing

- **Compilation**: Gradle task `:composeApp:installDebug` built and installed in 6 seconds.
- **ADB Execution**: App launched on Pixel 9 emulator (`com.sabeomnim.app/.MainActivity`).
- **Visual Verification**:
  - Main Dashboard: Verified belt unrolling, smooth drape, Romanized headings.
  - Taegeuks & Visual Cheat Sheet: Verified step diagram, Romanized move titles, audio button.
  - Glossary & Audio: Tested search and category filters with clean Romanized typography.
  - Belt Theory Quiz: Tested question rendering and grading exam flow.
