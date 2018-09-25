#version 330 core

uniform sampler2D tex0;
uniform float amplitude;
uniform float period;
uniform float phase;
in vec2 v_texCoord0;
out vec4 o_output;

uniform int steps ;

void main() {
    float phi = floor(v_texCoord0.y*period+phase * steps) / steps;
    vec4 t = texture(tex0, v_texCoord0 + vec2(amplitude*cos(phi), 0.0));
    o_output = t;
}