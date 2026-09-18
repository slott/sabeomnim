# Google Play Console Submission Guide — Sabeomnim (사범님)

This guide provides step-by-step instructions and ready-to-use assets for setting up and publishing **Sabeomnim** on the Google Play Console.

---

## 1. Store Presence: Graphic Assets Checklist

| Asset Type | Requirement | Provided File Path | Status |
| :--- | :--- | :--- | :--- |
| **App Icon** | 512 x 512 px, 32-bit PNG with alpha, max 1024 KB | [`playstore/graphics/icon_512x512.png`](file:///Users/msh/git/sabeomnim/playstore/graphics/icon_512x512.png) | ✅ Ready |
| **Feature Graphic** | 1024 x 500 px, JPG or 24-bit PNG (no alpha), max 15 MB | [`playstore/graphics/feature_graphic_1024x500.png`](file:///Users/msh/git/sabeomnim/playstore/graphics/feature_graphic_1024x500.png) | ✅ Ready |
| **Showcase Screenshots** | Min 2, max 8 (1080 x 2424 px 20:9 ratio) | [`playstore/screenshots/showcase/`](file:///Users/msh/git/sabeomnim/playstore/screenshots/showcase/) | ✅ 6 Ready |
| **Raw Device Screenshots** | Pristine Pixel 9 system UI captures | [`playstore/screenshots/raw/`](file:///Users/msh/git/sabeomnim/playstore/screenshots/raw/) | ✅ 6 Ready |

### Screenshot Breakdown
1. **Curriculum**: `01_showcase_curriculum.png` — *"Master Every Belt Grade (10th Geup to 1st Dan)"*
2. **Poomsae Player**: `02_showcase_poomsae_player.png` — *"Synchronized Dual Camera Views (Front 0° & Side 90°)"*
3. **Diagrams**: `03_showcase_movement_diagrams.png` — *"Official Movement Patterns & Kihap Markers"*
4. **Glossary**: `04_showcase_audio_glossary.png` — *"Native Spoken Pronunciation (160+ Terms)"*
5. **Theory Quiz**: `05_showcase_theory_exam.png` — *"Belt Promotion Grading Exam Simulator"*
6. **Voice & Themes**: `06_showcase_voice_and_themes.png` — *"Sabeomnim Voice & Dark Dobok Mode"*

---

## 2. Store Presence: Listing Text & Descriptions

All texts are formatted within the strict character limits:
* **English (Default)**: [`playstore/metadata/listing_en.md`](file:///Users/msh/git/sabeomnim/playstore/metadata/listing_en.md)
  * Title: `Sabeomnim: Taekwondo Master` (27 / 30 chars)
  * Short description: `Kukkiwon WT curriculum, dual-angle Taegeuk Poomsae, audio glossary & exam prep.` (79 / 80 chars)
  * Full description: Formatted with feature bullet points and belt curriculum breakdown.
* **Danish (Dansk)**: [`playstore/metadata/listing_da.md`](file:///Users/msh/git/sabeomnim/playstore/metadata/listing_da.md)
  * Titel: `Sabeomnim: Taekwondo Mester` (27 / 30 chars)
  * Kort beskrivelse: `WT & Kukkiwon pensum, multi-vinkel Taegeuk poomsae, lydordbog og teoriprøve.` (76 / 80 chars)

---

## 3. App Content & Policy Declarations

When completing the Google Play Console **App Content** questionnaire:

1. **Privacy Policy**:
   * Markdown: [`playstore/metadata/PRIVACY_POLICY.md`](file:///Users/msh/git/sabeomnim/playstore/metadata/PRIVACY_POLICY.md)
   * HTML: [`playstore/metadata/privacy_policy.html`](file:///Users/msh/git/sabeomnim/playstore/metadata/privacy_policy.html)
   * Public URL host: Host on GitHub Pages (e.g. `https://<user>.github.io/sabeomnim/privacy_policy.html`) or raw GitHub file link.
2. **Ads**: Select **"No, my app does not contain ads"**.
3. **App Access**: Select **"All functionality is available without special access"** (no login credentials needed).
4. **Content Rating (IARC)**:
   * Category: **Reference, News, or Educational** or **Sports**.
   * Violence / Profanity / Controlled Substances: **None**.
   * Rating Result: Typically **PEGI 3 / Everyone**.
5. **Target Audience**: Select **13 and older** (or All Ages).
6. **Data Safety**:
   * *Does your app collect or share any user data?* -> **No**.
   * *Is all user data collected by your app encrypted in transit?* -> Not applicable (No data collected).
   * *Do you provide a way for users to request data deletion?* -> Not applicable (No accounts or personal data).

---

## 4. Production Release Build (AAB)

To generate the signed Android App Bundle (`.aab`) ready for uploading to Google Play Console:

```bash
./gradlew :composeApp:bundleRelease
```

The output bundle will be generated at:
`composeApp/build/outputs/bundle/release/composeApp-release.aab`
