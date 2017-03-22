package nl.antimeta.unnamed.models;

import com.badlogic.gdx.math.Vector3;

public class Chunk {

    /**
     *  Array of all the blocks with there byte value
     */
    private final byte[] blocks;

    /**
     *  Width of one {@link Chunk}
     */
    private final int width;


    /**
     *  Height of one {@link Chunk}
     */
    private final int height;


    /**
     *  Depth of one {@link Chunk}
     */
    private final int depth;


    private final int topOffset;
    private final int bottomOffset;
    private final int leftOffset;
    private final int rightOffset;
    private final int frontOffset;
    private final int backOffset;
    private final int heightTimesWidth;

    /**
     *  Needed when calculating the vertices
     */
    public final Vector3 offset = new Vector3();

    public Chunk(int width, int height, int depth){
        this.blocks = new byte[width * height * depth];
        this.heightTimesWidth = height * width;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.topOffset = width * depth;
        this.bottomOffset = -width * depth;
        this.leftOffset = -1;
        this.rightOffset = 1;
        this.frontOffset = -width;
        this.backOffset = width;
    }

    public byte[] getBlocks() {
        return blocks;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public int getTopOffset() {
        return topOffset;
    }

    public int getBottomOffset() {
        return bottomOffset;
    }

    public int getLeftOffset() {
        return leftOffset;
    }

    public int getRightOffset() {
        return rightOffset;
    }

    public int getFrontOffset() {
        return frontOffset;
    }

    public int getBackOffset() {
        return backOffset;
    }

    public int getHeightTimesWidth() {
        return heightTimesWidth;
    }
}
