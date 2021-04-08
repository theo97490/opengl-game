package lyonun.theo.gameproject;

import java.io.File;
import java.util.ArrayList;

import org.lwjgl.system.CallbackI.Z;
import org.lwjgl.assimp.*;


public class Model3D extends Renderable{
    private Mesh[] meshes;
        
    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Model3D(String path){
        AIScene scene = Assimp.aiImportFile(path, Assimp.aiProcess_Triangulate | Assimp.aiProcess_FlipUVs);
        if (scene != null || (byte)scene.mFlags & (byte)Assimp.AI_SCENE_FLAGS_INCOMPLETE)||
            
        }
    }

    public void draw(){
        shader.bind();
        for (Mesh mesh : meshes){
            mesh.draw(shader);
        }
    }
}
