#version 330 core

uniform vec3 color;

out vec4 outColor;

uniform sampler2D tex;

void main() {
	outColor = texture(tex, gl_PointCoord);
	outColor *= vec4(color, 1);
}
