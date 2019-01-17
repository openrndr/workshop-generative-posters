import org.openrndr.PresentationMode
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.ColorBuffer
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.MagnifyingFilter
import org.openrndr.draw.MinifyingFilter
import org.openrndr.extensions.Screenshots
import org.openrndr.math.Vector2
import org.openrndr.shape.Rectangle
import org.openrndr.workshop.toolkit.typography.Fonts
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer


private fun Rectangle.translated(vector2: Vector2): Rectangle {
    return Rectangle(corner + vector2, width, height)
}

// -- entry point of the application
fun main(args: Array<String>) {
    application {
        configure {
            width = 480
            height = 680
        }

        program {


            window.presentationMode = PresentationMode.MANUAL

            val images = mutableListOf<ColorBuffer>()

            // -- setup drop event
            window.drop.listen {
                val supportedExtensions = setOf("png", "jpg", "jpeg", "gif")
                for (file in it.files) {
                    if (file.isDirectory) {
                        file.listFiles().forEach {
                            if (it.extension in supportedExtensions) {
                                images.add(ColorBuffer.fromFile(it.absolutePath))
                            }
                        }
                    }

                    if (file.isFile) {
                        if (file.extension in supportedExtensions) {
                            println(file.absolutePath)
                            images.add(ColorBuffer.fromFile(file.absolutePath))
                        }
                    }
                }
                window.requestDraw()
            }

            mouse.clicked.listen {
                window.requestDraw()
            }


            // -- extend with screenshots, spacebar to shoot
            extend(Screenshots().apply {
                scale = 4.0
            })


            val poster = compose {
                layer {
                    draw {
                        drawer.background(ColorRGBa.WHITE)
                        drawer.fontMap = FontImageMap.fromUrl(Fonts.IBMPlexMono_Bold, 14.0, 2.0)

                        if (images.size == 0) {
                            drawer.fill = ColorRGBa.BLUE
                            drawer.text("drop images here", 20.0, 20.0)
                        } else {
                            for (image in images.shuffled()) {
                                image.filter(MinifyingFilter.LINEAR_MIPMAP_LINEAR, MagnifyingFilter.LINEAR)
                                val imageBounds = image.bounds.offsetEdges(Math.random() * image.height * -0.2)

                                val iw = imageBounds.width * 0.1
                                val ih = imageBounds.height * 0.1
                                val spacing = 2.0
                                val stacking = 40
                                val margin = spacing * stacking

                                val targetBounds = Rectangle(Math.random() * (width - iw + margin / 2.0) - margin, Math.random() * (height - ih + margin / 2.0) - margin, imageBounds.width * 0.1, imageBounds.height * 0.1)

                                for (i in 0 until stacking) {
                                    drawer.image(image, imageBounds, targetBounds.translated(Vector2(spacing * i.toDouble(), spacing * i.toDouble())))
                                }
                            }
                        }
                    }
                }
            }

            extend {
                poster.draw(drawer)
            }
        }
    }
}
