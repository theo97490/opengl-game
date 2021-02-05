package lyonun.theo.gameproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.Buffer;
import java.util.Scanner;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class FileUtils{
	
	/** 
	 * Gets the content of a file 
	 * @param path : Path to the file 
	 */
    public static String getContent(String path){	
		
		StringBuilder content = new StringBuilder();
		File file = new File(path);
		
		try{
			Scanner fileReader = new Scanner(file);
			while (fileReader.hasNextLine()) 
				content.append(fileReader.nextLine() + "\n");	
			fileReader.close();
		}
		catch(FileNotFoundException f){
			f.printStackTrace();
        }
        
        return content.toString();
	}
	/*
	public static BufferedImage getImage(String path){
		File file = new File(path);	
		BufferedImage imgbuff;

		try {
			imgbuff = ImageIO.read(file);
			return imgbuff;

		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		
		
	}
	*/
}
