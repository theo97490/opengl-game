package lyonun.theo.gameproject;

import static org.lwjgl.opengl.GL46.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.system.MemoryUtil;
import org.w3c.dom.Text;

import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;

public class Mesh {
    private int vbID;
    private int ebID;
    private int vaID;
    private int vertSize = 0;
    private int indexSize = 0;
    private Shader shader;
    private ArrayList<Texture> textures;

    public int getEbID() { return ebID;}
    public int getVaID() { return vaID;}
    public int getVbID() { return vbID;}
    public int getIndexSize()   {return indexSize;}
    public int getVertSize()    {return vertSize;}
    public Shader getShader()   {return shader;}
    public Texture getTexture(int index) {return textures.get(index);}

    public Mesh(float[] vertices, int[] indices, Shader shader, ArrayList<Texture> textures){ 

        FloatBuffer vbuff = MemoryUtil.memAllocFloat(vertices.length)
                                      .put(vertices)
                                      .flip();
                                      
        IntBuffer ibuff = MemoryUtil.memAllocInt(indices.length)
                                    .put(indices)
                                    .flip();

        this.vbID = glGenBuffers();
        this.ebID = glGenBuffers();
        this.vaID = glGenVertexArrays();
        this.vertSize = vertices.length;
        this.indexSize = indices.length;
        this.shader = shader;
        this.textures = textures;

        glBindVertexArray(vaID);
        
        glBindBuffer(GL_ARRAY_BUFFER, vbID);
        glBufferData(GL_ARRAY_BUFFER, vbuff, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, ibuff, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, Float.BYTES * (3 + 2 * textures.size()), 0);

        for (int i = 0; i < textures.size(); i++){
            glVertexAttribPointer(i + 1, 2, GL_FLOAT, false, Float.BYTES * (3 + 2 * textures.size()), Float.BYTES * (3 + 2 * i));

            glEnableVertexAttribArray(i + 1);
        }

        MemoryUtil.memFree(vbuff);
        MemoryUtil.memFree(ibuff);
    }


    public void bindMesh(){
        glBindVertexArray(vaID);
        shader.bind();

        for (int i = 0; i < textures.size(); i++ ){
            glActiveTexture(i);
            textures.get(i).bind();
        }
    }

    public void cleanup(){
        glDeleteBuffers(vbID);
        glDeleteBuffers(ebID);
        glDeleteVertexArrays(vaID);
        vbID = ebID = vaID = vertSize = indexSize = 0;
    }
    
}
