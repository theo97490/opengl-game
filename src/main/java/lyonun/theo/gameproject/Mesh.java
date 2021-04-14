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
    protected Material material;
    private int indexSize;

    protected int getEbID() { return ebID;}
    protected int getVaID() { return vaID;}
    protected int getVbID() { return vbID;}



    public Mesh(float[] vertices, int[] indices, Material material){ 

        FloatBuffer vbuff = MemoryUtil.memAllocFloat(vertices.length)
                                      .put(vertices)
                                      .flip();
                                      
        IntBuffer ibuff = MemoryUtil.memAllocInt(indices.length)
                                    .put(indices)
                                    .flip();
                                    
        constructor(vbuff, ibuff, material);


    }

    public Mesh(FloatBuffer vbuff, IntBuffer ibuff, Material material){
        constructor(vbuff, ibuff, material);
    }

    private void constructor(FloatBuffer vbuff, IntBuffer ibuff, Material material){
        this.vbID = glGenBuffers();
        this.ebID = glGenBuffers();
        this.vaID = glGenVertexArrays();
        this.material = material;
        this.indexSize = ibuff.capacity();

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


    public void draw(){
        glBindVertexArray(vaID);
        
        material.bind();

        glDrawElements(GL_TRIANGLES, indexSize, GL_UNSIGNED_INT, 0);
    }

    public void cleanup(){
        glDeleteBuffers(vbID);
        glDeleteBuffers(ebID);
        glDeleteVertexArrays(vaID);
        vbID = ebID = vaID = 0;
    }
    
    
}
