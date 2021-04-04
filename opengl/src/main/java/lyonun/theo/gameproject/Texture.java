package lyonun.theo.gameproject;

import java.awt.image.BufferedImage;
import static org.lwjgl.opengl.GL46.*;

import javax.imageio.ImageIO;

import org.lwjgl.system.MemoryUtil;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Texture {
    private int textID;
    private boolean isSpecular;

    public boolean isSpecular() { return isSpecular; }

    public Texture(String image, boolean isSpecular){
        this.textID = glGenTextures();
        this.isSpecular = isSpecular;


        glBindTexture(GL_TEXTURE_2D, textID);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);	
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        
        try{ 
            BufferedImage img = ImageIO.read(new File(image));

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, img.getWidth(), img.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, getImageByteBufferRGBA(img));
            glGenerateMipmap(GL_TEXTURE_2D);

        } catch (IOException e){
            System.out.println("Va te faire foutre une texture n'existe pas ");
        }
    }

    public void bind(){
        glBindTexture(GL_TEXTURE_2D, textID);
    }

    public void cleanup(){

    }

    /** 
	 * Prends une image et la convertie sous forme de ByteBuffer ( données brutes ) arrangées
	 * dans l'ordre R G B A pour que openGl puisse traiter l'image
     * @param img Un objet BufferedImage représentant l'image
     * @return Un byte buffer contenant les données brutes RGBA
	 */
	public static ByteBuffer getImageByteBufferRGBA(BufferedImage img){
 
        ByteBuffer imgByteBuffer = ByteBuffer.allocateDirect(4 * img.getWidth() * img.getHeight());
        int[] rgbData = new int[img.getWidth() * img.getHeight()];

        img.getRGB(0, 0, img.getWidth(), img.getHeight(), rgbData, 0, img.getWidth());

        for (int h = 0; h < img.getWidth(); h++){
            for (int w = 0; w < img.getHeight(); w++){
                int pixel = rgbData[h * img.getWidth() + w];

				imgByteBuffer.put((byte) ((pixel >> 16) & 0xFF));
				imgByteBuffer.put((byte) ((pixel >> 8) & 0xFF));
				imgByteBuffer.put((byte) (pixel & 0xFF));
				imgByteBuffer.put((byte) ((pixel >> 24) & 0xFF));
                
            }
        }

        return imgByteBuffer.flip();

        /*  Explication:
            
            On souhaite retourner un ByteBuffer représentant l'image

            On récupére les données de l'image dans un objet de la classe bufferedimage,
            Avec celle ci on récupère les données rgb de chaque pixel dans le tableau rgbData
            Ces données sont formatés sur 8 bytes représentant sur 2 byte chacun la valeur Alpha Red Blue Green
            On veut un format RGBA et non ARGB, on va donc convertir convertir chaque byte

            On va extraire chaque bit et les ajouter au fur et a mesure dans notre byte buffer
            Prenons par exemple la couleur 0xFF 05 01 B4 

            On veut extraire la couleur rouge étant donc le second bit soit : 05
            On va décaller toute la séquence de bit vers à droite (pixel >> 16)
            pour avoir la valeur 05 à la fin de la séquence pour bien avoir
            la valeur 05 en sortie ( sinon on aurait en sortie 5 * 16^2 = 1280 au lieu de 5 si on décalle pas)

            Résultat : 
            0xFF0501B4 >> 16 
            1111 1111 0000 0101 0000 0001 1011 0100 >> 16
              Alpha      Red       blue     Green

         == 0000 0000 0000 0000 1111 1111 0000 0101
                                   Alpha     Red

            Résultat en Hexa: 0x00 00 FF 05 

            Maintenant que les byte rouges sont à droite, il faut maintenant enlever 
            tout le reste à gauche, on va donc utiliser une porte logique ET qui va
            permetre de garder juste les 2 bytes à droites et d'effacer le reste à gauche

            (0x0000FF05 & 0xFF)

             0000 0000 0000 0000 1111 1111 0000 0101
          &  0000 0000 0000 0000 0000 0000 1111 1111

          == 0000 0000 0000 0000 0000 0000 0000 0101
            
            On a extrait la valeur 05 et on peut maintenant l'ajouter au ByteBuffer de sortie             
        */
    
    }
}