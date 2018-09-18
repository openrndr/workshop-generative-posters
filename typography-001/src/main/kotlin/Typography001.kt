import org.openrndr.Program
import org.openrndr.application
import org.openrndr.configuration
import org.openrndr.draw.FontImageMap
import org.openrndr.extensions.Screenshots
import org.openrndr.filter.blend.add
import org.openrndr.filter.blur.GaussianBlur

class Typography001: Program() {

    var drawFunc = {}
    override fun setup() {

        val gaussianBlur = GaussianBlur()

        drawFunc = {
            poster(drawer) {
                drawer.fontMap = FontImageMap.fromUrl("file:data/fonts/IBMPlexMono-Bold.ttf", 32.0, 2.0)
                drawer.text("My name is", 20.0, 200.0)

                layer(post = gaussianBlur.apply {
                    gain = 1.0
                    spread = 1.0
                    window = 25
                    sigma = Math.cos(seconds)*5.0 + 5.0
                }, blend = add) {
                    drawer.fontMap = FontImageMap.fromUrl("file:data/fonts/IBMPlexMono-Bold.ttf", 64.0, 2.0)
                    drawer.text("Tim Blurton", 20.0, 250.0)
                }
            }
        }

        // -- extend with screenshots, spacebar to shoot
        extend(Screenshots())
    }

    override fun draw() {
        drawFunc()
    }

}

// -- entry point of the application
fun main(args: Array<String>) {
    application(Typography001(), configuration {  })

}