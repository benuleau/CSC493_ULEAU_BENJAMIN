package com.uleau.gdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

import com.badlogic.gdx.math.Rectangle;
import objects.BunnyHead;
import objects.BunnyHead.JUMP_STATE;
import objects.Feather;
import objects.GoldCoin;
import objects.Rock;

import util.CameraHelper;
import util.Constants;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.Game;
import screens.MenuScreen;

import util.AudioManager;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import objects.Carrot;
import com.badlogic.gdx.utils.Disposable;

/**
 * @author Benjamin Uleau
 *
 */
public class WorldController extends InputAdapter implements Disposable{
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
	
	float livesVisual;
	
	public float scoreVisual;
	
	private boolean goalReached;
	public World b2World;
	
	@Override
	public void dispose(){
		if(b2World!=null) b2World.dispose();
	}
	
	private void initLevel(){
		score=0; 
		scoreVisual=score;
		level=new Level(Constants.LEVEL_01);
		cameraHelper.setTarget(level.bunnyHead);
		initPhysics();
	}
	
	private void initPhysics(){
		if(b2World!=null) b2World.dispose();
		b2World=new World(new Vector2(0, -9.18f), true);
		//Rocks
		Vector2 origin=new Vector2();
		for(Rock rock : level.rocks){
			BodyDef bodyDef=new BodyDef();
			bodyDef.type=BodyType.KinematicBody;
			bodyDef.position.set(rock.position);
			Body body=b2World.createBody(bodyDef);
			rock.body=body;
			PolygonShape polygonShape=new PolygonShape();
			origin.x=rock.bounds.width/2.0f;
			origin.y=rock.bounds.height/2.0f;
			polygonShape.setAsBox(rock.bounds.width/2.0f,  rock.bounds.height/2.0f, origin, 0);
			FixtureDef fixtureDef=new FixtureDef();
			fixtureDef.shape=polygonShape;
			body.createFixture(fixtureDef);
			polygonShape.dispose();
		}
	}
	
	private void spawnCarrots(Vector2 pos, int numCarrots, float radius){
		float carrotShapeScale=0.5f;
		//Create carrots with box2d body and fixture
		for(int i=0; i<numCarrots; i++){
			Carrot carrot=new Carrot();
			//Calculate random spawn position, rotation, and scale
			float x=MathUtils.random(-radius, radius);
			float y=MathUtils.random(5.0f, 15.0f);
			float rotation=MathUtils.random(5.0f, 15.0f)*MathUtils.degreesToRadians;
			float carrotScale=MathUtils.random(5.0f, 1.5f);
			carrot.scale.set(carrotScale, carrotScale);
			
			//Create box2d body for carrot with start position and angle of rotation
			BodyDef bodyDef=new BodyDef();
			bodyDef.position.set(pos);
			bodyDef.position.add(x, y);
			bodyDef.angle=rotation;
			Body body=b2World.createBody(bodyDef);
			body.setType(BodyType.DynamicBody);
			carrot.body=body;
			
			//Create rectangular shape for carrot to allow interactions (collisions) with other objects
			PolygonShape polygonShape=new PolygonShape();
			float halfWidth=carrot.bounds.width/2.0f*carrotScale;
			float halfHeight=carrot.bounds.height/2.0f*carrotScale;
			polygonShape.setAsBox(halfWidth*carrotShapeScale,  halfHeight*carrotShapeScale);;
			
			//Set physics attributes
			FixtureDef fixtureDef=new FixtureDef();
			fixtureDef.shape=polygonShape;
			fixtureDef.density=50;
			fixtureDef.restitution=0.5f;
			fixtureDef.friction=0.5f;
			body.createFixture(fixtureDef);
			polygonShape.dispose();
			
			//Finally, add new carrot to list for updating / rendering
			level.carrots.add(carrot);
		}
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
		livesVisual=lives;
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
			cameraHelper.setTarget(cameraHelper.hasTarget() ? null : level.bunnyHead);
			Gdx.app.debug(TAG, "Camera follow enabled: "+cameraHelper.hasTarget());
		}
		//Back to menu
		else if(keycode==Keys.ESCAPE || keycode==Keys.BACK){
			backToMenu();
		}
		return false;
	}
	
	private void handleInputGame(float deltaTime){
		if(cameraHelper.hasTarget(level.bunnyHead)){
			//Player Movement
			if(Gdx.input.isKeyPressed(Keys.LEFT)){
				level.bunnyHead.velocity.x=-level.bunnyHead.terminalVelocity.x;
			}else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
				level.bunnyHead.velocity.x=level.bunnyHead.terminalVelocity.x;
			}
		}else{
			//Execute auto-forward movement on non-desktop platform
			if(Gdx.app.getType()!=ApplicationType.Desktop){
				level.bunnyHead.velocity.x=level.bunnyHead.terminalVelocity.x;
			}
		}
		
		//Bunny Jump
		if(Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE)){
			level.bunnyHead.setJumping(true);
		}else{
			level.bunnyHead.setJumping(false);
		}
	}
	
	private Pixmap createProceduralPixmap(int width, int height){
		Pixmap pixmap=new Pixmap(width, height, Format.RGBA8888);
		//Fill square with red color at 50% opacity
		pixmap.setColor(1, 0, 0, 0.5f);
		pixmap.fill();
		//Draw a yellow-colored X shape on the square
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
		if(isGameOver() || goalReached){
			timeLeftGameOverDelay-=deltaTime;
			if(timeLeftGameOverDelay<0) backToMenu();
		}else{
			handleInputGame(deltaTime);
		}
		level.update(deltaTime);
		testCollisions();
		b2World.step(deltaTime, 8, 3);
		
		cameraHelper.update(deltaTime);
		if(!isGameOver() && isPlayerInWater()){
			AudioManager.instance.play(Assets.instance.sounds.liveLost);
			lives--;
			if(isGameOver())
				timeLeftGameOverDelay=Constants.TIME_DELAY_GAME_OVER;
			else
				initLevel();
		}
		level.mountains.updateScrollPosition(cameraHelper.getPosition());
		if(livesVisual>lives)
			livesVisual=Math.max(lives,  livesVisual-1*deltaTime);
		if(scoreVisual<score){
			scoreVisual=Math.min(score, scoreVisual+250*deltaTime);
		}
	}
	
	private void handleDebugInput(float deltaTime){
		if(Gdx.app.getType()!=ApplicationType.Desktop) return;

		if(!cameraHelper.hasTarget(level.bunnyHead)){
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
	
	private void onCollisionBunnyHeadWithRock(Rock rock){
		BunnyHead bunnyHead=level.bunnyHead;
		float heightDifference=Math.abs(bunnyHead.position.y-(rock.position.y+rock.bounds.height));
		if(heightDifference > 0.25f){
			boolean hitRightEdge=bunnyHead.position.x>(rock.position.x+rock.bounds.width/2.0f);
			if(hitRightEdge)
				bunnyHead.position.x=rock.position.x+rock.bounds.width;
			else
				bunnyHead.position.x=rock.position.x-bunnyHead.bounds.width;
			return;
		}
		
		switch(bunnyHead.jumpState){
		case GROUNDED:
			break;
		case FALLING:
		case JUMP_FALLING:
			bunnyHead.position.y=rock.position.y+bunnyHead.bounds.height+bunnyHead.origin.y;
			bunnyHead.jumpState=JUMP_STATE.GROUNDED;
			break;
		case JUMP_RISING:
			bunnyHead.position.y=rock.position.y+bunnyHead.bounds.height+bunnyHead.origin.y;
			break;
		}
	}
	private void onCollisionBunnyWithGoldCoin(GoldCoin goldcoin){
		goldcoin.collected=true;
		AudioManager.instance.play(Assets.instance.sounds.pickupCoin);
		score+=goldcoin.getScore();
		Gdx.app.log(TAG, "Gold coin collected");
	}
	private void onCollisionBunnyWithFeather(Feather feather){
		feather.collected=true;
		AudioManager.instance.play(Assets.instance.sounds.pickupFeather);
		score+=feather.getScore();
		level.bunnyHead.setFeatherPowerup(true);
		Gdx.app.log(TAG, "Feather collected");
	}
	
	private void onCollisionBunnyWithGoal(){
		goalReached=true;
		timeLeftGameOverDelay=Constants.TIME_DELAY_GAME_FINISHED;
		Vector2 centerPosBunnyHead=new Vector2(level.bunnyHead.position);
		centerPosBunnyHead.x+=level.bunnyHead.bounds.width;
		spawnCarrots(centerPosBunnyHead, Constants.CARROTS_SPAWN_MAX, Constants.CARROTS_SPAWN_RADIUS);
	}
	
	private void testCollisions(){
		r1.set(level.bunnyHead.position.x, level.bunnyHead.position.y, level.bunnyHead.bounds.width, level.bunnyHead.bounds.height);
		
		//Test collision: Bunny Head <-> Rocks
		for(Rock rock : level.rocks){
			r2.set(rock.position.x, rock.position.y, rock.bounds.width, rock.bounds.height);
			if(!r1.overlaps(r2)) continue;
			onCollisionBunnyHeadWithRock(rock);
			//IMPORTANT: must do all collisions for alid edge testing on rocks
		}
		
		//Test collision: Bunny Head <-> Gold Coins
		for(GoldCoin goldcoin : level.goldcoins){
			if(goldcoin.collected) continue;
			
			r2.set(goldcoin.position.x, goldcoin.position.y, goldcoin.bounds.width, goldcoin.bounds.height);
			if(!r1.overlaps(r2)) continue;
			onCollisionBunnyWithGoldCoin(goldcoin);
			break;
		}
		
		//Text collision: Bunny Head <-> Feathers
		for(Feather feather : level.feathers){
			if(feather.collected) continue;
			r2.set(feather.position.x, feather.position.y, feather.bounds.width, feather.bounds.height);
			if(!r1.overlaps(r2)) continue;
			onCollisionBunnyWithFeather(feather);
		}
		
		//Test collision: Bunny Head <-> Goal
		if(!goalReached){
			r2.set(level.goal.bounds);
			r2.x+=level.goal.position.x;
			r2.y+=level.goal.position.y;
			if(r1.overlaps(r2)) onCollisionBunnyWithGoal();
		}
	}
	
	public boolean isGameOver(){
		return lives<0;
	}
	
	public boolean isPlayerInWater(){
		return level.bunnyHead.position.y<-5;
	}
	
	//BackToMenu function
		private void backToMenu ()
		{
			//Switch to menu screen
			game.setScreen(new MenuScreen(game));
		}
}
