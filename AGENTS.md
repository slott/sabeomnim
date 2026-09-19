# AGENTS.md — Sabeomnim (사범님)

Welcome to the **Sabeomnim** project repository. This document specifies the project requirements, architecture, coding guidelines, and operational procedures for all AI agents working on this codebase.

---

## 1. Project Overview

* **Application Name**: Sabeomnim (사범님 - Taekwondo Master/Instructor)
* **Core Purpose**: Comprehensive mobile learning companion for World Taekwondo (WT / Kukkiwon) practitioners, focusing on the complete curriculum from **10th Geup (White Belt)** to **1st Dan (Black Belt / Il Dan)**.
* **Primary Platforms**: Android and iOS, built with **Compose Multiplatform (CMP)** and Kotlin Multiplatform (KMP).
* **Target Verification**: Physical Android device / emulator testing via ADB; iOS framework verification via Gradle Kotlin/Native targets.
* **CDN / Asset Delivery**: GitHub (GitHub Releases / raw repository asset hosting) serves as the CDN for Taegeuk multi-angle video files and supplementary assets.

---

## 2. Core Functional Specifications

### 2.1 Belt Progression & Curriculum (10th Geup to 1st Dan)
The app covers all 10 Geup grades up to 1st Dan:
1. **10th Geup (White Belt / 백띠)**: Basic stances (Moa, Naranhi, Ap-seogi, Ap-kubi), Low block (Arae-makgi), Middle punch (Momtong-jireugi), Front kick (Ap-chagi). Basic movements (Kibon Dongjak).
2. **9th Geup (White + Yellow Stripe)**: Inner middle block (Momtong an-makgi), Roundhouse kick (Dollyo-chagi) basics.
3. **8th Geup (Yellow Belt / 노랑띠)**: **Taegeuk 1 Jang (태극 1장 - Keon / Sky)**.
4. **7th Geup (Yellow + Green Stripe)**: **Taegeuk 2 Jang (태극 2장 - Tae / Lake)**. High block (Olgul-makgi), Back stance (Dwit-kubi) introduction.
5. **6th Geup (Green Belt / 초록띠)**: **Taegeuk 3 Jang (태극 3장 - Ri / Sun & Fire)**. Knife-hand strike (Sonnal mok-chigi), Single knife-hand block (Hansonnal bakkat-makgi), Side kick (Yeop-chagi).
6. **5th Geup (Green + Blue Stripe)**: **Taegeuk 4 Jang (태극 4장 - Jin / Thunder)**. Double knife-hand block (Sonnal momtong-makgi), Spear-hand thrust (Pyeon-son-kkeut jjireugi).
7. **4th Geup (Blue Belt / 파란띠)**: **Taegeuk 5 Jang (태극 5장 - Son / Wind)**. Cross stance (Koa-seogi), Elbow strike (Palkup-chigi), Hammerfist (Me-jumeok), Axe kick (Naeryeo-chagi).
8. **3rd Geup (Blue + Red Stripe)**: **Taegeuk 6 Jang (태극 6장 - Gam / Water)**. Palm heel block (Batangson-makgi), Back kick (Dwi-chagi).
9. **2nd Geup (Red Belt / 빨간띠)**: **Taegeuk 7 Jang (태극 7장 - Gan / Mountain)**. Tiger stance (Beom-seogi), Scissors block (Gawi-makgi), Knee strike (Mureup-chigi).
10. **1st Geup (Red + Black Stripe)**: **Taegeuk 8 Jang (태극 8장 - Gon / Earth)**. Single mountain block (Oe-santeul-makgi), Jumping double front kick (Du-bal dangsang-chagi), Spin hook kick (Dwi-hurigi).
11. **1st Dan (Black Belt / 1단 - Cho Dan)**: Preparation for Koryo (고려), WT / Kukkiwon history, Olympic rules, Gam-jeom scoring penalties, promotion exam oral questions.

**Curriculum Screen Features**:
* Interactive Belt horizontal ribbon with real-time swing physics and settle animations.
* Belt curriculum metadata: Minimum training months, required techniques count, and direct link to belt grading exam.
* **Clickable Required Techniques**: Tapping any technique card or audio button plays authentic spoken Korean pronunciation using the user's selected voice. Clean card layout with technique category badge on top-left to preserve horizontal space.

---

### 2.2 Dual-Angle Taegeuk Poomsae & Diagram Viewer
* **Two Camera Angles**:
  * **Angle 1 (Front View - 0°)**: Standard judge view for alignment and chest squareness.
  * **Angle 2 (Side View - 90°)**: Profile view for stance depth, chambering, and knee angles.
* **Instant Synchronized Angle Switch**: Tapping the angle toggle switches video streams while preserving the exact playback position (`currentPositionMs`) and playback state (playing / paused).
* **Live Dynamic Move Subtitles / Heads-Up Display**:
  * Step number counter (e.g. `Step 03 / 18`)
  * Korean Hangul (e.g. `오른 앞굽이 몸통 반대지르기`)
  * Revised Romanization (e.g. `Oreun Ap-kubi Momtong Bandae-jireugi`)
  * English / Danish translation & description
* **Precision Scrubbing & Step Controls**:
  * Timeline slider with visual tick marks per movement.
  * Previous Step (`|<`) and Next Step (`>|`) buttons.
  * Loop Current Step (`⟲`) for repetitive drill rehearsal.
  * Speed toggles: `0.25x`, `0.5x`, `0.75x`, `1.0x`.
* **Fullscreen Landscape Player**:
  * Tap fullscreen icon on video to enter immersive landscape view with hardware/sensor orientation lock and hidden system bars.
  * Full controls HUD: angle switcher (Front/Side), speed menu, step skip, loop toggle, Korean audio pronunciation, scrubber, and auto-hiding overlays.
* **Visual Movement Diagram**:
  * Clean, content-wrapping card displaying the full Taegeuk movement pattern.
  * Tap to open a fullscreen interactive modal supporting smooth pinch-to-zoom and pan gestures.
* **Interactive Step-by-Step Breakdown**:
  * Ordered list of all movements for the selected Taegeuk.
  * Tap any step row to hear the spoken Korean pronunciation directly.

---

### 2.3 Korean Terminology Audio Glossary
* **Vocabulary Directory**:
  * Categories: Commands & Etiquette, Numbers (Native & Sino-Korean), Stances, Strikes/Punches, Blocks, Kicks, Anatomy & Target Zones, Titles & Dojang Equipment.
  * Searchable in English, Danish, Hangul, or Romanization.
  * Filterable by category chips and belt grade.
* **Compact Card Layout**:
  * Category badge positioned at the top-left of each term card to maximize horizontal room for terminology text.
  * Clean presentation without redundant flag icons or parenthetical duplicates.
* **Interactive Audio Pronunciation**:
  * Tap any term or speaker button to hear native pronunciation.
  * Two-tier architecture:
    * Primary: Pre-recorded audio files bundled in CMP resources.
    * Fallback: Native Text-to-Speech (TTS) engine (`android.speech.tts.TextToSpeech` on Android, `AVSpeechSynthesizer` on iOS).

---

### 2.4 Grading Theory & Quiz System
* **Belt-Specific Quizzes**: Comprehensive question pool covering terminology, form philosophy, stances, and referee hand signals (enhanced with questions imported from Master Nim and Kukkiwon standards).
* **Mock Promotion Examination**: Simulated grading oral exam with scoring and pass/fail readiness assessment.
* **Celebration Effects**: Confetti particle burst upon achieving a passing score.

---

### 2.5 Settings & Localization
* **Language Support**: Danish (`DA`) and English (`EN`), fully persisted across app restarts via `AppSettings` (`SharedPreferences` on Android, `NSUserDefaults` on iOS).
* **Theme Selection**: Dark, Light, and System modes.
* **Voice Gender Selection**:
  * Toggle between Female and Male voice in Settings with live preview speech audition (*"사범님"*).
  * Android: Maps to high-quality network/embedded voices (`ko-kr-x-ism` female, `ko-kr-x-kod` Voice D deep baritone male / Sabeomnim) with tuned pitch multiplier fallback.
  * iOS: Maps to `AVSpeechSynthesisVoiceGender.AVSpeechSynthesisVoiceGenderFemale` and `AVSpeechSynthesisVoiceGender.AVSpeechSynthesisVoiceGenderMale`.

---

## 3. Technical Architecture & Tech Stack

```
sabeomnim/
├── composeApp/
│   ├── commonMain/kotlin/com/sabeomnim/app/
│   │   ├── core/
│   │   │   ├── audio/            # AudioService expect/actual contracts & voice gender
│   │   │   ├── designsystem/     # Theme, BeltColors, Typography, Shared UI components
│   │   │   ├── i18n/             # AppLanguage enum, translations (EN, DA) & localized string helpers
│   │   │   ├── logging/          # CrashlyticsLogger expect/actual multiplatform contracts
│   │   │   ├── navigation/       # Navigation destinations & NavHost
│   │   │   ├── player/           # Multiplatform VideoPlayer interface & state
│   │   │   └── storage/          # AppSettings expect/actual persistent preferences
│   │   ├── data/
│   │   │   ├── database/         # Local persistence (progress, bookmarks, quiz scores)
│   │   │   ├── models/           # BeltRank, Poomsae, QuizQuestion, TerminologyEntry
│   │   │   └── repository/       # BeltRepository, QuizRepository, GlossaryRepository
│   │   └── presentation/         # Compose UI screens:
│   │       ├── dashboard/        # Belt curriculum dashboard & required techniques
│   │       ├── dictionary/       # Interactive audio glossary
│   │       ├── poomsae/          # Taegeuk video player, diagram viewer & step list
│   │       ├── quiz/             # Grading exam simulator & flashcards
│   │       └── settings/         # App language, theme, and voice selection
│   ├── androidMain/              # ExoPlayer (Media3), Android TTS, FirebaseCrashlytics, SharedPreferences AppSettings
│   └── iosMain/                  # AVPlayer, iOS AVSpeechSynthesizer, NSUserDefaults AppSettings
├── gradle/
│   └── libs.versions.toml        # Version Catalog
├── build.gradle.kts
└── settings.gradle.kts
```

* **Application ID**: `dk.slott_hansen.sabeomnim`
* **Kotlin**: 2.1.0+
* **Compose Multiplatform**: 1.7.x+
* **Android Gradle Plugin**: 8.7+
* **Crash & Analytics Reporting**:
  * Android: Google Services (`4.4.2`) + Firebase Crashlytics (`3.0.2` / BOM `33.7.0`) via multiplatform `CrashlyticsLogger`
  * iOS: `CrashlyticsLogger` bridging to console / native handlers
* **Video Playback**:
  * Android: AndroidX Media3 ExoPlayer
  * iOS: AVPlayer wrapped via UIKitView
* **Audio Engine**:
  * Android: `android.speech.tts.TextToSpeech` + `MediaPlayer`
  * iOS: `AVSpeechSynthesizer` + `AVAudioPlayer`
* **Target Architectures**:
  * Android: JVM 21, API 26 to 36
  * iOS: `iosSimulatorArm64`, `iosArm64`, `iosX64` producing static framework `ComposeApp.framework`

---

## 4. Agent Guidelines & Operating Rules

1. **Identity**: You are **Agent Smith**. Maintain a polite, slightly formal, yet helpful attitude.
2. **Artifacts & Files**: Always write newly generated files, analysis reports, and documentation directly into the user's active workspace directory (`/Users/msh/git/sabeomnim/`).
3. **Automated Testing & Deployment**:
   * Following code changes, always build and install the Android app via ADB to an attached Android device or emulator (`./gradlew installDebug` and `adb shell am start ...`), unless explicitly told otherwise.
   * Verify compilation and tests via `./gradlew build`, `./gradlew assembleDebug`, or iOS compilation targets (`./gradlew compileKotlinIosSimulatorArm64`).
4. **Git Discipline**:
   * Keep commits concise and meaningful.
   * Ensure `.gitignore` properly excludes Gradle caches, `.idea`, `build/`, `.gradle/`, and local SDK configs (`local.properties`).

---

## 5. Release & Deployment Operations (Google Play & GPP)

### 5.1 Dynamic Versioning (`versioning.gradle.kts`)
* **Single Source of Truth**: The `versionName` is declared in [`gradle.properties`](file:///Users/msh/git/sabeomnim/gradle.properties) (e.g. `versionName = 1.0.1` or `2026.38.0`).
* **Dynamic `versionCode` Calculation**: Evaluated automatically in [`composeApp/build.gradle.kts`](file:///Users/msh/git/sabeomnim/composeApp/build.gradle.kts) and [`versioning.gradle.kts`](file:///Users/msh/git/sabeomnim/versioning.gradle.kts):
  * Formula: `(year * 1,000,000) + (week * 10,000) + (release * 100) + candidate`
  * SemVer example: `1.0.1` ➔ `1,000,199`
  * CalVer example: `2026.38.0` ➔ `26,380,099`
  * Pre-release tags: `-snapshot` (`00`), `-rc1` (`01`), final release (`99`).
  * Never manually hardcode `versionCode` in Gradle. Always bump `versionName` in `gradle.properties`.

### 5.2 Gradle Play Publisher (GPP) Setup & Directory Structure
* **Plugin**: `com.github.triplet.play` (configured in `composeApp/build.gradle.kts`).
* **Service Account Credentials**: `composeApp/play-service-account.json` (git-ignored for security).
* **Listing Metadata Directory** (`composeApp/src/androidMain/play/`):
  * `default-language.txt`: Specifies default store listing locale (`en-GB`).
  * `contact-email.txt`: Developer support email (`slott.hansen@gmail.com`).
  * `contact-website.txt`: App support website (`https://sites.google.com/view/sabeomnim`).
  * `listings/<locale>/`:
    * `title.txt`: App title (max 30 chars).
    * `short-description.txt`: Promo snippet (max 80 chars).
    * `full-description.txt`: Complete store listing text (max 4000 chars).
    * `graphics/icon/`: 512x512 32-bit PNG.
    * `graphics/feature-graphic/`: 1024x500 PNG.
    * `graphics/phone-screenshots/`: 1080x2400 phone showcase screenshots (`1.png` to `6.png`).
* **Automated Asset Sync**: Run `python3 setup_gpp_assets.py` to regenerate all GPP listing files and structured screenshot hierarchies from `playstore/`.

### 5.3 Publishing Workflows & CLI Commands
* **Build Production Bundle (AAB)**:
  ```bash
  ./gradlew :composeApp:bundleRelease
  ```
  Generates `composeApp/build/outputs/bundle/release/composeApp-release.aab` with R8 minification and automatically embedded de-obfuscation mapping at `BUNDLE-METADATA/com.android.tools.build.obfuscation/proguard.map`.
* **Sync Store Listings, Graphics & Screenshots**:
  ```bash
  ./gradlew publishReleaseListing
  ```
* **Publish AAB Directly to Internal Testing Track**:
  ```bash
  ./gradlew publishReleaseBundle
  ```
* **Promote Releases Across Tracks**:
  ```bash
  # Promote internal release to Production (draft status for review submission):
  ./gradlew :composeApp:promoteReleaseArtifact --from-track=internal --promote-track=production --release-status=draft

  # Promote internal release to Beta (Open Testing):
  ./gradlew :composeApp:promoteReleaseArtifact --from-track=internal --promote-track=beta --release-status=completed
  ```

### 5.4 Keystore, Signing & Privacy
* **Keystore**: `composeApp/lego_keystore.jks` with key aliases and passwords securely stored in `local.properties`.
* **Privacy Policy**: Maintained in `playstore/PRIVACY_POLICY.md` (Markdown for Google Sites) and `playstore/privacy_policy.html` (responsive standalone HTML), hosted at the developer's Google Sites website.

