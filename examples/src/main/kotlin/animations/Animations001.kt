package animations

import org.openrndr.application
import org.openrndr.extra.compositor.compose
import org.openrndr.extra.compositor.draw
import org.openrndr.extra.compositor.layer
import org.openrndr.math.Vector2
import org.openrndr.numate.inOutExpo
import org.openrndr.numate.storyboard

fun main(args: Array<String>) {

    application {
        configure {
            width = 480
            height = 640
        }

        program {
            val poster = compose {
                layer {
                    val a = object {
                        var position: Vector2 = Vector2.ZERO
                    }
                    // https://github.com/edwinRNDR/numate
                    storyboard(loop = true) {
                        val newPosition = Vector2(Math.random()*width, Math.random()*height)
                        val length = (newPosition - a.position).length
                        a::position to newPosition during Math.max(0.1, length/200.0) eased inOutExpo
                    }
                    draw {
                        drawer.circle(a.position, 100.0)
                    }
                }
            }
            extend {
                poster.draw(drawer)
            }
        }
    }
}