#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 actex1;

out vec2 ctex1;

void main()
{
    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);
    ctex1 = actex1;
}