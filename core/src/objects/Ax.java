package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.uleau.gdx.game.AbstractGameObject;
import com.uleau.gdx.game.Assets;
import util.Constants;
import util.AudioManager;
import util.CharacterSkin;
import util.GamePreferences;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

public class Ax extends AbstractGameObject{
	public static final String TAG=Ax.class.getName();
	private final float JUMP_TIME_MAX=0.3f;
	private final float JUMP_TIME_MIN=0.1f;
	private final float JUMP_TIME_OFFSET_FLYING=JUMP_TIME_MAX - 0.018f;
	
	public enum VIEW_DIRECTION{LEFT, RIGHT}
	public enum JUMP_STATE{GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING}
	
	private TextureRegion regAx;
	public VIEW_DIRECTION viewDirection;
	public float timeJumping;
	public JUMP_STATE jumpState;
	public boolean hasOilPowerup;
	public float timeLeftOilPowerup;
	
	public ParticleEffect dustParticles=new ParticleEffect();
	
	public Ax(){
		init();
	}
	
	public void init () {
		dimension.set(1, 1);
		regAx = Assets.instance.ax.ax;
		
		// Center image on game object
		origin.set(dimension.x / 2, dimension.y / 2);
		
		// Bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		
		// Set physics values
		terminalVelocity.set(3.0f, 4.0f);
		friction.set(12.0f, 0.0f);
		acceleration.set(0.0f, -25.0f);
		
		// View direction
		viewDirection = VIEW_DIRECTION.RIGHT;
		
		// Jump state
		jumpState = JUMP_STATE.FALLING;
		timeJumping = 0;
		
		// Power-ups
		hasOilPowerup = false;
		timeLeftOilPowerup = 0;
		
		//Particles
		//dustParticles.load(Gdx.files.internal("particles/particles.pafx"), Gdx.files.internal("particles"));
		dustParticles.load(Gdx.files.internal("/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/particles/particles.pafx"), Gdx.files.internal("/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/particles"));

	}
	
	public void setJumping (boolean jumpKeyPressed) {
		switch (jumpState) {
			case GROUNDED: // Character is standing on a platform
				if (jumpKeyPressed) {
					AudioManager.instance.play(Assets.instance.sounds.jump);
					// Start counting jump time from the beginning
					timeJumping = 0;
					jumpState = JUMP_STATE.JUMP_RISING;
				}
				break;
			case JUMP_RISING: // Rising in the air
				if (!jumpKeyPressed)
					jumpState = JUMP_STATE.JUMP_FALLING;
				break;
			case FALLING:// Falling down
			case JUMP_FALLING: // Falling down after jump
				if (jumpKeyPressed && hasOilPowerup) {
					AudioManager.instance.play(Assets.instance.sounds.jumpWithOil, 1, MathUtils.random(1.0f, 1.1f));
					timeJumping = JUMP_TIME_OFFSET_FLYING;
					jumpState = JUMP_STATE.JUMP_RISING;
				}
				break;
		}
	}
	
	public void setOilPowerup (boolean pickedUp) {
		hasOilPowerup = pickedUp;
		if (pickedUp) {
			timeLeftOilPowerup =Constants.ITEM_OIL_POWERUP_DURATION;
		}
	}
	
	public boolean hasFeatherPowerup () {
		return hasOilPowerup && timeLeftOilPowerup>0;
	}
	
	@Override
	public void update (float deltaTime) {
		super.update(deltaTime);
		if (velocity.x != 0) {
			viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
		}
		if (timeLeftOilPowerup > 0) {
			timeLeftOilPowerup -= deltaTime;
			if (timeLeftOilPowerup < 0) {
				// disable power-up
				timeLeftOilPowerup = 0;
				setOilPowerup(false);
			}
		}
		dustParticles.update(deltaTime);
	}
	
	@Override
	protected void updateMotionY (float deltaTime) {
		switch (jumpState) {
			case GROUNDED:
				jumpState = JUMP_STATE.FALLING;
				break;
			case JUMP_RISING:
				// Keep track of jump time
				timeJumping += deltaTime;
				// Jump time left?
				if (timeJumping <= JUMP_TIME_MAX) {
					// Still jumping
					velocity.y = terminalVelocity.y;
				}
				break;
			case FALLING:
				break;
			case JUMP_FALLING:
				// Add delta times to track jump time
				timeJumping += deltaTime;
				// Jump to minimal height if jump key was pressed too short
				if (timeJumping > 0 && timeJumping <= JUMP_TIME_MIN) {
					// Still jumping
					velocity.y = terminalVelocity.y;
				}
		}
		if (jumpState != JUMP_STATE.GROUNDED){
			dustParticles.allowCompletion();
			super.updateMotionY(deltaTime);
		}
	}
	
	@Override
	public void render (SpriteBatch batch) {
		TextureRegion reg = null;
		
		//Draw particles
		dustParticles.draw(batch);
		
		//Apply skin color
		batch.setColor(CharacterSkin.values()[GamePreferences.instance.charSkin].getColor());
		
		// Set special color when game object has a feather power-up
		float relX = 0;
		//float relY = -4.5f;
		float relY=0;
		
		if (hasOilPowerup) {
			batch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
		}
		
		// Draw image
		reg = regAx;
		batch.draw(reg.getTexture(), position.x+relX, position.y+relY, origin.x,origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),reg.getRegionHeight(), viewDirection == VIEW_DIRECTION.LEFT,false);
		
		// Reset color to white
		batch.setColor(1, 1, 1, 1);
	}
}
