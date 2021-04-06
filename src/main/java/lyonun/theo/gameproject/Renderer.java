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
    private Shader shader;

    public Renderer(ArrayList<Mesh> meshes, Camera camera, Shader shader){
        bindArray(meshes);
        this.camera = camera;
        this.shader = shader;
    }

    public void bindArray(ArrayList<Mesh> meshes){
        this.meshes = meshes;
    }


    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public void clear(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); 
    }

    public void render(){
        if (camera != null){
            clear();
            FloatBuffer buff = MemoryUtil.memAllocFloat(16);

            camera.applyRotation(new Vector3f(  0 , 0.01f, 0));
            camera.update();

            for (Mesh mesh : meshes){

                camera.getViewProjMatrix().mul(new Matrix4f().translate(mesh.position)
                                                             .rotateXYZ(mesh.rotation)).get(buff);

                shader.setMVP(buff);
                
                mesh.draw(shader);

            }
        }
        
    }
}
