package projetTuteure;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Perso perso;
	Perso perso2;

	Map map;
	
	public static final int LARGEUR_ECRAN = 1312;
    public static final int HAUTEUR_ECRAN = 640;
	
    @Override
	public void create () {
		batch = new SpriteBatch();
		map = new Map("map.txt");
		
		if(Controllers.getControllers().size == 0)
			perso = new Perso(new Vector2(0, 0));
		else
			perso = new Perso(new Vector2(0,0), 0);
		
	}

	@Override
	public void render () {
		//Initialisation de la fenêtre
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Recupération des événements
		perso.updateEvent();
		
		//Calcul le deplacement
		perso.update();
		
		// Collision
		
        //Deplacement
		perso.deplacement();
        
        //Affichages
		batch.begin();
			map.draw(batch);
			perso.draw(batch);
		batch.end();
	}
}
