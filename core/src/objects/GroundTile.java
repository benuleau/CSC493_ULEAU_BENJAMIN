package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.uleau.gdx.game.AbstractGameObject;
import com.uleau.gdx.game.Assets;

public class GroundTile extends AbstractGameObject{
	private TextureRegion regTile;
	private int length;
	
	public GroundTile(){
		init();
	}
	
	private void init () {
		dimension.set(1, 1.5f);
		regTile=Assets.instance.ground.ground;
		// Start length of this rock
		setLength(1);
	}
	
	public void setLength (int length) {
		this.length = length;
	}
	
	public void increaseLength (int amount) {
		setLength(length + amount);
	}
	
	@Override
	public void render(SpriteBatch batch){
		TextureRegion reg=null;
		float relX=0;
		float relY=0;
		
		//Draw tile
		relX = 0;
		reg = regTile;
		for (int i = 0; i < length; i++) {
			batch.draw(reg.getTexture(), position.x + relX, position.y+ relY, origin.x, origin.y, dimension.x, dimension.y,scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),reg.getRegionWidth(), reg.getRegionHeight(), false, false);
			relX += dimension.x;
		}
	}
}
