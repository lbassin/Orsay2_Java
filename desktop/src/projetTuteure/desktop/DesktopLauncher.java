package projetTuteure.desktop;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.audio.Mp3.Sound;
import projetTuteure.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = 1920;
		config.height = 1080;

		config.fullscreen = true;
		config.vSyncEnabled = true;
		
		new LwjglApplication(new MyGdxGame(), config);
		com.badlogic.gdx.audio.Sound sound = Gdx.audio.newSound(Gdx.files.internal("OMFG-Hello.mp3"));
		sound.play(1.0f);
	}
}