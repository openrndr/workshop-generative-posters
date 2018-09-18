import org.openrndr.color.ColorRGBa
import org.openrndr.draw.*

class PosterBuilder(val drawer: Drawer) {

    val target = RenderTarget.active.let {
        renderTarget(it.width, it.height) {
            colorBuffer()
            depthBuffer()
        }
    }

    fun layer(blend:Filter? = null, post:Filter? = null, builder: PosterBuilder.() -> Unit) {
        val rt = RenderTarget.active
        val layerTarget = renderTarget(rt.width, rt.height) {
            colorBuffer()
            depthBuffer()
        }

        drawer.isolatedWithTarget(layerTarget) {
            drawer.background(ColorRGBa.TRANSPARENT)
            builder()
        }

        val layerPost = if (post != null) { colorBuffer(rt.width, rt.height) } else { layerTarget.colorBuffer(0) }

        post?.let { filter ->
            filter.apply(layerTarget.colorBuffer(0), layerPost)
        }

        if (blend == null) {
            drawer.isolatedWithTarget(target) {
                drawer.image(layerPost)
            }
        } else {
            blend.apply( arrayOf(layerPost, rt.colorBuffer(0)), rt.colorBuffer(0) )
        }

        if (post != null) {
            layerPost.destroy()
        }

        layerTarget.colorBuffer(0).destroy()
        layerTarget.depthBuffer?.destroy()
        layerTarget.destroy()
    }

}


fun poster(drawer:Drawer, builder: PosterBuilder.() -> Unit) {

    val pb = PosterBuilder(drawer)
    drawer.withTarget(pb.target) {
        pb.builder()
    }
    drawer.image(pb.target.colorBuffer(0))
}