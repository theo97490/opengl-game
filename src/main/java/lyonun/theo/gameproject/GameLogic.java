package lyonun.theo.gameproject;

import java.util.ArrayList;

import javax.swing.text.Position;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

import static lyonun.theo.gameproject.Ressources.*;

public class GameLogic extends IGameLogic {
    Shader shader;
	ArrayList<Renderable> renderQueue;
	Camera camera;

    public void init(Window win){
		renderQueue = new ArrayList<>(1000);
		camera = new Camera(win);
		
		shader = getShader("basic");

	
		renderQueue.add(getVoxModel("grass-block"));

		renderer = new Renderer(renderQueue, camera, Ressources.getShader("basic"));
		camera.lookDirection = new Vector3f(0.5f, -0.5f, 0);
    }

	public void input(Window win){
		camera.input(win);

	}
	public void update(){
		//a
	}

}
