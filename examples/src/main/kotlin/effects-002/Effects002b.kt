import org.openrndr.Program

import org.openrndr.application
import org.openrndr.configuration

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.math.Vector2
import org.openrndr.workshop.ZoomMosaic

class Effects002b : Program() {

    var drawFunc = {}
    override fun setup() {

        drawFunc = {
            val scale = (RenderTarget.active.width.toDouble() / width)

            val zoomMosaic = ZoomMosaic()
            val zoomMosaic2 = ZoomMosaic()

            poster(drawer) {
                drawer.background(ColorRGBa.BLACK)

                zoomMosaic2.scale = Math.cos(seconds * 2.1) + 2.0
                zoomMosaic2.ySteps = 4
                zoomMosaic2.xSteps = 4
                zoomMosaic.scale = Math.cos(seconds) + 2.0
                layer(post = listOf(zoomMosaic2, zoomMosaic)) {
                    drawer.fill = ColorRGBa.WHITE
                    drawer.stroke = null
                    drawer.circle(Vector2(width / 2.0, height / 2.0), 200.0)
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
    application(Effects002b(), configuration {
        width = 480
        height = 640
    })
}