package com.uleau.gdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Water extends AbstractGameObject{
	private TextureRegion regWater;
	private float length;
	
	public Water(float length){
		this.length=length;
		init();
	}
	
	private void init(){
		dimension.set(length*10, 3);
		regWater=Assets.instance.levelDecoration.water;
		
		origin.x=-dimension.x/2;
	}
	
	@Override
	public void render(SpriteBatch batch){
		TextureRegion reg=null;
		reg=regWater;
		batch.draw(reg.getTexture(), position.x + origin.x, position.y+ origin.y, origin.x, origin.y, dimension.x, dimension.y, scale.x,scale.y, rotation, reg.getRegionX(), reg.getRegionY(),reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}
}
