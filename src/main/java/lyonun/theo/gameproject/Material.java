package lyonun.theo.gameproject;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Vector;

import org.lwjgl.opencl.CL;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL46.*;
import static lyonun.theo.gameproject.MaterialConstants.*;

public class Material {
    private String name;
    private Shader shader;
    private ByteBuffer[] settings;
    private Texture[] textures;

    public String getName() {
        return name;
    }

    public Material(Shader shader){
        this.shader = shader;
        this.settings = new ByteBuffer[shader.getUniforms().length];
    }

    public Material(String shadername){
        this.shader = Ressources.getShader(shadername);
        this.settings = new ByteBuffer[shader.getUniforms().length];
    }

    private void transferUniforms(){
        Uniform[] uniforms = shader.getUniforms();
        for (int i = 0; i < uniforms.length; i++){
            Uniform u = uniforms[i];
            int location = shader.locateUniform(u.name);

            //INFO On est obligé d'écrire la fonction pour passer les paramètre pour chaque type de donnée 
            // Les matrices de float, vecteur de float / int et des valeur unique float / int seront largement suffisant
            if ( u.type == GL_FLOAT_MAT4){
                glUniformMatrix4fv(location, false, settings[i].asFloatBuffer());
            }
            else if (u.type == GL_FLOAT_VEC3){
                glUniform3fv(location, settings[i].asFloatBuffer());
            }        
            else if (u.type == GL_FLOAT){
                glUniform1fv(location, settings[i].asFloatBuffer());
            }
            else if (u.type == GL_INT_VEC3){
                glUniform3iv(location, settings[i].asIntBuffer());
            }
            else if (u.type == GL_INT){
                glUniform1iv(location, settings[i].asIntBuffer());
            }

        }
    }

    public void setParameters(String pname, ByteBuffer data){
        int index = shader.getUniformIndex(pname);

        if (index >= 0){
            if (this.settings[index] != null)
                MemoryUtil.memFree(this.settings[index]);

            this.settings[index] = data;
        }
        else
            System.out.println("Material: Le paramètre " + pname + " n'existe pas dans le shader " + shader.getName());
    }

    public void setTextures(Texture[] textures){
        this.textures = textures;
    }

    public void mapTextureUniforms(){
        //TODO check si il y a bien assez de uniform texture avant d'envoyer le mapping des textures
        // Précise le format des données des textures dans le tableau et leur emplacement 
        int specCount = 0;
        int diffCount = 0;
    
        for (int i = 0; i < textures.length; i++){
            
            //Assigne une Texture_ID à chaque uniform du shader 
            //HACK Réecrire pour prendre en charge chaque d'autre types de textures
            boolean specular = textures[i].getType().equals("specular");

            if (specular){
                glUniform1i(shader.locateUniform(TEX_SPECULAR + specCount), i);
                specCount++;
            } else {
                glUniform1i(shader.locateUniform(TEX_SPECULAR + diffCount), i);
                diffCount++;
            }
        }
    }


    public void cleanup(){
        for (Buffer buff : settings){
            MemoryUtil.memFree(buff);
        }
    }


    public void bind(){

        transferUniforms();

        mapTextureUniforms();
        
        for (int i = 0; i < textures.length; i++ ){
            glActiveTexture(i);
            textures[i].bind();
        }

        shader.bind();
        
    }
    
}


