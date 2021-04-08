package lyonun.theo.gameproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Scanner;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import org.lwjgl.system.MemoryUtil;

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

	public static String[] fetchFileNames(String path){
		return fetchFileNames(path, "");
	}

	public static String[] fetchFileNames(String path, String regex){
        
		File dir = new File(path);
		String[] fileNames = {};

		if (dir.isDirectory()){
			File[] files = dir.listFiles(new FileFilter(){
				public boolean accept(File pathname) {
					if (pathname.getName().matches(regex)){
						return true;
					}
					return false;
				}
			});

			fileNames = new String[files.length];

			for (int i = 0; i < fileNames.length; i++){
				fileNames[i] = files[i].getName();
			}
		}
		else{
			System.out.println("Chemin : " + path + " n'est pas un dossier");
		}

		return fileNames;


    }
	

}
