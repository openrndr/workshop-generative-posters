#version 330 core

uniform sampler2D tex0;
uniform float amplitude;
uniform float period;
uniform float phase;
in vec2 v_texCoord0;
out vec4 o_output;

uniform int xSteps;
uniform int ySteps;
uniform float scale;

void main() {
    float cu = floor(v_texCoord0.x * xSteps) / xSteps + 0.5 / xSteps;
    float cv = floor(v_texCoord0.y * ySteps) / ySteps + 0.5 / ySteps;

    float du = (v_texCoord0.x - cu) / (0.5 / xSteps);
    float dv = (v_texCoord0.y - cv) / (0.5 / ySteps);

    float adu = abs(du);
    float adv = abs(dv);

    float fu = smoothstep(1.0, 0.9, adu);
    float fv = smoothstep(1.0, 0.9, adv);

    float su = (v_texCoord0.x - cu) * scale + cu;
    float sv = (v_texCoord0.y - cv) * scale + cv;

    o_output = texture(tex0, vec2(su, sv));

}