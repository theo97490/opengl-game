#version 330 core
out vec4 FragColor;

uniform sampler2D diffuse_texture0;
uniform sampler2D specular_texture0;
in vec2 ctex1;


void main()
{
    FragColor = texture(diffuse_texture0, ctex1);    
} 