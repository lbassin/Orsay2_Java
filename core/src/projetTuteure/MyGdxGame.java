package projetTuteure;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter {
	
	// Test
	
	// Constante
	public static final int LARGEUR_ECRAN = 1312;
    public static final int HAUTEUR_ECRAN = 640;
    public static final int NB_JOUEUR_MAX = 4;
	
    private SpriteBatch batch;
	private SpriteBatch batchHUD;
	
	
	private Map map;
	private Perso perso;
	private GestionEnnemi ennemis;
	private Camera camera;
	private HUD hud;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		batchHUD = new SpriteBatch();
		map = new Map("map.txt", "collision.txt");
		
		if(Perso.nbJoueurs <= Perso.NB_JOUEURS_MAX)
		{
			// Si une manette est connect�e, le perso est controll� avec la manette
			if(Controllers.getControllers().size == 0)
				perso = new Perso(new Vector2(350, 0));
			else
				perso = new Perso(new Vector2(350,0), 0);
		}
		

		ennemis = new GestionEnnemi("initEnnemi.txt");
		
		hud = new HUD();
		hud.addJoueur(perso);
		
		camera = new Camera(batch, perso);
	}

	@Override
	public void render () {
		
		//Initialisation de la fenetre
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Actualise la camera
		camera.update();
        
		//Recuperation des evenements
		perso.updateEvent();
				
		//Calcul le deplacement
		perso.update(ennemis.getListeEnnemis(), camera);
		ennemis.update(perso);
		
		// Collision
		map.collision(perso);
		
        //Deplacement
		perso.deplacement();
		ennemis.deplacement();		
		
        //Affichages
		batch.begin(); // Batch avec matrice de la camera
			map.draw(batch);
			perso.draw(batch);
			perso.drawProjectile(batch);
			ennemis.draw(batch);
		batch.end();
		
		batchHUD.begin(); // Batch classique pour affichage sans tenir compte camera
			hud.afficher(batchHUD);
		batchHUD.end();
		
		// Supprime les ennemis morts
		ennemis.suppressionEnnemi();
		
		// Supprime les projectiles sortie de l'ecran
		perso.gestionProjectile(camera);
		
	}
}
