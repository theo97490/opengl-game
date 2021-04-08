package lyonun.theo.gameproject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.management.RuntimeErrorException;
import java.io.FileFilter;

import jdk.dynalink.beans.StaticClass;

public final class RessourceManager {
	public static final String SHADER_PATH = "./shaders/";
	public static final String TEXTURE_PATH = "./textures/";
	public static final String MODEL_PATH = "./models/";

    private static final String[] SHADER_EXT = {".frag",".vert"}; /*".geom", ".tess", ".comp" , ".eval" Si on utilise un jour lol */
    private static final String TEXTURE_TYPE = "diffuse|specular"; /*".geom", ".tess", ".comp" , ".eval" Si on utilise un jour lol */

    private static ArrayList<Shader> shaders = new ArrayList<>();
    private static HashMap<String, Texture[]> textures = new HashMap<>();
    //HashMap => Comme dictionnaire en python : Association d'un identifiant/nom avec une valeur, 
    //On stocke ici un ensemble de texture sous un même nom 

    public static void init(){
        //TODO Init des shaders écrit à la main , vu que l'on peut réutiliser plusieurs fois un même shader
        // peut être utiliser un fichier json pour définir les différentes combinaisons de shaders
    
        shaders.add(new Shader("basic"));

        //Textures de Voxels, chacune des textures sont rangées dans leur dossier suivant la convention nomdurépertoire-typetexture0.png
        File[] files = new File(TEXTURE_PATH).listFiles();
        for (File directory : files){
            if (directory.isDirectory()){
                processTextures(directory);
            }
        }

    }

    private static void processTextures(File dir) {

        //Récupère les fichier dont le nom match la forme : nomdurépertoire-typetexture0.png
        File[] ftextures = dir.listFiles(new FileFilter(){
            public boolean accept(File pathname) {
                return pathname.getName().matches("^("+ dir.getName() + ")-("+ TEXTURE_TYPE +")\\d\\.png$");
            };
        }); 

        Texture[] texList = new Texture[ftextures.length];

        // for chaque textures dans le dossier
        for (int i = 0; i < texList.length; i++){
            //Extraire le type de la texture (le numéro permet juste de différencier les textures)
            String textureType = ftextures[i].getName().replaceAll("(^.*-)|(\\d\\..*$)", "");
            texList[i] = (new Texture(ftextures[i], textureType));
        }

        textures.put(dir.getName(), texList);
    
    }

    public static Shader getShader(String name) {
        for (Shader shader : shaders){
            if (shader.getName().equals(name))
                return shader;
        }

        //On ne veut pas gérer cette exception car c'est une erreur dans le programme 
        //et pas de l'utilisateur, on utilise Runtime exception pour ne pas à avoir
        //A la gérer
        throw new RuntimeException("Impossible de trouver le shader : " + name);
    }

    public static Texture[] getTextures(String name) {
        return textures.get(name);
    }


    

}
