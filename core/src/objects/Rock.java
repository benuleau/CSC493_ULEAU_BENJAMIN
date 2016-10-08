package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.uleau.gdx.game.AbstractGameObject;
import com.uleau.gdx.game.Assets;

public class Rock extends AbstractGameObject{
	private TextureRegion regRock;
	public boolean collected;
	
	public Rock(){
		init();
	}
	
	private void init(){
		dimension.set(0.5f, 0.5f);
		regRock=Assets.instance.rock.rock;
		
		//Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		
		collected=false;
	}
	
	public void render(SpriteBatch batch){
		if(collected) return;
		
		float relX=0;
		float relY=0;
		
		TextureRegion reg=null;
		reg=regRock;
		
		batch.draw(reg.getTexture(), position.x+relX, position.y+relY, origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y,rotation, reg.getRegionX(), reg.getRegionY(),reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}
	
	public int getScore(){
		return -100;
	}
}
