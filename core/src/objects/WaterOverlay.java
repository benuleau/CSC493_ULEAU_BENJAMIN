package objects;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.uleau.gdx.game.AbstractGameObject;
import com.uleau.gdx.game.Assets;

public class WaterOverlay extends AbstractGameObject{
	private TextureRegion regWaterOverlay;
	private float length;
	
	public WaterOverlay(float length){
		this.length=length;
		init();
	}
	
	private void init(){
		dimension.set(length*10, 3);
		regWaterOverlay=Assets.instance.levelDecoration.waterOverlay;
		
		origin.x=-dimension.x/2;
	}
	
	@Override
	public void render(SpriteBatch batch){
		float relX=0;
		float relY=5;
		TextureRegion reg=null;
		reg=regWaterOverlay;
		batch.draw(reg.getTexture(), position.x + origin.x+relX, position.y+relY+ origin.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,scale.y, rotation, reg.getRegionX(), reg.getRegionY(),reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}
}
