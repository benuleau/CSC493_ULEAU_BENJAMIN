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
	
	public AssetBunny bunny;
	public AssetRock rock;
	public AssetGoldCoin goldCoin;
	public AssetFeather feather;
	public AssetLevelDecoration levelDecoration;
	
	public AssetFonts fonts;
	
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
		assetManager.load(Constants.JUMP_WITH_FEATHER, Sound.class);
		assetManager.load(Constants.PICKUP_COIN, Sound.class);
		assetManager.load(Constants.PICKUP_FEATHER, Sound.class);
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
		bunny=new AssetBunny(atlas);
		rock=new AssetRock(atlas);
		goldCoin=new AssetGoldCoin(atlas);
		feather=new AssetFeather(atlas);
		levelDecoration=new AssetLevelDecoration(atlas);
		sounds=new AssetSounds(assetManager);
		music=new AssetMusic(assetManager);
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

	
	
	public class AssetBunny{
		public final AtlasRegion head;
		
		public AssetBunny(TextureAtlas atlas){
			head=atlas.findRegion("bunny_head");
		}
	}
	
	public class AssetRock{
		public final AtlasRegion edge;
		public final AtlasRegion middle;
		
		public AssetRock(TextureAtlas atlas){
			 edge=atlas.findRegion("rock_edge");
			 middle=atlas.findRegion("rock_middle");
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
	}
	
	public class AssetFonts{
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;
		
		public AssetFonts(){
			//Create three fonts using libgdx's 15px bitmap font
			
			/***********************
			 * RELATIVE CLASSPATHS *
			 ***********************/
			
			defaultSmall=new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			defaultNormal=new BitmapFont(Gdx.files.internal("images/arial-15.fnt"), true);
			defaultBig=new BitmapFont(Gdx.files.internal("Images/arial-15.fnt"), true);
			
			
			/***********************
			 * ABSOLUTE CLASSPATHS *
			 ***********************/
			/*
			defaultSmall=new BitmapFont(Gdx.files.internal("/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/images/arial-15.fnt"), true);
			defaultNormal=new BitmapFont(Gdx.files.internal("/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/images/arial-15.fnt"), true);
			defaultBig=new BitmapFont(Gdx.files.internal("/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/images/arial-15.fnt"), true);
			*/

			
			//Set font sizes
			defaultSmall.getData().setScale(.75f);
			defaultNormal.getData().setScale(1.0f);
			defaultBig.getData().setScale(2.0f);
			
			//Enable linear texture filtering for smooth fonts
			defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			
		}
	}
	
	public class AssetSounds{
		public final Sound jump;
		public final Sound jumpWithFeather;
		public final Sound pickupCoin;
		public final Sound pickupFeather;
		public final Sound liveLost;
		
		public AssetSounds(AssetManager am){
			/***********************
			 * RELATIVE CLASSPATHS *
			 ***********************/
			
			jump=am.get("sounds/jump.wav", Sound.class);
			jumpWithFeather=am.get("sounds/jump_with_feather.wav", Sound.class);
			pickupCoin=am.get("sounds/pickup_coin.wav", Sound.class);
			pickupFeather=am.get("sounds/pickup_feather.wav", Sound.class);
			liveLost=am.get("sounds/live_lost.wav", Sound.class);
			
			
			/***********************
			 * ABSOLUTE CLASSPATHS *
			 ***********************/
			/*
			jump=am.get("/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/sounds/jump.wav", Sound.class);
			jumpWithFeather=am.get("/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/sounds/jump_with_feather.wav", Sound.class);
			pickupCoin=am.get("/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/sounds/pickup_coin.wav", Sound.class);
			pickupFeather=am.get("/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/sounds/pickup_feather.wav", Sound.class);
			liveLost=am.get("/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/sounds/live_lost.wav", Sound.class);
			*/
		}
	}
	
	public class AssetMusic{
		public final Music song01;
		
		public AssetMusic(AssetManager am){
			/***********************
			 * RELATIVE CLASSPATHS *
			 ***********************/
			song01=am.get("music/keith303_-_brand_new_highscore.mp3", Music.class);
			
			/***********************
			 * ABSOLUTE CLASSPATHS *
			 ***********************/
			//song01=am.get("/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/music/keith303_-_brand_new_highscore.mp3", Music.class);
		}
	}
}

















