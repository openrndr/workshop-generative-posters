import org.openrndr.application
import org.openrndr.draw.FontImageMap
import org.openrndr.extensions.Screenshots
import org.openrndr.filter.blur.GaussianBlur
import org.openrndr.workshop.toolkit.typography.Fonts
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer
import org.openrndr.extra.compositor.post

// -- entry point of the application
fun main(args: Array<String>) {
    application {
        configure {
            width = 640
            height = 480
        }

        program {
            extend(Screenshots())
            val poster = compose {
                layer {
                    post(GaussianBlur()) {
                        gain = 1.0
                        spread = 1.0
                        window = 25
                        sigma = Math.cos(seconds) * 5.0 + 5.0
                    }
                    draw {

                        drawer.fontMap = FontImageMap.fromUrl(Fonts.IBMPlexMono_Bold, 64.0, 2.0)
                        drawer.text("Tim Blurton", 20.0, 250.0)
                    }
                }
                layer {
                    draw {
                        drawer.fontMap = FontImageMap.fromUrl(Fonts.IBMPlexMono_Bold, 32.0, 2.0)
                        drawer.text("My name is", 20.0, 200.0)
                    }
                }
            }
            extend {
                poster.draw(drawer)
            }
        }
    }
}
