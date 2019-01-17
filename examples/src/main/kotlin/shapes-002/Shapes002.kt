import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.LineCap
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.extra.compositor.*
import org.openrndr.filter.blend.multiply
import org.openrndr.math.Vector2
import org.openrndr.shape.LineSegment


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
                    draw {

                        val baseColor = ColorRGBa(Math.random(), Math.random(), Math.random())
                        drawer.background(baseColor)

                        val radius = 15 + (Math.random() * 40.0).toInt()
                        val lines = mutableListOf<LineSegment>()
                        for (y in 0 until height / radius + 1) {
                            for (x in 0 until width / radius + 1) {
                                lines.add(LineSegment(Vector2(x * radius * 1.0, y * radius * 1.0), Vector2(x * radius + Math.random() * radius, y * radius + Math.random() * radius)))
                            }
                        }
                        drawer.stroke = ColorRGBa(Math.random(), Math.random(), Math.random())
                        drawer.lineCap = LineCap.ROUND
                        drawer.strokeWeight = Math.random() * 4.0 + 4.0
                        drawer.lineSegments(lines.flatMap { listOf(it.start, it.end) })
                    }
                }
                layer {
                    blend(multiply)
                    draw {
                        drawer.background(ColorRGBa.WHITE)
                        val radius = 15 + (Math.random() * 40.0).toInt()
                        val lines = mutableListOf<LineSegment>()
                        for (y in 0 until height / radius + 1) {
                            for (x in 0 until width / radius + 1) {
                                lines.add(LineSegment(Vector2(x * radius * 1.0, y * radius * 1.0), Vector2(x * radius + Math.random() * radius, y * radius + Math.random() * radius)))
                            }
                        }

                        drawer.stroke = ColorRGBa(Math.random(), Math.random(), Math.random())
                        drawer.lineCap = LineCap.ROUND
                        drawer.strokeWeight = Math.random() * 4.0 + 4.0
                        drawer.lineSegments(lines.flatMap { listOf(it.start, it.end) })
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
