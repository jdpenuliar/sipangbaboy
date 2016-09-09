package com.dsolutions.sipangbaboy02;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import screens.Splash;

public class SipangBaboy02 extends Game {

	public Music bgMusic;
	@Override
	public void create () {
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("menu.mp3"));
		bgMusic.setLooping(true);
		bgMusic.setVolume(0.5f);
		bgMusic.play();


		setScreen(new Splash());
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose() {
		super.dispose();
		bgMusic.dispose();
	}


}
