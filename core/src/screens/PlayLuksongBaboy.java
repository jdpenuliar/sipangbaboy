package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by MrTechno on 1/19/2016.
 */
public class PlayLuksongBaboy implements Screen {SpriteBatch batch;
    Texture background, topTube, bottomTube,gameover;
    Texture[] birds;
    ShapeRenderer shapeRenderer;
    int flapState = 0;
    float velocity = 0;
    Circle birdCircle;
    int gameState = 0;
    float birdY = 0;
    float gravity = 1;
    float gap = 320;
    float maxTubeOffset;
    Random randomGenerator;
    float tubeVelocity = 4;
    int numberOfTubes = 10;
    float[] tubeX = new float[numberOfTubes];
    float[] tubeOffset = new float[numberOfTubes];
    float distanceBetweenTubes;
    Rectangle[] topTubeRectangles;
    Rectangle[] bottomTubeRectangles;
    int score = 0;
    int ts;
    int scoringTube = 0;
    float loopingFloat = -0.2f;
    int tapState = 0;

    BitmapFont font;
    Preferences sipangBaboySharedPreferences = Gdx.app.getPreferences("sipangBaboySharedPreferences");

    public void startGame(){
        //birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() /2;
        //birdY = birds[0].getHeight() /2;
        for(int i=0; i <numberOfTubes; i++){
            loopingFloat += 0.1;
            //randomGenerator.nextFloat() * 0.3f;
            tubeOffset[i] = ((randomGenerator.nextFloat() * -0.2f) -0.4f)*(Gdx.graphics.getHeight() - gap - 200);
            tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() +i * distanceBetweenTubes;
            //topTubeRectangles[i] = new Rectangle();
            bottomTubeRectangles[i] = new Rectangle();
        }
    }

    @Override
    public void show() {
        sipangBaboySharedPreferences.getInteger("globalScore", 0);
        sipangBaboySharedPreferences.getInteger("triviaNumber", 0);
        Gdx.app.log("global counter luksong baboy", String.valueOf(sipangBaboySharedPreferences.getInteger("globalScore", 0)));
        Gdx.app.log("trivia counter luksong baboy", String.valueOf( sipangBaboySharedPreferences.getInteger("triviaNumber", 0)));
        batch = new SpriteBatch();
        background = new Texture("sipagame.png");
        topTube = new Texture("topTubeWood.jpg");
        bottomTube = new Texture("bottomTubePig.jpg");
        birds = new Texture[2];
        birds[0] = new Texture("asukaFalling.png");
        birds[1] = new Texture("asukaJump.png");
        maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
        randomGenerator = new Random();
        //tubeX = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2;
        distanceBetweenTubes = Gdx.graphics.getWidth() * 3/ 4;
        //topTubeRectangles = new Rectangle [numberOfTubes];
        bottomTubeRectangles = new Rectangle [numberOfTubes];

        shapeRenderer = new ShapeRenderer();
        birdCircle = new Circle();

        font = new BitmapFont();
        font.setColor(Color.BLACK);
        font.getData().setScale(4);


        startGame();
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (gameState == 1){
            if(tubeX[scoringTube] < Gdx.graphics.getWidth()/2){

                score++;
                Gdx.app.log("Score: ", String.valueOf(score));
                if(scoringTube < numberOfTubes - 1){
                    scoringTube++;
                }else{
                    scoringTube = 0;
                }

                if((score == 9 && sipangBaboySharedPreferences.getInteger("globalScore",0) == 5)||(score == 6 && sipangBaboySharedPreferences.getInteger("globalScore",0) == 4)||(score == 3 && sipangBaboySharedPreferences.getInteger("globalScore",0) == 3)){
                    ((Game)Gdx.app.getApplicationListener()).setScreen(new PlayBugtongQuestionAndAnswer());
                }
            }
            /*if(Gdx.input.justTouched()){
                velocity = -15;
            }*/

            for(int i=0; i <numberOfTubes; i++){
                loopingFloat+=0.1;
                if(tubeX[i] < - topTube.getWidth()){
                    tubeX[i] += numberOfTubes * distanceBetweenTubes;
                    //tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);
                    tubeOffset[i] = ((randomGenerator.nextFloat() * -0.2f) -0.4f)*(Gdx.graphics.getHeight() - gap - 200);
                }else{
                    tubeX[i] = tubeX[i] - tubeVelocity;
                }

                //batch.draw(topTube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);
                batch.draw(bottomTube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);

                //topTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(),topTube.getHeight());
                bottomTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(),bottomTube.getHeight());

            }


            Gdx.app.log("tapstateout", String.valueOf(tapState));
            if(birdY > 0 ){
                //gravity. Velocity is the speed which is then lessed to the birdY which creates the effect of gravity
                velocity = velocity + gravity;
                birdY -= velocity;

                if(tapState == 0 || tapState == 1){
                    if(Gdx.input.justTouched()){

                        if(flapState == 0){
                            flapState = 1;
                        }else{
                            flapState = 0;
                        }

                        velocity = -16;
                        velocity = velocity + gravity;
                        birdY -= velocity;
                        Gdx.app.log("tapstatex", String.valueOf(tapState));
                        tapState++;
                    }
                }
            }else if(birdY <= 0){
                tapState = 0;
                if(tapState == 0 || tapState == 1){
                    if(Gdx.input.justTouched()){

                        if(flapState == 0){
                            flapState = 1;
                        }else{
                            flapState = 0;
                        }

                        velocity = -16;
                        velocity = velocity + gravity;
                        birdY -= velocity;
                        Gdx.app.log("tapstatexz", String.valueOf(tapState));
                        Gdx.app.log("ground", "ground");
                        tapState++;
                    }
                }

            }else {

                //tapState = 0;
                //gameState = 2;
            }

        }else if(gameState == 0){
            if(Gdx.input.justTouched()){
                gameState = 1;
            }
        }else if(gameState == 2){
            //batch.draw(gameover, Gdx.graphics.getWidth() / 2 - gameover.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameover.getHeight() / 2);
            if(Gdx.input.justTouched()){
                gameState = 1;
                startGame();
                score = 0;
                scoringTube = 0;
                velocity = 0;
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

        /*if(Gdx.input.justTouched()){
            if(flapState == 0){
                flapState = 1;
            }else{
                flapState = 0;
            }
        }*/

        batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2, birdY);
        font.draw(batch, String.valueOf(score), 100, Gdx.graphics.getHeight()-150);
        batch.end();


        birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + birds[flapState].getHeight() / 2, birds[flapState].getWidth() / 2);

        //font.draw(batch, String.valueOf(score), 100, 200);
		/*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);*/

        for(int i=0; i <numberOfTubes; i++){
			/*shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(),topTube.getHeight());
			shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());
			*/
            //if(Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle,bottomTubeRectangles[i])){
            if(Intersector.overlaps(birdCircle,bottomTubeRectangles[i])){
                //Gdx.app.log("collision", "ahh");
                Gdx.app.log("score", Integer.toString(score));
                Gdx.app.log("highscore", Integer.toString(sipangBaboySharedPreferences.getInteger("highScore",0)));
                /*if(score > sipangBaboySharedPreferences.getInteger("highScore",0)){
                    sipangBaboySharedPreferences.putInteger("highScore",score);
                    sipangBaboySharedPreferences.flush();
                }*/
                ts = sipangBaboySharedPreferences.getInteger("temoraryScore",0);
                ts += 1;
                sipangBaboySharedPreferences.putInteger("temporaryScore", ts);

                sipangBaboySharedPreferences.flush();
                Gdx.app.log("temporaryScore", Integer.toString(sipangBaboySharedPreferences.getInteger("temporaryScore", 0)));
                if(sipangBaboySharedPreferences.getInteger("temporaryScore",0) > sipangBaboySharedPreferences.getInteger("highScore",0)){
                    sipangBaboySharedPreferences.putInteger("highScore",sipangBaboySharedPreferences.getInteger("temporaryScore",0));
                    //sipangBaboySharedPreferences.putInteger("highScore",0);
                    sipangBaboySharedPreferences.flush();
                }
                gameState = 2;
            }
        }
        //shapeRenderer.end();
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
    }
}
