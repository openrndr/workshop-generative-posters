import org.openrndr.PresentationMode
import org.openrndr.Program
import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.configuration
import org.openrndr.draw.FontImageMap
import org.openrndr.draw.renderTarget
import org.openrndr.extensions.FunctionDrawer
import org.openrndr.math.Vector2
import org.openrndr.math.clamp
import org.openrndr.workshop.toolkit.typography.Fonts


class Fonts001 : Program() {

    data class Font(val name: String, val variant: String, val url: String)


    val getText = { font: Font ->
//        font.variant
         "Pack my box with five dozen liquor jugs."
//        "your text"
    }

    override fun setup() {
        val groupedFonts = Fonts.availableFonts.map { url ->
            url.split("/").let {
                val g = it[it.size - 2]
                val f = it[it.size - 1]
                Font(g, f, url)
            }
        }.groupBy {
            it.name
        }
        val fs = groupedFonts.entries.toList()
        var index = 0
        window.presentationMode = PresentationMode.MANUAL

        keyboard.keyUp.listen { evt ->
            if (evt.key == 263) {
                index = if (index == 0) fs.size - 1 else index - 1
                window.requestDraw()
            }
            if (evt.key == 262) {
                index = (index + 1) % fs.size
                window.requestDraw()
            }
        }

        var contentScale = 0.1
        val fontSize = 320.0

        mouse.scrolled.listen { evt ->
            val d = evt.rotation.y
            val newContentScale = contentScale + (if (d < 0) -0.005 else 0.005)
            contentScale = clamp(newContentScale, 0.05, 1.0)
            window.requestDraw()
        }

        extend(FunctionDrawer {
            println("${index + 1} / ${fs.size}: ${fs[index].key}")
            val variants = fs[index].value
            drawer.background(ColorRGBa.WHITE)
            drawer.fill = ColorRGBa.BLACK
            drawer.translate( Vector2(50.0, 50.0))
            drawer.scale(contentScale)
            variants.forEachIndexed { i, variant ->
                try {
                    drawer.fontMap = FontImageMap.fromUrl(
                            variant.url,
                            fontSize
                    )
                    drawer.text(getText(variant), 50.0, (i * fontSize))
                } catch (e: Exception) {
                    println("caught exception: ${e.message}")
                }
            }
        })

    }


}

fun main(args: Array<String>) {
    application(Fonts001(), configuration {
        width = 1280
        height = 720
    })
}