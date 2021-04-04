package lyonun.theo.gameproject;

import java.nio.FloatBuffer;
import java.util.Vector;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera extends WorldObject{
    public Vector3f lookDirection;
    private Matrix4f viewProjMatrix; 

    private static final Vector3f vecUp = new Vector3f(0, 1, 0);
    private final Matrix4f perspective;
    
    public Matrix4f getViewProjMatrix() 
    {
         return viewProjMatrix; 
    }

    public Camera(){

        this.perspective = new Matrix4f().perspective((float)Math.PI/2, Window.getAspectRatio(), 0.1f, 100f);
        setVectors( new Vector3f(0,0,0), 
                    new Vector3f(0,0,0), 
                    new Vector3f(0,0,0));

    }

    public void setVectors(Vector3f pos, Vector3f rot, Vector3f lookAt){
        this.position = new Vector3f(0,0,-1);
        this.rotation = new Vector3f(0,0,0);
        
        this.lookAt(new Vector3f(0,0,0));
    }

    public void update(){

        viewProjMatrix = new Matrix4f(perspective)
                                    .lookAt(position, new Vector3f(position).add(lookDirection), vecUp);

        rotation.x = (float)Math.atan2(lookDirection.y, lookDirection.z);
        rotation.y = (float)(Math.atan2(lookDirection.z, lookDirection.x) - Math.PI/2);
        rotation.z = (float)Math.atan2(lookDirection.x, lookDirection.y);
    }


    public void lookAt(Vector3f targetPos){
        lookDirection = targetPos.sub(position).normalize();
        update(); 
        
    }

    public void attachTo(WorldObject obj){
        this.position = obj.position;
    }

    public void detach(){
        this.position = new Vector3f(this.position);
    }

}
