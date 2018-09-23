import org.openrndr.Program

import org.openrndr.application
import org.openrndr.configuration

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.RenderTarget
import org.openrndr.draw.grayscale
import org.openrndr.extensions.Screenshots
import org.openrndr.filter.blend.multiply
import org.openrndr.math.Vector2
import org.openrndr.shape.Circle
import org.openrndr.shape.Rectangle
import org.openrndr.text.Writer

class Shapes001 : Program() {

    var drawFunc = {}
    override fun setup() {

        drawFunc = {
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
                    //drawer.background(ColorRGBa.BLACK)

                    val radius = 5 + (Math.random()*40.0).toInt()
                    val circles = mutableListOf<Circle>()
                    for (y in 0 until height/radius) {
                        for (x in 0 until width/radius) {
                            circles.add(Circle(Vector2(x * radius * 1.0 + radius*0.5, y * radius * 1.0  + radius*0.5), radius * 0.5))
                        }
                    }
                    drawer.fill = layer1Color
                    drawer.stroke = null
                    drawer.circles(circles)
                }
                layer(blend = multiply) {
                    drawer.background(ColorRGBa.WHITE)
                    val radius = 5 + (Math.random()*40.0).toInt()
                    val circles = mutableListOf<Circle>()
                    for (y in 0 until height/radius) {
                        for (x in 0 until width/radius) {
                            circles.add(Circle(Vector2(x * radius * 1.0 + radius*0.5, y * radius * 1.0 + radius*0.5), radius * 0.5))
                        }
                    }
                    drawer.fill = layer2Color
                    drawer.stroke = null
                    drawer.circles(circles)
                }
                layer(blend = multiply) {
                    drawer.background(ColorRGBa.WHITE)
                    val radius = 5 + (Math.random()*40.0).toInt()
                    val circles = mutableListOf<Circle>()
                    for (y in 0 until height/radius) {
                        for (x in 0 until width/radius) {
                            circles.add(Circle(Vector2(x * radius * 1.0 + radius*0.5, y * radius * 1.0 + radius*0.5), radius * 0.5))
                        }
                    }
                    drawer.fill = layer3Color
                    drawer.stroke = null
                    drawer.circles(circles)
                }



            }
            Thread.sleep(500)
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
    application(Shapes001(), configuration {
        width = 480
        height = 640
    })
}