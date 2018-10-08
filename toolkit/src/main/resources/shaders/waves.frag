#version 330 core

uniform sampler2D tex0;
uniform float amplitude;
uniform float period;
uniform float phase;
in vec2 v_texCoord0;
out vec4 o_output;

void main() {
    vec4 t = texture(tex0, v_texCoord0 + vec2(amplitude*cos(v_texCoord0.y*period+phase), 0.0));
    o_output = t;
}