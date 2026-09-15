# Authentic Dual-Tail Taekwondo Belt with 2D Cloth Physics

## 1. Overview & Objectives

In martial arts traditions (World Taekwondo / Kukkiwon), a tied belt (*tti*) has specific characteristics:
1. **Dual Hanging Tails**: A square knot (*jeong-jwasik* knot) produces **two hanging ends (tails)** descending side by side.
2. **Finished Flat Hem Ends**: Belts are never cut at an angle; their tips are straight, flat, rectangular hems finished with double stitching.
3. **Single-Tail Rank Tab**: Rank identification (such as colored rank stripes for Geup grades, or golden Dan bars / black federation patches) is worn on **only one tail** (conventionally the right tail when tied).
4. **Interactive 2D Physics**: When touched or dragged with a finger, the belt tails react dynamically with real cloth physics (Verlet integration, distance constraint relaxation, gravitational pull, and fabric stiffness) and settle back into a natural vertical hang.

---

## 2. Technical Implementation

### 2.1 2D Verlet Cloth Physics Engine (`BeltPhysicsSystem`)
Implemented in [`UnfoldingBeltView.kt`](file:///Users/msh/git/sabeomnim/composeApp/src/commonMain/kotlin/com/sabeomnim/app/core/ui/belt/UnfoldingBeltView.kt):
- **Particles & Links**: Each tail is represented by a chain of linked particles (`BeltTail`) with anchor nodes pinned inside the square knot.
- **Verlet Integration**:
  $$x(t + \Delta t) = x(t) + (x(t) - x(t - \Delta t)) \times \text{damping} + a \cdot \Delta t^2$$
- **Fabric Stiffness Restoring Force**: A restoring spring pulls particles toward the vertical axis beneath the anchor, mimicking the stiff woven cotton structure of a genuine martial arts belt with longitudinal thread stitching.
- **Constraint Relaxation**: Multiple iterations per physics step maintain exact segment lengths, preventing stretching or warping.

### 2.2 Gesture Interaction (`detectDragGestures` & `detectTapGestures`)
- **Interactive Dragging**: Users can drag either belt tail across the screen. The physics engine tracks touch velocities and imparts momentum upon release.
- **Tapping & Flicking**: Tapping the tails applies a lateral impulse wave that travels down the fabric ribbon.
- **Real-Time Frame Clock**: Driven by `withFrameNanos`, ensuring 60fps / 120fps physics simulation.

### 2.3 Visual Appearance & Authentic Details
- **Flat Rectangular Finished Ends**: Ends are perpendicular to the tail tangent with a horizontal hem stitch line.
- **Single-Tail Rank Tab**:
  - Left Tail: Plain cotton body.
  - Right Tail: Contains the belt grade tab (e.g. Yellow stripe for 9th Geup, Green stripe for 7th Geup, Gold embroidered Dan bar for 1st Dan, or woven federation patch).
- **Longitudinal Stitching**: 4 parallel stitch rows run the full length of each tail.

---

## 3. Physical Device Verification

The application was built, deployed, and verified on the connected Android physical device (`PN9PUC7TVWI7B6AI`):
1. **At Rest ([`screen_belt_perfect.png`](file:///Users/msh/git/sabeomnim/screen_belt_perfect.png))**: Both tails hang vertically side by side with clean rectangular hemmed ends.
2. **Swinging Under Touch ([`screen_belt_swinging_clean.png`](file:///Users/msh/git/sabeomnim/screen_belt_swinging_clean.png))**: Demonstrates continuous quadratic curvature and dynamic deflection during swipe.
3. **Settled State ([`screen_belt_settled.png`](file:///Users/msh/git/sabeomnim/screen_belt_settled.png))**: Naturally oscillates and dampens back to the vertical resting position.
