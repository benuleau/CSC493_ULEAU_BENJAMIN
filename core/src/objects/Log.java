package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.uleau.gdx.game.AbstractGameObject;
import com.uleau.gdx.game.Assets;
import com.badlogic.gdx.math.MathUtils;

public class Log extends AbstractGameObject {
	private TextureRegion regLog;
	public boolean collected;

	public Log() {
		init();
	}

	private void init() {
		dimension.set(0.5f, 0.5f);

		setAnimation(Assets.instance.log.animLog);
		stateTime = MathUtils.random(0.0f, 1.0f);

		regLog = Assets.instance.log.log;

		// Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);

		collected = false;
	}

	public void render(SpriteBatch batch) {
		if (collected)
			return;

		float relX = 0;
		// float relY = -6;
		float relY = 0;

		TextureRegion reg = null;
		// reg=regLog;
		reg = animation.getKeyFrame(stateTime, true);

		// batch.draw(reg.getTexture(), position.x+relX,
		// position.y+relY,origin.x, origin.y, dimension.x, dimension.y,
		// scale.x, scale.y,rotation, reg.getRegionX(),
		// reg.getRegionY(),reg.getRegionWidth(), reg.getRegionHeight(), false,
		// false);
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,
				scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				false, false);
	}

	public int getScore() {
		return 100;
	}
}
