package projetTuteure.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import projetTuteure.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.width = MyGdxGame.LARGEUR_ECRAN;
		config.height = MyGdxGame.HAUTEUR_ECRAN;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
		
		new LwjglApplication(new MyGdxGame(), config);
	}
}
