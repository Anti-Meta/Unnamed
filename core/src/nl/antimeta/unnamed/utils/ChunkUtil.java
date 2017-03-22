package nl.antimeta.unnamed.utils;

import com.badlogic.gdx.math.Vector3;
import nl.antimeta.unnamed.models.Chunk;

public class ChunkUtil {
    //Get selected voxel from blocks
    public static byte checkAndGet (Chunk chunk, int x, int y, int z) {
        if (x < 0 || x >= chunk.getWidth()) return 0;
        if (y < 0 || y >= chunk.getHeight()) return 0;
        if (z < 0 || z >= chunk.getDepth()) return 0;
        return getFast(chunk, x, y, z);
    }

    public static byte getFast (Chunk chunk, int x, int y, int z) {
        return chunk.getBlocks()[x + z * chunk.getWidth() + y * chunk.getHeightTimesWidth()];
    }

    //Set voxel on blocks
    public static void checkAndSet(Chunk chunk, int x, int y, int z, byte voxel){
        if (x < 0 || x >= chunk.getWidth()) return;
        if (y < 0 || y >= chunk.getHeight()) return;
        if (z < 0 || z >= chunk.getDepth()) return;
        setFast(chunk, x, y, z, voxel);
    }

    public static void setFast (Chunk chunk, int x, int y, int z, byte voxel) {
        chunk.getBlocks()[x + z * chunk.getWidth() + y * chunk.getHeightTimesWidth()] = voxel;
    }


    public static int calculateVertices (Chunk chunk, float[] vertices) {
        int i = 0;
        int vertexOffset = 0;
        for (int y = 0; y < chunk.getHeight(); y++) {
            for (int z = 0; z < chunk.getDepth(); z++) {
                for (int x = 0; x < chunk.getWidth(); x++, i++) {
                    byte voxel = chunk.getBlocks()[i];
                    if (voxel == 0) continue;

                    if (y < chunk.getHeight() - 1) {
                        if (chunk.getBlocks()[i + chunk.getTopOffset()] == 0) vertexOffset = createTop(chunk.offset, x, y, z, vertices, vertexOffset);
                    } else {
                        vertexOffset = createTop(chunk.offset, x, y, z, vertices, vertexOffset);
                    }
                    if (y > 0) {
                        if (chunk.getBlocks()[i + chunk.getBottomOffset()] == 0) vertexOffset = createBottom(chunk.offset, x, y, z, vertices, vertexOffset);
                    } else {
                        vertexOffset = createBottom(chunk.offset, x, y, z, vertices, vertexOffset);
                    }
                    if (x > 0) {
                        if (chunk.getBlocks()[i + chunk.getLeftOffset()] == 0) vertexOffset = createLeft(chunk.offset, x, y, z, vertices, vertexOffset);
                    } else {
                        vertexOffset = createLeft(chunk.offset, x, y, z, vertices, vertexOffset);
                    }
                    if (x < chunk.getWidth() - 1) {
                        if (chunk.getBlocks()[i + chunk.getRightOffset()] == 0) vertexOffset = createRight(chunk.offset, x, y, z, vertices, vertexOffset);
                    } else {
                        vertexOffset = createRight(chunk.offset, x, y, z, vertices, vertexOffset);
                    }
                    if (z > 0) {
                        if (chunk.getBlocks()[i + chunk.getFrontOffset()] == 0) vertexOffset = createFront(chunk.offset, x, y, z, vertices, vertexOffset);
                    } else {
                        vertexOffset = createFront(chunk.offset, x, y, z, vertices, vertexOffset);
                    }
                    if (z < chunk.getDepth() - 1) {
                        if (chunk.getBlocks()[i + chunk.getBackOffset()] == 0) vertexOffset = createBack(chunk.offset, x, y, z, vertices, vertexOffset);
                    } else {
                        vertexOffset = createBack(chunk.offset, x, y, z, vertices, vertexOffset);
                    }
                }
            }
        }
        return vertexOffset / 6;
    }

    //Vertices creators

    public static int createTop (Vector3 offset, int x, int y, int z, float[] vertices, int vertexOffset) {
        vertices[vertexOffset++] = offset.x + x;
        vertices[vertexOffset++] = offset.y + y + 1;
        vertices[vertexOffset++] = offset.z + z;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 1;
        vertices[vertexOffset++] = 0;

        vertices[vertexOffset++] = offset.x + x + 1;
        vertices[vertexOffset++] = offset.y + y + 1;
        vertices[vertexOffset++] = offset.z + z;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 1;
        vertices[vertexOffset++] = 0;

        vertices[vertexOffset++] = offset.x + x + 1;
        vertices[vertexOffset++] = offset.y + y + 1;
        vertices[vertexOffset++] = offset.z + z + 1;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 1;
        vertices[vertexOffset++] = 0;

        vertices[vertexOffset++] = offset.x + x;
        vertices[vertexOffset++] = offset.y + y + 1;
        vertices[vertexOffset++] = offset.z + z + 1;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 1;
        vertices[vertexOffset++] = 0;
        return vertexOffset;
    }

    public static int createBottom (Vector3 offset, int x, int y, int z, float[] vertices, int vertexOffset) {
        vertices[vertexOffset++] = offset.x + x;
        vertices[vertexOffset++] = offset.y + y;
        vertices[vertexOffset++] = offset.z + z;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = -1;
        vertices[vertexOffset++] = 0;

        vertices[vertexOffset++] = offset.x + x;
        vertices[vertexOffset++] = offset.y + y;
        vertices[vertexOffset++] = offset.z + z + 1;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = -1;
        vertices[vertexOffset++] = 0;

        vertices[vertexOffset++] = offset.x + x + 1;
        vertices[vertexOffset++] = offset.y + y;
        vertices[vertexOffset++] = offset.z + z + 1;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = -1;
        vertices[vertexOffset++] = 0;

        vertices[vertexOffset++] = offset.x + x + 1;
        vertices[vertexOffset++] = offset.y + y;
        vertices[vertexOffset++] = offset.z + z;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = -1;
        vertices[vertexOffset++] = 0;
        return vertexOffset;
    }

    public static int createLeft (Vector3 offset, int x, int y, int z, float[] vertices, int vertexOffset) {
        vertices[vertexOffset++] = offset.x + x;
        vertices[vertexOffset++] = offset.y + y;
        vertices[vertexOffset++] = offset.z + z;
        vertices[vertexOffset++] = -1;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 0;

        vertices[vertexOffset++] = offset.x + x;
        vertices[vertexOffset++] = offset.y + y + 1;
        vertices[vertexOffset++] = offset.z + z;
        vertices[vertexOffset++] = -1;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 0;

        vertices[vertexOffset++] = offset.x + x;
        vertices[vertexOffset++] = offset.y + y + 1;
        vertices[vertexOffset++] = offset.z + z + 1;
        vertices[vertexOffset++] = -1;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 0;

        vertices[vertexOffset++] = offset.x + x;
        vertices[vertexOffset++] = offset.y + y;
        vertices[vertexOffset++] = offset.z + z + 1;
        vertices[vertexOffset++] = -1;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 0;
        return vertexOffset;
    }

    public static int createRight (Vector3 offset, int x, int y, int z, float[] vertices, int vertexOffset) {
        vertices[vertexOffset++] = offset.x + x + 1;
        vertices[vertexOffset++] = offset.y + y;
        vertices[vertexOffset++] = offset.z + z;
        vertices[vertexOffset++] = 1;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 0;

        vertices[vertexOffset++] = offset.x + x + 1;
        vertices[vertexOffset++] = offset.y + y;
        vertices[vertexOffset++] = offset.z + z + 1;
        vertices[vertexOffset++] = 1;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 0;

        vertices[vertexOffset++] = offset.x + x + 1;
        vertices[vertexOffset++] = offset.y + y + 1;
        vertices[vertexOffset++] = offset.z + z + 1;
        vertices[vertexOffset++] = 1;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 0;

        vertices[vertexOffset++] = offset.x + x + 1;
        vertices[vertexOffset++] = offset.y + y + 1;
        vertices[vertexOffset++] = offset.z + z;
        vertices[vertexOffset++] = 1;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 0;
        return vertexOffset;
    }

    public static int createFront (Vector3 offset, int x, int y, int z, float[] vertices, int vertexOffset) {
        vertices[vertexOffset++] = offset.x + x;
        vertices[vertexOffset++] = offset.y + y;
        vertices[vertexOffset++] = offset.z + z;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 1;

        vertices[vertexOffset++] = offset.x + x + 1;
        vertices[vertexOffset++] = offset.y + y;
        vertices[vertexOffset++] = offset.z + z;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 1;

        vertices[vertexOffset++] = offset.x + x + 1;
        vertices[vertexOffset++] = offset.y + y + 1;
        vertices[vertexOffset++] = offset.z + z;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 1;

        vertices[vertexOffset++] = offset.x + x;
        vertices[vertexOffset++] = offset.y + y + 1;
        vertices[vertexOffset++] = offset.z + z;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 1;
        return vertexOffset;
    }

    public static int createBack (Vector3 offset, int x, int y, int z, float[] vertices, int vertexOffset) {
        vertices[vertexOffset++] = offset.x + x;
        vertices[vertexOffset++] = offset.y + y;
        vertices[vertexOffset++] = offset.z + z + 1;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = -1;

        vertices[vertexOffset++] = offset.x + x;
        vertices[vertexOffset++] = offset.y + y + 1;
        vertices[vertexOffset++] = offset.z + z + 1;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = -1;

        vertices[vertexOffset++] = offset.x + x + 1;
        vertices[vertexOffset++] = offset.y + y + 1;
        vertices[vertexOffset++] = offset.z + z + 1;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = -1;

        vertices[vertexOffset++] = offset.x + x + 1;
        vertices[vertexOffset++] = offset.y + y;
        vertices[vertexOffset++] = offset.z + z + 1;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = 0;
        vertices[vertexOffset++] = -1;
        return vertexOffset;
    }
}
