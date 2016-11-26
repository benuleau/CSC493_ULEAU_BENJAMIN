package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.uleau.gdx.game.AbstractGameObject;
import com.uleau.gdx.game.Assets;

public class Goal extends AbstractGameObject {
	private TextureRegion regGoal;
	
	public Goal(){
		init();
	}
	
	private void init(){
		dimension.set(3.0f, 3.0f);
		regGoal=Assets.instance.levelDecoration.goal;
		
		//Set bounding box for collision detection
		bounds.set(1, Float.MIN_VALUE, 10, Float.MAX_VALUE);
		origin.set(dimension.x/2.0f, 0.0f);
	}
	
	public void render(SpriteBatch batch){
		TextureRegion reg=null;
		
		reg=regGoal;
		
		//The following variables are to test for a null pointer exception
		float px=position.x;
		float ox=origin.x;
		float py=position.y;
		float oy=origin.y;
		float dx=dimension.x;
		float dy=dimension.y;
		float sx=scale.x;
		float sy=scale.y;
		float rot=rotation;
		int regx=reg.getRegionX();
		int regy=reg.getRegionY();
		int regw=reg.getRegionWidth();
		int regh=reg.getRegionHeight();
		
		batch.draw(reg.getTexture(),
				px-ox,
				py-oy,
				ox,
				oy,
				dx,
				dy,
				sx,
				sy,
				rot,
				regx,
				regy,
				regw,
				regh,
				false,
				false
				);
		
		//batch.draw(reg.getTexture(), position.x - origin.x,position.y - origin.y, origin.x, origin.y, dimension.x,dimension.y, scale.x, scale.y, rotation,reg.getRegionX(),reg.getRegionY(),reg.getRegionWidth(), reg.getRegionHeight(),false, false);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}