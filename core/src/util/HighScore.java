package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;

public class HighScore {
	public static final String TG=GamePreferences.class.getName();
	public static final HighScore instance=new HighScore();
	
	//public int score;
	
	private Preferences prefs;
	
	//Singleton: Prevent instantiation from other classes
	private HighScore(){
		prefs=Gdx.app.getPreferences(Constants.HIGH_SCORE);
		if(!prefs.contains("high_score"))
			prefs.putInteger("high_score", 0);
	}
	
	public int getHighScore(){
		return prefs.getInteger("high_score");
	}
	
	public void setHighScore(int score){
		if(score>prefs.getInteger("high_score")){
			prefs.putInteger("high_score", score);
			prefs.flush();
		}
	}
	
	public void test(){
		System.out.println("Test");
	}
}
