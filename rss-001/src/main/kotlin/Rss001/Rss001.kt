import org.openrndr.Program
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.draw.FontImageMap
import org.openrndr.extensions.Screenshots

import org.openrndr.filter.blend.multiply
import org.openrndr.math.Vector2
import org.openrndr.rss.RSSParser
import org.openrndr.shape.Rectangle

import org.openrndr.text.Writer
import java.net.URL

class Rss001: Program() {
    var drawFunc = { }

    override fun setup() {

        val articles = RSSParser.parse(URL("http://feeds.nos.nl/nosnieuwsalgemeen"))

        println("I got ${articles.size} articles")
        drawFunc = {

            val article = articles[seconds.toInt()%articles.size]

            poster(drawer) {

                layer {
                    drawer.background(ColorRGBa.WHITE.shade(0.1))
                    drawer.fill = ColorRGBa.PINK
                    drawer.stroke = null
                    drawer.circle(Vector2(width/2.0, height/2.0) + Vector2(0.0, Math.sin(seconds)*50.0), 300.0)
                }

                layer(blend = multiply) {
                    drawer.fontMap = FontImageMap.fromUrl("file:data/fonts/IBMPlexMono-Bold.ttf", 16.0, 2.0)
                    drawer.fill = ColorRGBa.PINK
                    val w = Writer(drawer)
                    w.apply {
                        box = Rectangle(20.0, 20.0, width-40.0, height-20.0)
                        text(article.content ?: "")
                    }

                }
            }
        }
        extend(Screenshots().apply {
            scale = 4.0
        })
    }

    override fun draw() = drawFunc()
}

fun main(args: Array<String>) {
    application(Rss001(), configuration {
        height = 680
        width = 480
    })
}