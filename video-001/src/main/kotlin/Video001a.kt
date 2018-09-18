import org.openrndr.Program
import org.openrndr.application
import org.openrndr.configuration
import org.openrndr.draw.FontImageMap
import org.openrndr.extensions.Screenshots
import org.openrndr.ffmpeg.FFMPEGVideoPlayer
import org.openrndr.filter.blur.GaussianBlur

class Video001a: Program() {

    var drawFunc = {}
    override fun setup() {
        val gaussianBlur = GaussianBlur().apply {
            gain = 1.0
            spread = 1.0
            window = 25
        }
        var videoPlayer: FFMPEGVideoPlayer? = null

        window.drop.listen {
            if (it.files.isNotEmpty()) {
                if (it.files[0].extension in setOf("mp4", "mov", "mpg")) {
                    val path = it.files[0].toString()
                    videoPlayer = FFMPEGVideoPlayer.fromFile(path)
                    videoPlayer?.start()
                }
            }
        }

        drawFunc = {
            poster(drawer) {
                // -- video background layer
                layer(post = gaussianBlur) {
                    if (videoPlayer == null) {
                        drawer.fontMap = FontImageMap.fromUrl("file:data/fonts/IBMPlexMono-Bold.ttf", 32.0, 2.0)
                        drawer.text("Drop a video on me", 20.0, 200.0)
                    } else {
                        videoPlayer?.let {
                            it.next()
                            drawer.pushTransforms()
                            drawer.scale(drawer.height.toDouble() / it.height)
                            it.draw(drawer)
                            drawer.popTransforms()
                        }
                    }
                }

                // -- typography layer
                layer(post = gaussianBlur.apply { sigma = Math.random()*4.0 }) {
                    drawer.fontMap = FontImageMap.fromUrl("file:data/fonts/IBMPlexMono-Bold.ttf", 64.0, 2.0)
                    drawer.text("VIDEO", Math.random() * drawer.width, Math.random() * drawer.height)
                }
            }
        }

        // -- extend with screenshots, spacebar to shoot
        extend(Screenshots().apply {
            scale = 2.0
        })
    }

    override fun draw() {
        drawFunc()
    }

}

// -- entry point of the application
fun main(args: Array<String>) {
    application(Video001a(), configuration {
        width = 480
        height = 680
    })

}