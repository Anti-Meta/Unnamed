package nl.antimeta.unnamed;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.RenderableProvider;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import nl.antimeta.unnamed.models.Chunk;
import nl.antimeta.unnamed.utils.ChunkUtil;

public class World implements RenderableProvider {

    public final int CHUNK_SIZE;

    public final int chunksX;
    public final int chunksY;
    public final int chunksZ;

    public final int voxelsX;
    public final int voxelsY;
    public final int voxelsZ;

    public final Chunk[] chunks;
    public final Mesh[] meshes;
    public final boolean[] dirty;
    public final int[] numVertices;
    public float[] vertices;
    public final Material[] materials;

    public int renderedChunks;
    public int numberOfChunks;

    private World(int chunkSize, int chunksX, int chunksY, int chunksZ){
        //Used to easily get the chunk size
        this.CHUNK_SIZE = chunkSize;

        //Set total x * y * z for the array size
        this.chunks = new Chunk[chunksX * chunksY * chunksZ];
        this.meshes = new Mesh[chunksX * chunksY * chunksZ];
        this.dirty = new boolean[chunksX * chunksY * chunksZ];
        this.numVertices = new int[chunksX * chunksY * chunksZ];
        this.vertices = new float[6 * 6 * CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE];
        this.materials = new Material[chunksX * chunksY * chunksZ];

        this.numberOfChunks = chunksX * chunksY * chunksZ;

        this.chunksX = chunksX;
        this.chunksY = chunksY;
        this.chunksZ = chunksZ;

        this.voxelsX = chunksX * CHUNK_SIZE;
        this.voxelsY = chunksY * CHUNK_SIZE;
        this.voxelsZ = chunksZ * CHUNK_SIZE;

        int len = CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE * 6 * 6 / 3;

        short[] indices = new short[len];
        short j = 0;

        int i;
        for (i = 0; i < len; i += 6, j += 4) {
            indices[i] = (j);
            indices[i + 1] = (short)(j + 1);
            indices[i + 2] = (short)(j + 2);
            indices[i + 3] = (short)(j + 2);
            indices[i + 4] = (short)(j + 3);
            indices[i + 5] = (j);
        }

        i = 0;
        for (int y = 0; y < chunksY; y++){
            for (int z = 0; z < chunksZ; z++){
                for (int x = 0; x < chunksX ; x++) {
                    //Create one new chunk object and place them in the chunk array.
                    Chunk chunk = new Chunk(CHUNK_SIZE, CHUNK_SIZE, CHUNK_SIZE);
                    chunk.offset.set(x * CHUNK_SIZE, y * CHUNK_SIZE, z * CHUNK_SIZE);
                    chunks[i] = chunk;

                    meshes[i] = new Mesh(true,
                            CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE * 6 * 4,
                            CHUNK_SIZE * CHUNK_SIZE * CHUNK_SIZE * 36 / 3,
                            VertexAttribute.Position(), VertexAttribute.Normal());
                    meshes[i].setIndices(indices);

                    dirty[i] = true;
                    numVertices[i] = 0;
                    materials[i] =  new Material(new ColorAttribute(ColorAttribute.Diffuse, MathUtils.random(0.5f, 1f), MathUtils.random(
                            0.5f, 1f), MathUtils.random(0.5f, 1f), 1));
                    i++;
                }
            }
        }
    }

    public void placeAndCheckChunk(byte voxel, float x, float y, float z){
        //Get the round value of X, Y and Z
        int intX = (int) x;
        int intY = (int) y;
        int intZ = (int) z;

        //Get the chunk cords and check if something is wrong.
        int chunkXCord = intX / CHUNK_SIZE;
        if (chunkXCord < 0 || chunkXCord >= chunksX) return;

        int chunkYCord = intY / CHUNK_SIZE;
        if (chunkYCord < 0 || chunkYCord >= chunksY) return;

        int chunkZCord = intZ / CHUNK_SIZE;
        if (chunkZCord < 0 || chunkZCord >= chunksZ) return;
        placeChunk(voxel, intX, intY, intZ, chunkXCord, chunkYCord, chunkZCord);
    }

    public void placeChunk(byte voxel, int x, int y, int z, int chunkXCord, int chunkYCord, int chunkZCord){
        Chunk selectedChunk = chunks[chunkXCord + chunkZCord * chunksX + chunkYCord * chunksX * chunksZ];
        ChunkUtil.checkAndSet(selectedChunk,x % CHUNK_SIZE, y % CHUNK_SIZE, z % CHUNK_SIZE, voxel);
    }

    @Override
    public void getRenderables(Array<Renderable> renderables, Pool<Renderable> pool) {
        renderedChunks = 0;
        //Render each chunk
        for (int i = 0; i < chunks.length ; i++) {
            Chunk chunk = chunks[i];
            Mesh mesh = meshes[i];

            if(dirty[i]){
                int tempNumOfVertices = ChunkUtil.calculateVertices(chunk, vertices);
                numVertices[i] = tempNumOfVertices / 4 * 6;
                mesh.setVertices(vertices, 0, tempNumOfVertices * 6);
                dirty[i] = false;
            }

            if(numVertices[i] == 0){
                Renderable renderable = pool.obtain();
                renderable.material = materials[i];
                renderable.meshPart.mesh = mesh;
                renderable.meshPart.offset = 0;
                renderable.meshPart.size = numVertices[i];
                renderable.meshPart.primitiveType = GL20.GL_TRIANGLES;
                renderables.add(renderable);
            }
            else{
                return;
            }
        }
    }

    public static class WorldBuilder {
        private World world;
        private final int chunkSize;
        private int chunksX;
        private int chunksY;
        private int chunksZ;

        public WorldBuilder(int chunkSize){
            this.chunkSize = chunkSize;
        }

        public World create(){
            world = new World(chunkSize, chunksX, chunksY, chunksZ);

            return world;
        }

        public void setChunksX(int chunksX) {
            this.chunksX = chunksX;
        }

        public void setChunksY(int chunksY) {
            this.chunksY = chunksY;
        }

        public void setChunksZ(int chunksZ) {
            this.chunksZ = chunksZ;
        }
    }
}
