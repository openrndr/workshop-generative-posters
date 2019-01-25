import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.RenderTarget
import org.openrndr.draw.grayscale
import org.openrndr.extensions.Screenshots
import org.openrndr.extra.compositor.*
import org.openrndr.filter.blend.multiply
import org.openrndr.shape.Rectangle
import org.openrndr.text.Writer
import org.openrndr.workshop.toolkit.typography.Fonts
import org.openrndr.workshop.toolkit.website.scrapeWebsite

fun main(args: Array<String>) {
    application {
        configure {
            width = 480
            height = 640
        }

        program {
            val url = "https://nos.nl/artikel/2268648-ijsballen-veroorzaken-schade-aan-auto-s-en-een-gewonde.html"
            val websiteContents = scrapeWebsite(url)
            extend(Screenshots().apply {
                scale = 4.0
            })

            val scale = (RenderTarget.active.width.toDouble() / width)
            var baseColor = ColorRGBa(Math.random(), Math.random(), Math.random())

            val poster = compose {
                draw {
                    baseColor = ColorRGBa(Math.random(), Math.random(), Math.random())
                }

                layer {
                    draw {
                        drawer.background(baseColor)
                    }
                }
                websiteContents.images.forEach {
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
                        w.style.tracking = Math.random() * 10.0 + 10.0
                        w.style.leading = Math.random() * 10.0 + 10.0
                        w.text(websiteContents.title)
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
