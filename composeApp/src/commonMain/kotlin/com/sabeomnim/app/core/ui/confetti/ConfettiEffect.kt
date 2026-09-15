package com.sabeomnim.app.core.ui.confetti

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.isActive
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

enum class ConfettiShape {
    RECTANGLE,
    RIBBON,
    CIRCLE
}

class ConfettiParticle(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    var rotationX: Float = Random.nextFloat() * 2f * PI.toFloat(),
    var rotationY: Float = Random.nextFloat() * 2f * PI.toFloat(),
    var rotationZ: Float = Random.nextFloat() * 2f * PI.toFloat(),
    val vRotX: Float = (Random.nextFloat() * 6f - 3f),
    val vRotY: Float = (Random.nextFloat() * 6f - 3f),
    val vRotZ: Float = (Random.nextFloat() * 5f - 2.5f),
    val width: Float,
    val height: Float,
    val baseColor: Color,
    val shape: ConfettiShape,
    val maxLifeTimeMs: Float,
    var currentLifeTimeMs: Float = 0f
) {
    val isDead: Boolean
        get() = currentLifeTimeMs >= maxLifeTimeMs
}

val ConfettiCelebrationColors = listOf(
    Color(0xFFFFD700), // Kukkiwon Gold
    Color(0xFFE53935), // Taegeuk Red
    Color(0xFF1E88E5), // Taegeuk Blue
    Color(0xFF43A047), // Taekwondo Green
    Color(0xFFFFB300), // Warm Yellow
    Color(0xFFFFFFFF), // Pure Dobok White
    Color(0xFF8E24AA)  // Royal Purple
)

/**
 * High-performance Confetti State.
 * Uses flat ArrayList with O(1) swap-removal and zero Compose SnapshotStateList overhead.
 */
@Stable
class ConfettiState {
    val particles = ArrayList<ConfettiParticle>()

    var isRunning by mutableStateOf(false)
        private set

    var frameTick by mutableStateOf(0L)
        internal set

    var containerSize by mutableStateOf(IntSize.Zero)

    fun spawnCelebration(count: Int = 140) {
        val width = if (containerSize.width > 0) containerSize.width.toFloat() else 1080f
        val height = if (containerSize.height > 0) containerSize.height.toFloat() else 1920f

        val newParticles = ArrayList<ConfettiParticle>(count)

        // Cannon 1: Bottom Left shooting up-right
        val leftCount = count / 3
        for (i in 0 until leftCount) {
            val angle = (-Random.nextFloat() * 32f - 48f) * (PI.toFloat() / 180f)
            val speed = Random.nextFloat() * 700f + 650f
            newParticles.add(
                createParticle(
                    startX = Random.nextFloat() * 60f,
                    startY = height * 0.88f + Random.nextFloat() * 60f,
                    vx = cos(angle) * speed,
                    vy = sin(angle) * speed
                )
            )
        }

        // Cannon 2: Bottom Right shooting up-left
        val rightCount = count / 3
        for (i in 0 until rightCount) {
            val angle = (-Random.nextFloat() * 32f - 100f) * (PI.toFloat() / 180f)
            val speed = Random.nextFloat() * 700f + 650f
            newParticles.add(
                createParticle(
                    startX = width - Random.nextFloat() * 60f,
                    startY = height * 0.88f + Random.nextFloat() * 60f,
                    vx = cos(angle) * speed,
                    vy = sin(angle) * speed
                )
            )
        }

        // Fountain 3: Center bursting outward
        val centerCount = count - leftCount - rightCount
        for (i in 0 until centerCount) {
            val angle = (-Random.nextFloat() * 100f - 40f) * (PI.toFloat() / 180f)
            val speed = Random.nextFloat() * 550f + 500f
            newParticles.add(
                createParticle(
                    startX = width * 0.5f + (Random.nextFloat() * 100f - 50f),
                    startY = height * 0.65f + (Random.nextFloat() * 60f - 30f),
                    vx = cos(angle) * speed,
                    vy = sin(angle) * speed
                )
            )
        }

        particles.addAll(newParticles)
        isRunning = true
    }

    private fun createParticle(startX: Float, startY: Float, vx: Float, vy: Float): ConfettiParticle {
        val shapeType = when (Random.nextInt(10)) {
            in 0..4 -> ConfettiShape.RECTANGLE
            in 5..7 -> ConfettiShape.RIBBON
            else -> ConfettiShape.CIRCLE
        }

        val width = when (shapeType) {
            ConfettiShape.RECTANGLE -> Random.nextFloat() * 10f + 16f
            ConfettiShape.RIBBON -> Random.nextFloat() * 7f + 9f
            ConfettiShape.CIRCLE -> Random.nextFloat() * 10f + 11f
        }

        val height = when (shapeType) {
            ConfettiShape.RECTANGLE -> Random.nextFloat() * 8f + 12f
            ConfettiShape.RIBBON -> Random.nextFloat() * 20f + 24f
            ConfettiShape.CIRCLE -> width
        }

        val color = ConfettiCelebrationColors[Random.nextInt(ConfettiCelebrationColors.size)]
        val lifeTimeMs = Random.nextFloat() * 1200f + 2500f

        return ConfettiParticle(
            x = startX,
            y = startY,
            vx = vx,
            vy = vy,
            width = width,
            height = height,
            baseColor = color,
            shape = shapeType,
            maxLifeTimeMs = lifeTimeMs
        )
    }

    fun update(dtSec: Float) {
        if (particles.isEmpty()) {
            isRunning = false
            return
        }

        val gravity = 880f
        val drag = 0.984f
        val dtMs = dtSec * 1000f

        var i = particles.size - 1
        while (i >= 0) {
            val p = particles[i]
            p.currentLifeTimeMs += dtMs

            if (p.isDead) {
                // O(1) swap remove
                val lastIdx = particles.size - 1
                if (i != lastIdx) {
                    particles[i] = particles[lastIdx]
                }
                particles.removeAt(lastIdx)
            } else {
                p.vy += gravity * dtSec
                p.vx *= drag
                p.x += p.vx * dtSec
                p.y += p.vy * dtSec

                p.rotationX += p.vRotX * dtSec
                p.rotationY += p.vRotY * dtSec
                p.rotationZ += p.vRotZ * dtSec
            }
            i--
        }

        if (particles.isEmpty()) {
            isRunning = false
        }
    }
}

@Composable
fun rememberConfettiState(): ConfettiState {
    return remember { ConfettiState() }
}

/**
 * Ultra-smooth, hardware-accelerated Confetti Canvas host.
 * Synchronized with Choreographer VSync, zero memory allocations per frame,
 * direct trigonometric vertex computation (no Skia matrix save/restore stack thrashing).
 */
@Composable
fun ConfettiHost(
    state: ConfettiState,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(state.isRunning) {
        if (!state.isRunning) return@LaunchedEffect

        var lastFrameNanos = withFrameNanos { it }
        while (isActive && state.isRunning) {
            withFrameNanos { currentFrameNanos ->
                val dtSec = ((currentFrameNanos - lastFrameNanos) / 1_000_000_000f).coerceIn(0.005f, 0.033f)
                lastFrameNanos = currentFrameNanos
                state.update(dtSec)
                state.frameTick = currentFrameNanos
            }
        }
    }

    Box(
        modifier = modifier
            .onSizeChanged { state.containerSize = it }
    ) {
        if (state.isRunning || state.particles.isNotEmpty()) {
            val reusablePath = remember { Path() }

            Canvas(modifier = Modifier.fillMaxSize()) {
                // Reading frameTick triggers exact 60/120fps VSync invalidation
                val tick = state.frameTick
                if (tick == -1L) return@Canvas

                val count = state.particles.size
                for (idx in 0 until count) {
                    val p = state.particles[idx]
                    val alpha = (1f - (p.currentLifeTimeMs / p.maxLifeTimeMs)).coerceIn(0f, 1f)
                    if (alpha <= 0.02f) continue

                    // 3D fluttering projection
                    val scaleX = cos(p.rotationX)
                    val scaleY = cos(p.rotationY)
                    val absScaleX = if (scaleX < 0f) -scaleX else scaleX
                    val absScaleY = if (scaleY < 0f) -scaleY else scaleY

                    if (absScaleX < 0.05f || absScaleY < 0.05f) continue

                    val particleColor = p.baseColor.copy(alpha = alpha)

                    when (p.shape) {
                        ConfettiShape.CIRCLE -> {
                            drawCircle(
                                color = particleColor,
                                radius = (p.width * 0.5f) * absScaleX,
                                center = Offset(p.x, p.y)
                            )
                        }
                        ConfettiShape.RECTANGLE, ConfettiShape.RIBBON -> {
                            val w = p.width * absScaleX
                            val h = p.height * absScaleY
                            val hw = w * 0.5f
                            val hh = h * 0.5f

                            val rotZ = p.rotationZ
                            val cosZ = cos(rotZ)
                            val sinZ = sin(rotZ)

                            // Direct rotated 4 corner vertices
                            val x1 = p.x - hw * cosZ + hh * sinZ
                            val y1 = p.y - hw * sinZ - hh * cosZ

                            val x2 = p.x + hw * cosZ + hh * sinZ
                            val y2 = p.y + hw * sinZ - hh * cosZ

                            val x3 = p.x + hw * cosZ - hh * sinZ
                            val y3 = p.y + hw * sinZ + hh * cosZ

                            val x4 = p.x - hw * cosZ - hh * sinZ
                            val y4 = p.y - hw * sinZ + hh * cosZ

                            reusablePath.rewind()
                            reusablePath.moveTo(x1, y1)
                            reusablePath.lineTo(x2, y2)
                            reusablePath.lineTo(x3, y3)
                            reusablePath.lineTo(x4, y4)
                            reusablePath.close()

                            drawPath(path = reusablePath, color = particleColor)
                        }
                    }
                }
            }
        }
    }
}
