import org.openrndr.application
import org.openrndr.draw.FontImageMap
import org.openrndr.extensions.Screenshots
import org.openrndr.filter.blend.add
import org.openrndr.filter.blur.GaussianBlur
import org.openrndr.workshop.toolkit.typography.Fonts


// -- entry point of the application
fun main(args: Array<String>) {
    application {
        configure {
            width = 640
            height = 480
        }

        program {
            val gaussianBlur = GaussianBlur()
            // -- extend with screenshots, spacebar to shoot
            extend(Screenshots())
            extend {

                poster(drawer) {
                    drawer.fontMap = FontImageMap.fromUrl(Fonts.IBMPlexMono_Bold, 32.0, 2.0)
                    drawer.text("My name is", 20.0, 200.0)

                    layer(post = gaussianBlur.apply {
                        gain = 1.0
                        spread = 1.0
                        window = 25
                        sigma = Math.cos(seconds) * 5.0 + 5.0
                    }, blend = add) {
                        drawer.fontMap = FontImageMap.fromUrl(Fonts.IBMPlexMono_Bold, 64.0, 2.0)
                        drawer.text("Tim Blurton", 20.0, 250.0)
                    }
                }

            }
        }
    }
}
