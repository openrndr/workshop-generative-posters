package org.openrndr.workshop.toolkit.filters

import org.openrndr.color.ColorRGBa
import org.openrndr.draw.Filter
import org.openrndr.draw.filterShaderFromUrl
import org.openrndr.math.Vector2
import org.openrndr.resourceUrl

class GradientMap : Filter(filterShaderFromUrl(resourceUrl("/shaders/gradient-map.frag"))) {
    var dark: ColorRGBa by parameters
    var light: ColorRGBa by parameters
    init {
        dark = ColorRGBa.BLACK
        light = ColorRGBa.WHITE
    }
}


class Threshold : Filter(filterShaderFromUrl(resourceUrl("/shaders/threshold.frag"))) {
    var dark: ColorRGBa by parameters
    var light: ColorRGBa by parameters
    var threshold: Double by parameters
    init {
        dark = ColorRGBa.BLACK
        light = ColorRGBa.WHITE
        threshold = 0.5
    }
}

class EdgeDetect : Filter(filterShaderFromUrl(resourceUrl("/shaders/edge-detect.frag"))) {
    var gain: Double by parameters
    init {
        gain = 20.0
    }
}

class OpacityDetect : Filter(filterShaderFromUrl(resourceUrl("/shaders/opacity-detect.frag"))) {
    var gain: Double by parameters
    init {
        gain = 20.0
    }
}


class Mosaic : Filter(filterShaderFromUrl(resourceUrl("/shaders/mosaic.frag"))) {
    var xSteps: Int by parameters
    var ySteps: Int by parameters

    init {
        xSteps = 32
        ySteps = 32
    }
}

class ZoomMosaic : Filter(filterShaderFromUrl(resourceUrl("/shaders/zoom-mosaic.frag"))) {
    var xSteps: Int by parameters
    var ySteps: Int by parameters
    var scale: Double by parameters

    init {
        xSteps = 16
        ySteps = 16
        scale = 1.0
    }
}


class Separate : Filter(filterShaderFromUrl(resourceUrl("/shaders/separate.frag"))) {
    var redShift: Vector2 by parameters
    var greenShift: Vector2 by parameters
    var blueShift: Vector2 by parameters

    init {
        redShift = Vector2(0.0, 0.0)
        greenShift = Vector2(1.0 / 200.0, 0.0)
        blueShift = Vector2(0.0, 1.0 / 200.0)
    }
}

class Waves : Filter(filterShaderFromUrl(resourceUrl("/shaders/waves.frag"))) {
    var period: Double by parameters
    var amplitude: Double by parameters
    var phase: Double by parameters
}

class StepWaves : Filter(filterShaderFromUrl(resourceUrl("/shaders/step-waves.frag"))) {
    var period: Double by parameters
    var amplitude: Double by parameters
    var phase: Double by parameters
    var steps: Int by parameters

    init {
        steps = 4
        phase = 0.0
        amplitude = 0.1
        period = Math.PI * 4.0
    }
}

class VerticalStepWaves : Filter(filterShaderFromUrl(resourceUrl("/shaders/step-waves-vertical.frag"))) {
    var period: Double by parameters
    var amplitude: Double by parameters
    var phase: Double by parameters
    var steps: Int by parameters

    init {
        steps = 4
        phase = 0.0
        amplitude = 0.1
        period = Math.PI * 4.0
    }
}

class VerticalWaves : Filter(filterShaderFromUrl(resourceUrl("/shaders/waves-vertical.frag"))) {
    var period: Double by parameters
    var amplitude: Double by parameters
    var phase: Double by parameters
}