package com.uleau.gdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import objects.Cloud;
import objects.GroundTile;
import objects.Tree;
import objects.Water;

public class Level {
	public static final String TAG=Level.class.getName();
	
	public enum BLOCK_TYPE{
		EMPTY(0, 255, 255), //cyan
		GROUND(0, 0, 0), //black
		PLAYER_SPAWNPOINT(255, 255, 255), //white
		ITEM_LOG(255, 255, 0), //yellow
		ITEM_OIL(0, 255, 0), //green
		ITEM_ROCK(255, 0, 0); //red
		
		private int color;
		
		private BLOCK_TYPE(int r, int g, int b){
			color=r<<24 | g<<16 | b<<8 | 0xff;
		}
		
		public boolean sameColor(int color){
			return this.color==color;
		}
		
		public int getColor(){
			return color;
		}
	}
	
	//objects
	public Array<GroundTile> ground;
	
	//Decoration
	public Cloud clouds;
	public Tree tree;
	public Water water;
	
	public Level(String filename){
		init(filename);
	}
	
	private void init(String filename){
		//objects
		ground=new Array<GroundTile>();
		
		//load image file that represents the level data
		Pixmap pixmap=new Pixmap(Gdx.files.internal(filename));
		//scan pixels from top-left to bottom-right
		int lastPixel=-1;
		
		for(int pixelY=0; pixelY<pixmap.getHeight(); pixelY++){
			for(int pixelX=0; pixelX<pixmap.getWidth(); pixelX++){
				AbstractGameObject obj=null;
				float offsetHeight=0;
				
				//Height grows from bottom to top
				float baseHeight=pixmap.getHeight()-pixelY;
				
				//Get the color of the current pixel as a 32 bit RGBA value
				int currentPixel=pixmap.getPixel(pixelX,  pixelY);
				//Find matching color falues to identify block type at (x, y) point and create the corresponding game object if there is a match
				
				//empty space
				if(BLOCK_TYPE.EMPTY.sameColor(currentPixel)){
					//do nothing
				}
				//rock
				else if(BLOCK_TYPE.GROUND.sameColor(currentPixel)){
					if(lastPixel!=currentPixel){
						obj=new GroundTile();
						float heightIncreaseFactor=0.25f;
						offsetHeight=-2.5f;
						obj.position.set(pixelX, baseHeight*obj.dimension.y*heightIncreaseFactor+offsetHeight);
						ground.add((GroundTile)obj);
					}else{
						ground.get(ground.size-1).increaseLength(1);;
					}
				}
				//player spawn point
				else if(BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)){
					
				}
				//Log
				else if(BLOCK_TYPE.ITEM_LOG.sameColor(currentPixel)){
					
				}
				//Rock
				else if(BLOCK_TYPE.ITEM_ROCK.sameColor(currentPixel)){
					
				}
				//Oil
				else if(BLOCK_TYPE.ITEM_OIL.sameColor(currentPixel)){
					
				}
				//Unknown object/pixel color
				else{
					int r=0xff & (currentPixel>>>24); //Red color channel
					int g=0xff & (currentPixel>>>16); //Green color channel
					int b=0xff & (currentPixel>>>8); //Green color channel
					int a=0xff & currentPixel; //Green color channel
					
					Gdx.app.error(TAG, "Unknown object at (X, Y) (R, G, B, A) ("+pixelX+","+pixelY+") ("+r+","+g+","+b+","+a+")");
				}
				lastPixel=currentPixel;
			}
		}
		
		//decoration
		clouds=new Cloud();
		clouds.setLength(pixmap.getWidth());
		
		clouds.position.set(0, 2);
		tree=new Tree(pixmap.getWidth());
		tree.position.set(-1, -1);
		water=new Water(pixmap.getWidth());
		water.position.set(0, -3.75f);
		
		//Free memory
		pixmap.dispose();
		Gdx.app.debug(TAG,  "level '"+filename+"' loaded");
	}
	
	public void render(SpriteBatch batch){
		//Draw Trees
		tree.render(batch);
		
		//Draw ground
		for(GroundTile ground : ground){
			ground.render(batch);
		}
		
		//Draw water overlay
		water.render(batch);
		
		
		//Draw clouds
		//clouds.render(batch);;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
