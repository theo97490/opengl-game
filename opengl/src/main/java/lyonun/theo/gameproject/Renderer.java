package lyonun.theo.gameproject;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;
import java.util.Vector;

public class Renderer {
    private ArrayList<Mesh> meshes;

    public Renderer(){ 
        this.meshes = new ArrayList<Mesh>();
    }
    public Renderer(ArrayList<Mesh> meshes){ 
        this.meshes = meshes;
    }
    
    public void addMesh(Mesh mesh){ meshes.add(mesh);}
    public void remMesh(Mesh mesh){ meshes.remove(mesh);}

    public void clear(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
    }

    public void render(){
        clear();

        for (Mesh mesh : meshes){
            glBindVertexArray(mesh.getVaID());
            mesh.getShader().bind();
            glDrawElements(GL_TRIANGLES, mesh.getIndexSize(), GL_UNSIGNED_INT, 0);
        }

        meshes.clear();
        
    }
}
