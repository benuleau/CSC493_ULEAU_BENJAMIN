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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;

/**
 * @author Benjamin Uleau
 *
 */
public class Assets implements Disposable, AssetErrorListener{
	public static final String TAG=Assets.class.getName();
	public static final Assets instance=new Assets();
	private AssetManager assetManager;
	
	public AssetFonts fonts;
	
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
	public AssetSounds sounds;
	public AssetMusic music;
	
	public void init(AssetManager assetManager){
		this.assetManager=assetManager;
		
		//set asset manager error handler
		assetManager.setErrorListener(this);
		
		//load texture atlas
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		
		//Load sounds
		assetManager.load(Constants.JUMP, Sound.class);
		assetManager.load(Constants.JUMP_WITH_OIL, Sound.class);
		assetManager.load(Constants.PICKUP_LOG, Sound.class);
		assetManager.load(Constants.PICKUP_OIL, Sound.class);
		assetManager.load(Constants.LIVE_LOST, Sound.class);
		
		//Load music
		assetManager.load(Constants.SONG01, Music.class);
		
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
		fonts=new AssetFonts();
		ax=new AssetAx(atlas);
		ground=new AssetGround(atlas);
		log=new AssetLog(atlas);
		oil=new AssetOil(atlas);
		rock=new AssetRock(atlas);
		levelDecoration=new AssetLevelDecoration(atlas);
		sounds=new AssetSounds(assetManager);
		music=new AssetMusic(assetManager);
		
	/*	rock=new AssetRock(atlas);
		goldCoin=new AssetGoldCoin(atlas);
		feather=new AssetFeather(atlas);
		levelDecoration=new AssetLevelDecoration(atlas);*/
	}
	

	@Override
	public void dispose() {
		assetManager.dispose();
		fonts.defaultSmall.dispose();
		fonts.defaultNormal.dispose();
		fonts.defaultBig.dispose();
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
		public Animation animLog;
		
		public AssetLog(TextureAtlas atlas){
			log=atlas.findRegion("Log");
			logL=atlas.findRegion("Log-left");
			logR=atlas.findRegion("Log-right");
			
			//Animation: Log
			Array<AtlasRegion> regions=atlas.findRegions("anim_log");
			AtlasRegion region=regions.first();
			for(int i=0; i<10; i++){
				regions.insert(0,  region);
				animLog=new Animation(1.0f/20.0f, regions, Animation.PlayMode.LOOP);
			}
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
		public final AtlasRegion carrot;
		public final AtlasRegion goal;
		
		public AssetLevelDecoration(TextureAtlas atlas){
			cloud=atlas.findRegion("cloud");
			treeL1=atlas.findRegion("Tree");
			treeL2=atlas.findRegion("Tree-Layer2");
			treeL3=atlas.findRegion("Tree-Layer3");
			water=atlas.findRegion("Water");
			carrot=atlas.findRegion("carrot");
			goal=atlas.findRegion("goal");
		}
	}
	
	public class AssetFonts {
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;
		public AssetFonts () {
			// create three fonts using Libgdx's 15px bitmap font
		
			
			/***********************
			 * RELATIVE CLASSPATHS *
			 ***********************/
			/*
			defaultSmall = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			defaultNormal = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			defaultBig = new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			*/
			
			
			/***********************
			 * ABSOLUTE CLASSPATHS *
			 ***********************/
			
			defaultSmall=new BitmapFont(Gdx.files.internal("/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/images/arial-15.fnt"), true);
			defaultNormal=new BitmapFont(Gdx.files.internal("/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/images/arial-15.fnt"), true);
			defaultBig=new BitmapFont(Gdx.files.internal("/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/images/arial-15.fnt"), true);
			

			// set font sizes
			defaultSmall.getData().setScale(0.75f);
			defaultNormal.getData().setScale(1.0f);
			defaultBig.getData().setScale(2.0f);
			
			// enable linear texture filtering for smooth fonts
			defaultSmall.getRegion().getTexture().setFilter(
			TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture().setFilter(
			TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture().setFilter(
			TextureFilter.Linear, TextureFilter.Linear);
		}
	}
	
	public class AssetSounds{
		public final Sound jump;
		public final Sound jumpWithOil;
		public final Sound pickupLog;
		public final Sound pickupOil;
		public final Sound liveLost;
		
		public AssetSounds(AssetManager am){
			jump=am.get(Constants.JUMP, Sound.class);
			jumpWithOil=am.get(Constants.JUMP_WITH_OIL, Sound.class);
			pickupLog=am.get(Constants.PICKUP_LOG, Sound.class);
			pickupOil=am.get(Constants.PICKUP_OIL, Sound.class);
			liveLost=am.get(Constants.LIVE_LOST, Sound.class);
		}
	}
	
	public class AssetMusic{
		public final Music song01;
		public AssetMusic(AssetManager am){
			song01=am.get(Constants.SONG01, Music.class);
		}
	}
}
