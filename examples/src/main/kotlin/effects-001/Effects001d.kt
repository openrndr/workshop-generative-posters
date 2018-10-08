import org.openrndr.Program

import org.openrndr.application
import org.openrndr.configuration

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.math.Vector2
import org.openrndr.workshop.StepWaves
import org.openrndr.workshop.VerticalStepWaves

class Effects001d : Program() {

    var drawFunc = {}
    override fun setup() {

        drawFunc = {
            val scale = (RenderTarget.active.width.toDouble() / width)

            val stepWaves = VerticalStepWaves()
            val stepWaves2 = StepWaves()
            poster(drawer) {
                drawer.background(ColorRGBa.BLACK)

                stepWaves.phase = seconds
                stepWaves2.phase = seconds

                stepWaves.steps = 64
                stepWaves.period = 64 * Math.PI*2.0
                stepWaves2.steps = 8

                stepWaves2.amplitude = 0.5

                layer(post = stepWaves2) {
                    layer(post = stepWaves) {
                        drawer.fill = ColorRGBa.WHITE
                        drawer.stroke = null
                        drawer.circle(Vector2(width / 2.0, height / 2.0), 200.0)
                    }
                }
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
    application(Effects001d(), configuration {
        width = 480
        height = 640
    })
}