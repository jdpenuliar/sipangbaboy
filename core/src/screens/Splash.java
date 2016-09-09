package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import tween.SpriteAccessor;

/**
 * Created by MrTechno on 1/16/2016.
 */
public class Splash implements Screen {
    private SpriteBatch batch;
    private Sprite splashSprite;
    private Texture splashTexture;
    private TweenManager tweenManager;


    Preferences sipangBaboySharedPreferences = Gdx.app.getPreferences("sipangBaboySharedPreferences");
    //bgMusic
    //https://github.com/libgdx/libgdx/wiki/Universal-Tween-Engine

    @Override
    public void show() {

        batch = new SpriteBatch();
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());

        splashTexture = new Texture("sipangbaboysplashscreen.jpg");
        splashSprite = new Sprite(splashTexture);
        splashSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Tween.set(splashSprite, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        Gdx.app.log("splash state", "Start");
        Tween.to(splashSprite, SpriteAccessor.ALPHA, 1.5f).target(1).repeatYoyo(1,2).setCallback(new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
            }
        }).start(tweenManager);
        Gdx.app.log("splash state", "fade in and out");
    }

    @Override
    public void render(float delta) {
        //default must
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        batch.begin();
            splashSprite.draw(batch);
        batch.end();
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
        splashSprite.getTexture().dispose();
    }
}
