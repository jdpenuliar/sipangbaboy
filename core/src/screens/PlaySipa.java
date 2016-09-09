package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import tween.SpriteAccessor;

/**
 * Created by MrTechno on 1/18/2016.
 */
public class PlaySipa implements Screen {
    SpriteBatch batch;
    Texture background,sipa;
    Texture[] asuka;
    int gameState = 0;
    float gravity = 1;
    float velocity = 0;
    float asukaY = 0;
    float asukaX = 0;
    float sipaX = 0;
    float sipaY = 0;
    int asukaStanceState = 0;
    int ts;
    boolean falling = true;
    Random randomGenerator;
    BitmapFont font;

    Preferences sipangBaboySharedPreferences = Gdx.app.getPreferences("sipangBaboySharedPreferences");


    ShapeRenderer shapeRenderer;
    Rectangle asukaRectangle,sipaRectangle;

    int sipaScore = 0;

    private TweenManager tweenManager;

    private Sprite splashSprite;
    private Texture splashTexture;


    public static class SimpleDirectionGestureDetector extends GestureDetector {
        public interface DirectionListener {
            void onLeft();

            void onRight();

            void onUp();

            void onDown();
        }

        public SimpleDirectionGestureDetector(DirectionListener directionListener) {
            super(new DirectionGestureListener(directionListener));
        }

        private static class DirectionGestureListener extends GestureAdapter{
            DirectionListener directionListener;

            public DirectionGestureListener(DirectionListener directionListener){
                this.directionListener = directionListener;
            }

            @Override
            public boolean fling(float velocityX, float velocityY, int button) {
                if(Math.abs(velocityX)>Math.abs(velocityY)){
                    if(velocityX>0){
                        directionListener.onRight();
                    }else{
                        directionListener.onLeft();
                    }
                }else{
                    if(velocityY>0){
                        directionListener.onDown();
                    }else{
                        directionListener.onUp();
                    }
                }
                return super.fling(velocityX, velocityY, button);
            }

        }

    }

    public void startGameSipa(){

        sipaX = Gdx.graphics.getWidth() / 2 - sipa.getWidth()/2;
        sipaY = Gdx.graphics.getHeight() / 2 - sipa.getHeight() / 2;
        asukaY = Gdx.graphics.getHeight() / 2 - asuka[0].getHeight() /2;
        asukaX = Gdx.graphics.getWidth() / 2 - asuka[0].getWidth() /2;
    }

    @Override
    public void show() {


        //creates the background, player and "sipa" sprite
        batch = new SpriteBatch();
        background = new Texture("sipagame.png");
        asuka = new Texture[3];
        asuka[0] = new Texture("asukaStand01.png");
        asuka[1] = new Texture("asukaWalk.png");
        asuka[2] = new Texture("asukaKick.png");
        sipa = new Texture("sipa.png");
        //first use is for the random placement of the sipa on the x axis
        randomGenerator = new Random();

        //initial position of the sipa when the game starts
        asukaX = Gdx.graphics.getWidth() / 2 - asuka[asukaStanceState].getWidth() / 2;

        //creates the font for creating the text for the score
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(4);

        //creates the shapes for collision detection but invisibly renders it at the end
        shapeRenderer = new ShapeRenderer();
        asukaRectangle = new Rectangle();
        sipaRectangle = new Rectangle();

        //detector for swipes such as right swipe and left swipe
        Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {

            @Override
            public void onUp() {
                // TODO Auto-generated method stub
            }

            @Override
            public void onRight() {
                // TODO Auto-generated method stub

                if (asukaX < Gdx.graphics.getWidth() - asuka[0].getWidth()) {
                    asukaX += asuka[asukaStanceState].getWidth() / 2 ;
                    Gdx.app.log("width max: ", String.valueOf(Gdx.graphics.getWidth()));
                } else {
                    Gdx.app.log("limit: ", "right");
                }

                if(asukaStanceState == 0){
                    asukaStanceState = 1;
                }else{
                    asukaStanceState = 0;
                }
                Gdx.app.log("touched: ", String.valueOf(asukaX));
            }

            @Override
            public void onLeft() {
                // TODO Auto-generated method stub

                if (asukaX > 0) {
                    asukaX -= asuka[asukaStanceState].getWidth() / 2;
                } else {
                    Gdx.app.log("limit: ", "Left");
                }
                Gdx.app.log("touched: ", String.valueOf(asukaX));
                if(asukaStanceState == 0){
                    asukaStanceState = 1;
                }else{
                    asukaStanceState = 0;
                }
            }

            @Override
            public void onDown() {
                // TODO Auto-generated method stub

            }
        }));
        startGameSipa();

        //motion tween
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        splashTexture = new Texture("sipangbaboysplashscreen.jpg");
        splashSprite = new Sprite(splashTexture);
        splashSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Tween.set(splashSprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Gdx.app.log("splash state", "Start");
        Tween.to(splashSprite, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1,2).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        }).start(tweenManager);
    }

    @Override
    public void render(float delta) {
        //sipangBaboySharedPreferences.putInteger("highScore",0);
        //sipangBaboySharedPreferences.flush();
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Game sates
        if(gameState == 0){
            //game over but will start again on touch(sipa)
            if((sipaScore == 7 && sipangBaboySharedPreferences.getInteger("globalScore",0) == 2)||(sipaScore == 5 && sipangBaboySharedPreferences.getInteger("globalScore",0) == 1)||(sipaScore == 3 && sipangBaboySharedPreferences.getInteger("globalScore",0) == 0)){
                if(Gdx.input.justTouched()){
                    ((Game)Gdx.app.getApplicationListener()).setScreen(new PlayBugtongQuestionAndAnswer());
                }
            }else{
                if(Gdx.input.justTouched()){
                    gameState = 1;
                }
            }
        }else if(gameState == 1){
            //game start on touch (sipa)
            if(Gdx.input.justTouched()){
                velocity = -20;
            }

            //Gameplay detection
            if(sipaY > 0){
                //sipa is not hitting the ground
                if(falling == true){
                    //velocity = velocity + gravity;
                    sipaY -= 10;
                    //Gdx.app.log("sipa: ", "going down");

                    //collision detection
                    if(Intersector.overlaps(asukaRectangle, sipaRectangle)){
                        if(sipaY <= asuka[0].getHeight()/3){
                            asukaStanceState = 2;
                            falling = false;
                            sipaScore++;
                            Gdx.app.log("score", Integer.toString(sipaScore));
                            Gdx.app.log("highscore", Integer.toString(sipangBaboySharedPreferences.getInteger("highScore",0)));
                            ts = sipangBaboySharedPreferences.getInteger("temporaryScore", 0);
                            ts += 1;
                            sipangBaboySharedPreferences.putInteger("temporaryScore", ts);
                            sipangBaboySharedPreferences.flush();
                            Gdx.app.log("temporaryScore", Integer.toString(sipangBaboySharedPreferences.getInteger("temporaryScore", 0)));
                            if(sipangBaboySharedPreferences.getInteger("temporaryScore",0) > sipangBaboySharedPreferences.getInteger("highScore",0)){
                                sipangBaboySharedPreferences.putInteger("highScore",sipangBaboySharedPreferences.getInteger("temporaryScore",0));
                                //sipangBaboySharedPreferences.putInteger("highScore",0);
                                sipangBaboySharedPreferences.flush();
                            }
                            if((sipaScore == 7 && sipangBaboySharedPreferences.getInteger("globalScore",0) == 2)||(sipaScore == 5 && sipangBaboySharedPreferences.getInteger("globalScore",0) == 1)||(sipaScore == 3 && sipangBaboySharedPreferences.getInteger("globalScore",0) == 0)){
                                /*if(sipaScore > sipangBaboySharedPreferences.getInteger("highScore",0)){
                                    //sipangBaboySharedPreferences.putInteger("highScore",sipaScore);
                                    sipangBaboySharedPreferences.putInteger("highScore",0);
                                    sipangBaboySharedPreferences.flush();
                                }*/
                                gameState = 0;
                            }
                        }
                    }
                }else if(falling == false){
                    if(sipaY >= Gdx.graphics.getHeight()/3){
                        asukaStanceState = 0;
                    }

                    if(sipaY >= Gdx.graphics.getHeight() + sipa.getHeight()){
                        falling = true;
                        sipaX = randomGenerator.nextInt(Gdx.graphics.getWidth() + 1);
                        if(sipaX >= 0 && sipaX <= sipa.getWidth()){
                            sipaX += 20;
                        }else if(sipaX >= Gdx.graphics.getWidth()- sipa.getWidth()){
                            sipaX -=20;
                        }else{

                        }
                        //Gdx.app.log("sipa: ", "peak");
                    }else{
                        sipaY += 20;
                    }
                }
            }else{
                gameState = 2;
            }
        }else if(gameState == 2){
            //game over but on touched it will go back to game over then start (sipa)
            if(Gdx.input.justTouched()){
                //gameState = 1;
                //startGameSipa();
                //sipaScore = 0;

                sipangBaboySharedPreferences.putInteger("globalScore", 0);
                sipangBaboySharedPreferences.putInteger("triviaNumber", 0);
                sipangBaboySharedPreferences.flush();
                //scoringTube = 0;
                //velocity = 0;

                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameOver());
            }
        }

        //Gdx.input.setInputProcessor();
        //asukaY = Gdx.graphics.getHeight() / 2 - asuka[0].getHeight() /2;
        batch.draw(asuka[asukaStanceState], asukaX, 0);
        batch.draw(sipa, sipaX, sipaY);

        font.draw(batch, String.valueOf(sipaScore), 100, Gdx.graphics.getHeight()-150);

        batch.end();



        asukaRectangle.set(asukaX, 0, asuka[asukaStanceState].getWidth(), asuka[asukaStanceState].getHeight());
        sipaRectangle.set(sipaX, sipaY, sipa.getWidth(), sipa.getHeight());

		/*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);


		shapeRenderer.rect(asukaX, 0, asuka[asukaStanceState].getWidth(), asuka[asukaStanceState].getHeight());
		shapeRenderer.rect(sipaX, sipaY, sipa.getWidth(), sipa.getHeight());
		shapeRenderer.end();*/
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

        batch.dispose();
        font.dispose();
        splashTexture.dispose();
    }
}
