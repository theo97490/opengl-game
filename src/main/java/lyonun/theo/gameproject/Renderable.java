package lyonun.theo.gameproject;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryStack;

public abstract class Renderable{

    //TODO A enlever, a moins que le modèle 3d a sa propre position
    //Par rapport à un objet du jeu 
    protected Vector3f position;
    protected Vector3f rotation;

    public abstract void draw(Matrix4f projView);


}
