package lyonun.theo.gameproject;

import java.lang.Math;
import java.util.Vector;

import org.joml.Vector3f;

public abstract class WorldObject {
    public Vector3f position; 
    public Vector3f rotation; 

    public WorldObject(){
        position = new Vector3f(0,0,0);
        rotation = new Vector3f(0,0,0);

    }
    

}
