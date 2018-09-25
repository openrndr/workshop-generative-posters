#version 330 core

uniform sampler2D tex0;
uniform float amplitude;
uniform float period;
uniform float phase;
in vec2 v_texCoord0;
out vec4 o_output;

uniform int xSteps;
uniform int ySteps;
uniform float gain;

void main() {

    vec2 step = 1.0 / textureSize(tex0, 0);

    vec2 north = vec2(v_texCoord0 + step * vec2(0.0, 1.0));
    vec2 south = vec2(v_texCoord0 + step * vec2(0.0, -1.0));
    vec2 west = vec2(v_texCoord0 + step * vec2(-1.0, 0.0));
    vec2 east = vec2(v_texCoord0 + step * vec2(1.0, 0.0));

    vec3 d = vec3(1.0/3.0);

    float northI = dot(texture(tex0, north).rgb, d);
    float southI = dot(texture(tex0, south).rgb, d);
    float westI = dot(texture(tex0, west).rgb, d);
    float eastI = dot(texture(tex0, east).rgb, d);


    vec4 center = texture(tex0, v_texCoord0);
    float centerI = dot(center.rgb, d);


    float edge = (northI + southI + westI + eastI) - 4 * centerI;
    o_output.rgb = vec3(1.0) * abs(edge*gain);
    o_output.a = 1.0;
}