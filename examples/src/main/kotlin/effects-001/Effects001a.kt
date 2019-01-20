import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extensions.Screenshots
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer
import org.openrndr.extra.compositor.post
import org.openrndr.math.Vector2
import org.openrndr.workshop.toolkit.filters.StepWaves

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
                    post(StepWaves()) {
                        phase = seconds
                    }
                    draw {
                        drawer.fill = ColorRGBa.WHITE
                        drawer.stroke = null
                        drawer.circle(Vector2(width / 2.0, height / 2.0), 200.0)
                    }
                }
            }

            extend {
                poster.draw(drawer)
            }

        }
    }
}
