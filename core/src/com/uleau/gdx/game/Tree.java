package com.uleau.gdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class Tree extends AbstractGameObject{
	private TextureRegion regTreeL1;
	private TextureRegion regTreeL2;
	private TextureRegion regTreeL3;
	private int length;
	
	public Tree(int length){
		this.length=length;
		init();
	}
	
	private void init(){
		dimension.set(10, 2);
		regTreeL1=Assets.instance.levelDecoration.treeL1;
		regTreeL2=Assets.instance.levelDecoration.treeL2;
		regTreeL3=Assets.instance.levelDecoration.treeL3;
	}
	
	private void drawTrees (SpriteBatch batch, float offsetX, float offsetY) {
		TextureRegion reg = null;
		float xRel = dimension.x * offsetX;
		float yRel = dimension.y * offsetY;
		// Trees span the whole level
		int treeLength = 0;
		treeLength += MathUtils.ceil(length / (2 * dimension.x));
		treeLength += MathUtils.ceil(0.5f + offsetX);
		for (int i = 0; i < treeLength; i++) {
			// Trees layer1
			reg = regTreeL1;
			batch.draw(reg.getTexture(), origin.x + xRel, position.y +origin.y + yRel, origin.x, origin.y, dimension.x, dimension.y,scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),reg.getRegionWidth(), reg.getRegionHeight(), false, false);
			xRel += dimension.x;
			
			//Trees layer2
			reg = regTreeL2;
			batch.draw(reg.getTexture(),origin.x + xRel, position.y +origin.y + yRel, origin.x, origin.y, dimension.x, dimension.y,scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),reg.getRegionWidth(), reg.getRegionHeight(), false, false);
			xRel += dimension.x;
			
			//Trees layer2
			reg = regTreeL3;
			batch.draw(reg.getTexture(),origin.x + xRel, position.y +origin.y + yRel, origin.x, origin.y, dimension.x, dimension.y,scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),reg.getRegionWidth(), reg.getRegionHeight(), false, false);
			xRel += dimension.x;
		}
	}
	
	public void render(SpriteBatch batch){
		drawTrees(batch, 0.15f, 0.15f);
		//Draw layer1		
		drawTrees(batch, 0.5f, 0.5f);
		//Draw layer2
		drawTrees(batch, 0.75f, 0.75f);
	}
}
