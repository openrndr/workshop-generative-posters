import org.openrndr.Program

import org.openrndr.application
import org.openrndr.configuration

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.LineCap
import org.openrndr.draw.RenderTarget
import org.openrndr.draw.grayscale
import org.openrndr.extensions.Screenshots
import org.openrndr.filter.blend.multiply
import org.openrndr.math.Vector2
import org.openrndr.shape.Circle
import org.openrndr.shape.LineSegment
import org.openrndr.shape.Rectangle
import org.openrndr.text.Writer

class Shapes002 : Program() {

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

                    val radius = 15 + (Math.random()*40.0).toInt()
                    val lines = mutableListOf<LineSegment>()
                    for (y in 0 until height/radius + 1) {
                        for (x in 0 until width/radius + 1) {
                            lines.add(LineSegment(Vector2(x*radius*1.0, y*radius*1.0), Vector2(x*radius+Math.random()*radius, y*radius+Math.random()*radius)))
                        }
                    }

                    drawer.stroke = layer1Color
                    drawer.lineCap = LineCap.ROUND
                    drawer.strokeWeight = Math.random()*4.0 + 4.0
                    drawer.lineSegments(lines.flatMap { listOf(it.start,it.end) })
                }

                layer(blend = multiply) {
                    drawer.background(ColorRGBa.WHITE)

                    val radius = 15 + (Math.random()*40.0).toInt()
                    val lines = mutableListOf<LineSegment>()
                    for (y in 0 until height/radius + 1) {
                        for (x in 0 until width/radius + 1) {
                            lines.add(LineSegment(Vector2(x*radius*1.0, y*radius*1.0), Vector2(x*radius+Math.random()*radius, y*radius+Math.random()*radius)))
                        }
                    }

                    drawer.stroke = layer1Color
                    drawer.lineCap = LineCap.ROUND
                    drawer.strokeWeight = Math.random()*4.0 + 4.0
                    drawer.lineSegments(lines.flatMap { listOf(it.start,it.end) })
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
    application(Shapes002(), configuration {
        width = 480
        height = 640
    })
}