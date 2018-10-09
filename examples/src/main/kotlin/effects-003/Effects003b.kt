import org.openrndr.Program

import org.openrndr.application
import org.openrndr.configuration

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.math.Vector2
import org.openrndr.workshop.Separate

class Effects003b : Program() {

    var drawFunc = {}
    override fun setup() {

        drawFunc = {
            val scale = (RenderTarget.active.width.toDouble() / width)

            val separate = Separate()

            poster(drawer) {
                drawer.background(ColorRGBa.BLACK)

                separate.redShift = Vector2(Math.random() - 0.5, Math.random() - 0.5) * 0.1
                separate.greenShift = Vector2(Math.random() - 0.5, Math.random() - 0.5) * 0.1
                separate.blueShift = Vector2(Math.random() - 0.5, Math.random() - 0.5) * 0.1

                layer(post = separate) {
                    drawer.fill = ColorRGBa.WHITE
                    drawer.stroke = null
                    drawer.circle(Vector2(width / 2.0, height / 2.0), 200.0)
                }
                Thread.sleep(100)
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
    application(Effects003b(), configuration {
        width = 480
        height = 640
    })
}