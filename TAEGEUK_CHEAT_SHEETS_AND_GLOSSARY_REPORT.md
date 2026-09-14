# Sabeomnim (사범님) — Taegeuk Cheat Sheets & Comprehensive Glossary Implementation

This report documents the implementation and live device verification of two core features requested for **Sabeomnim**:
1. **Interactive Taegeuk Poomsae Cheat Sheets** (Taegeuk 1 to 8)
2. **Comprehensive Taekwondo Audio Glossary** (164 entries across 10 categories)

---

## 1. Visual Taegeuk Poomsae Cheat Sheets

### 1.1 Diagram Assets & Bundle
All 8 official Taegeuk diagrams referenced from Blue Dragon TKD have been embedded into Compose Multiplatform resources:
- `composeApp/src/commonMain/composeResources/drawable/taegeuk_1_cheat_sheet.jpg` (Taegeuk 1 Il Jang)
- `composeApp/src/commonMain/composeResources/drawable/taegeuk_2_cheat_sheet.png` (Taegeuk 2 Ee Jang)
- `composeApp/src/commonMain/composeResources/drawable/taegeuk_3_cheat_sheet.jpg` (Taegeuk 3 Sam Jang)
- `composeApp/src/commonMain/composeResources/drawable/taegeuk_4_cheat_sheet.png` (Taegeuk 4 Sa Jang)
- `composeApp/src/commonMain/composeResources/drawable/taegeuk_5_cheat_sheet.png` (Taegeuk 5 Oh Jang)
- `composeApp/src/commonMain/composeResources/drawable/taegeuk_6_cheat_sheet.png` (Taegeuk 6 Yuk Jang)
- `composeApp/src/commonMain/composeResources/drawable/taegeuk_7_cheat_sheet.png` (Taegeuk 7 Chil Jang)
- `composeApp/src/commonMain/composeResources/drawable/taegeuk_8_cheat_sheet.png` (Taegeuk 8 Pal Jang)

### 1.2 Dedicated Cheat Sheet Viewer (`TaegeukCheatSheetView.kt`)
- **Philosophy & Grade Banner**: Displays the trigram symbol (e.g. ☰ Keon, ☱ Tae, ☲ Ri), associated belt rank, total movement count, and exact Kihap step.
- **Interactive Move Diagram**:
  - Zoom In (`+`), Zoom Out (`-`), and Reset (`↻`) controls.
  - Fullscreen Modal Dialog (`⛶`) with pinch-to-zoom and multi-directional panning.
- **Choreography Step Breakdown**:
  - Numbered move cards with Korean Hangul, WT Revised Romanization, and English descriptions.
  - Stance & Technique chips (e.g., `Stance: Ap-seogi`, `Action: Arae-makgi`).
  - Examiner coaching tips and grading deduction warnings.
  - Audio button on each step triggering native Korean TTS pronunciation.

### 1.3 Mode Switcher in Poomsae Screen (`PoomsaePlayerScreen.kt`)
Seamless switching between:
- 🎥 **Dual-Angle Video**: Multi-camera synchronized playback (Front 0° vs Side 90°), precision slider scrubber, step looping (`⟲`), and live HUD.
- 📋 **Visual Cheat Sheet**: Visual step-by-step diagram, zoom/fullscreen modal, and choreography checklist.

---

## 2. Comprehensive Taekwondo Audio Glossary (164 Terms)

Extracted from the official curriculum reference (`https://www.bluedragontkd.net/learn-taekwondo/glossary/`) and integrated into [`TerminologyRepository.kt`](file:///Users/msh/git/sabeomnim/composeApp/src/commonMain/kotlin/com/sabeomnim/app/data/repository/TerminologyRepository.kt):

| Category | Term Count | Example Entries |
|---|---|---|
| **General Terms & Etiquette** | 31 terms | 태권도 (*Taekwondo*), 도장 (*Dojang*), 도복 (*Dobok*), 차렷 (*Charyeot*), 경례 (*Gyeongnye*) |
| **Directions & Target Zones** | 13 terms | 앞 (*Ap* - Front), 뒤 (*Dwi* - Back), 왼쪽 (*Oen-jjok*), 얼굴 (*Olgul* - High), 몸통 (*Momtong* - Middle), 아래 (*Arae* - Low) |
| **Stances (Seogi)** | 12 terms | 주춤서기 (*Juchum-seogi*), 앞서기 (*Ap-seogi*), 앞굽이 (*Ap-kubi*), 뒷굽이 (*Dwit-kubi*), 범서기 (*Beom-seogi*) |
| **Blocks (Makgi)** | 18 terms | 아래막기 (*Arae-makgi*), 몸통막기 (*Momtong-makgi*), 얼굴막기 (*Olgul-makgi*), 손날막기 (*Sonnal-makgi*) |
| **Strikes (Chigi)** | 14 terms | 손날 목치기 (*Sonnal mok-chigi*), 팔굽치기 (*Palkup-chigi*), 무릎치기 (*Mureup-chigi*) |
| **Punches & Thrusts (Jireugi & Jjireugi)** | 9 terms | 바로지르기 (*Baro-jireugi*), 반대지르기 (*Bandae-jireugi*), 편손끝찌르기 (*Pyeon-son-kkeut jjireugi*) |
| **Kicks (Chagi)** | 16 terms | 앞차기 (*Ap-chagi*), 돌려차기 (*Dollyo-chagi*), 옆차기 (*Yeop-chagi*), 뒤차기 (*Dwi-chagi*), 뒤후리기 (*Dwi-hurigi*) |
| **Anatomy & Body Parts** | 20 terms | 주먹 (*Jumeok* - Fist), 등주먹 (*Deung-jumeok*), 메주먹 (*Me-jumeok*), 발날 (*Bal-nal*), 앞축 (*Ap-chuk*) |
| **Native Korean Numbers** | 16 terms | 하나 (*Hana* - 1), 둘 (*Dul* - 2), 셋 (*Set* - 3) ... 열 (*Yeol* - 10) ... 온 (*On* - 100) |
| **Sino-Korean Numbers** | 15 terms | 일 (*Il* - 1st), 이 (*I* - 2nd), 삼 (*Sam* - 3rd) ... 십 (*Sip* - 10th) ... 오십 (*O-sip* - 50th) |

### 2.1 Audio & Pronunciation Capabilities
- **Dual Spellings**: Standard World Taekwondo (WT) Revised Romanization alongside Blue Dragon phonetic spellings (e.g. `Ap Chagi (Ahp Cha Gee)`).
- **Native TTS Engine**: Powered by Android `android.speech.tts.TextToSpeech` with real-time pronunciation synthesis in Korean (`Locale.KOREAN`).
- **Speed Toggle**: Toggle between Normal (1.0x) and Slow (0.75x) for syllable-by-syllable clarity.
- **Search & Filtering**: Live search by Korean Hangul, English definition, Romanization, or phonetic spelling.

---

## 3. Verification Screenshots

- [`screen_glossary.png`](file:///Users/msh/git/sabeomnim/screen_glossary.png): 164-entry categorized glossary with search, phonetic badges, and audio buttons.
- [`screen_poomsae_video.png`](file:///Users/msh/git/sabeomnim/screen_poomsae_video.png): Dual-Angle video player with angle switcher and move HUD.
- [`screen_cheat_sheet.png`](file:///Users/msh/git/sabeomnim/screen_cheat_sheet.png): Taegeuk 1 visual cheat sheet with trigram badge, belt requirements, and diagram.
- [`screen_cheat_steps.png`](file:///Users/msh/git/sabeomnim/screen_cheat_steps.png): Choreography breakdown with stance/action chips and TTS buttons.
- [`screen_fullscreen.png`](file:///Users/msh/git/sabeomnim/screen_fullscreen.png): Fullscreen high-resolution diagram modal.
- [`screen_taegeuk2_selected.png`](file:///Users/msh/git/sabeomnim/screen_taegeuk2_selected.png): Taegeuk 2 cheat sheet and diagram.
- [`screen_taegeuk3_selected.png`](file:///Users/msh/git/sabeomnim/screen_taegeuk3_selected.png): Taegeuk 3 cheat sheet and diagram.
