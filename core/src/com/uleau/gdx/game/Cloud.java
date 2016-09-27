package com.uleau.gdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Cloud extends AbstractGameObject{
	private TextureRegion regCloud;
	private int length;
	
	public Cloud(){
		init();
	}
	
	private void init(){
		dimension.set(1, 1.5f);
		regCloud=Assets.instance.levelDecoration.cloud;
		setLength(1);
	}
	
	public void setLength(int length){
		this.length=length;
	}
	
	@Override
	public void render(SpriteBatch batch){
		TextureRegion reg=regCloud;
		batch.draw(reg.getTexture(), 
				position.x + origin.x,position.y + origin.y,
				origin.x, origin.y, 
				dimension.x,dimension.y, scale.x, scale.y, rotation, reg.getRegionX(),reg.getRegionY(), 
				reg.getRegionWidth(), reg.getRegionHeight(),false, false);
	}
}
