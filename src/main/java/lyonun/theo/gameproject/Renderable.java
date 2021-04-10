package lyonun.theo.gameproject;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

public abstract class Renderable{
    protected Shader shader;

    //TODO A enlever, a moins que le modèle 3d a sa propre position
    //Par rapport à un objet du jeu 
    protected Vector3f position;
    protected Vector3f rotation;

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public abstract void draw(Matrix4f projView);

    public void createMVP(Matrix4f projView){
        try(MemoryStack stack = MemoryStack.stackPush()){
            FloatBuffer buff = stack.mallocFloat(16);

            projView.mul(new Matrix4f().translate(position).rotateXYZ(rotation)).get(buff);
            shader.setMVP(buff);
        }
    }
}
