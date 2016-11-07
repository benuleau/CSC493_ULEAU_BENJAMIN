package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.uleau.gdx.game.WorldController;
import com.uleau.gdx.game.WorldRenderer;
import util.GamePreferences;

public class GameScreen extends AbstractGameScreen{
	private static final String TAG=GameScreen.class.getName();
	
	private WorldController worldController;
	private WorldRenderer worldRenderer;
	
	private boolean paused;
	
	public GameScreen(Game game){
		super(game);
	}
	
	@Override
	public void render(float deltaTime){
		//Do not update game world when paused
		if(!paused){
			//Update the game world by the time that has passed since last rendered frame
			worldController.update(deltaTime);
		}
		
		//Sets the clear screen color to: Cornflower Blue
		Gdx.gl.glClearColor(0x64 / 255.0f,  0x95 / 255.0f, 0xed / 255.0f, 0xff / 255.0f);
		//Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Render the game world to the screen
		worldRenderer.render();
	}
	
	@Override
	public void resize(int width, int height){
		worldRenderer.resize(width,  height);;
	}
	
	@Override
	public void show(){
		GamePreferences.instance.load();
		worldController=new WorldController(game);
		worldRenderer=new WorldRenderer(worldController);
		Gdx.input.setCatchBackKey(true);
	}
	
	@Override
	public void hide(){
		worldController.dispose();
		worldRenderer.dispose();
		Gdx.input.setCatchBackKey(false);
	}
	
	@Override
	public void pause(){
		paused=true;
	}
	
	@Override
	public void resume(){
		super.resume();
		//Only called on android
		paused=false;
	}	
}
