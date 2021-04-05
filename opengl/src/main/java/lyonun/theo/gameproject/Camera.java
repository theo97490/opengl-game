package lyonun.theo.gameproject;

import java.nio.FloatBuffer;
import java.util.Vector;
import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;


public class Camera extends WorldObject{
    public Vector3f lookDirection;
    private Matrix4f viewProjMatrix; 

    public static final Vector3f vecUp = new Vector3f(0, 1, 0);
    private final Matrix4f perspective;

    // HACK : On ne va pas gérer l'input de la souris dans la camera
    public double xmouse;
    public double ymouse;
    
    //Utiliser les getters dans le cas ou vous souhaitez appliquer des opérations sur la matrice 
    // sans modifier l'instance actuelle dans la classe
    public Matrix4f getViewProjMatrix() { return new Matrix4f(viewProjMatrix); }
    public Vector3f getLookDirection() { return new Vector3f(lookDirection); }

    public Camera(Window win){

        this.perspective = new Matrix4f().perspective((float)Math.PI/2, Window.getAspectRatio(), 0.1f, 100f);
        this.position = new Vector3f(0,0,-1);
        this.rotation = new Vector3f(0,0,0);
        
        this.lookAt(new Vector3f(0,0,0));

        //Non FInal 
        win.setMouseCursorCallback((window, xpos, ypos) -> {
            xmouse = xpos;
            ymouse = ypos;
        });

    }

    private void calculateRotation(){
       
    }

    public void applyRotation(Vector3f rot){
        rotation.add(rot);

        //TODO Ranger le code dans une fonction dans une autre classe, à réutiliser
        rotation.x = rotation.x - (float)(2 * Math.PI * (int)(rotation.x / (2 * Math.PI)));
        rotation.y = rotation.y - (float)(2 * Math.PI * (int)(rotation.y / (2 * Math.PI)));
        rotation.z = rotation.z - (float)(2 * Math.PI * (int)(rotation.z / (2 * Math.PI)));
        

        //TODO Ranger le code dans une fonction dans une autre classe, à réutiliser
        // Calcule la valeur lookDirection si on set avec applyRotation
        
        lookDirection.x = (float) (Math.cos(rotation.y) * Math.cos(rotation.x)) ;
        lookDirection.y = (float) (Math.sin(rotation.x));
        lookDirection.z = (float) (Math.sin(rotation.y) * Math.cos(rotation.x));
    }

    public void update(){
        viewProjMatrix = new Matrix4f(perspective)
                                    .lookAt(position, new Vector3f(position).add(lookDirection), vecUp);

    }

    public void lookAt(Vector3f targetPos){
        lookDirection = targetPos.sub(position).normalize();
        
        // Calcule la valeur rotation si on set avec lookAt
        rotation.x = (float) Math.atan2(lookDirection.y, lookDirection.z);
        rotation.y = (float)(Math.atan2(lookDirection.z, lookDirection.x) - Math.PI/2);
        rotation.z = (float) Math.atan2(lookDirection.x, lookDirection.y);
    }



    public void attachTo(WorldObject obj){
        this.position = obj.position;
    }

    public void detach(){
        this.position = new Vector3f(this.position);
    }

    public void input(Window win){
        float moveSpeed = 0.01f;
		if (glfwGetKey(win.getWindow(), GLFW_KEY_W) == GLFW_PRESS){
			position.add(getLookDirection().mul(moveSpeed));
		}
		if (glfwGetKey(win.getWindow(), GLFW_KEY_A) == GLFW_PRESS){
			position.sub(getLookDirection().cross(vecUp).normalize().mul(moveSpeed));
		}
		if (glfwGetKey(win.getWindow(), GLFW_KEY_S) == GLFW_PRESS){
			position.sub(getLookDirection().mul(moveSpeed));
		}
		if (glfwGetKey(win.getWindow(), GLFW_KEY_D) == GLFW_PRESS){
			position.add(getLookDirection().cross(vecUp).normalize().mul(moveSpeed));
		}
    }


}
