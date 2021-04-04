package lyonun.theo.gameproject;

import static org.lwjgl.opengl.GL46.*;
import static org.lwjgl.glfw.GLFW.*;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;


public class Renderer {
    private ArrayList<Mesh> meshes;
    private Camera camera;

    public Renderer(){ 
        this.meshes = new ArrayList<Mesh>();
    }
    public Renderer(ArrayList<Mesh> meshes){ 
        bindArray(meshes);
    }

    public void bindArray(ArrayList<Mesh> meshes){
        this.meshes = meshes;
    }

    public void bindCamera(Camera camera){
        this.camera = camera;
    }

    public void init(ArrayList<Mesh> meshes, Camera camera){
        bindArray(meshes);
        bindCamera(camera);
    }

    public void clear(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
    }

    public void render(){
        if (camera != null){
            clear();

            camera.position = new Vector3f(0,0,-2);
            camera.rotation = new Vector3f(2,2,2);

            for (Mesh mesh : meshes){
                mesh.bindMesh();
                FloatBuffer buff = MemoryUtil.memAllocFloat(16);
                camera.getViewProjMatrix().mul(new Matrix4f().translate(mesh.position)
                                                             .rotateXYZ(mesh.rotation)).get(buff);


                mesh.getShader().setModelViewProjection(buff);
                
                glDrawElements(GL_TRIANGLES, mesh.getIndexSize(), GL_UNSIGNED_INT, 0);
            }
        }
        
    }
}
