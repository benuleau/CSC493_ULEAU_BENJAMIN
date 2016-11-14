package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.uleau.gdx.game.AbstractGameObject;
import com.uleau.gdx.game.Assets;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GroundTile extends AbstractGameObject {
	private TextureRegion regTile;
	private int length;

	private final float FLOAT_CYCLE_TIME = 2.0f;
	private final float FLOAT_AMPLITUDE = 0.25f;
	private float floatCycleTimeLeft;
	private boolean floatingDownwards;
	private Vector2 floatTargetPosition;

	public GroundTile() {
		init();
	}

	private void init() {
		dimension.set(1, 1.5f);
		regTile = Assets.instance.ground.ground;
		// Start length of this rock
		setLength(1);

		floatingDownwards = false;
		floatCycleTimeLeft = MathUtils.random(0, FLOAT_CYCLE_TIME / 2);
		floatTargetPosition = null;
	}

	public void setLength(int length) {
		this.length = length;
		bounds.set(0, 0, dimension.x * length, dimension.y);
	}

	public void increaseLength(int amount) {
		setLength(length + amount);
	}

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		float relX = 0;
		float relY = 0;

		// Draw tile
		relX = 0;
		reg = regTile;
		for (int i = 0; i < length; i++) {
			batch.draw(reg.getTexture(), position.x + relX, position.y + relY, origin.x, origin.y, dimension.x,
					dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(),
					reg.getRegionHeight(), false, false);
			relX += dimension.x;
		}
	}

	/*@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		floatCycleTimeLeft -= deltaTime;
		if (floatTargetPosition == null)
			floatTargetPosition = new Vector2(position);
		if (floatCycleTimeLeft <= 0) {
			floatCycleTimeLeft = FLOAT_CYCLE_TIME;
			floatingDownwards = !floatingDownwards;
			floatTargetPosition.y += FLOAT_AMPLITUDE * (floatingDownwards ? -1 : 1);
		}
		position.lerp(floatTargetPosition, deltaTime);
	}*/
	
	@Override
	public void update(float deltaTime){
		super.update(deltaTime);;
		
		floatCycleTimeLeft-=deltaTime;
		if(floatCycleTimeLeft<=0){
			floatCycleTimeLeft-=FLOAT_CYCLE_TIME;
			floatingDownwards=!floatingDownwards;
			body.setLinearVelocity(0, FLOAT_AMPLITUDE*(floatingDownwards ? -1 : 1));
		}else{
			body.setLinearVelocity(body.getLinearVelocity().scl(0.98f));
		}
	}
}
