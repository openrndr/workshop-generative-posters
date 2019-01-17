import org.openrndr.application
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.RenderTarget
import org.openrndr.extensions.Screenshots
import org.openrndr.extra.compositor.*
import org.openrndr.filter.blend.add
import org.openrndr.filter.blur.GaussianBlur
import org.openrndr.math.Vector2
import org.openrndr.workshop.toolkit.filters.VerticalWaves
import org.openrndr.workshop.toolkit.filters.Waves
import org.openrndr.workshop.toolkit.typography.Fonts


// -- entry point of the application
fun main(args: Array<String>) {
    application {
        configure {
            height = 680
            width = 480
        }

        program {
            // -- extend with screenshots, spacebar to shoot
            extend(Screenshots().apply {
                scale = 4.0
            })


            val poster = compose {
                val scale = (RenderTarget.active.width.toDouble() / width)
                layer {
                    post(Waves()) {
                        amplitude = 0.01
                        period = Math.PI * 2.0 * 32.0
                        phase = 0.0
                    }
                    post(Waves()) {
                        amplitude = 0.1
                        period = Math.PI * 2.0 * 8.432
                        phase = seconds
                    }
                    draw {
                        drawer.fontMap = FontImageMap.fromUrl(Fonts.IBMPlexMono_Bold, 32.0, scale)
                        drawer.texts((0..40).map { "My name is" }, (0..40).map { Vector2(20.0, it * 20.0) })
                    }
                }

                layer {
                    blend(add)
                    post(VerticalWaves()) {
                        amplitude = 0.1
                        period = Math.PI * 2.0 * 2.0
                        phase = seconds * 2.0
                    }
                    post(GaussianBlur()) {
                        gain = 1.0
                        spread = 1.0
                        window = (25 * scale).toInt()
                        sigma = (Math.cos(seconds) * 5.0 + 5.0) * scale
                    }
                    draw {
                        drawer.fontMap = FontImageMap.fromUrl(Fonts.IBMPlexMono_Bold, 64.0, scale)
                        drawer.text("Tim Blurton", 20.0, 250.0)
                    }
                }
            }


            extend {
                poster.draw(drawer)
            }
        }
    }
}
