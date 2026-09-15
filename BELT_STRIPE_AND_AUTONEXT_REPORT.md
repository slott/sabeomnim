# Belt Stripe Refinement & Auto-Next Progress Bar Report

**Author**: Agent Smith  
**Target Device Tested**: OnePlus 12 / Android 16 (`PN9PUC7TVWI7B6AI`)  
**Commit**: `273adec`  

---

## 1. Summary of Changes

### 1.1 Authentic Thin Belt Stripe Tape
* **Previous State**: The colored stripe on senior rank belts (e.g., 9th Geup White Belt with Yellow Stripe, 7th Geup Yellow Belt with Green Stripe) spanned across an entire 40dp node segment, appearing disproportionately thick.
* **New Implementation**:
  * Replaced node-span segment color fills with authentic rank stripe tape geometry in [`UnfoldingBeltView.kt`](file:///Users/msh/git/sabeomnim/composeApp/src/commonMain/kotlin/com/sabeomnim/app/core/ui/belt/UnfoldingBeltView.kt).
  * Modeled after standard dojang rank tape (~6.4mm / 6.5dp width) wrapped around the right belt tail, positioned ~28dp above the hemmed bottom tip.
  * Form-fitted to the cloth mesh: the tape coordinates are projected perpendicularly and along the cloth's tangent vector, allowing it to bend naturally when tails swing with 2D physics.
  * Maintained consistent single-color knot wrap tie matching the primary belt cloth.
  * Preserved the 1st Dan gold embroidered rank bar (5dp) and brand tag patch (14dp).

### 1.2 "Auto-Next" Countdown & Progress Bar on Quiz Next Button
* **Previous State**: Submitting an answer required a manual second tap on the "Next Question" button to proceed.
* **New Implementation**:
  * Added auto-advance timer loop in [`QuizScreen.kt`](file:///Users/msh/git/sabeomnim/composeApp/src/commonMain/kotlin/com/sabeomnim/app/presentation/quiz/QuizScreen.kt) driven by hardware VSync (`withFrameNanos`) over a 2.8s window.
  * Transformed the "Next Question" / "See Results" button into an interactive progress-indicator button:
    1. **Fill Overlay**: A translucent white fill (`Color.White.copy(alpha = 0.22f)`) sweeps across the button from left to right as time elapses.
    2. **Bottom Progress Accent**: A 4dp high-contrast white progress bar advances along the bottom edge of the button.
    3. **Live Countdown Badge**: A `${secondsLeft}s` badge updates smoothly in real time alongside the label and arrow icon.
    4. **Manual Override**: Tapping the button at any point immediately advances without waiting for the timer.
    5. **Auto-Scroll Behavior**: Submitting an answer triggers an animated scroll to bring both the explanation card and the auto-advancing Next button into full view.
  * Upgraded option card background styling to use theme-adaptive emerald and ruby tinted surface colors (`Color(0xFF2E7D32).copy(alpha = 0.25f)`) with white text for dark mode contrast.

---

## 2. On-Device Verification

1. **Thin Belt Stripe**:
   * Verified on 9th Geup (White Belt + Yellow Stripe) and 7th Geup (Yellow Belt + Green Stripe).
   * Verified in screenshots:
     - [`screen_belt_thin_stripe_9th_settled.png`](file:///Users/msh/git/sabeomnim/screen_belt_thin_stripe_9th_settled.png)
     - [`screen_belt_thin_stripe_7th.png`](file:///Users/msh/git/sabeomnim/screen_belt_thin_stripe_7th.png)
2. **Auto-Next Progress Bar & Advance**:
   * Verified on physical Android device:
     - [`screen_quiz_autonext_midway.png`](file:///Users/msh/git/sabeomnim/screen_quiz_autonext_midway.png): Shows the Next button with ~40% progress bar and "2s" badge.
     - [`screen_quiz_autonext_complete.png`](file:///Users/msh/git/sabeomnim/screen_quiz_autonext_complete.png): Shows automatic transition to Question 2 upon completion.
