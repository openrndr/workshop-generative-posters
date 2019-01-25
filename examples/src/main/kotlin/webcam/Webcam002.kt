import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.compositor.*
import org.openrndr.ffmpeg.FFMPEGVideoPlayer
import org.openrndr.workshop.toolkit.filters.Threshold

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
                        drawer.background(ColorRGBa.PINK)
                        for (y in 0 until height step 64) {
                            for (x in 0 until width step 64) {
                                drawer.circle(x*1.0, y*1.0, 64.0)
                            }
                        }
                    }
                }
                layer {
                    post(Threshold()) {
                        light = ColorRGBa.RED
                        dark = ColorRGBa.TRANSPARENT
                        threshold = 0.5
                    }
                    draw {
                        webcam.next()
                        val s = height.toDouble()/cameraHeight
                        drawer.scale(s)
                        drawer.translate((s*width-cameraWidth)/2.0, 0.0)

                        webcam.draw(drawer)
                    }
                }
            }
            extend {
                poster.draw(drawer)
            }
        }
    }
}