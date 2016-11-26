package util;

/**
 * @author Benjamin Uleau
 *
 */
public class Constants {
	//Visible game world is 5 meters wide
	public static final float VIEWPORT_WIDTH=5.0f;
	
	//Visible game world is 5 meters tall
	public static final float VIEWPORT_HEIGHT=5.0f;
	
	//GUI width
	public static final float VIEWPORT_GUI_WIDTH=800.0f;
	//GUI Height
	public static final float VIEWPORT_GUI_HEIGHT=480.0f;
	
	//Location of description file for texture atlas
	
	//Location of image file for level 01

	//Amount of extra lives at level start
	public static final int LIVES_START=3;
	
	//Duration of the oil power-up in seconds
	public static final float ITEM_OIL_POWERUP_DURATION=9;
	
	//Delay after game over
	public static final float TIME_DELAY_GAME_OVER=3;
	
	public static final String PREFERENCES="mygame.preferences";
	
	//Number of carrots to spawn
	public static final int CARROTS_SPAWN_MAX=100;
	
	//Spawn radius for carrots
	public static final float CARROTS_SPAWN_RADIUS=3.5f;
	
	//Delay after game finished
	public static final float TIME_DELAY_GAME_FINISHED=6;
	
	
	
	/***********************
	 * RELATIVE CLASSPATHS *
	 ***********************/

	public static final String TEXTURE_ATLAS_OBJECTS="images/mygame.pack.atlas";
	
	public static final String LEVEL_01="levels/level-01.png";
	
	public static final String TEXTURE_ATLAS_UI="images/game-ui-pack.atlas";
	public static final String TEXTURE_ATLAS_LIBGDX_UI="images/uiskin.atlas";
	//Location of description for skins
	public static final String SKIN_LIBGDX_UI="images/uiskin.json";
	public static final String SKIN_MYGAME_UI="images/game-ui.json";
	
	//Sounds
	public static final String JUMP="sounds/jump.wav";
	public static final String JUMP_WITH_OIL="sounds/jump_with_feather.wav";
	public static final String PICKUP_LOG="sounds/pickup_coin.wav";
	public static final String PICKUP_OIL="sounds/pickup_feather.wav";
	public static final String LIVE_LOST="sounds/live_lost.wav";
	
	//Music
	public static final String SONG01="music/keith303_-_brand_new_highscore.mp3";
	
	
	
	/***********************
	 * ABSOLUTE CLASSPATHS *
	 ***********************/
/*
	public static final String TEXTURE_ATLAS_OBJECTS="/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/images/mygame.pack.atlas";
	
	public static final String LEVEL_01="/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/levels/level-01.png";

	public static final String TEXTURE_ATLAS_UI="/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/images/game-ui-pack.atlas";
	public static final String TEXTURE_ATLAS_LIBGDX_UI="/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/images/uiskin.atlas";
	//Location of description file for skins
	public static final String SKIN_LIBGDX_UI="/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/images/uiskin.json";
	public static final String SKIN_MYGAME_UI="/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/images/game-ui.json";
		
	//Sounds
	public static final String JUMP="/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/sound/jump.wav";
	public static final String JUMP_WITH_OIL="/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/sound/jump_with_oil.wav";
	public static final String PICKUP_LOG="/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/sound/pickup_log.wav";
	public static final String PICKUP_OIL="/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/sound/pickup_oil.wav";
	public static final String LIVE_LOST="/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/sound/live_lost.wav";
	//Music
	public static final String SONG01="/Users/benuleau/Desktop/School/JuniorS1/CSC493/CSC493_ULEAU_BENJAMIN/core/assets/music/keith303_-_brand_new_highscore.mp3";
	*/
}