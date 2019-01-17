import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.math.Vector2
import org.openrndr.workshop.toolkit.filters.Separate
import org.openrndr.workshop.toolkit.filters.VerticalStepWaves
import org.openrndr.workshop.toolkit.filters.Waves


fun main(args: Array<String>) {
    application {
        configure {
            width = 480
            height = 640
        }

        program {
            extend(Screenshots().apply {
                scale = 4.0
            })

            extend {

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
        }
    }
}
