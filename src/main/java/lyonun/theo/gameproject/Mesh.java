package lyonun.theo.gameproject;

import static org.lwjgl.opengl.GL46.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.system.MemoryUtil;
import org.w3c.dom.Text;


public class Mesh extends WorldObject{
    private int vbID;
    private int ebID;
    private int vaID;
    private int vertSize = 0;
    private int indexSize = 0;
    private ArrayList<Texture> textures;

    public int getEbID() { return ebID;}
    public int getVaID() { return vaID;}
    public int getVbID() { return vbID;}
    public int getIndexSize()   {return indexSize;}
    public int getVertSize()    {return vertSize;}
    public Texture getTexture(int index) {return textures.get(index);}

    public Mesh(float[] vertices, int[] indices, ArrayList<Texture> textures){ 
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
    
        for (int i = 0; i < textures.size(); i++){
            
            //Assigne une Texture_ID à chaque uniform du shader 
            boolean specular = textures.get(i).isSpecular();
            if (specular){
                specCount++;
                shader.setUniformText(specular, specCount, i);
            } else {
                diffCount++;
                shader.setUniformText(specular, diffCount, i);
            }
        }
    }

    public void draw(Shader shader){
        glBindVertexArray(vaID);
        shader.bind();

        mapTextureUniforms(shader);
        
        for (int i = 0; i < textures.size(); i++ ){
            glActiveTexture(i);
            textures.get(i).bind();
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
