package com.uleau.gdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

import objects.Ax;
import objects.Ax.JUMP_STATE;
import objects.OilCan;
import objects.Log;
import objects.GroundTile;
import objects.Rock;

import util.CameraHelper;
import util.Constants;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.Game;
import screens.MenuScreen;

/**
 * @author Benjamin Uleau
 *
 */
public class WorldController extends InputAdapter{
	private static final String TAG=WorldController.class.getName();
	//public Sprite[] testSprites;
	//public int selectedSprite;
	public CameraHelper cameraHelper;
	private int selectedSprites;
	public Level level;
	public int lives;
	public int score;
	
	//Rectangles for collision detection
	private Rectangle r1=new Rectangle();
	private Rectangle r2=new Rectangle();
	
	private float timeLeftGameOverDelay;
	
	private Game game;
	
	private void initLevel(){
		score=0; 
		level=new Level(Constants.LEVEL_01);
		cameraHelper.setTarget(level.ax);
	}
	
	public WorldController(Game game){
		this.game=game;
		init();
	}
	
	private void init(){
		Gdx.input.setInputProcessor(this);
		cameraHelper=new CameraHelper();
		//initTestObjects();
		lives=Constants.LIVES_START;
		timeLeftGameOverDelay=0;
		initLevel();
	}
	
	@Override
	public boolean keyUp(int keycode){
		//Reset game world
		if(keycode==Keys.R){
			init();
			Gdx.app.debug(TAG,  "Game world resetted");
		}	
		//Toggle camera follow
		else if(keycode==Keys.ENTER){
			cameraHelper.setTarget(cameraHelper.hasTarget() ? null : level.ax);
			Gdx.app.debug(TAG, "Camera follow enabled: "+cameraHelper.hasTarget());
		}
		//Back to menu
		else if(keycode==Keys.ESCAPE || keycode==Keys.BACK){
			backToMenu();
		}
		return false;
	}
	
	private void handleInputGame(float deltaTime){
		if(cameraHelper.hasTarget(level.ax)){
			//Player Movement
			if(Gdx.input.isKeyPressed(Keys.LEFT)){
				level.ax.velocity.x=-level.ax.terminalVelocity.x;
			}else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
				level.ax.velocity.x=level.ax.terminalVelocity.x;
			}
		}else{
			//Execute auto-forward movement on non-desktop platform
			if(Gdx.app.getType()!=ApplicationType.Desktop){
				level.ax.velocity.x=level.ax.terminalVelocity.x;
			}
		}
		
		//Ax Jump
		if(Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)){
			level.ax.setJumping(true);
		}else{
			level.ax.setJumping(false);
		}
	}
	
	private Pixmap createProceduralPixmap(int width, int height){
		Pixmap pixmap=new Pixmap(width, height, Format.RGBA8888);
		//Fill square with red color at 50% opacity
		pixmap.setColor(1, 0, 0, 0.5f);
		pixmap.fill();
		//Draw a yellow-coored X shape on teh square
		pixmap.setColor(1, 1, 0, 1);
		pixmap.drawLine(0,  0,  width,  height);
		pixmap.drawLine(width,  0,  0,  height);
		
		//Draw a cyan-colored border around square
		pixmap.setColor(0, 1, 1, 1);
		pixmap.drawRectangle(0,  0,  width,  height);
		return pixmap;
	}
	
	public void update(float deltaTime){
		handleDebugInput(deltaTime);
		if(isGameOver()){
			timeLeftGameOverDelay-=deltaTime;
			if(timeLeftGameOverDelay<0) backToMenu();
		}else{
			handleInputGame(deltaTime);
		}
		level.update(deltaTime);
		testCollisions();
		cameraHelper.update(deltaTime);
		if(!isGameOver() && isPlayerInWater()){
			lives--;
			if(isGameOver())
				timeLeftGameOverDelay=Constants.TIME_DELAY_GAME_OVER;
			else
				initLevel();
		}
	}
	
	private void handleDebugInput(float deltaTime){
		if(Gdx.app.getType()!=ApplicationType.Desktop) return;

		if(!cameraHelper.hasTarget(level.ax)){
			//Camera Controls (move)
			float camMoveSpeed=5*deltaTime;
			float camMoveSpeedAccelerationFactor=5;
			
			if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
				camMoveSpeed*=camMoveSpeedAccelerationFactor;
			if(Gdx.input.isKeyPressed(Keys.LEFT))
				moveCamera(-camMoveSpeed, 0);
			if(Gdx.input.isKeyPressed(Keys.RIGHT))
				moveCamera(camMoveSpeed, 0);
			if(Gdx.input.isKeyPressed(Keys.UP))
				moveCamera(0, camMoveSpeed);
			if(Gdx.input.isKeyPressed(Keys.DOWN))
				moveCamera(0, -camMoveSpeed);
			if(Gdx.input.isKeyPressed(Keys.BACKSPACE))
				cameraHelper.setPosition(0, 0);
			
			//Camera Controls (zoom)
			float camZoomSpeed=1*deltaTime;
			float camZoomSpeedAccleerationFactor=5;
			if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
				camZoomSpeed*=camMoveSpeedAccelerationFactor;
			if(Gdx.input.isKeyPressed(Keys.COMMA))
				cameraHelper.addZoom(camZoomSpeed);
			if(Gdx.input.isKeyPressed(Keys.PERIOD))
				cameraHelper.addZoom(-camZoomSpeed);
			if(Gdx.input.isKeyPressed(Keys.SLASH))
				cameraHelper.setZoom(1);
		}
	}
	
	private void moveCamera(float x, float y){
		x+=cameraHelper.getPosition().x;
		y+=cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}
	
	private void onCollisionAxWithGround(GroundTile ground){
		Ax ax=level.ax;
		float heightDifference=Math.abs(ax.position.y-(ground.position.y+ground.bounds.height));
		if(heightDifference > 0.25f){
			boolean hitRightEdge=ax.position.x>(ground.position.x+ground.bounds.width/2.0f);
			if(hitRightEdge)
				ax.position.x=ground.position.x+ground.bounds.width;
			else
				ax.position.x=ground.position.x-ax.bounds.width;
			return;
		}
		
		switch(ax.jumpState){
		case GROUNDED:
			break;
		case FALLING:
		case JUMP_FALLING:
			ax.position.y=ground.position.y+ax.bounds.height+ax.origin.y;
			ax.jumpState=JUMP_STATE.GROUNDED;
			break;
		case JUMP_RISING:
			ax.position.y=ground.position.y+ax.bounds.height+ax.origin.y;
			break;
		}
	}
	
	private void onCollisionAxWithLog(Log log){
		log.collected=true;
		score+=log.getScore();
		Gdx.app.log(TAG, "Log collected");
	}
	
	private void onCollisionAxWithOil(OilCan oil){
		oil.collected=true;
		score+=oil.getScore();
		level.ax.setOilPowerup(true);
		Gdx.app.log(TAG, "Oil collected");
	}
	
	private void onCollisionAxWithRock(Rock rock){
		rock.collected=true;
		score+=rock.getScore();
		Gdx.app.log(TAG, "Rock collected");
	}
	
	private void testCollisions(){
		r1.set(level.ax.position.x, level.ax.position.y, level.ax.bounds.width, level.ax.bounds.height);
		
		//Test collision: Ax <-> Ground
		for(GroundTile g : level.ground){
			r2.set(g.position.x, g.position.y, g.bounds.width, g.bounds.height);
			if(!r1.overlaps(r2)) continue;
			onCollisionAxWithGround(g);
			//IMPORTANT: must do all collisions for alid edge testing on rocks
		}
		
		//Test collision: Ax <-> Logs
		for(Log log : level.logs){
			if(log.collected) continue;
			
			r2.set(log.position.x, log.position.y, log.bounds.width, log.bounds.height);
			if(!r1.overlaps(r2)) continue;
			onCollisionAxWithLog(log);
			break;
		}
		
		//Text collision: Ax <-> OilCans
		for(OilCan oil : level.oils){
			if(oil.collected) continue;
			r2.set(oil.position.x, oil.position.y, oil.bounds.width, oil.bounds.height);
			if(!r1.overlaps(r2)) continue;
			onCollisionAxWithOil(oil);
		}
		
		//Test collision: Ax <-> Rocks
		for(Rock rock : level.rocks){
			if(rock.collected) continue;
			r2.set(rock.position.x, rock.position.y, rock.bounds.width, rock.bounds.height);
			if(!r1.overlaps(r2)) continue;
			onCollisionAxWithRock(rock);
		}
	}
	
	public boolean isGameOver(){
		return lives<0;
	}
	
	public boolean isPlayerInWater(){
		return level.ax.position.y<-5;
	}
	
	private void backToMenu(){
		//Switch to menu screen
		game.setScreen(new MenuScreen(game));
	}
}