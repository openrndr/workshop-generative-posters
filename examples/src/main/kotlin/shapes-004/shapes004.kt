import org.openrndr.Program
import org.openrndr.application
import org.openrndr.configuration
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*
import org.openrndr.extensions.Screenshots
import org.openrndr.filter.blend.add
import org.openrndr.filter.blend.multiply
import org.openrndr.math.Vector2
import org.openrndr.math.map
import org.openrndr.workshop.toolkit.filters.darken
import org.openrndr.workshop.toolkit.filters.lighten
import poster

class Shapes004 : Program() {

    var drawFunc = {}

    override fun setup() {

        drawFunc = {
            poster(drawer) {

                drawer.background(ColorRGBa.WHITE)

                for(l in 0..10) {
                    if(l%2 == 0) {
                        layer(blend = lighten) {
                            drawer.fill = null
                            drawer.stroke = ColorRGBa.PINK
                            drawer.strokeWeight = l*0.5+0.5
                            drawCircles(drawer, l)
                        }
                    } else {
                        layer(blend = darken) {
                            drawer.fill = null
                            drawer.stroke = ColorRGBa.WHITE
                            drawer.strokeWeight = l*0.5+0.5
                            drawCircles(drawer, l)
                        }
                    }
                }
            }
        }
        extend(Screenshots().apply {
            scale = 4.0
        })
    }

    private fun drawCircles(drawer: Drawer, l: Int) {

        var count = 1
        if(mouse.position.y> 0) {
            count = map(0.0, height * 1.0, 0.0, 10.0, mouse.position.y).toInt() + 1
        }
        val xStep = (width  / count)
        val yStep = (height / count)
        drawer.translate(Vector2(width / 2.0, height / 2.0))
        val listOfVector2 = mutableListOf<Vector2>()
        for (x in 0 until count) {
            for (y in 0 until count) {
                val xCorrect = (count * xStep) / 2.0
                val yCorrect = (count * yStep) / 2.0
                listOfVector2.add(Vector2(x * xStep - xCorrect + xStep / 2, y * yStep - yCorrect + yStep / 2))
            }
        }
        drawer.circles(listOfVector2, l*10.0)

    }

    override fun draw() {
        drawFunc()
    }
}

fun main(args: Array<String>) {
    application(Shapes004(), configuration {
        width = 480
        height = 640
    })
}