# AGENTS.md — Sabeomnim (사범님)

Welcome to the **Sabeomnim** project repository. This document specifies the project requirements, architecture, coding guidelines, and operational procedures for all AI agents working on this codebase.

---

## 1. Project Overview

* **Application Name**: Sabeomnim (사범님 - Taekwondo Master/Instructor)
* **Core Purpose**: Comprehensive mobile learning companion for World Taekwondo (WT / Kukkiwon) practitioners, focusing on the complete curriculum from **10th Geup (White Belt)** to **1st Dan (Black Belt / Il Dan)**.
* **Primary Platforms**: Android and iOS, built with **Compose Multiplatform (CMP)** and Kotlin Multiplatform (KMP).
* **Initial Target**: Android device / emulator testing.
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

---

### 2.2 Dual-Angle Taegeuk Poomsae Video Player
* **Two Camera Angles**:
  * **Angle 1 (Front View - 0°)**: Standard judge view for alignment and chest squareness.
  * **Angle 2 (Side View - 90°)**: Profile view for stance depth, chambering, and knee angles.
* **Instant Synchronized Angle Switch**: Tapping the angle toggle switches video streams while preserving the exact playback position (`currentPositionMs`) and playback state (playing / paused).
* **Live Dynamic Move Subtitles / Heads-Up Display**:
  * Step number counter (e.g. `Step 03 / 18`)
  * Korean Hangul (e.g. `오른 앞굽이 몸통 반대지르기`)
  * Revised Romanization (e.g. `Oreun Ap-kubi Momtong Bandae-jireugi`)
  * English translation & description
  * Coaching notes & common grading deduction points
* **Precision Scrubbing & Step Controls**:
  * Timeline slider with visual tick marks per movement.
  * Previous Step (`|<`) and Next Step (`>|`) buttons.
  * Loop Current Step (`⟲`) for repetitive drill rehearsal.
  * Speed toggles: `0.25x`, `0.5x`, `0.75x`, `1.0x`.
* **Video Delivery**: Hosted on GitHub (Releases or raw repository URLs) acting as a lightweight CDN.

---

### 2.3 Korean Terminology Audio Dictionary
* **Vocabulary Directory**:
  * Categories: Commands & Etiquette, Numbers (Native & Sino-Korean), Stances, Strikes/Punches, Blocks, Kicks, Anatomy & Target Zones, Titles & Dojang Equipment.
  * Searchable in English, Hangul, or Romanization.
  * Filterable by Belt Grade.
* **Interactive Audio Pronunciation**:
  * Tap any term or speaker button to hear native pronunciation.
  * Slow audio toggle (0.75x) for clean syllable distinction.
  * Resilient two-tier architecture:
    * Primary: Pre-recorded audio files bundled in CMP resources.
    * Fallback: Native Text-to-Speech (TTS) engine (`android.speech.tts.TextToSpeech` on Android, `AVSpeechSynthesizer` on iOS).

---

### 2.4 Grading Theory & Quiz System
* **Belt-Specific Quizzes**: Multi-choice and flashcard testing covering terminology, form philosophy, stances, and referee hand signals.
* **Mock Promotion Examination**: Simulated grading oral exam with scoring and pass/fail readiness assessment.

---

## 3. Technical Architecture & Tech Stack

```
sabeomnim/
├── composeApp/
│   ├── commonMain/kotlin/com/sabeomnim/app/
│   │   ├── core/
│   │   │   ├── audio/            # AudioPlayer & TextToSpeech multiplatform contracts
│   │   │   ├── designsystem/     # Theme, BeltColors, Typography, Shared UI components
│   │   │   ├── navigation/       # Navigation destinations & NavHost
│   │   │   └── player/           # Multiplatform VideoPlayer interface & state
│   │   ├── data/
│   │   │   ├── database/         # Local persistence (progress, bookmarks, quiz scores)
│   │   │   ├── models/           # BeltRank, Poomsae, Question, TerminologyEntry
│   │   │   └── repository/       # Repositories with seed data
│   │   ├── domain/               # Business logic & use cases
│   │   └── presentation/         # Compose UI screens:
│   │       ├── dictionary/       # Interactive audio terminology dictionary
│   │       ├── home/             # Belt dashboard & current grade
│   │       ├── poomsae/          # Dual-angle Taegeuk video player & step viewer
│   │       └── quiz/             # Grading exam simulator & flashcards
│   ├── androidMain/              # ExoPlayer (Media3), Android TTS, Platform audio
│   └── iosMain/                  # AVPlayer, iOS AVSpeechSynthesizer
├── gradle/
│   └── libs.versions.toml        # Version Catalog
├── build.gradle.kts
└── settings.gradle.kts
```

* **Kotlin**: 2.1.0+
* **Compose Multiplatform**: 1.7.x+
* **Android Gradle Plugin**: 8.7+
* **Navigation**: Jetpack Navigation Compose Multiplatform
* **Video Playback**:
  * Android: AndroidX Media3 ExoPlayer
  * iOS: AVPlayer wrapped via UIKitView
* **Audio Engine**:
  * Android: `android.speech.tts.TextToSpeech` + `MediaPlayer`
  * iOS: `AVSpeechSynthesizer` + `AVAudioPlayer`
* **Dependency Injection**: Koin Multiplatform

---

## 4. Agent Guidelines & Operating Rules

1. **Identity**: You are **Agent Smith**. Maintain a polite, slightly formal, yet helpful attitude.
2. **Artifacts & Files**: Always write newly generated files, analysis reports, and documentation directly into the user's active workspace directory (`/Users/msh/git/sabeomnim/`).
3. **Automated Testing & Deployment**:
   * Following code changes, always build and install the Android app via ADB to an attached Android device or emulator (`./gradlew installDebug` and `adb shell am start ...`), unless explicitly told otherwise.
   * Verify compilation and tests via `./gradlew build` or `./gradlew assembleDebug`.
4. **Git Discipline**:
   * Keep commits concise and meaningful.
   * Ensure `.gitignore` properly excludes Gradle caches, `.idea`, `build/`, `.gradle/`, and local SDK configs (`local.properties`).
