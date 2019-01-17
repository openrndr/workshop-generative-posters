import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.math.Vector2
import org.openrndr.workshop.toolkit.filters.Separate


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

                poster(drawer) {
                    drawer.background(ColorRGBa.BLACK)

                    separate.redShift = Vector2(0.1, 0.1)
                    separate.greenShift = Vector2(0.1, 0.0)
                    separate.blueShift = Vector2(0.0, 0.1)

                    layer(post = separate) {
                        drawer.fill = ColorRGBa.WHITE
                        drawer.stroke = null
                        drawer.circle(Vector2(width / 2.0, height / 2.0), 200.0)
                    }
                }

            }
        }
    }
}
