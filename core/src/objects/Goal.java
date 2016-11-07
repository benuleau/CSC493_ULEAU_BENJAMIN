package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.uleau.gdx.game.Assets;
import com.uleau.gdx.game.AbstractGameObject;

public class Goal extends AbstractGameObject{
	private TextureRegion regGoal;
	
	public Goal(){
		init();
	}
	
	private void init(){
		dimension.set(3.0f, 3.0f);
		regGoal=Assets.instance.levelDecoration.goal;
		
		//Set bounding box for collision detection
		bounds.set(2, Float.MIN_VALUE, 10, Float.MAX_VALUE);
		origin.set(dimension.x/2.0f, 0.0f);
	}
	
	public void render(SpriteBatch batch){
		TextureRegion reg=null;
		
		float relX=0.0f;
		float relY=-6.5f;
		
		reg=regGoal;
		batch.draw(reg.getTexture(), position.x+relX, position.y+relY, origin.x, origin.y, dimension.x,dimension.y, scale.x, scale.y, rotation,reg.getRegionX(), reg.getRegionY(),reg.getRegionWidth(), reg.getRegionHeight(),false, false);
	}
}
