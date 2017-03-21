package nl.antimeta.unnamed.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class OptionsScreen implements Screen {
    private Stage stage;
    private SpriteBatch batch;
    private Texture texture;

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        //background color
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        texture = new Texture(pixmap);


        //TODO Je construct.
        //Maak hier de buttons, de onclicklisteners(code die uitgevoerd wordt als je op een knop clickt), de table van de buttons.
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //TODO Elke FPS Render
        //Add hier je table met buttons of andere dingen

        batch.begin();
        batch.draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);

        //TODO Als het scherm resized
        //Alleen nodig als je bijvoorbeeld een achtergrond plaatje opnieuw wilt zetten.
        //Tables resizen automatisch.
    }

    @Override
    public void pause() {
        //unneeded
    }

    @Override
    public void resume() {
        //unneeded
    }

    @Override
    public void hide() {
        //unneeded
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
    }
}
