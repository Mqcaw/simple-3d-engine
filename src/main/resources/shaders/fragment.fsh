#version 400 core

in vec3 color;

out vec4 fragmentColor;

void main () {
    fragmentColor = vec4(color, 1.0);
}