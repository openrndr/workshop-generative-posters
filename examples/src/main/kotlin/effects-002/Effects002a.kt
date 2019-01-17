import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.math.Vector2
import org.openrndr.workshop.toolkit.filters.ZoomMosaic

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

                val zoomMosaic = ZoomMosaic()

                poster(drawer) {
                    drawer.background(ColorRGBa.BLACK)

                    zoomMosaic.scale = Math.cos(seconds)+2.0
                    layer(post = zoomMosaic) {
                        drawer.fill = ColorRGBa.WHITE
                        drawer.stroke = null
                        drawer.circle(Vector2(width/2.0, height/2.0), 200.0)
                    }
                }

            }
        }
    }
}
