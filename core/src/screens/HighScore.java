package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

/**
 * Created by MrTechno on 1/22/2016.
 */
public class HighScore implements Screen {
    //stage for the menu (buttons etc)
    private Stage stage;
    //texture of the buttons
    private TextureAtlas atlas;
    //background texture
    private Texture bgTexture;
    //background sprite
    private Sprite bgSprite;
    //skin of the button for images
    private Skin skin;
    //platform for when this is on stage
    private Table table;
    private TextButton buttonOption1;
    //font of everything
    private BitmapFont white, black, blackOnWhitex;
    //labe for heading
    private Label heading, headingx;
    private SpriteBatch batch;
    private ScrollPane scrollPane;


    Preferences sipangBaboySharedPreferences = Gdx.app.getPreferences("sipangBaboySharedPreferences");

    //create
    @Override
    public void show() {

        sipangBaboySharedPreferences.putInteger("globalScore", 0);
        sipangBaboySharedPreferences.putInteger("triviaNumber", 0);
        sipangBaboySharedPreferences.flush();
        sipangBaboySharedPreferences.getInteger("globalScore", 0);
        sipangBaboySharedPreferences.getInteger("triviaNumber", 0);
        Gdx.app.log("global counter gameover", String.valueOf(sipangBaboySharedPreferences.getInteger("globalScore", 0)));
        Gdx.app.log("trivia counter gameover", String.valueOf( sipangBaboySharedPreferences.getInteger("triviaNumber", 0)));
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/buttons.pack");
        skin = new Skin(atlas);

        batch = new SpriteBatch();


        bgTexture = new Texture("score.png");
        bgSprite = new Sprite(bgTexture);
        bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        //aligns the buttons like columns
        table = new Table();
        //limit of the table is whole screen
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


        //creating fonts
        white = new BitmapFont(Gdx.files.internal("font/white.fnt"),false);
        black = new BitmapFont(Gdx.files.internal("font/black.fnt"),false);
        blackOnWhitex = new BitmapFont(Gdx.files.internal("font/blackOnWhitex.fnt"),false);

        //creating buttons
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("button.up");
        textButtonStyle.down = skin.getDrawable("button.down");
        textButtonStyle.pressedOffsetX = 1;
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        buttonOption1 = new TextButton("Main Menu", textButtonStyle);
        buttonOption1.getLabel().setFontScale(1, 1);

        buttonOption1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new Splash());
            }
        });

        Label.LabelStyle headingStyle = new Label.LabelStyle(blackOnWhitex, Color.WHITE);

        int x = sipangBaboySharedPreferences.getInteger("globalScore", 0);

        String xString = "";
        /*
        for(x=0;x<20;x++){
            xString += sipangBaboySharedPreferences.getString("hs","none");
        }
*/
        heading = new Label(String.valueOf(sipangBaboySharedPreferences.getInteger("highScore",0)), headingStyle);
        heading.setFontScale(1, 1);
        heading.setWidth(Gdx.graphics.getWidth()/2);
        heading.setWrap(true);
        heading.setAlignment(Align.center);
        scrollPane = new ScrollPane(heading);



        //adding stuff to table to show
        //bgSprite
        //table.add(heading);//creating heading
        //table.getCell(heading).spaceBottom(100);
        //table.row();
        table.add(scrollPane).width(Gdx.graphics.getWidth()/2);
        table.row();
        table.add(buttonOption1).width(Gdx.graphics.getWidth()/3);//creating play button
        table.row();
        //table.debug();
        stage.addActor(table);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        bgSprite.draw(batch);
        batch.end();
        //table.drawDebug(stage);
        stage.act(delta);


        stage.draw();
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
        stage.dispose();
        atlas.dispose();
        white.dispose();
        black.dispose();
        skin.dispose();
    }

}
