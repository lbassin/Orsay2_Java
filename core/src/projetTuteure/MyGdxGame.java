package projetTuteure;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Perso perso;
	public static final int LARGEUR_ECRAN = 1312;
    public static final int HAUTEUR_ECRAN = 640;
	
    @Override
	public void create () {
		batch = new SpriteBatch();
		perso = new Perso();
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
		
        //Deplacement
        perso.deplacement();
        
        //Affichages
		batch.begin();
		perso.draw(batch);
		batch.end();
	}
}
