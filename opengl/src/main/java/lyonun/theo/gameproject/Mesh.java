package lyonun.theo.gameproject;

import static org.lwjgl.opengl.GL46.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryUtil;
import org.w3c.dom.Text;

public class Mesh {
    private int vbID;
    private int ebID;
    private int vaID;
    private int vertSize = 0;
    private int indexSize = 0;
    private Shader shader;
    private Texture texture;

    public int getEbID() { return ebID;}
    public int getVaID() { return vaID;}
    public int getVbID() { return vbID;}
    public int getIndexSize()   {return indexSize;}
    public int getVertSize()    {return vertSize;}
    public Shader getShader()   {return shader;}
    public Texture getTexture() {return texture;}

    public Mesh(float[] vertices, int[] indexes, Shader shader){ 
        FloatBuffer vbuff = MemoryUtil.memAllocFloat(vertices.length).put(vertices).flip();
        IntBuffer ibuff = MemoryUtil.memAllocInt(indexes.length).put(indexes).flip();

        vbID = glGenBuffers();
        ebID = glGenBuffers();
        vaID = glGenVertexArrays();
        vertSize = vertices.length;
        indexSize = indexes.length;
        this.shader = shader;

        glBindVertexArray(vaID);
        
        glBindBuffer(GL_ARRAY_BUFFER, vbID);
        glBufferData(GL_ARRAY_BUFFER, vbuff, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, ibuff, GL_STATIC_DRAW);

        MemoryUtil.memFree(vbuff);
        MemoryUtil.memFree(ibuff);
    }

    public Mesh(float[] vertices, int[] indexes, Shader shader, Texture texture){
        this(vertices, indexes, shader);

    }

    public void cleanup(){
        glDeleteBuffers(vbID);
        glDeleteBuffers(ebID);
        glDeleteVertexArrays(vaID);
        vbID = ebID = vaID = vertSize = indexSize = 0;
    }
    
}
