package lyonun.theo.gameproject;

public class Voxel {
    //TODO Ajouter les normales quand on pourra les gérer ici
    public static final float[] vertices = {
        -0.5f,  0.5f,  0.0f, 	0f, 0f,		// top-left
		0.5f,  0.5f,  0.0f,		1f, 0f,		// top-right
		0.5f, -0.5f,  0.0f, 	1f, 1f,		// bottom-right
	   -0.5f, -0.5f,  0.0f, 	0f, 1f		// bottom-left
    }

    public static final int[] indices = { 
        0, 1, 2,
        0, 3, 2 
    };

    private static Mesh mesh;
    

    public static void initVoxelMesh(Texture tex){
        this.mesh = new Mesh(vertices, indices, ); 
    }

    public Voxel(String name){
        if (this.mesh == null)
            throw new RuntimeException("Un voxel à été crée mais this.mesh est null, initVoxelMesh n'as pas été appellé ?");

        shader = RessourceManager.getShader("basic");
    }





}
