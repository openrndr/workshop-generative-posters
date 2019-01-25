import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.draw.LineCap
import org.openrndr.extra.compositor.blend
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer
import org.openrndr.ffmpeg.FFMPEGVideoPlayer
import org.openrndr.filter.blend.multiply

fun main(args: Array<String>) {
    application {
        configure {
            width = 480
            height = 640
        }

        program {
            val cameraWidth = 640
            val cameraHeight = 480
            val webcam = FFMPEGVideoPlayer.fromDevice(width = cameraWidth, height = cameraHeight, framerate = 30.0)

            webcam.start()
            val poster = compose {
                layer {
                    draw {
                        webcam.next()
                        val s = height.toDouble()/cameraHeight
                        drawer.scale(s)
                        drawer.translate((s*width-cameraWidth)/2.0, 0.0)

                        webcam.draw(drawer)
                    }
                }
                layer {
                    blend(multiply)
                    draw {
                        drawer.fill = ColorRGBa.RED
                        drawer.stroke = null
                        drawer.rectangle(20.0, 20.0, width-40.0, height-40.0)

                        drawer.stroke = ColorRGBa.WHITE
                        drawer.strokeWeight = 20.0
                        drawer.lineCap = LineCap.ROUND
                        drawer.lineSegment(60.0, 60.0, width-60.0, height-60.0)
                    }

                }
            }
            extend {
                poster.draw(drawer)
            }
        }

    }
}