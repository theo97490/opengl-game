package lyonun.theo.gameproject;

import org.joml.Matrix4f;
import org.joml.Vector3f;

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
}
