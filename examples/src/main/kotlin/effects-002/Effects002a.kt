import org.openrndr.Program

import org.openrndr.application
import org.openrndr.configuration

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.math.Vector2
import org.openrndr.workshop.ZoomMosaic

class Effects002a : Program() {

    var drawFunc = {}
    override fun setup() {

        drawFunc = {
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
        extend(Screenshots().apply {
            scale = 4.0
        })
    }

    override fun draw() {
        drawFunc()
    }
}

fun main(args: Array<String>) {
    application(Effects002a(), configuration {
        width = 480
        height = 640
    })
}