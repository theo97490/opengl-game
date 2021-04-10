package lyonun.theo.gameproject;

import java.io.File;
import java.util.ArrayList;

import org.lwjgl.system.CallbackI.Z;
import org.joml.Matrix4f;
import org.lwjgl.assimp.*;


public class Model3D extends Renderable{
    private Mesh[] meshes;
        
    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Model3D(String path){
        AIScene scene = Assimp.aiImportFile(path, Assimp.aiProcess_Triangulate | Assimp.aiProcess_FlipUVs);
        if (scene != null){
            throw new LoadException("Model3D : Un modèle 3D n'a pas pu être chargé");
        }
        
    }

    public void draw(Matrix4f ProjView){
        shader.bind();
        for (Mesh mesh : meshes){
            mesh.draw(shader);
        }
    }
}
