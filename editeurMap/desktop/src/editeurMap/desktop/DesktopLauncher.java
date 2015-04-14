package editeurMap.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import editeurMap.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.height = (int) MyGdxGame.TAILLE_FENETRE.y;
		config.width = (int) MyGdxGame.TAILLE_FENETRE.x;
		
		new LwjglApplication(new MyGdxGame(), config);
	}
}
