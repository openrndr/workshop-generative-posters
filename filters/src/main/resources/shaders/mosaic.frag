#version 330 core

uniform sampler2D tex0;
uniform float amplitude;
uniform float period;
uniform float phase;
in vec2 v_texCoord0;
out vec4 o_output;

uniform int xSteps;
uniform int ySteps;

void main() {
    float u = floor(v_texCoord0.x * xSteps) / xSteps;
    float v = floor(v_texCoord0.y * ySteps) / ySteps;
    vec4 t = texture(tex0, vec2(u,v));
    o_output = t;
}