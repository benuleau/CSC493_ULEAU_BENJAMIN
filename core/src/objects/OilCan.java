package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.uleau.gdx.game.AbstractGameObject;
import com.uleau.gdx.game.Assets;

public class OilCan extends AbstractGameObject{
	private TextureRegion regOil;
	public boolean collected;
	
	public OilCan(){
		init();
	}
	
	private void init(){
		dimension.set(0.5f, 0.5f);
		
		
		setAnimation(Assets.instance.oil.animOil);
		stateTime=MathUtils.random(0.0f, 1.0f);
		
		regOil=Assets.instance.oil.oil;
		
		//Set bounding box for collision detection		
		bounds.set(0, 0, dimension.x, dimension.y);
		
		collected=false;
	}
	
	public void render(SpriteBatch batch){
		if(collected) return;
		
		float relX=0;
		//float relY=-5.5f;
		float relY=0;
		
		TextureRegion reg=null;
		reg=animation.getKeyFrame(stateTime, true);
		
		batch.draw(reg.getTexture(), position.x+relX, position.y+relY, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,rotation, reg.getRegionX(), reg.getRegionY(),reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y, dimension.x, dimension.y,
				scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				false, false);
	}
	
	public int getScore(){
		return 250;
	}
}
