package lyonun.theo.gameproject;

import java.util.ArrayList;

import javax.swing.text.Position;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

import static lyonun.theo.gameproject.RessourceManager.*;

public class GameLogic extends IGameLogic {
    Shader shader;
    Mesh quad;
	ArrayList<Mesh> meshes;
	Camera camera;

    public void init(Window win){
		meshes = new ArrayList<>(1000);
		camera = new Camera(win);

        final float[] vertices = {
		   -0.5f,  0.5f,  0.0f, 	0f, 0f,		// top-left
			0.5f,  0.5f,  0.0f,		1f, 0f,		// top-right
			0.5f, -0.5f,  0.0f, 	1f, 1f,		// bottom-right
		   -0.5f, -0.5f,  0.0f, 	0f, 1f		// bottom-left
		};

		final int[] indices = { 
			0, 1, 2,
			0, 3, 2 
		};

		
		
		shader = getShader("basic");

		quad = new Mesh(vertices, indices, getTextures("grass-block"));
	
		meshes.add(quad);

		renderer = new Renderer(meshes, camera, shader);
    }

	public void input(Window win){
		camera.input(win);

	}
	public void update(){
		//a
	}

}
