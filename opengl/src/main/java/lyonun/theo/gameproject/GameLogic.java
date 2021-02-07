package lyonun.theo.gameproject;

public class GameLogic extends IGameLogic {
    Shader shader;
    Mesh quad;

	public void input(Window win){

	}

    public void init(Window win){
        final float[] vertices = {
			-0.5f, 0.5f, 0.0f, 	// top-left
			0.5f, 0.5f, 0.0f, 	// top-right
			0.5f, -0.5f, 0.0f, 	// bottom-right
			-0.5f, -0.5f, 0.0f 	// bottom-left
		};

		final int[] indices = { 
			0, 1, 2,
			0, 3, 2
		 };
		
		shader   = new Shader("basic");
		quad 	 = new Mesh(vertices, indices, shader);
    }

	public void update(){
		renderer.addMesh(quad);
	}

}
