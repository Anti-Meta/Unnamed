package nl.antimeta.unnamed.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.util.Random;

public class LevelBuilderUtil {
    //private static MeshBuilder meshBuilder;

    public static final String VERT_SHADER =
            "attribute vec2 a_position;\n" +
                    "attribute vec4 a_color;\n" +
                    "uniform mat4 u_projTrans;\n" +
                    "varying vec4 vColor;\n" +
                    "void main() {\n" +
                    "	vColor = a_color;\n" +
                    "	gl_Position =  u_projTrans * vec4(a_position.xy, 0.0, 1.0);\n" +
                    "}";

    public static final String FRAG_SHADER =
            "#ifdef GL_ES\n" +
                    "precision mediump float;\n" +
                    "#endif\n" +
                    "varying vec4 vColor;\n" +
                    "void main() {\n" +
                    "	gl_FragColor = vColor;\n" +
                    "}";

    //Position attribute - (x, y)
    private static final int POSITION_COMPONENTS = 2;

    //Color attribute - (r, g, b, a)
    private static final int COLOR_COMPONENTS = 4;

    //Total number of components for all attributes
    private static final int NUM_COMPONENTS = POSITION_COMPONENTS + COLOR_COMPONENTS;

    //The "size" (total number of floats) for a single triangle
    public static final int PRIMITIVE_SIZE = 3 * NUM_COMPONENTS;

    //The maximum number of triangles our mesh will hold
    private static final int MAX_TRIS = 1;

    //The maximum number of vertices our mesh will hold
    private static final int MAX_VERTS = MAX_TRIS * 3;

    private static float[] verts = new float[MAX_VERTS * NUM_COMPONENTS];

    private static int idx = 0;

    private static Mesh mesh;
    private static ShaderProgram shader;
    private static OrthographicCamera camera;

    public static Mesh createLevel(OrthographicCamera camera){
        //meshBuilder = new MeshBuilder();
        LevelBuilderUtil.camera = camera;

        mesh = new Mesh(true, MAX_VERTS, 0,
                new VertexAttribute(Usage.Position, POSITION_COMPONENTS, "a_position"),
                new VertexAttribute(Usage.Generic, COLOR_COMPONENTS, "a_color"));

        //create(10,10,50, 50, Color.RED);

        return mesh;

        //createVerticels();
        //createTriangles();

    }

    public static ShaderProgram createMeshShader() {
        ShaderProgram.pedantic = false;
        ShaderProgram shader = new ShaderProgram(VERT_SHADER, FRAG_SHADER);
        String log = shader.getLog();
        if (!shader.isCompiled())
            throw new GdxRuntimeException(log);
        if (log!=null && log.length()!=0)
            System.out.println("Shader Log: "+log);
        LevelBuilderUtil.shader = shader;
        return shader;
    }

    public static void flush(Mesh mesh, OrthographicCamera camera, ShaderProgram shader) {
        //if we've already flushed
        if (idx==0)
            return;

        //sends our vertex data to the mesh
        mesh.setVertices(verts);

        //no need for depth...
        Gdx.gl.glDepthMask(false);

        //enable blending, for alpha
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        //number of vertices we need to render
        int vertexCount = (idx/NUM_COMPONENTS);

        //update the camera with our Y-up coordiantes
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //start the shader before setting any uniforms
        shader.begin();

        //update the projection matrix so our triangles are rendered in 2D
        shader.setUniformMatrix("u_projTrans", camera.combined);

        //render the mesh
        mesh.render(shader, GL20.GL_TRIANGLES, 0, vertexCount);

        shader.end();

        //re-enable depth to reset states to their default
        Gdx.gl.glDepthMask(true);

        //reset index to zero
        idx = 0;
    }

    public static void create(float x, float y, float width, float height, Color color){
        if (idx==verts.length){
            flush(mesh, camera, shader);
        }

        verts[idx++] = x;           //Position(x, y)
        verts[idx++] = y;
        verts[idx++] = color.r;     //Color(r, g, b, a)
        verts[idx++] = color.g;
        verts[idx++] = color.b;
        verts[idx++] = color.a;

        //top left vertex
        verts[idx++] = x;           //Position(x, y)
        verts[idx++] = y + height;
        verts[idx++] = color.r;     //Color(r, g, b, a)
        verts[idx++] = color.g;
        verts[idx++] = color.b;
        verts[idx++] = color.a;

        //bottom right vertex
        verts[idx++] = x + width;    //Position(x, y)
        verts[idx++] = y;
        verts[idx++] = color.r;      //Color(r, g, b, a)
        verts[idx++] = color.g;
        verts[idx++] = color.b;
        verts[idx++] = color.a;

    }

    private static void creteVerticels(){
        /*meshBuilder.begin(Usage.Position | Usage.Normal | Usage.ColorPacked | Usage.TextureCoordinates, GL20.GL_TRIANGLES);
        MeshPart part1 = meshBuilder.part("part1", GL20.GL_TRIANGLES);
        MeshPart part2 = meshBuilder.part("part2", GL20.GL_TRIANGLE_STRIP);

        meshBuilder.setColor(Color.RED);
        VertexInfo v1 = new VertexInfo().setPos(0, 0, 0).setNor(0, 0, 1).setCol(null).setUV(0.5f, 0.0f);
        VertexInfo v2 = new VertexInfo().setPos(3, 0, 0).setNor(0, 0, 1).setCol(null).setUV(0.0f, 0.0f);
        VertexInfo v3 = new VertexInfo().setPos(3, 3, 0).setNor(0, 0, 1).setCol(null).setUV(0.0f, 0.5f);
        VertexInfo v4 = new VertexInfo().setPos(0, 3, 0).setNor(0, 0, 1).setCol(null).setUV(0.5f, 0.5f);
        meshBuilder.rect(v1, v2, v3, v4);
        meshBuilder.setColor(Color.RED);
        meshBuilder.setUVRange(0.5f, 0f, 0f, 0.5f);
        meshBuilder.rect(0,0,0, 3,0,0, 3,3,0, 0,3,0, 0,0,1);
        Mesh mesh = meshBuilder.end();
        //mesh.render();*/
    }

    private static void createTriangles(){
    }
}
