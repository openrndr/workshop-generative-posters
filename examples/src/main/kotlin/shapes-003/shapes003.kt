import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.LineCap
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.math.Vector2
import org.openrndr.shape.*


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
            extend {

                val scale = (RenderTarget.active.width.toDouble() / width)
                // -- create a random base color
                val baseColor = ColorRGBa(Math.random(), Math.random(), Math.random())
                val layer1Color = ColorRGBa(Math.random(), Math.random(), Math.random())
                val layer2Color = ColorRGBa(Math.random(), Math.random(), Math.random())
                val layer3Color = ColorRGBa(Math.random(), Math.random(), Math.random())

                poster(drawer) {
                    drawer.background(baseColor)
                    // -- create a layer for every image, these will be multiply blended

                    layer {

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

                        drawer.stroke = layer1Color
                        drawer.fill = null
                        drawer.lineCap = LineCap.ROUND
                        drawer.strokeWeight = 2.0
                        drawer.contours(contours)
                    }


                }
                Thread.sleep(500)

            }
        }
    }
}
