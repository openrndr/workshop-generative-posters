import org.openrndr.application
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.filter.blur.GaussianBlur
import org.openrndr.math.Vector2
import org.openrndr.workshop.toolkit.filters.VerticalWaves
import org.openrndr.workshop.toolkit.typography.Fonts
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer
import org.openrndr.extra.compositor.post


// -- entry point of the application
fun main(args: Array<String>) {
    application {
        configure {
            height = 680
            width = 480
        }

        program {
            var text = mutableListOf("drop a text file here")

            window.drop.listen {
                text = it.files[0].readLines().toMutableList()
            }


            // -- extend with screenshots, spacebar to shoot
            extend(Screenshots().apply {
                scale = 4.0
            })

            val scale = (RenderTarget.active.width.toDouble() / width)
            val poster = compose {
                layer {
                    post(VerticalWaves()) {
                        amplitude = 0.01
                        period = Math.PI * 2.0 * mouse.position.y / height * 16.0
                        phase = seconds
                    }
                    draw {
                        drawer.fontMap = FontImageMap.fromUrl(Fonts.IBMPlexMono_Bold, 32.0, scale)
                        drawer.texts((0 until text.size).map { text[it] }, (0 until text.size).map { Vector2(20.0, (it + 1) * 20.0) })
                    }
                }
            }

            extend {
                poster.draw(drawer)
            }
        }
    }
}
