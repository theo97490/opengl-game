package lyonun.theo.gameproject;

import java.nio.FloatBuffer;
import java.util.Vector;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class Camera extends WorldObject{
    private Vector3f lookDirection;
    private Matrix4f viewProjMatrix; 

    private static final Vector3f vecUp = new Vector3f(0, 1, 0);
    
    public Matrix4f getViewProjMatrix() { return viewProjMatrix; }

    public Camera(){
        this.position = this.rotation = new Vector3f(0,0,0);
        this.lookAt(new Vector3f(0,0,0));
    
                      
    }

    public void lookAt(Vector3f targetPos){
        lookDirection = this.position.sub(targetPos);
        viewProjMatrix = new Matrix4f().perspective((float)Math.PI/2, Window.getAspectRatio(), 0.1f, 100f)
                                       .lookAt(position, targetPos, vecUp);
    }

    public void attachTo(WorldObject obj){
        this.position = obj.position;
    }

    public void detach(){
        this.position = new Vector3f(this.position);
    }
}
