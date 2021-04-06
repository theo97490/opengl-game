package lyonun.theo.gameproject;

import java.util.ArrayList;

import org.lwjgl.system.CallbackI.Z;

public class Model3D {
    private Mesh[] meshes;
    private Shader shader;

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Model3D(String path){
        
    }

    public void draw(){
        for (Mesh mesh : meshes){
            mesh.draw(shader);
        }
    }
}
