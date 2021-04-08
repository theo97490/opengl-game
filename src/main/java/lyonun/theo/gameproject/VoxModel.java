package lyonun.theo.gameproject;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import java.nio.FloatBuffer;

public class VoxModel extends Renderable{
    //TODO Ajouter les normales quand on pourra les gérer ici
    //TODO Changer les coordonées des textures quand on aura les textures des 4 cotés 
    
    public static final float[] vertices = {
        //Face avant
        -0.5f,  0.5f,  0.5f, 	0f, 0f,		// top-left
		0.5f,  0.5f,  0.5f,		1f, 0f,		// top-right
		0.5f, -0.5f,  0.5f, 	1f, 1f,		// bottom-right
	   -0.5f, -0.5f,  0.5f, 	0f, 1f,		// bottom-left

       //Face arrière 
       -0.5f,  0.5f,  -0.5f, 	0f, 0f,		// top-left
		0.5f,  0.5f,  -0.5f,	1f, 0f,		// top-right
		0.5f, -0.5f,  -0.5f, 	1f, 1f,		// bottom-right
	   -0.5f, -0.5f,  -0.5f, 	0f, 1f		// bottom-left
       
    };

    public static final int[] indices = { 
        //Face Avant
        0, 1, 2,
        0, 3, 2,
        
        //Arrière
        4, 5, 6,
        6, 7, 4,
        
        //Gauche
        0, 3, 7,
        0, 4, 7,

        //Droite
        1, 5, 6,
        1, 2, 6,

        //Dessus
        0, 4, 5,
        0, 1, 5,

        //Dessous
        7, 6, 2,
        7, 3, 2
        

    };

    private static Mesh mesh;
    
    private Texture[] textures;
    private String name;

    public String getName() {
        return name;
    }

    public static void initVoxelMesh(Texture[] initTexts){
        mesh = new Mesh(vertices, indices, initTexts); 
    }

    public VoxModel(String name, Texture[] textures){
        if (this.mesh == null)
            throw new RuntimeException("Un voxel à été crée mais this.mesh est null, initVoxelMesh n'as pas été appellé ?");

        this.shader = Ressources.getShader("basic");
        this.textures = textures;
        this.name = name;

        this.position = new Vector3f(0,0,0);
        this.rotation = new Vector3f(0,0,0);

    }

    public void draw(Matrix4f projView){
        mesh.setTextures(textures);
    
        //Block try-with-ressources, la ressource stack est fermée en fin de block
        //TODO Comprendre mémoire on-heap / off-heap pourquoi on doit faire ça)
        try(MemoryStack stack = MemoryStack.stackPush()){
            FloatBuffer buff = stack.mallocFloat(16);

            projView.mul(new Matrix4f().translate(position).rotateXYZ(rotation)).get(buff);
            shader.setMVP(buff);
        }
        

        mesh.draw(shader);
    }

}
