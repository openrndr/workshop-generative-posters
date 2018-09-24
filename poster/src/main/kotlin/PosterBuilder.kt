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
            drawer.isolatedWithTarget(rt) {
                drawer.image(layerPost, layerPost.bounds, drawer.bounds)
            }
        } else {
            blend.apply( arrayOf(rt.colorBuffer(0), layerPost), rt.colorBuffer(0) )
        }

        if (post != null) {
            layerPost.destroy()
        }

        layerTarget.colorBuffer(0).destroy()
        layerTarget.depthBuffer?.destroy()
        layerTarget.detachColorBuffers()
        layerTarget.detachDepthBuffer()
        layerTarget.destroy()
    }

}


fun poster(drawer:Drawer, builder: PosterBuilder.() -> Unit) {

    val pb = PosterBuilder(drawer)
    drawer.withTarget(pb.target) {
        drawer.background(ColorRGBa.BLACK)
        pb.builder()
    }
    drawer.image(pb.target.colorBuffer(0),pb.target.colorBuffer(0).bounds, drawer.bounds)
}