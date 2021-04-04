package lyonun.theo.gameproject;

import java.util.ArrayList;

public class GameLogic extends IGameLogic {
    Shader shader;
    Mesh quad;

    public void init(){
		ArrayList<Mesh> meshes = new ArrayList<>(1000);
		renderer.bindArray(meshes);

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

		
		
		shader   = new Shader("basic");
		Texture tex = new Texture("opengl/textures/grass-block.jpg", false);
		ArrayList<Texture> textures = new ArrayList<>();
		textures.add(tex);


		quad 	 = new Mesh(vertices, indices, shader, textures);
		
		meshes.add(quad);
    }

	public void input(Window win){}
	public void update(){}

}
