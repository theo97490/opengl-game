package lyonun.theo.gameproject;

import static org.lwjgl.opengl.GL46.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.system.MemoryUtil;
import org.w3c.dom.Text;


public class Mesh {
    private int vbID;
    private int ebID;
    private int vaID;
    private int vertSize = 0;
    private int indexSize = 0;
    private Texture[] textures;

    protected int getEbID() { return ebID;}
    protected int getVaID() { return vaID;}
    protected int getVbID() { return vbID;}
    protected int getIndexSize()   {return indexSize;}
    protected int getVertSize()    {return vertSize;}
    protected Texture getTexture(int index) {return textures[index];}
    
    public void setTextures(Texture[] textures) {
        this.textures = textures;
    }

    public Mesh(float[] vertices, int[] indices, Texture[] textures){ 
        super();
        
        this.vbID = glGenBuffers();
        this.ebID = glGenBuffers();
        this.vaID = glGenVertexArrays();
        this.vertSize = vertices.length;
        this.indexSize = indices.length;
        this.textures = textures;

        FloatBuffer vbuff = MemoryUtil.memAllocFloat(vertices.length)
                                      .put(vertices)
                                      .flip();
                                      
        IntBuffer ibuff = MemoryUtil.memAllocInt(indices.length)
                                    .put(indices)
                                    .flip();   

        glBindVertexArray(vaID);
        
        glBindBuffer(GL_ARRAY_BUFFER, vbID);
        glBufferData(GL_ARRAY_BUFFER, vbuff, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, ibuff, GL_STATIC_DRAW);

        //Vertex positions
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES * 5, 0);
        glEnableVertexAttribArray(0);

        //Texture coordinates
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Float.BYTES * 5, Float.BYTES * 3);
        glEnableVertexAttribArray(1);
        
        MemoryUtil.memFree(vbuff);
        MemoryUtil.memFree(ibuff);
    }


    public void mapTextureUniforms(Shader shader){
        // Précise le format des données des textures dans le tableau et leur emplacement 
        int specCount = 0;
        int diffCount = 0;
    
        for (int i = 0; i < textures.length; i++){
            
            //Assigne une Texture_ID à chaque uniform du shader 
            //HACK Réecrire pour prendre en charge chaque d'autre types de textures
            boolean specular = textures[i].getType().equals("specular");

            if (specular){
                shader.setUniformText(specular, specCount, i);
                specCount++;
            } else {
                shader.setUniformText(specular, diffCount, i);
                diffCount++;
            }
        }
    }

    public void draw(Shader shader){
        glBindVertexArray(vaID);
        shader.bind();

        mapTextureUniforms(shader);
        
        for (int i = 0; i < textures.length; i++ ){
            glActiveTexture(i);
            textures[i].bind();
        }

        glDrawElements(GL_TRIANGLES, getIndexSize(), GL_UNSIGNED_INT, 0);
    }

    public void cleanup(){
        glDeleteBuffers(vbID);
        glDeleteBuffers(ebID);
        glDeleteVertexArrays(vaID);
        vbID = ebID = vaID = vertSize = indexSize = 0;
    }
    
}
