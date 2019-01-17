import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.LineCap
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.math.Vector2
import org.openrndr.shape.*
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer
import org.openrndr.extra.compositor.post


fun main(args: Array<String>) {

    fun srand() = (Math.random() - 0.5) * 2.0
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
                    draw {
                        drawer.background(ColorRGBa(Math.random(), Math.random(), Math.random()))
                        val radius = 15 + (Math.random() * 40.0).toInt()
                        val contours = mutableListOf<ShapeContour>()
                        for (y in 0 until height / radius + 1) {
                            for (x in 0 until width / radius + 1) {
                                contours.add(
                                    contour {
                                        moveTo(Vector2(x * radius * 1.0, y * radius * 1.0))
                                        curveTo(cursor + Vector2(srand(), srand()) * radius.toDouble(),
                                            cursor + Vector2(srand(), srand()) * radius.toDouble(),
                                            cursor + Vector2(srand(), srand()) * radius.toDouble())
                                    }

                                )
                            }
                        }

                        drawer.stroke = ColorRGBa(Math.random(), Math.random(), Math.random())
                        drawer.fill = null
                        drawer.lineCap = LineCap.ROUND
                        drawer.strokeWeight = 2.0
                        drawer.contours(contours)
                    }
                }
            }

            extend {
                poster.draw(drawer)
                Thread.sleep(500)
            }
        }
    }
}
