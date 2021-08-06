#version 140

in vec2 position;

out vec2 textureCoords;

uniform mat4 transformationMatrix;
uniform mat4 viewMatrix;

void main(void){

    gl_Position = viewMatrix * transformationMatrix * vec4(position, -0.2, 1.0);
    textureCoords = vec2((position.x+1.0)/2.0, 1 - (position.y+1.0)/2.0);
}