package lyonun.theo.gameproject;

import static org.lwjgl.opengl.GL46.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import static lyonun.theo.gameproject.Ressources.*;



public class Shader {
    private String name;
    private int program;
    private Uniform[] uniforms;

    public int getProgramID()   {return program;}
    public String getName()     {return name;}
    public Uniform[] getUniforms() {
        return uniforms;
    }

    /** 
     * @param shaderName : The name for both .vert and .frag files 
     * */
    public Shader(String shaderName) {this(shaderName, shaderName, shaderName);};
    public Shader(String name, String vertname, String fragname) {
        this.name = name;
        this.program = glCreateProgram();

        final String vcode = FileUtils.getContent(SHADER_PATH + vertname + ".vert");
        final String fcode = FileUtils.getContent(SHADER_PATH + fragname + ".frag");

        processShaders(vcode, fcode);
        retrieveUniforms();

    }


    /** TODO Peut être plus flexible mais ça n'a peu d'importance
    * Crée un programme avec plusieurs shaders
    * @param vcode Code du vertex shader
    * @param fcode Code du frag shader
    */
    private void processShaders(String vcode, String fcode ){
        int vshader = glCreateShader(GL_VERTEX_SHADER);
        int fshader = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(vshader, vcode);
        glCompileShader(vshader);
        if (glGetShaderi(vshader, GL_COMPILE_STATUS) == 0)
            System.err.println("Shader: Error in vertex shader code\n" + glGetShaderInfoLog(vshader));
        
        glShaderSource(fshader, fcode);
        glCompileShader(fshader);
        if (glGetShaderi(fshader, GL_COMPILE_STATUS) == 0)
            System.err.println("Shader: Error in fragment shader code\n" + glGetShaderInfoLog(fshader));
        
        glAttachShader(program, vshader);
        glAttachShader(program, fshader);
        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) == 0)
            System.err.println("Shader: Error when linking program\n" + glGetProgramInfoLog(program));
        
        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0)
            System.err.println("Shader: Error when validating program\n" + glGetProgramInfoLog(program));

        glDeleteShader(vshader);
        glDeleteShader(fshader);
    }

    private void retrieveUniforms(){
        this.uniforms = new Uniform[glGetProgrami(program, GL_ACTIVE_UNIFORMS)];
        IntBuffer typebuff = MemoryUtil.memAllocInt(1);
        IntBuffer sizebuff = MemoryUtil.memAllocInt(1);
        
        for(int i = 0; i < uniforms.length; i++){
            uniforms[i] = new Uniform();
            uniforms[i].name = glGetActiveUniform(program, i, sizebuff, typebuff);
            uniforms[i].size = sizebuff.get(0); 
            uniforms[i].type = typebuff.get(0); 
            uniforms[i].println();

        }
    }

    public int locateUniform(String name){
        return glGetUniformLocation(program, name);
    }
    
    /*
    * Retourne l'index dans le tableau Uniform d'un uniform
    */
    public int getUniformIndex(String name){
        for (int i = 0; i < uniforms.length; i++){
            if (uniforms[i].name == name)
                return i;
        }

        throw new Error("Shader: Couldn't retrieve uniform " + name + " from array");
    }

    public int getUniformLength(){
        return uniforms.length;
    }

    public Uniform getUniform(int index){
        return uniforms[index];
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
