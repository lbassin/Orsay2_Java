package projetTuteure;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter {
	
	// Desolé Bryan :/
	
	// Constante
	public static final int LARGEUR_ECRAN = 1312;
    public static final int HAUTEUR_ECRAN = 640;
    public static final int NB_JOUEUR_MAX = 4;
	
	// TODO : Mettre attribut en private :o !
	
	SpriteBatch batch;
	Map map;
	Perso perso;
	ArrayList <Ennemi> ennemis;
	
	HUD hud;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		map = new Map("map.txt", "collision.txt");
		
		if(Perso.nbJoueurs <= Perso.NB_JOUEURS_MAX)
		{
			// Si une manette est connect�e, le perso est controll� avec la manette
			if(Controllers.getControllers().size == 0)
				perso = new Perso(new Vector2(0, 0));
			else
				perso = new Perso(new Vector2(0,0), 0);
		}
		

		ennemis = new ArrayList<Ennemi>();
		ennemis.add(new Ennemi());
		
		hud = new HUD();
		hud.addJoueur(perso);
	}

	@Override
	public void render () {
		//Initialisation de la fen�tre
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Recup�ration des �v�nements
		perso.updateEvent();
				
		//Calcul le deplacement
		perso.update(ennemis.get(0));
		ennemis.get(0).update(perso);
		
		//Sortie de l'ecran du projectile
		perso.sortieEcranProjectile();
		
		// Collision
		map.collision(perso);
		
        //Deplacement
		perso.deplacement();
		ennemis.get(0).deplacement();
        
        //Affichages
		batch.begin();
			map.draw(batch);
			ennemis.get(0).afficher(batch);
			perso.draw(batch);
			perso.drawProjectile(batch);
			hud.afficher(batch);
		batch.end();
		
		System.out.println(ennemis.size());
		
		//Mort
		if (ennemis.get(0).getMort())
		{
			ennemis.remove(0);
		}
	}
}
