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

	
		//renderQueue.add(getVoxModel("grass-block"));
		try{
			Model3D model = new Model3D("./models/backpack/backpack.obj");
			renderQueue.add(model);
		} catch (LoadException e ){
			System.out.println(e.getMessage());
			System.out.println("GameLogic: Model3D n'est pas rajouté à la renderQueue");
		}

		


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
