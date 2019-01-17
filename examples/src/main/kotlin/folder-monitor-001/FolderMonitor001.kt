import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.ColorBuffer
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.shape.Rectangle
import org.openrndr.text.Writer
import org.openrndr.workshop.toolkit.typography.Fonts
import java.io.File
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer

fun main(args: Array<String>) {
    application {
        configure {

            width = 480
            height = 640
        }
        program {
            val folderName = "monitor-data"

            if (!File(folderName).exists()) {
                File(folderName).mkdir()
            }

            extend(Screenshots().apply {
                scale = 4.0
            })


            val scale = (RenderTarget.active.width.toDouble() / width)

            val folder = File(folderName)
            val textExtensions = setOf("txt")
            val imageExtensions = setOf("jpg", "png")
            val supportedExtensions = textExtensions + imageExtensions

            var posterFile: File? = null

            val poster = compose {
                layer {
                    draw {
                        drawer.background(ColorRGBa.BLACK)
                        posterFile?.let {

                            // -- if the selected file is a text file
                            if (it.extension in textExtensions) {
                                val text = it.readText();
                                drawer.fill = ColorRGBa.WHITE
                                drawer.fontMap = FontImageMap.fromUrl(Fonts.IBMPlexMono_Bold, 32.0, scale)
                                val w = Writer(drawer)
                                w.box = Rectangle(0.0, 0.0, width * 1.0, height * 1.0)
                                w.newLine()
                                w.text(text)
                            }

                            // -- if the selected file is an image
                            if (it.extension in imageExtensions) {
                                val image = ColorBuffer.fromFile(it.absolutePath)
                                drawer.image(image)
                                // -- clean-up the image
                                image.destroy()
                            }
                        }
                    }
                }
            }

            extend {
                // -- list the files in the specified folder and sort by most recent
                val recentFiles = folder.listFiles().filter { it.extension.toLowerCase() in supportedExtensions }.sortedByDescending { it.lastModified() }.take(10)
                // -- only do work when files have been found
                if (recentFiles.isNotEmpty()) {
                    // -- select a file from recentFiles
                    posterFile = recentFiles[(Math.random() * recentFiles.size).toInt()]
                }

                poster.draw(drawer)

                Thread.sleep(500)

            }
        }
    }
}
