import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extensions.Screenshots
import org.openrndr.extra.compositor.*
import org.openrndr.filter.blend.multiply
import org.openrndr.math.Vector2
import org.openrndr.shape.Circle


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
                        val radius = 5 + (Math.random() * 40.0).toInt()
                        val circles = mutableListOf<Circle>()
                        for (y in 0 until height / radius) {
                            for (x in 0 until width / radius) {
                                circles.add(Circle(Vector2(x * radius * 1.0 + radius * 0.5, y * radius * 1.0 + radius * 0.5), radius * 0.5))
                            }
                        }
                        drawer.background(ColorRGBa(Math.random(), Math.random(), Math.random()))
                        drawer.fill = ColorRGBa(Math.random(), Math.random(), Math.random())
                        drawer.stroke = null
                        drawer.circles(circles)
                    }
                }

                layer {
                    blend(multiply)
                    draw {
                        drawer.background(ColorRGBa.WHITE)
                        val radius = 5 + (Math.random() * 40.0).toInt()
                        val circles = mutableListOf<Circle>()
                        for (y in 0 until height / radius) {
                            for (x in 0 until width / radius) {
                                circles.add(Circle(Vector2(x * radius * 1.0 + radius * 0.5, y * radius * 1.0 + radius * 0.5), radius * 0.5))
                            }
                        }
                        drawer.fill = ColorRGBa(Math.random(), Math.random(), Math.random())
                        drawer.stroke = null
                        drawer.circles(circles)
                    }
                }

                layer {
                    blend(multiply)
                    draw {
                        drawer.background(ColorRGBa.WHITE)
                        val radius = 5 + (Math.random() * 40.0).toInt()
                        val circles = mutableListOf<Circle>()
                        for (y in 0 until height / radius) {
                            for (x in 0 until width / radius) {
                                circles.add(Circle(Vector2(x * radius * 1.0 + radius * 0.5, y * radius * 1.0 + radius * 0.5), radius * 0.5))
                            }
                        }
                        drawer.fill = ColorRGBa(Math.random(), Math.random(), Math.random())
                        drawer.stroke = null
                        drawer.circles(circles)
                    }
                }
            }


            extend {
                poster.draw(drawer)
                Thread.sleep(1000)
            }
        }
    }
}
