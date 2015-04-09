package projetTuteure;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter {
	
	// Constante
	public static final int LARGEUR_ECRAN = 1312;
    public static final int HAUTEUR_ECRAN = 640;
    public static final int NB_JOUEUR_MAX = 4;
	
	private SpriteBatch batch;
	private Map map;
	private Perso perso;
	private ArrayList <Ennemi> ennemis;
	private Camera camera;
	private HUD hud;
	
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
		
		camera = new Camera(batch, perso);
	}

	@Override
	public void render () {
		//Initialisation de la fen�tre
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Actualise la camera
		camera.update();
        
		//Recup�ration des �v�nements
		perso.updateEvent();
				
		//Calcul le deplacement
		perso.update(ennemis);
		
		//Sortie de l'ecran du projectile
		perso.sortieEcranProjectile();
		
		// Collision
		map.collision(perso);
		
        //Deplacement
		perso.deplacement();
		
		//Boucle pour l'ennemi
		int i;
		for(i=0; i<ennemis.size(); i++)
		{
			ennemis.get(i).update(perso);
			ennemis.get(i).deplacement();
			//Mort
			if (ennemis.get(i).getMort())
			{
				ennemis.remove(i);
			}
		}
		
        //Affichages
		batch.begin();
			map.draw(batch);
			perso.draw(batch);
			perso.drawProjectile(batch);
			//Boucle pour l'ennemi
			for(i=0; i<ennemis.size(); i++)
			{
				ennemis.get(i).afficher(batch);
			}
			hud.afficher(batch);
		batch.end();
	}
}
