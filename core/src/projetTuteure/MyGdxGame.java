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

	Map map;
	
	Ennemi ennemi;
	
	public static final int LARGEUR_ECRAN = 1312;
    public static final int HAUTEUR_ECRAN = 640;
	
    @Override
	public void create () {
		batch = new SpriteBatch();
		map = new Map("map.txt");
		
		// Si une manette est connect�, le perso est controll� avec la manette
		if(Controllers.getControllers().size == 0)
			perso = new Perso(new Vector2(0, 0));
		else
			perso = new Perso(new Vector2(0,0), 0);

		ennemi = new Ennemi();
		
	}

	@Override
	public void render () {
		//Initialisation de la fen�tre
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Recup�ration des �v�nements
		perso.updateEvent();
				
		//Calcul le deplacement
		perso.update();
		ennemi.update(perso);
		
		// Collision
		
        //Deplacement
		perso.deplacement();
		ennemi.deplacement();
        
        //Affichages
		batch.begin();
			map.draw(batch);
			ennemi.afficher(batch);
			perso.draw(batch);
			perso.drawProjectile(batch);
		batch.end();
	}
}
