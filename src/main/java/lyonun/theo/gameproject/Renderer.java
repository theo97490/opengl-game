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
    private ArrayList<Renderable> renderQueue;
    private Camera camera;
    private Shader shader;

    public Renderer(ArrayList<Renderable> meshes, Camera camera, Shader shader){
        bindArray(meshes);
        this.camera = camera;
        this.shader = shader;

    }

    public void bindArray(ArrayList<Renderable> renderQueue){
        this.renderQueue = renderQueue;
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

            camera.update();

            for (Renderable renderable : renderQueue){

                renderable.draw(camera.getViewProjMatrix());

            }
        }
        
    }
}
