package nl.antimeta.unnamed.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import nl.antimeta.unnamed.UnnamedGame;

public class MainMenuScreen implements Screen {
    private UnnamedGame game;

    private SpriteBatch batch;
    private Texture menuBackground;

    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton startButton, optionsButton, exitButton;
    private BitmapFont fontWhite, fontBlack;
    private Label heading;
    private TextButton.TextButtonStyle textButtonStyle;

    private ClickListener startClickListener;
    private ClickListener optionsClickListener;
    private ClickListener exitClickListener;

    public MainMenuScreen(final UnnamedGame game){
        this.game = game;
    }

    private void createButtons(){
        startButton = new TextButton("Start", textButtonStyle);
        startButton.addListener(startClickListener);
        optionsButton = new TextButton("Options", textButtonStyle);
        optionsButton.addListener(optionsClickListener);
        exitButton = new TextButton("Exit", textButtonStyle);
        exitButton.addListener(exitClickListener);
    }

    private void createButtonTextStyle(){
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("menu_button_normal");
        textButtonStyle.down = skin.getDrawable("menu_button_active");
        textButtonStyle.font = fontBlack;
    }

    private void createButtonActionListeners(){
        startClickListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Game2Screen());
            }
        };
        optionsClickListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new OptionsScreen());
            }
        };
        exitClickListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        };
    }

    @Override
    public void show() {
        stage = new Stage();

        batch = new SpriteBatch();
        menuBackground = new Texture(Gdx.files.internal("ui/menu/background.jpg"));

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/menu/button.pack");
        skin = new Skin(atlas);

        table = new Table(skin);
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        fontWhite = new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
        fontBlack = new BitmapFont(Gdx.files.internal("font/black.fnt"), false);

        createButtonTextStyle();
        createButtonActionListeners();
        createButtons();

        Label.LabelStyle labelStyle = new Label.LabelStyle(fontWhite, Color.WHITE);

        heading = new Label(UnnamedGame.GAME_NAME, labelStyle);
        heading.setFontScale(2);

        table.add(heading).pad(10);
        table.row();
        table.add(startButton).size(200, 50).pad(10);
        table.row();
        table.add(optionsButton).size(200, 50).pad(10);
        table.row();
        table.add(exitButton).size(200, 50).pad(10);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        //sprite.setSize(1f, 1f * sprite.getHeight() / sprite.getWidth() );
        batch.draw(menuBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        batch.begin();
        batch.draw(menuBackground, 0, 0, width, height);
        batch.end();
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
        skin.dispose();
    }
}
