#version 330 core

uniform sampler2D tex0;
uniform float threshold;
in vec2 v_texCoord0;
out vec4 o_output;

uniform vec4 dark;
uniform vec4 light;

void main() {
    vec4 center = texture(tex0, v_texCoord0);
    float intensity = dot(center.rgb, vec3(1.0/3.0));
    intensity = step(threshold, intensity);
    o_output = mix(dark, light, intensity);

   o_output.a *= center.a;
}