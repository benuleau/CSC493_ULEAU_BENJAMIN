package com.uleau.gdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import objects.Clouds;
import objects.Mountains;
import objects.Rock;
import objects.WaterOverlay;
import objects.BunnyHead;
import objects.Feather;
import objects.GoldCoin;
import objects.Carrot;
import objects.Goal;

public class Level {
	public static final String TAG=Level.class.getName();
	
	//objects
	public Array<Rock> rocks;
		
	public BunnyHead bunnyHead;
	public Array<GoldCoin> goldcoins;
	public Array<Feather> feathers;
	public Array<Carrot> carrots;
	public Goal goal;
		
	//Decoration
	public Clouds clouds;
	public Mountains mountains;
	public WaterOverlay waterOverlay;
	
	public enum BLOCK_TYPE{
		EMPTY(0, 255, 255), //cyan
		ROCK(0, 0, 0), //black
		PLAYER_SPAWNPOINT(255, 255, 255), //white
		ITEM_FEATHER(255, 0, 0), //red
		ITEM_GOLD_COIN(255, 255, 0), //yellow
		GOAL(0, 255, 0); //green
		
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
	
	public Level(String filename){
		init(filename);
	}
	
	private void init(String filename){
		//Player character
		bunnyHead=null;
		
		//objects
		rocks=new Array<Rock>();
		goldcoins=new Array<GoldCoin>();
		feathers=new Array<Feather>();
		carrots=new Array<Carrot>();
		
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
				else if(BLOCK_TYPE.ROCK.sameColor(currentPixel)){
					if(lastPixel!=currentPixel){
						obj=new Rock();
						float heightIncreaseFactor=0.25f;
						offsetHeight=-2.5f;
						obj.position.set(pixelX, baseHeight*obj.dimension.y*heightIncreaseFactor+offsetHeight);
						rocks.add((Rock)obj);
					}else{
						rocks.get(rocks.size-1).increaseLength(1);;
					}
				}
				//player spawn point
				else if(BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel)){
					obj=new BunnyHead();
					offsetHeight=-3.0f;
					obj.position.set(pixelX, baseHeight*obj.dimension.y+offsetHeight);
					bunnyHead=(BunnyHead)obj;
				}
				//Feather
				else if(BLOCK_TYPE.ITEM_FEATHER.sameColor(currentPixel)){
					obj=new Feather();
					offsetHeight=-1.5f;
					obj.position.set(pixelX, baseHeight*obj.dimension.y+offsetHeight);
					feathers.add((Feather)obj);
				}
				//Gold coin
				else if(BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel)){
					obj=new GoldCoin();
					offsetHeight=-2.0f;
					obj.position.set(pixelX, baseHeight*obj.dimension.y+offsetHeight);
					goldcoins.add((GoldCoin)obj);
				}
				//Goal
				else if(BLOCK_TYPE.GOAL.sameColor(currentPixel)){
					obj=new Goal();
					offsetHeight=-13.5f;
					obj.position.set(pixelX, baseHeight+offsetHeight);
					goal=(Goal)obj;
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
		clouds=new Clouds(pixmap.getWidth());
		clouds.position.set(0, 2);
		mountains=new Mountains(pixmap.getWidth());
		mountains.position.set(-1, -1);
		waterOverlay=new WaterOverlay(pixmap.getWidth());
		waterOverlay.position.set(0, -3.75f);
		
		//Free memory
		pixmap.dispose();
		Gdx.app.debug(TAG,  "level '"+filename+"' loaded");
	}
	
	public void render(SpriteBatch batch){
		//Draw mountains
		mountains.render(batch);
		
		//Draw Goal
		goal.render(batch);
		
		//Draw rocks
		for(Rock rock : rocks){
			rock.render(batch);
		}
		
		//Draw Gold Coins
		for(GoldCoin goldCoin : goldcoins){
			goldCoin.render(batch);
		}
				
		//Draw Feathers
		for(Feather feather : feathers){
			feather.render(batch);
		}
		
		//Draw Carrots
		for(Carrot carrot : carrots)
			carrot.render(batch);
		
		//Draw Player Character
		bunnyHead.render(batch);
		
		//Draw water overlay
		waterOverlay.render(batch);
		
		//Draw clouds
		clouds.render(batch);
	}
	
	public void update(float deltaTime){
		bunnyHead.update(deltaTime);
		for(Rock rock : rocks)
			rock.update(deltaTime);
		for(GoldCoin goldCoin : goldcoins)
			goldCoin.update(deltaTime);
		for(Feather feather : feathers)
			feather.update(deltaTime);
		for(Carrot carrot : carrots)
			carrot.update(deltaTime);
		clouds.update(deltaTime);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
