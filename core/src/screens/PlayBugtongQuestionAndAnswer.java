package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.Random;

/**
 * Created by MrTechno on 1/18/2016.
 */
public class PlayBugtongQuestionAndAnswer implements Screen {
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

    public Music correct, wrong;
    Random r = new Random();
    private int arrayToken,tester = 0,gameStateBugtong = 0;

    Preferences sipangBaboySharedPreferences = Gdx.app.getPreferences("sipangBaboySharedPreferences");

    String [][] arrayBugtongAtSagot = new String[][]{
            {"Isang balong malalim, punong-puno ng patalim.", "bibig", "ilong","taenga","lalamunan"},//bibig
            {"Dalawang batong maitim, malayo ang dinarating.", "gulong", "yoyo","bala","mata"},//mata
            {"Dalawang balon, hindi malingon.", "bibig", "ilong","taenga","lalamunan"},//taenga
            {"Naligo ang kapitan, hindi nabasa ang tiyan.", "banko", "banka","bangketa","bibig"},//banka
            {"Dalawa kong kahon, buksan walang ugong.", "mata", "ilong","taenga","bibig"},//mata
            {"Limang puno ng niyog, isa'y matayog.", "ngipin", "katawan","daliri","gubat"},//daliri
            {"Isang reyna, nakabaligtad ang korona", "kasoy", "duhat","bayabas","dalandan"},//bayabas
            {"Nang hatakin ko ang baging, nagkagulo ang mga matsing.", "kampana", "lampara","paputok","alarma"},//kampana
            {"Nakatalikod na ang prinsesa, mukha niya'y nakaharap pa.", "kasoy", "balimbing","bayabas","pinya"},//balimbing
            {"Isang reynang maraming mata nasa gitna ang mga espada.", "kasoy", "balimbing","bayabas","pinya"},//pinya
            {"Heto na si Ingkong nakaupo sa lusong.", "kasoy", "balimbing","bayabas","pinya"},//kasoy
            {"May isang prinsesa nakaupo sa tasa.", "kasoy", "balimbing","atis","pinya"},//kasoy
            {"Ate mo, ate ko, Ate ng lahat ng tao.", "kasoy", "balimbing","atis","pinya"},//atis
            {"Nagbibigay na'y sinasakal pa.", "bote", "faucet","traffic","pluma"},//bote
            {"Lumuluha walang mata lumalakad walang paa.", "bote", "faucet","traffic","pluma"},//bote
            {"Ano ang Pambansang bulaklak?", "Sampaguita", "Rosas","Santan","Gumamela"},//Sampaguita
            {"Ano ang Pambansang hayop?", "Baka", "Kalabaw","Tamaraw","Agila"},//Kalabaw
            {"Ano ang Pambansang dahon?", "Oregano", "Santan","Anahaw","Saging"},//Anahaw
            {"Ano ang Pambansang Isda ng Pilipinas?", "Bangus", "Tilapia","Dilis","Pandaca"},//Bangus
            {"Ano ang pambansang ibon ng Pilipinas?", "Vulture", "Parot","Haribon","Kalapati"},//Haribon
            {"Ano ang pambansang wika ng Pilipinas?", "Cebuano", "Filipino","Visayan","Bicolano"},//Filipino
            {"Sino ang unang presidente ng Pilipinas?", "Ferdinand Marcos", "Joseph Estrada","Emilio Aguinaldo","Gloria Arroyo"},//Emilio Aguinaldo
            {"Ano ang pambansang laro ng Pilipinas?", "Sipa", "Soccer","Basketball","Boxing"},//Sipa
            {"Ano ang pambansang puno ng Pilipinas?", "Atis", "Saging","Akasya","Narra"},//Narra
            {"Ano ang pambansang awit ng Pilipinas?", "Lupang Hinirang", "Lupa","Bayan Ko","Kalayaan"}//Lupang Hinirang
    };
    String [] arraySagot = new String[]{
            "bibig",
            "mata",
            "taenga",
            "banka",
            "mata",
            "daliri",
            "bayabas",
            "kampana",
            "balimbing",
            "pinya",
            "kasoy",
            "kasoy",
            "atis",
            "bote",
            "pluma",
            "Sampaguita",
            "Kalabaw",
            "Anahaw",
            "Bangus",
            "Haribon",
            "Filipino",
            "Emilio Aguinaldo",
            "Sipa",
            "Narra",
            "Lupang Hinirang"
    };
    String [] arrayBugtongTrivia = new String[]{
            "Ang bibig ay para sa tao at ang bunganga naman ay para sa hayop at gamit na walang buhay. re: bunganga ng bulkan.",
            "Ang isang ordinaryong tao ay kumukurapt ng 12 na beses sa isang minuto. Halos 10,000 kada araw",
            "Anong pag dinig ay hindi tumitigil kahit natutulog. Binabalewala lang ng ating utak ang naririnig ng taenga pag tayo ay tulog",
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
            "Filipino ang pambansang wika at isa sa mga opisyal na wika ng Pilipinas.  Noong 2007, ang wikang Filipino ay ang unang wika ng 28 milyon na tao, o mahigit kumulang isang-katlo ng populasyon ng Pilipinas.",
            "Si Emilio Aguinaldo y Famy (22 Marso 1869–6 Pebrero 1964) ay isang Pilipinong heneral, politiko at pinuno ng kalayaan, ay ang unang Pangulo ng Republika ng Pilipinas (20 Enero 1899–1 Abril 1901). Isa siyang bayaning nakibaka para sa kasarinlan ng Pilipinas.",
            "Ito ay nilalaro sa pamamaraang pag sala ng sipa. Maaari saluhin sa paa, tuhod, ulo, siko o braso. Hindi maaari na gamitin ang kamay sa paglalaro ng sipa.",
            "Ang Naga o mas kilala sa tawag na Narra (Pterocarpus indicus), na Pambansang Puno ng Pilipinas, ay isang puno na pinahahalagahan dahil sa angkin nitong tibay, bigat at magandang kalidad.",
            "“Marcha Nacional Filipina”, Pambansang Awit ng Pilipinas, ay isang marcha na binuo ni Julian Felipe at unang pinatugtog sa Araw ng Kalayaan sa Kawit, Cavite noong 12 Hunyo 1898."

    };

    public boolean checker(int counter, String answer){
        Gdx.app.log("sagot" , answer);
        if(arraySagot[counter].equals(answer)){
            return true;
        }else{
            return false;
        }
    }
    //create
    @Override
    public void show() {

        correct = Gdx.audio.newMusic(Gdx.files.internal("correct.mp3"));
        wrong = Gdx.audio.newMusic(Gdx.files.internal("wrong.mp3"));

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

        arrayToken = r.nextInt(arrayBugtongTrivia.length);

        buttonOption1 = new TextButton(arrayBugtongAtSagot[arrayToken][1], textButtonStyle);
        buttonOption1.getLabel().setFontScale(1, 1);
        buttonOption1.pad(100);

        buttonOption2 = new TextButton(arrayBugtongAtSagot[arrayToken][2], textButtonStyle);
        buttonOption2.getLabel().setFontScale(1, 1);
        buttonOption2.pad(100);

        buttonOption3 = new TextButton(arrayBugtongAtSagot[arrayToken][3], textButtonStyle);
        buttonOption3.getLabel().setFontScale(1, 1);
        buttonOption3.pad(100);

        buttonOption4 = new TextButton(arrayBugtongAtSagot[arrayToken][4], textButtonStyle);
        buttonOption4.getLabel().setFontScale(1, 1);
        buttonOption4.pad(100);

        buttonOption1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int spglobalscore = sipangBaboySharedPreferences.getInteger("globalScore", 0);
                Gdx.app.log("label", String.valueOf(buttonOption1.getText()));
                if(checker(arrayToken, String.valueOf(buttonOption1.getText())) == true){
                    Gdx.app.log("Answer", String.valueOf(buttonOption1.getText()) + " is correct! " + String.valueOf(arrayToken));
                    spglobalscore++;
                    sipangBaboySharedPreferences.putInteger("globalScore", spglobalscore);
                    sipangBaboySharedPreferences.putInteger("triviaNumber", arrayToken);
                    sipangBaboySharedPreferences.putInteger("highScore",sipangBaboySharedPreferences.getInteger("temporaryScore",0)+1);
                    sipangBaboySharedPreferences.flush();
                    correct.play();
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Trivia());
                }else{
                    Gdx.app.log("Answer", String.valueOf(buttonOption1.getText()) + " is wrong! " + String.valueOf(arrayToken));
                    sipangBaboySharedPreferences.putInteger("globalScore", 0);
                    sipangBaboySharedPreferences.putInteger("triviaNumber", 0);
                    sipangBaboySharedPreferences.flush();
                    wrong.play();
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOver());
                }
            }
        });
        buttonOption2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("label", String.valueOf(buttonOption2.getText()));
                int spglobalscore = sipangBaboySharedPreferences.getInteger("globalScore", 0);
                if(checker(arrayToken, String.valueOf(buttonOption2.getText())) == true){
                    Gdx.app.log("Answer", String.valueOf(buttonOption2.getText()) + " is correct! " + String.valueOf(arrayToken));
                    spglobalscore++;
                    sipangBaboySharedPreferences.putInteger("globalScore", spglobalscore);
                    sipangBaboySharedPreferences.putInteger("triviaNumber", arrayToken);
                    sipangBaboySharedPreferences.putInteger("highScore",sipangBaboySharedPreferences.getInteger("highScore",0)+1);
                    sipangBaboySharedPreferences.flush();
                    correct.play();
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Trivia());
                }else{
                    Gdx.app.log("Answer", String.valueOf(buttonOption2.getText()) + " is wrong! " + String.valueOf(arrayToken));
                    sipangBaboySharedPreferences.putInteger("globalScore", 0);
                    sipangBaboySharedPreferences.putInteger("triviaNumber", 0);
                    sipangBaboySharedPreferences.flush();
                    wrong.play();
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOver());
                }
            }
        });
        buttonOption3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("label", String.valueOf(buttonOption3.getText()));
                int spglobalscore = sipangBaboySharedPreferences.getInteger("globalScore", 0);
                if(checker(arrayToken, String.valueOf(buttonOption3.getText())) == true){
                    Gdx.app.log("Answer", String.valueOf(buttonOption3.getText()) + " is correct! " + String.valueOf(arrayToken));
                    spglobalscore++;
                    sipangBaboySharedPreferences.putInteger("globalScore", spglobalscore);
                    sipangBaboySharedPreferences.putInteger("triviaNumber", arrayToken);
                    sipangBaboySharedPreferences.putInteger("highScore",sipangBaboySharedPreferences.getInteger("highScore",0)+1);
                    sipangBaboySharedPreferences.flush();
                    correct.play();
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Trivia());
                }else{
                    Gdx.app.log("Answer", String.valueOf(buttonOption3.getText()) + " is wrong! " + String.valueOf(arrayToken));
                    sipangBaboySharedPreferences.putInteger("globalScore", 0);
                    sipangBaboySharedPreferences.putInteger("triviaNumber", 0);
                    sipangBaboySharedPreferences.flush();
                    wrong.play();
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOver());
                }
            }
        });
        buttonOption4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("label", String.valueOf(buttonOption4.getText()));
                int spglobalscore = sipangBaboySharedPreferences.getInteger("globalScore", 0);
                if(checker(arrayToken, String.valueOf(buttonOption4.getText())) == true){
                    Gdx.app.log("Answer", String.valueOf(buttonOption4.getText()) + " is correct! " + String.valueOf(arrayToken));
                    spglobalscore++;
                    sipangBaboySharedPreferences.putInteger("globalScore", spglobalscore);
                    sipangBaboySharedPreferences.putInteger("triviaNumber", arrayToken);
                    sipangBaboySharedPreferences.putInteger("highScore",sipangBaboySharedPreferences.getInteger("highScore",0)+1);
                    sipangBaboySharedPreferences.flush();
                    correct.play();
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new Trivia());
                }else{
                    Gdx.app.log("Answer", String.valueOf(buttonOption4.getText()) + " is wrong! " + String.valueOf(arrayToken));
                    sipangBaboySharedPreferences.putInteger("globalScore", 0);
                    sipangBaboySharedPreferences.putInteger("triviaNumber", 0);
                    sipangBaboySharedPreferences.flush();
                    wrong.play();
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new GameOver());
                }
            }
        });


        Label.LabelStyle headingStyle = new Label.LabelStyle(blackOnWhitex, Color.WHITE);

        int x = sipangBaboySharedPreferences.getInteger("globalScore", 0) + 1;

        heading = new Label("Tanong " + x, headingStyle);
        heading.setFontScale(1, 1);
        bugtong = new Label(arrayBugtongAtSagot[arrayToken][0], headingStyle);
        bugtong.setAlignment(Align.center);
        bugtong.setWrap(true);
        bugtong.setWidth(Gdx.graphics.getWidth());
        bugtong.setFontScale(1, 1);
        sagot = new Label("Bugtong", headingStyle);
        sagot.setFontScale(1, 1);
        trivia = new Label("Bugtong", headingStyle);
        trivia.setFontScale(1, 1);


        Gdx.app.log("global counter bugtong", String.valueOf(sipangBaboySharedPreferences.getInteger("globalScore", 0)));
        Gdx.app.log("trivia counterx bugtong", String.valueOf(sipangBaboySharedPreferences.getInteger("triviaNumber", 0)));
        //adding stuff to table to show
        //bgSprite
        table.add(heading);//creating heading
        table.getCell(heading).spaceBottom(100);
        table.row();
        table.add(bugtong).width(Gdx.graphics.getWidth());//creating heading
        table.getCell(bugtong).spaceBottom(100);
        table.row();
        table.add(buttonOption1).width(Gdx.graphics.getWidth() / 3).height(Gdx.graphics.getHeight() / 9);//creating play button
        table.row();
        table.add(buttonOption2).width(Gdx.graphics.getWidth() / 3).height(Gdx.graphics.getHeight() / 9);//creating exit button
        table.row();
        table.add(buttonOption3).width(Gdx.graphics.getWidth() / 3).height(Gdx.graphics.getHeight() / 9);//creating play button
        table.row();
        table.add(buttonOption4).width(Gdx.graphics.getWidth() / 3).height(Gdx.graphics.getHeight() / 9);//creating exit button
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
        correct.dispose();
        wrong.dispose();
    }
}
