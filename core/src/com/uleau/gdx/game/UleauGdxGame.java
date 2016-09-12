package com.uleau.gdx.game;

import game.WorldController;
import game.WorldRenderer;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UleauGdxGame implements ApplicationListener {
	private static final String TAG=UleauGdxGame.class.getName();
	
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	private boolean paused;
	
	@Override public void create(){
		//Set Libgdx log level to DEBUG
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		//Initialize controller and renderer
		worldController=new WorldController();
		worldRenderer=new WorldRenderer(worldController);
		//Game world is active on start
		paused=false;
	}
	@Override public void render(){
		//Don't update game world when paused
		if(!paused){
			//Update the game world by the time that has passed since the last rendered frame
			worldController.update(Gdx.graphics.getDeltaTime());
		}
		//Sets the clear screen color to: Cornflower Blue
		Gdx.gl.glClearColor(0x64/255.0f, 0x95/255.0f, 0xed/255.0f, 0xff/255.0f);
		//Clears the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Render game world to screen
		worldRenderer.render();
	}
	@Override public void resize(int width, int height){
		worldRenderer.resize(width, height);
	}
	@Override public void pause(){
		paused=true;
	}
	@Override public void resume(){
		paused=false;
	}
	@Override public void dispose(){
		worldRenderer.dispose();
	};
}
