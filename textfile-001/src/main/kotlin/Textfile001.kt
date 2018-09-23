import org.openrndr.Program
import org.openrndr.application
import org.openrndr.configuration
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.filter.blend.add
import org.openrndr.filter.blur.GaussianBlur
import org.openrndr.math.Vector2
import org.openrndr.workshop.VerticalWaves
import org.openrndr.workshop.Waves

class Textfile001 : Program() {

    var drawFunc = {}
    override fun setup() {
        val gaussianBlur = GaussianBlur()
        val verticalWaves = VerticalWaves()

        var text = mutableListOf("drop a text file here")


        window.drop.listen {
            text = it.files[0].readLines().toMutableList()
        }

        drawFunc = {
            val scale = (RenderTarget.active.width.toDouble() / width)

            verticalWaves.amplitude = 0.01
            verticalWaves.period = Math.PI * 2.0 * mouse.position.y/height * 16.0
            verticalWaves.phase = seconds
            poster(drawer) {

                layer(post = verticalWaves) {
                        drawer.fontMap = FontImageMap.fromUrl("file:data/fonts/IBMPlexMono-Bold.ttf", 32.0, scale)
                        drawer.texts((0 until text.size).map { text[it] }, (0 until text.size).map { Vector2(20.0, (it+1)*20.0) })
                }
            }
        }

        // -- extend with screenshots, spacebar to shoot
        extend(Screenshots().apply {
            scale = 4.0
        })
    }

    override fun draw() {
        drawFunc()
    }

}

// -- entry point of the application
fun main(args: Array<String>) {
    application(Textfile001(), configuration {

        height = 680
        width = 480

    })

}