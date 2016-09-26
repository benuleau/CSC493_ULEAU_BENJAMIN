package com.uleau.gdx.game;

import util.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;


/**
 * @author Benjamin Uleau
 *
 */
public class Assets implements Disposable, AssetErrorListener{
	public static final String TAG=Assets.class.getName();
	public static final Assets instance=new Assets();
	private AssetManager assetManager;
	
	//singleton: prevent instantiation from other classes
	private Assets(){}
	
	/*public AssetBunny bunny;
	public AssetRock rock;
	public AssetGoldCoin goldCoin;
	public AssetFeather feather;
	public AssetLevelDecoration levelDecoration;*/
	
	public AssetAx ax;
	public AssetGround ground;
	public AssetLog log;
	public AssetOil oil;
	public AssetRock rock;
	public AssetLevelDecoration levelDecoration;
	
	public void init(AssetManager assetManager){
		this.assetManager=assetManager;
		
		//set asset manager error handler
		assetManager.setErrorListener(this);
		
		//load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		
		//Start loading assets and wait until finished
		assetManager.finishLoading();
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		
		for(String a : assetManager.getAssetNames()){
			Gdx.app.debug(TAG, "asset: " + a);
		}
		
		TextureAtlas atlas=assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
		
		//Enable texture filtering for pixel smoothing
		for(Texture t : atlas.getTextures()){
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		//Create game resource objects
		ax=new AssetAx(atlas);
		ground=new AssetGround(atlas);
		log=new AssetLog(atlas);
		oil=new AssetOil(atlas);
		rock=new AssetRock(atlas);
		levelDecoration=new AssetLevelDecoration(atlas);
		
	/*	rock=new AssetRock(atlas);
		goldCoin=new AssetGoldCoin(atlas);
		feather=new AssetFeather(atlas);
		levelDecoration=new AssetLevelDecoration(atlas);*/
	}
	

	@Override
	public void dispose() {
		assetManager.dispose();
	}
	
	public void error(String filename, Class type, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + filename + "'", (Exception)throwable);
	}

	@Override
	public void error(AssetDescriptor asset, Throwable throwable) {
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
	}

	
	public class AssetAx{
		public final AtlasRegion ax;
		
		public AssetAx(TextureAtlas atlas){
			ax=atlas.findRegion("Ax");
		}
	}
	
	public class AssetGround{
		public final AtlasRegion ground;
		
		public AssetGround(TextureAtlas atlas){
			ground=atlas.findRegion("GroundTile");
		}
	}
	
	public class AssetLog{
		public final AtlasRegion log;
		public final AtlasRegion logL;
		public final AtlasRegion logR;
		
		public AssetLog(TextureAtlas atlas){
			log=atlas.findRegion("Log");
			logL=atlas.findRegion("Log-left");
			logR=atlas.findRegion("Log-right");
		}
	}
	
	public class AssetOil{
		public final AtlasRegion oil;
		
		public AssetOil(TextureAtlas atlas){
			oil=atlas.findRegion("OilCan");
		}
	}
	
	public class AssetRock{
		public final AtlasRegion rock;
		
		public AssetRock(TextureAtlas atlas){
			rock=atlas.findRegion("Rock");
		}
	}
	
	public class AssetBunny{
		public final AtlasRegion head;
		
		public AssetBunny(TextureAtlas atlas){
			head=atlas.findRegion("bunny_head");
		}
	}
	
	public class AssetGoldCoin{
		public final AtlasRegion goldCoin;
		
		public AssetGoldCoin(TextureAtlas atlas){
			goldCoin=atlas.findRegion("item_gold_coin");
		}
	}
	
	public class AssetFeather{
		public final AtlasRegion feather;
		
		public AssetFeather(TextureAtlas atlas){
			feather=atlas.findRegion("item_feather");
		}
	}
	
	public class AssetLevelDecoration{
		public final AtlasRegion cloud;
		public final AtlasRegion treeL1;
		public final AtlasRegion treeL2;
		public final AtlasRegion treeL3;
		public final AtlasRegion water;
		
		public AssetLevelDecoration(TextureAtlas atlas){
			cloud=atlas.findRegion("cloud");
			treeL1=atlas.findRegion("Tree");
			treeL2=atlas.findRegion("Tree-Layer2");
			treeL3=atlas.findRegion("Tree-Layer3");
			water=atlas.findRegion("Water");
		}
	}
	
	/*public class AssetLevelDecoration{
		public final AtlasRegion cloud01;
		public final AtlasRegion cloud02;
		public final AtlasRegion cloud03;
		public final AtlasRegion mountainLeft;
		public final AtlasRegion mountainRight;
		public final AtlasRegion waterOverlay;
		
		public AssetLevelDecoration(TextureAtlas atlas){
			cloud01=atlas.findRegion("cloud01");
			cloud02=atlas.findRegion("cloud02");
			cloud03=atlas.findRegion("cloud03");
			mountainLeft=atlas.findRegion("mountain_left");
			mountainRight=atlas.findRegion("mountain_right");
			waterOverlay=atlas.findRegion("water_overlay");
		}
	}*/
}
