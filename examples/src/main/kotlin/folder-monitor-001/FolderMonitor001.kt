import org.openrndr.Program

import org.openrndr.application
import org.openrndr.configuration

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.ColorBuffer
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.shape.Rectangle
import org.openrndr.text.Writer
import org.openrndr.workshop.StepWaves
import java.io.File

class FolderMonitor001 : Program() {

    var drawFunc = {}
    override fun setup() {

        val folderName = "monitor-data"

        drawFunc = {
            val scale = (RenderTarget.active.width.toDouble() / width)

            val folder = File(folderName)

            val textExtensions = setOf("txt")
            val imageExtensions = setOf("jpg", "png")
            val supportedExtensions = textExtensions + imageExtensions

            // -- list the files in the specified folder and sort by most recent
            val recentFiles = folder.listFiles().
                    filter { it.extension.toLowerCase() in supportedExtensions }.
                    sortedByDescending { it.lastModified() }.take(10)


            // -- only do work when files have been found
            if (recentFiles.isNotEmpty()) {

                // -- select a file from recentFiles
                val posterFile = recentFiles[(Math.random() * recentFiles.size).toInt()]

                // -- if the selected file is a text file
                if (posterFile.extension in textExtensions) {
                    val text = posterFile.readText()
                    poster(drawer) {
                        drawer.background(ColorRGBa.BLACK)
                        layer {
                            drawer.fill = ColorRGBa.WHITE
                            drawer.fontMap = FontImageMap.fromUrl("file:data/fonts/IBMPlexMono-Bold.ttf", 32.0, scale)
                            val w = Writer(drawer)
                            w.box = Rectangle(0.0, 0.0, width * 1.0, height * 1.0)
                            w.newLine()
                            w.text(text)
                        }
                    }
                }

                // -- if the selected file is an image
                if (posterFile.extension in imageExtensions) {
                    val image = ColorBuffer.fromFile(posterFile.absolutePath)
                    poster(drawer) {
                        drawer.background(ColorRGBa.BLACK)
                        layer {
                            drawer.image(image)
                        }
                    }
                    // -- clean-up the image
                    image.destroy()
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
    application(FolderMonitor001(), configuration {
        width = 480
        height = 640
    })
}