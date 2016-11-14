package com.uleau.gdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import objects.Cloud;
import objects.Tree;
import objects.GroundTile;
import objects.Water;
import objects.Ax;
import objects.OilCan;
import objects.Log;
import objects.Rock;
import objects.Carrot;
import objects.Goal;

public class Level {
	public static final String TAG=Level.class.getName();
	
	//objects
	public Array<GroundTile> ground;
		
	public Ax ax;
	public Array<Log> logs;
	public Array<OilCan> oils;
	public Array<Rock> rocks;
	public Array<Carrot> carrots;
	public Goal goal;
	
	//Decoration
	public Cloud clouds;
	public Tree tree;
	public Water water;
	
	public enum BLOCK_TYPE{
		EMPTY(0, 255, 255), //cyan
		GROUND(0, 0, 0), //black
		PLAYER_SPAWNPOINT(255, 255, 255), //white
		ITEM_ROCK(255, 0, 0), //red
		ITEM_OIL(0, 255, 0), //green
		ITEM_LOG(255, 255, 0), //yellow
		GOAL(255, 0, 255); //violet
		
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
		ax=null;
		
		//objects
		ground=new Array<GroundTile>();
		
		logs=new Array<Log>();
		oils=new Array<OilCan>();
		rocks=new Array<Rock>();
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
				//Ground
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
					obj=new Ax();
					offsetHeight=-3.0f;
					obj.position.set(pixelX, baseHeight*obj.dimension.y+offsetHeight);
					ax=(Ax)obj;
				}
				//Feather
				else if(BLOCK_TYPE.ITEM_OIL.sameColor(currentPixel)){
					obj=new OilCan();
					offsetHeight=-1.5f;
					obj.position.set(pixelX, baseHeight*obj.dimension.y+offsetHeight);
					oils.add((OilCan)obj);
				}
				//Gold coin
				else if(BLOCK_TYPE.ITEM_LOG.sameColor(currentPixel)){
					obj=new Log();
					offsetHeight=-1.5f;
					obj.position.set(pixelX, baseHeight*obj.dimension.y+offsetHeight);
					logs.add((Log)obj);
				}
				//Rock
				else if(BLOCK_TYPE.ITEM_ROCK.sameColor(currentPixel)){
					obj=new Rock();
					offsetHeight=-1.5f;
					obj.position.set(pixelX, baseHeight*obj.dimension.y+offsetHeight);
				}
				//Goal
				else if(BLOCK_TYPE.GOAL.sameColor(currentPixel)){
					obj=new Goal();
					offsetHeight=-7.0f;
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
		//Draw mountains
		tree.render(batch);
		
		//Draw goal
		goal.render(batch);
		
		//Draw Ground tiles
		for(GroundTile g : ground){
			g.render(batch);
		}
		
		//Draw Logs
		for(Log log : logs){
			log.render(batch);
		}
				
		//Draw Oils
		for(OilCan oil : oils){
			oil.render(batch);
		}
				
		//Draw Ax
		ax.render(batch);
		
		//Draw rocks
		for(Rock rock : rocks){
			rock.render(batch);
		}
		
		//Draw Carrots
		for(Carrot carrot : carrots){
			carrot.render(batch);
		}
				
		//Draw water overlay
		water.render(batch);
		
		
		
		//Draw clouds
		//clouds.render(batch);;
	}
	
	public void update(float deltaTime){
		ax.update(deltaTime);
		for(GroundTile g : ground)
			g.update(deltaTime);
		for(Log log : logs)
			log.update(deltaTime);
		for(OilCan oil : oils)
			oil.update(deltaTime);
		for(Rock rock : rocks)
			rock.update(deltaTime);
		for(Carrot carrot : carrots)
			carrot.update(deltaTime);
		clouds.update(deltaTime);
	}
}