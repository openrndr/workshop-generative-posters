package org.openrndr.workshop.toolkit.filters

import org.openrndr.draw.Filter
import org.openrndr.draw.filterShaderFromUrl
import org.openrndr.filter.blend.*
import org.openrndr.resourceUrl

// fix-up for OPENRNDR 0.3.26's broken subtract filter
class Subtract : Filter(filterShaderFromUrl(resourceUrl("/shaders/subtract.frag")))

class AbsoluteDifference : Filter(filterShaderFromUrl(resourceUrl("/shaders/difference.frag")))

val absoluteDifference by lazy { AbsoluteDifference() }
val subtract by lazy { Subtract() }
val colorBurn by lazy { ColorBurn() }
val colorDodge by lazy { ColorDodge() }
val darken by lazy { Darken() }
val hardLight by lazy { HardLight() }
val lighten by lazy { Lighten() }
val overlay by lazy { Overlay() }
val screen by lazy { Screen() }
val multiplyContrast by lazy { MultiplyContrast() }

