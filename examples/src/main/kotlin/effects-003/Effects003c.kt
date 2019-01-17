import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extensions.Screenshots
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer
import org.openrndr.extra.compositor.post
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

            val poster = compose {
                layer {
                    post(Separate()) {
                        redShift = Vector2(Math.random() - 0.5, Math.random() - 0.5) * 0.1
                        greenShift = Vector2(Math.random() - 0.5, Math.random() - 0.5) * 0.1
                        blueShift = Vector2(Math.random() - 0.5, Math.random() - 0.5) * 0.1
                    }
                    post(Waves()) {
                        amplitude = 0.1
                        phase = seconds
                        period = Math.PI * 2 * 16.0 * Math.random()
                    }
                    post(VerticalStepWaves()) {
                        steps = (Math.random() * 20.0 + 10.0).toInt()
                        phase = seconds
                        amplitude = Math.random()
                    }
                    draw {
                        drawer.fill = ColorRGBa.WHITE.opacify(Math.cos(seconds) * 0.5 + 0.5)
                        drawer.stroke = null
                        drawer.circle(mouse.position, 200.0)
                    }
                }
            }

            extend {
                poster.draw(drawer)
            }
        }
    }
}
