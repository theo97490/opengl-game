package lyonun.theo.gameproject;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.CallbackI.Z;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.assimp.*;


public class Model3D extends Renderable{
    public static final int[] AITextSupport = { Assimp.aiTextureType_DIFFUSE, 
                                                Assimp.aiTextureType_SPECULAR};


    private ArrayList<Mesh> meshes;

    

    public Model3D(String path) throws LoadException{
        this.meshes = new ArrayList<>(1);
        this.position = new Vector3f(0,0,0);
        this.rotation = new Vector3f(0,0,0);
        
        AIScene scene = Assimp.aiImportFile(path, Assimp.aiProcess_Triangulate | Assimp.aiProcess_FlipUVs);
        if (scene != null)
            processNode(scene.mRootNode(), scene);
        else
            throw new LoadException("Model3D : Un modèle 3D n'a pas pu être chargé");
        
        
    }
    
    private void processNode(AINode node, AIScene scene){
        int a = node.mNumChildren();
        for (int i = 0; i < node.mNumMeshes(); i++){
            try (AIMesh mesh = AIMesh.create (scene.mMeshes().get( node.mMeshes().get(i) ))){
                this.meshes.add(processMesh(mesh, scene));
            } 
        }

        for (int i = 0; i < node.mNumChildren(); i++){
            processNode(AINode.create(node.mChildren().get(i)), scene);
        }
    }

    private Mesh processMesh(AIMesh mesh, AIScene scene){
        //Processing vertices, on connait la taille du buffer à l'avance
        //HACK Créer une classe VertexData pour des données plus flexible ? 
        // posx, posy, posz, (normx, normy, normz), tex, texy

        FloatBuffer vertexBuffer = processVertices(mesh);
        IntBuffer indexBuffer = processIndices(mesh);


        //Processing material
        //HACK Hardcoded shader
        Material mat = new Material("basic");
        if (mesh.mMaterialIndex() >= 0){
            AIMaterial aiMaterial = AIMaterial.create(scene.mMaterials().get(mesh.mMaterialIndex()));
            mat.setTextures(processTextures(aiMaterial));
        }

        return new Mesh(vertexBuffer, indexBuffer, mat);
    }

    private FloatBuffer processVertices(AIMesh mesh){
        FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(5 * mesh.mNumVertices()); 

        for (int vertI = 0; vertI < mesh.mNumVertices(); vertI++){
            //Mesh contient un buffer contenant plusieurs vecteur, on prend le i-ème vecteur de ce buffer
            AIVector3D vertices = mesh.mVertices().get(vertI);
            vertexBuffer.put(vertices.x());
            vertexBuffer.put(vertices.y());
            vertexBuffer.put(vertices.z());
    
            /*
            mesh.mNormals().get(vertI).x();
            mesh.mNormals().get(vertI).x();
            mesh.mNormals().get(vertI).x();
            */
    
            AIVector3D tex = mesh.mTextureCoords(0).get(vertI);
    
            if (tex != null){
                vertexBuffer.put(tex.x());
                vertexBuffer.put(tex.y());
            } 
            //HACK c'est très dodgy 
            else{
                vertexBuffer.put(0);
                vertexBuffer.put(0);
            }

        }

        return vertexBuffer;
    }


    private IntBuffer processIndices(AIMesh mesh){
        ArrayList<Integer> indexData = new ArrayList<>(100);

        for (int faceIndex = 0; faceIndex < mesh.mNumFaces(); faceIndex++){
            AIFace face = mesh.mFaces().get(faceIndex);
            //mNumIndices toujours égal à 3 normalement car on a crée la scene avec le flag aiProcess_Triangulate 
            //qui transforme toute les faces en triangles
            for (int i = 0; i < face.mNumIndices(); i++){
                indexData.add(face.mIndices().get(i));
            }
        }

        //INFO Il y a peut être mieux
        int[] tmpindices = new int[indexData.size()];
        for (int i = 0; i < tmpindices.length; i++){
            tmpindices[i] = indexData.get(i);
        }

        return MemoryUtil.memAllocInt(tmpindices.length)
                         .put(tmpindices)
                         .flip();
    }

    private Texture[] processTextures(AIMaterial aimaterial){
        int totalTexCount = 0;
        int texCounter = 0;

        for (int aitype : AITextSupport){
            totalTexCount += Assimp.aiGetMaterialTextureCount(aimaterial, aitype);
        }

        Texture[] textures = new Texture[totalTexCount];

        
        for (int aitype : AITextSupport){
            int texCount = Assimp.aiGetMaterialTextureCount(aimaterial, aitype);

            for (int i = 0; i < texCount; i++, texCounter++){
                AIString aipath = AIString.calloc();
                Assimp.aiGetMaterialTexture(aimaterial, aitype, i, aipath, null, (IntBuffer) null, null, null, null, null);
    
                String type = "";
                if ( aitype == Assimp.aiTextureType_DIFFUSE )
                    type = Texture.DIFFUSE;
                else if ( aitype == Assimp.aiTextureType_AMBIENT )
                    type = Texture.AMBIENT;
                else if ( aitype == Assimp.aiTextureType_SPECULAR )
                    type = Texture.SPECULAR;
    
                textures[texCounter] = new Texture(new File("./models/backpack/" + aipath.dataString()), type);

            }
    
        }

        return textures;

    }

    public void draw(Matrix4f projView){
        ByteBuffer buff = MemoryUtil.memAlloc(16 * 4);
        projView.mul(new Matrix4f().translate(position).rotateXYZ(rotation)).get(buff);

        for (Mesh mesh : meshes){
            mesh.material.setParameters("MVP", buff);
    
            mesh.draw();
        }


    }
}
