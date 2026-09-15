package com.sabeomnim.app.core.ui.confetti

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.delay
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
    var rotationZ: Float = Random.nextFloat() * 360f,
    val vRotX: Float = (Random.nextFloat() * 8f - 4f),
    val vRotY: Float = (Random.nextFloat() * 8f - 4f),
    val vRotZ: Float = (Random.nextFloat() * 300f - 150f),
    val width: Float,
    val height: Float,
    val color: Color,
    val shape: ConfettiShape,
    val maxLifeTimeMs: Float,
    var currentLifeTimeMs: Float = 0f
) {
    val isDead: Boolean
        get() = currentLifeTimeMs >= maxLifeTimeMs

    val lifeRatio: Float
        get() = (1f - (currentLifeTimeMs / maxLifeTimeMs)).coerceIn(0f, 1f)
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

@Stable
class ConfettiState {
    var particles = mutableStateListOf<ConfettiParticle>()
        private set

    var isRunning by mutableStateOf(false)
        private set

    var containerSize by mutableStateOf(IntSize.Zero)

    fun spawnCelebration(count: Int = 160) {
        val width = if (containerSize.width > 0) containerSize.width.toFloat() else 1080f
        val height = if (containerSize.height > 0) containerSize.height.toFloat() else 1920f

        val newParticles = ArrayList<ConfettiParticle>(count)

        // Cannon 1: Bottom Left shooting up-right
        val leftCount = count / 3
        for (i in 0 until leftCount) {
            val angle = (-Random.nextFloat() * 35f - 45f) * (PI.toFloat() / 180f) // -45° to -80°
            val speed = Random.nextFloat() * 650f + 650f
            newParticles.add(
                createParticle(
                    startX = Random.nextFloat() * 80f,
                    startY = height * 0.85f + Random.nextFloat() * 80f,
                    vx = cos(angle) * speed,
                    vy = sin(angle) * speed
                )
            )
        }

        // Cannon 2: Bottom Right shooting up-left
        val rightCount = count / 3
        for (i in 0 until rightCount) {
            val angle = (-Random.nextFloat() * 35f - 100f) * (PI.toFloat() / 180f) // -100° to -135°
            val speed = Random.nextFloat() * 650f + 650f
            newParticles.add(
                createParticle(
                    startX = width - Random.nextFloat() * 80f,
                    startY = height * 0.85f + Random.nextFloat() * 80f,
                    vx = cos(angle) * speed,
                    vy = sin(angle) * speed
                )
            )
        }

        // Fountain 3: Center bursting outward
        val centerCount = count - leftCount - rightCount
        for (i in 0 until centerCount) {
            val angle = (-Random.nextFloat() * 90f - 45f) * (PI.toFloat() / 180f) // -45° to -135°
            val speed = Random.nextFloat() * 550f + 500f
            newParticles.add(
                createParticle(
                    startX = width * 0.5f + (Random.nextFloat() * 120f - 60f),
                    startY = height * 0.65f + (Random.nextFloat() * 80f - 40f),
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
            ConfettiShape.RECTANGLE -> Random.nextFloat() * 12f + 16f
            ConfettiShape.RIBBON -> Random.nextFloat() * 8f + 10f
            ConfettiShape.CIRCLE -> Random.nextFloat() * 10f + 12f
        }

        val height = when (shapeType) {
            ConfettiShape.RECTANGLE -> Random.nextFloat() * 8f + 12f
            ConfettiShape.RIBBON -> Random.nextFloat() * 22f + 26f
            ConfettiShape.CIRCLE -> width
        }

        val color = ConfettiCelebrationColors[Random.nextInt(ConfettiCelebrationColors.size)]
        val lifeTimeMs = Random.nextFloat() * 1500f + 2800f

        return ConfettiParticle(
            x = startX,
            y = startY,
            vx = vx,
            vy = vy,
            width = width,
            height = height,
            color = color,
            shape = shapeType,
            maxLifeTimeMs = lifeTimeMs
        )
    }

    fun update(dtSec: Float) {
        if (particles.isEmpty()) {
            isRunning = false
            return
        }

        val gravity = 780f // downward acceleration
        val drag = 0.985f  // air resistance

        val iterator = particles.iterator()
        while (iterator.hasNext()) {
            val p = iterator.next()
            p.currentLifeTimeMs += dtSec * 1000f

            if (p.isDead) {
                iterator.remove()
                continue
            }

            // Physics integration
            p.vy += gravity * dtSec
            p.vx *= drag
            p.x += p.vx * dtSec
            p.y += p.vy * dtSec

            // 3D tumble rotations
            p.rotationX += p.vRotX * dtSec
            p.rotationY += p.vRotY * dtSec
            p.rotationZ += p.vRotZ * dtSec
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

@Composable
fun ConfettiHost(
    state: ConfettiState,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(state.isRunning) {
        if (!state.isRunning) return@LaunchedEffect

        var lastFrameTime = withFrameMillis { it }
        while (isActive && state.isRunning) {
            withFrameMillis { currentFrameTime ->
                val dtSec = ((currentFrameTime - lastFrameTime) / 1000f).coerceIn(0.001f, 0.05f)
                lastFrameTime = currentFrameTime
                state.update(dtSec)
            }
        }
    }

    Box(
        modifier = modifier
            .onSizeChanged { state.containerSize = it }
    ) {
        if (state.particles.isNotEmpty()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                for (p in state.particles) {
                    val alpha = p.lifeRatio
                    if (alpha <= 0.01f) continue

                    // 3D fluttering projection
                    val scaleX = cos(p.rotationX).coerceIn(-1f, 1f)
                    val scaleY = cos(p.rotationY).coerceIn(-1f, 1f)

                    if (scaleX == 0f || scaleY == 0f) continue

                    val particleColor = p.color.copy(alpha = alpha)

                    when (p.shape) {
                        ConfettiShape.CIRCLE -> {
                            drawCircle(
                                color = particleColor,
                                radius = (p.width / 2f) * kotlin.math.abs(scaleX),
                                center = Offset(p.x, p.y)
                            )
                        }
                        ConfettiShape.RECTANGLE, ConfettiShape.RIBBON -> {
                            val w = p.width * kotlin.math.abs(scaleX)
                            val h = p.height * kotlin.math.abs(scaleY)

                            rotate(degrees = p.rotationZ, pivot = Offset(p.x, p.y)) {
                                drawRect(
                                    color = particleColor,
                                    topLeft = Offset(p.x - w / 2f, p.y - h / 2f),
                                    size = Size(w, h)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
