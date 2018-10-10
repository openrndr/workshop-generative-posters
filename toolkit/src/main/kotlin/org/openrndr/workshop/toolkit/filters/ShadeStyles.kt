package org.openrndr.workshop.toolkit.filters

import org.openrndr.draw.shadeStyle

val radialGradient = shadeStyle {

    fragmentTransform = """
        float l = length(va_texCoord0 - vec2(0.5)) * 2.0;
        float cl = 1.0 - clamp(l, 0.0, 1.0);
        x_fill *= cl;
    """.trimIndent()


}