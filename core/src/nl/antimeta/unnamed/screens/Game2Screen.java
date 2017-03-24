package nl.antimeta.unnamed.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.utils.Array;
import nl.antimeta.unnamed.utils.NoiseUtil;

public class Game2Screen implements Screen {

    private final float FIELD_OF_VIEW = 67;

    private PerspectiveCamera camera;
    public FirstPersonCameraController controller;
    private ModelBatch modelBatch;
    private Environment environment;
    private AssetManager assets;

    public Model stone, grass, dirt;
    public Array<ModelInstance> instances = new Array<>();

    private Material gray, green, brown;

    private boolean loading;

    private float[][] noise;
    private float[][] smoothNoise;

    @Override
    public void show() {
        //noise = NoiseUtil.generateWhiteNoise(10, 10);
        //smoothNoise = NoiseUtil.generateSmoothNoise(noise, 2);

        modelBatch = new ModelBatch();
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.Ambient, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.5f, 0.5f, 0.5f, -1f, -0.8f, -0.2f));

        gray = new Material(ColorAttribute.createDiffuse(Color.GRAY));
        green = new Material(ColorAttribute.createDiffuse(Color.GREEN));
        brown = new Material(ColorAttribute.createDiffuse(Color.BROWN));

        camera = new PerspectiveCamera(FIELD_OF_VIEW, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0f, 15f, 20f);
        camera.lookAt(0,0,0);
        camera.near = 1f;
        camera.far = 300f;
        camera.update();

        controller = new FirstPersonCameraController(camera);
        Gdx.input.setInputProcessor(controller);

        loadTempAssets();
        //loadAssets();

        loading = true;
    }

    private void loadTempAssets() {
        ModelBuilder modelBuilder = new ModelBuilder();
        grass = modelBuilder.createBox(1, 1, 1, green, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        stone = modelBuilder.createBox(1,1,1, gray, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        dirt = modelBuilder.createBox(1,1,1, brown, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    }

    private void loadAssets() {
        //TODO
        assets = new AssetManager();
    }

    @Override
    public void render(float delta) {
        if(loading){
            renderModels();
        }

        controller.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(camera);
        modelBatch.render(instances, environment);
        modelBatch.end();
    }

    private void renderModels() {
        for (int x = -10; x < 10 ; x+= 2) {
            for (int y = 0; y > -10; y--) {
                for (int z = -10; z < 10 ; z+= 2) {
                    if(y >= 0){
                        renderGrass(x, y, z);
                    }
                    else if(y >= -3){
                        renderDirt(x, y, z);
                    }
                    else{
                        renderStone(x, y, z);
                    }
                }
            }
        }
        loading = false;
    }

    private void renderDirt(int x, int y, int z) {
        ModelInstance modelInstance = new ModelInstance(dirt);
        modelInstance.transform.setToTranslation(x, y, z);
        instances.add(modelInstance);
    }

    private void renderStone(int x, int y, int z) {
        ModelInstance modelInstance = new ModelInstance(stone);
        modelInstance.transform.setToTranslation(x, y, z);
        instances.add(modelInstance);
    }

    private void renderGrass(int x, int y, int z) {
        ModelInstance modelInstance = new ModelInstance(grass);
        modelInstance.transform.setToTranslation(x, y, z);
        instances.add(modelInstance);
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
        modelBatch.dispose();
        instances.clear();
        dirt.dispose();
        grass.dispose();
        stone.dispose();
    }
}
