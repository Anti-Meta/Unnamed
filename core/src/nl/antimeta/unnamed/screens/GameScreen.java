package nl.antimeta.unnamed.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import nl.antimeta.unnamed.utils.CameraUtil;
import nl.antimeta.unnamed.utils.LevelBuilderUtil;

import java.util.Random;

public class GameScreen implements Screen {
    //TODO add field voor seed in menu, voornu gebruik altijd seed 5
    private int seed = 5;
    private Random random = new Random(seed);
    private OrthographicCamera camera;
    private Mesh mesh;
    private ShaderProgram shader;

    @Override
    public void show() {
        camera = new OrthographicCamera();
        mesh = LevelBuilderUtil.createLevel(camera);
        shader = LevelBuilderUtil.createMeshShader();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        LevelBuilderUtil.create(10,10,40,40, Color.GREEN);
        LevelBuilderUtil.create(50,50,250,250, Color.BLUE);

        LevelBuilderUtil.flush(mesh, camera, shader);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mesh.dispose();
        shader.dispose();
    }
}
