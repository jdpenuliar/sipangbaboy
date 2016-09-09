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
 * Created by MrTechno on 1/19/2016.
 */
public class Trivia implements Screen {
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
    private TextButton buttonOption1, buttonOption2,buttonOption3,buttonOption4;
    //font of everything
    private BitmapFont white, black, blackOnWhitex;
    //labe for heading
    private Label heading,bugtong,sagot,trivia;
    private SpriteBatch batch;
    private ScrollPane scrollPane;

    private int arrayToken,tester = 0,gameStateBugtong = 0;

    Preferences sipangBaboySharedPreferences = Gdx.app.getPreferences("sipangBaboySharedPreferences");
    String [] arrayBugtongTrivia = new String[]{
            "Ang bibig ay para sa tao at ang bunganga naman ay para sa hayop at gamit na walang buhay. re: bunganga ng bulkan.",
            "Ang isang ordinaryong tao ay kumukurapt ng 12 na beses sa isang minuto. Halos 10,000 kada araw",
            "Ang pag dinig ay hindi tumitigil kahit natutulog. Binabalewala lang ng ating utak ang naririnig ng taenga pag tayo ay tulog",
            "Ang pagpangalan sa isang banka ng mga dayuhan ay halos sa mga kababaihan",
            "Ang 1/6 ng mata lamang ang nakalabas sa ating katawan",
            "Ang mga daliri ay may mga muscle na kayang mag akyat ng mga tao sa mga patayong mga lugar tulad ng pader",
            "Mayroong humigit kumulang 150 na klase ng bayabas sa buong mundo",
            "Ang mga pinakaunang kampana ay nag mula sa Ancient China 4000 taon ang nakalipas",
            "The english word for balimbing is star fruit or carambola fruit.",
            "Halos tatlong taon bago mahinog ang isang pinya",
            "Buto ang kasoy hindi mani",
            "1oz ng kasoy ay mayroong 150 calories",
            "Ang ingles ng atis ay Sugar/Custard Apple",
            "Ang mga bote dati ay nilalagyan ng mensahe at itatapon sa dagat para makahanap ng tulong",
            "Isa itong sandata na hindi nakakasakit ng pisikal at ginamit ni Jose Rizal",
            "bilang pambansang bulaklak ng Pilipinas, dahil sa natatanging halimuyak, kasikatan at ornamental na kahalagahan nito. Tinatawag itong Arabian Jasmin sa Ingles, Manul ng mga Bisaya at Sampaga o Pongso ng mga Kapampangan.",
            "Itinuturing na Pambansang Hayop ng Pilipinas, ang Kalabaw (Bubalus bubalis) ay ang matalik na kaibigan ng magsasaka at ang masasabing pinakamahalagang hayop sa mga palayan. Anuwang ang katawagan nito sa lumang Tagalog at Nuwang naman sa mga Ilokano. Sa kasalukuyan, may 3.2 milyong kalabaw na matatagpuan sa Pilipinas.",
            "( Livistona rotundifolia ) ay isang pabilog na dahon na palma na matatagpuan sa Timog-Silangang Asya. Kasapi ito sagenus Livistona na tinatawag na Footstool palm sa Ingles. Pambansang dahon ito ng Pilipinas.",
            "Ang bangus (milkfish), bangos, o Chanos chanos ay isang uri ng isdang matinik o mabuto subalit nakakain. Sinasabing may pitong mga uri na kabilang sa limang karagdagang sari ang nawala na sa mundo.",
            "(Philippine Eagle) ay isang malaking agila na makikita sa mga gubat ng Luzon, Samar, Leyte at Rehiyon XII o SOCCSKSARGEN. Ito ay ang pambansang ibon ng Pilipinas. Ang haribon ay simbolo ng katapangan ng mga ninuno ng Pilipino. ",
            "Filipino -  ang pambansang wika at isa sa mga opisyal na wika ng Pilipinas.  Noong 2007, ang wikang Filipino ay ang unang wika ng 28 milyon na tao, o mahigit kumulang isang-katlo ng populasyon ng Pilipinas.",
            "Si Emilio Aguinaldo y Famy (22 Marso 1869–6 Pebrero 1964) ay isang Pilipinong heneral, politiko at pinuno ng kalayaan, ay ang unang Pangulo ng Republika ng Pilipinas (20 Enero 1899–1 Abril 1901). Isa siyang bayaning nakibaka para sa kasarinlan ng Pilipinas.",
            "Ito ay nilalaro sa pamamaraang pag sala ng sipa. Maaari saluhin sa paa, tuhod, ulo, siko o braso. Hindi maaari na gamitin ang kamay sa paglalaro ng sipa.",
            "Ang Naga o mas kilala sa tawag na Narra (Pterocarpus indicus), na Pambansang Puno ng Pilipinas, ay isang puno na pinahahalagahan dahil sa angkin nitong tibay, bigat at magandang kalidad.",
            "“Marcha Nacional Filipina”, Pambansang Awit ng Pilipinas, ay isang marcha na binuo ni Julian Felipe at unang pinatugtog sa Araw ng Kalayaan sa Kawit, Cavite noong 12 Hunyo 1898."
    };

    //create
    @Override
    public void show() {

        sipangBaboySharedPreferences.getInteger("globalScore", 0);
        sipangBaboySharedPreferences.getInteger("triviaNumber", 0);
        Gdx.app.log("global counter trivia", String.valueOf(sipangBaboySharedPreferences.getInteger("globalScore", 0)));
        Gdx.app.log("trivia counter trivia", String.valueOf( sipangBaboySharedPreferences.getInteger("triviaNumber", 0)));
        stage = new Stage();

        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("ui/buttons.pack");
        skin = new Skin(atlas);

        batch = new SpriteBatch();


        bgTexture = new Texture("menu.jpg");
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

        buttonOption1 = new TextButton("Next Level", textButtonStyle);
        buttonOption1.getLabel().setFontScale(1, 1);

        buttonOption1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(sipangBaboySharedPreferences.getInteger("globalScore", 0) == 6){
                    Gdx.app.log("next stage if else", "6");
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Congratulations());
                }else if(sipangBaboySharedPreferences.getInteger("globalScore", 0) >= 3){
                    Gdx.app.log("next stage if else", ">=3");
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new PlayLuksongBaboy());
                }else if(sipangBaboySharedPreferences.getInteger("globalScore", 0) >= 1){
                    Gdx.app.log("next stage if else", ">=1");
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new PlaySipa());
                }else{
                    Gdx.app.log("next stage if else", "else");
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOver());
                }
            }
        });

        Label.LabelStyle headingStyle = new Label.LabelStyle(blackOnWhitex, Color.WHITE);

        int x = sipangBaboySharedPreferences.getInteger("globalScore", 0);

        heading = new Label("Trivia", headingStyle);
        heading.setFontScale(1, 1);

        sipangBaboySharedPreferences.getInteger("globalScore", 0);
        sipangBaboySharedPreferences.getInteger("triviaNumber", 0);
        Gdx.app.log("trivia number ", String.valueOf(sipangBaboySharedPreferences.getInteger("globalScore", 0)));
        trivia = new Label("Tama! " + arrayBugtongTrivia[sipangBaboySharedPreferences.getInteger("triviaNumber", 0)], headingStyle);
        trivia.setAlignment(Align.center);
        trivia.setWrap(true);
        trivia.setWidth(Gdx.graphics.getWidth());
        trivia.setFontScale(0.9f, 0.9f);



        /*heading = new Label(String.valueOf(sipangBaboySharedPreferences.getInteger("highScore",0)), headingStyle);
        heading.setFontScale(1, 1);
        heading.setWidth(Gdx.graphics.getWidth() / 2);
        heading.setWrap(true);
        heading.setAlignment(Align.center);*/
        scrollPane = new ScrollPane(trivia);


        //adding stuff to table to show
        //bgSprite
        table.add(heading);//creating heading
        table.getCell(heading).spaceBottom(100);
        table.row();
        table.add(scrollPane).width(Gdx.graphics.getWidth());//creating heading
        table.getCell(scrollPane).spaceBottom(100);
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