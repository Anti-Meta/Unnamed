package nl.antimeta.unnamed.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.shaders.DefaultShader;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import nl.antimeta.unnamed.World;

public class GameScreen implements Screen {

    SpriteBatch spriteBatch;
    BitmapFont font;
    ModelBatch modelBatch;
    PerspectiveCamera camera;
    Environment lights;
    FirstPersonCameraController controller;
    World world;

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
        modelBatch = new ModelBatch();
        DefaultShader.defaultCullFace = GL20.GL_FRONT;
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = 0.5f;
        camera.far = 250;
        camera.position.set(0, 0, 100);
        controller = new FirstPersonCameraController(camera);
        Gdx.input.setInputProcessor(controller);

        lights = new Environment();
        lights.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.f));
        lights.add(new DirectionalLight().set(1, 1, 1, 0, -1, 0));

        /*Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();

        //Texture texture = new Texture(pixmap);
        Texture texture = new Texture(Gdx.files.internal("data/tiles.png"));
        TextureRegion[][] tiles = TextureRegion.split(texture, 32, 32);*/

        World.WorldBuilder builder = new World.WorldBuilder(16);
        builder.setChunksX(10);
        builder.setChunksZ(10);
        builder.setChunksY(2);
        world = builder.create();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        modelBatch.begin(camera);
        modelBatch.render(world, lights);
        modelBatch.end();
        controller.update();

        spriteBatch.begin();
        font.draw(spriteBatch, "FPS: " + Gdx.graphics.getFramesPerSecond() + ", Rendered Chunks: " + world.renderedChunks + " / " + world.numberOfChunks, 0, 20);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
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
        modelBatch.dispose();
        spriteBatch.dispose();
        font.dispose();
    }
}
