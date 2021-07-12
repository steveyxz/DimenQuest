#version 140

in vec2 textureCoords;

uniform sampler2D particleTexture;

out vec4 out_colour;


void main(void){

    out_colour = texture(particleTexture, textureCoords);

}