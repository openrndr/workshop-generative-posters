#version 330 core

uniform sampler2D tex0;
uniform float amplitude;
uniform float period;
uniform float phase;
in vec2 v_texCoord0;
out vec4 o_output;


uniform vec4 dark;
uniform vec4 light;

void main() {
    vec4 center = texture(tex0, v_texCoord0);
    float intensity = dot(center.rgb, vec3(1.0/3.0));
    intensity = pow(intensity, 1.0);
    o_output.rgb = mix(dark.rgb, light.rgb, intensity);
    o_output.a = center.a;
}