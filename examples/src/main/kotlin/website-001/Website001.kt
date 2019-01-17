import com.chimbori.crux.articles.ArticleExtractor
import org.openrndr.application
import org.openrndr.draw.ColorBuffer
import java.net.URL
import org.jsoup.Jsoup
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.RenderTarget
import org.openrndr.draw.grayscale
import org.openrndr.extensions.Screenshots
import org.openrndr.extra.compositor.*
import org.openrndr.filter.blend.add
import org.openrndr.filter.blend.multiply
import org.openrndr.shape.Rectangle
import org.openrndr.text.Writer
import org.openrndr.workshop.toolkit.typography.Fonts


fun main(args: Array<String>) {
    application {
        configure {
            width = 480
            height = 640
        }

        program {
            val url = "https://nos.nl/artikel/2251729-labour-leider-voor-nieuw-brexit-referendum-als-partijleden-dat-wensen.html"
            val rawHTML = URLFetcher.fetch(URL(url), 1000, 1000)

            val article = ArticleExtractor.with(url, rawHTML)
                .extractMetadata()
                .extractContent()
                .article()

            val imageElements = Jsoup.parse(rawHTML).getElementsByTag("img")
            val images = mutableListOf<ColorBuffer>()

            // -- download all images
            imageElements.forEach {
                val imageUrl = it.attr("src")
                if (imageUrl.endsWith(".png") || imageUrl.endsWith(".jpg") || imageUrl.endsWith(".gif")) {
                    images.add(ColorBuffer.fromUrl(imageUrl))
                }
            }


            extend(Screenshots().apply {
                scale = 4.0
            })


            val scale = (RenderTarget.active.width.toDouble() / width)

            var baseColor = ColorRGBa(Math.random(), Math.random(), Math.random())

            val poster = compose {
                layer {
                    draw {
                        drawer.background(baseColor)
                    }
                }

                images.forEach {
                    // -- create a layer for every image, these will be multiply blended
                    layer {
                        blend(multiply)
                        draw {
                            drawer.background(ColorRGBa.WHITE)
                            drawer.drawStyle.colorMatrix = grayscale(0.3, 0.3, 0.3)
                            val imageSize = it.bounds
                            val target = Rectangle(Math.random() * (width - imageSize.width), Math.random() * (height - imageSize.height), imageSize.width, imageSize.height)
                            drawer.image(it, imageSize, target)
                        }
                    }
                }

                // - create the typography layer
                layer {
                    draw {
                        drawer.fill = baseColor
                        drawer.fontMap = FontImageMap.fromUrl(Fonts.IBMPlexMono_Bold, 64.0, scale)

                        val w = Writer(drawer)
                        w.box = Rectangle(10.0, 10.0, width - 20.0, height - 20.0)
                        w.newLine()
                        // -- randomize tracking and leading
                        w.style.tracking = Math.random() * 10.0
                        w.style.leading = Math.random() * 10.0
                        w.text(article.title)
                    }
                }

            }

            extend {
                baseColor = ColorRGBa(Math.random(), Math.random(), Math.random())
                poster.draw(drawer)
                Thread.sleep(500)
            }
        }
    }
}
