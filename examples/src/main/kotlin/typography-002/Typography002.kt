import org.openrndr.Program
import org.openrndr.application
import org.openrndr.configuration
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.filter.blend.add
import org.openrndr.filter.blur.GaussianBlur
import org.openrndr.math.Vector2
import org.openrndr.workshop.toolkit.filters.VerticalWaves
import org.openrndr.workshop.toolkit.filters.Waves
import org.openrndr.workshop.toolkit.typography.Fonts

class Typography002 : Program() {

    var drawFunc = {}
    override fun setup() {
        val gaussianBlur = GaussianBlur()
        val waves = Waves()
        val waves2 = Waves()
        val verticalWaves = VerticalWaves()

        drawFunc = {
            val scale = (RenderTarget.active.width.toDouble() / width)

            poster(drawer) {
                waves.amplitude = 0.01
                waves.period = Math.PI * 2.0 * 32.0
                waves.phase = 0.0
                layer(post = waves) {

                    waves2.amplitude = 0.1
                    waves2.period = Math.PI * 2.0 * 8.432
                    waves2.phase = seconds
                    layer(post = waves2) {
                        drawer.fontMap = FontImageMap.fromUrl(Fonts.IBMPlexMono_Bold, 32.0, scale)
                        drawer.texts((0..40).map { "My name is" }, (0..40).map { Vector2(20.0, it * 20.0) })
                    }
                }

                verticalWaves.amplitude = 0.1
                verticalWaves.period = Math.PI * 2.0 * 2.0
                verticalWaves.phase = seconds * 2.0

                layer(post = verticalWaves, blend = add) {
                    layer(post = gaussianBlur.apply {
                        gain = 1.0
                        spread = 1.0
                        window = (25 * scale).toInt()
                        sigma = (Math.cos(seconds) * 5.0 + 5.0) * scale
                    }, blend = add) {
                        drawer.fontMap = FontImageMap.fromUrl(Fonts.IBMPlexMono_Bold, 64.0, scale)
                        drawer.text("Tim Blurton", 20.0, 250.0)
                    }
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
    application(Typography002(), configuration {
        height = 680
        width = 480

    })
}