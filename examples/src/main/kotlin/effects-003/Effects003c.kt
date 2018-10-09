import org.openrndr.Program

import org.openrndr.application
import org.openrndr.configuration

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.math.Vector2
import org.openrndr.workshop.Separate
import org.openrndr.workshop.VerticalStepWaves
import org.openrndr.workshop.Waves

class Effects003c : Program() {

    var drawFunc = {}
    override fun setup() {

        drawFunc = {
            val scale = (RenderTarget.active.width.toDouble() / width)

            val separate = Separate()
            val wave = Waves()
            val stepWaves = VerticalStepWaves()


            poster(drawer) {
                drawer.background(ColorRGBa.BLACK)


                wave.amplitude = 0.1
                wave.phase = seconds
                wave.period = Math.PI * 2 * 16.0 * Math.random()

                stepWaves.steps = (Math.random() * 20.0 + 10.0).toInt()
                stepWaves.phase = seconds
                stepWaves.amplitude = Math.random()

                separate.redShift = Vector2(Math.random() - 0.5, Math.random() - 0.5) * 0.1
                separate.greenShift = Vector2(Math.random() - 0.5, Math.random() - 0.5) * 0.1
                separate.blueShift = Vector2(Math.random() - 0.5, Math.random() - 0.5) * 0.1

                val effects = listOf(separate, stepWaves, wave, separate)
                layer(post = effects) {
                    drawer.fill = ColorRGBa.WHITE.opacify(Math.cos(seconds) * 0.5 + 0.5)
                    drawer.stroke = null
                    drawer.circle(mouse.position, 200.0)
                }
                //Thread.sleep(100)
            }
        }
        extend(Screenshots().apply {
            scale = 4.0
        })

    }

    override fun draw() {
        drawFunc()
    }
}

fun main(args: Array<String>) {
    application(Effects003c(), configuration {
        width = 480
        height = 640
    })
}