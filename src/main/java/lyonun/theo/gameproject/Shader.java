package lyonun.theo.gameproject;

import static org.lwjgl.opengl.GL46.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;

/**
 * Creates a program from a vertex shader and a fragment shader form the shader folder
 */

public class Shader {
    private int program;

    public int getProgramID() {return program;}

    /** @param shaderName : The name for both .vert and .frag files */
    public Shader(String shaderName) {this(shaderName + ".vert", shaderName +".frag");};
    public Shader(String vertpath, String fragpath) {
        int vshader = glCreateShader(GL_VERTEX_SHADER);
        int fshader = glCreateShader(GL_FRAGMENT_SHADER);
        program = glCreateProgram();

        final String vcode = FileUtils.getContent("./shaders/" + vertpath);
        final String fcode = FileUtils.getContent("./shaders/" + fragpath);

        glShaderSource(vshader, vcode);
        glCompileShader(vshader);
        if (glGetShaderi(vshader, GL_COMPILE_STATUS) == 0)
            System.err.println("Shader::Shader() Error in vertex shader code\n" + glGetShaderInfoLog(vshader));
        
        glShaderSource(fshader, fcode);
        glCompileShader(fshader);
        if (glGetShaderi(fshader, GL_COMPILE_STATUS) == 0)
            System.err.println("Shader::Shader() Error in fragment shader code\n" + glGetShaderInfoLog(fshader));
        
        glAttachShader(program, vshader);
        glAttachShader(program, fshader);
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == 0)
            System.err.println("Shader::Shader() Error when linking program\n" + glGetProgramInfoLog(program));
        
        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0)
            System.err.println("Shader::Shader() Error when validating program\n" + glGetProgramInfoLog(program));

        glDeleteShader(vshader);
        glDeleteShader(fshader);
    }

    public void setUniformText(boolean isSpecular, int index, int value){
        String name = "_texture" + index;

        if (isSpecular)
            name += "specular" + name;
        else
            name += "diffuse" + name;

        glUniform1i(glGetUniformLocation(program, name), value);
    }

    public void setMVP(FloatBuffer buffer){
        glUniformMatrix4fv(glGetUniformLocation(program, "MVP"), false , buffer);
    }



    public void bind(){
        glUseProgram(program);
    }

    public void unbind(){
        //Unecessary because we just have to use the .use() of an other shader
        glUseProgram(0);
    }


    public void cleanup(){
        unbind();
        if (program != 0){
            glDeleteProgram(program);
        }
    }
}
