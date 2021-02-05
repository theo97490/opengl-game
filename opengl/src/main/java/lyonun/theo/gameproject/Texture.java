package lyonun.theo.gameproject;

import java.awt.image.BufferedImage;
import static org.lwjgl.opengl.GL46.*;

import javax.imageio.ImageIO;

public class Texture {
    int textID;

    public Texture(String image){
        textID = glGenTextures();
        //BufferedImage img = FileUtils.getImage(image);
        
        glBindTexture(GL_TEXTURE_2D, textID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);	
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
   
        //glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, img.getWidth(), img.getHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, img.);
        
    }
    
}
