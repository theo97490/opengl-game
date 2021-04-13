package lyonun.theo.gameproject;

import java.nio.IntBuffer;

import org.lwjgl.system.MemoryUtil;

public class Uniform {
    public String name;
    public int type;
    public int size;

    public Uniform(){
        name = "";
        type = 0;
        size = 0;
    }

    public Uniform(String name, int type, int size){
        this.name = name;
        this.type = type;
        this.size = size;
    }

    public void println(){
        System.out.println(name);
        System.out.println(type);
        System.out.println(size);

    }
    
}

