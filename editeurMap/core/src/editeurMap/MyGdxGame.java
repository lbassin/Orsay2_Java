package editeurMap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter {
	// Taille de la fenetre en multiple de 32
	public static final Vector2 TAILLE_FENETRE = new Vector2(1280, 704);

	private SpriteBatch batch;

	private Event event;
	private ImageTile imgTile;
	
	private Map map;
	
	private Menu menu;
	
	public void create () {
		batch = new SpriteBatch();

		event = new Event();
		imgTile = new ImageTile("tileFond.png");
		
		map = new Map(new Vector2(10,10));
		menu = new Menu(imgTile, event);
		
		
		// Tmp
			map.ajoutTile(new Vector2(1,0), new Tile(imgTile.getImgTile(1)));
			map.ajoutTile(new Vector2(2,0), new Tile(imgTile.getImgTile(2)));
			map.ajoutTile(new Vector2(3,0), new Tile(imgTile.getImgTile(3)));
			map.ajoutTile(new Vector2(4,0), new Tile(imgTile.getImgTile(4)));
			map.ajoutTile(new Vector2(1,1), new Tile(imgTile.getImgTile(7)));
			map.ajoutTile(new Vector2(2,1), new Tile(imgTile.getImgTile(8)));
		//
		
		
	}

	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		event.update();
		menu.update();
		
		// Quand on appui sur btn gauche, on ajout tile
		if(event.getClicGauche())
			map.ajoutTile(event.getPosSourisEnUnite(), menu.getTileSelection());
		
		
		batch.begin();

			map.draw(batch);
			menu.draw(batch);
			event.drawCurseur(batch);
		
		batch.end();
	}
}
