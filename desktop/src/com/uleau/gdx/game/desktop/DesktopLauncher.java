package com.uleau.gdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.uleau.gdx.game.UleauGdxGame;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;

public class DesktopLauncher {
	static boolean rebuildAtlas=false;
	static boolean drawDebugOutline=false;
	
	public static void main (String[] arg) {
		if(rebuildAtlas){
			Settings settings=new Settings();
			settings.maxWidth=1024;
			settings.maxHeight=1024;
			settings.duplicatePadding=false;
			//settings.debug=drawDebugOutline;
			//TexturePacker.process(settings, "assets-raw/images", "../core/assets/images", "mygame.pack");
			TexturePacker.process(settings, "assets-raw/images_ui", "../core/assets/images", "game-ui.pack");
		}
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=800;
		config.height=480;
		new LwjglApplication(new UleauGdxGame(), config);
	}
}
