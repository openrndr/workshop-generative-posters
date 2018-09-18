package org.openrndr.workshop

import org.openrndr.draw.Filter
import org.openrndr.filter.filterShaderFromUrl
import org.openrndr.resourceUrl

class Waves : Filter(filterShaderFromUrl(resourceUrl("/shaders/waves.frag"))) {
    var period: Double by parameters
    var amplitude: Double by parameters
    var phase: Double by parameters
}

class VerticalWaves : Filter(filterShaderFromUrl(resourceUrl("/shaders/waves-vertical.frag"))) {
    var period: Double by parameters
    var amplitude: Double by parameters
    var phase: Double by parameters
}