package projetTuteure;

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
	private SpriteBatch batchHUD;
	
	private Perso perso;
	private HUD hud;
	private Niveau niveau;
	
	@Override
	public void create () {
			batch = new SpriteBatch();
			batchHUD = new SpriteBatch();
			
			if(Perso.nbJoueurs <= Perso.NB_JOUEURS_MAX)
			{
				// Si une manette est connect�e, le perso est controll� avec la manette
				if(Controllers.getControllers().size == 0)
					perso = new Perso(new Vector2(400, 200));
				else
					perso = new Perso(new Vector2(400, 200), 0);
			}
			
			hud = new HUD();
			hud.addJoueur(perso);
			niveau = new Niveau ("map2.txt", "collision.txt", perso, "initEnnemi.txt", batch);
	}

	@Override
	public void render () {
		
		//Initialisation de la fenetre
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Actualise la camera
		//Recuperation des evenements		
		//Calcul le deplacement
		niveau.niveauUpdate();
		
		// Collision sur le niveau
		niveau.collision();
		
        //Deplacement sur le niveau
		niveau.deplacement();	
		
		//Si le joueur fini le niveau
		if (perso.aFiniLevel())
		{
			niveau = new Niveau ("map3.txt", "collision.txt", perso, "initEnnemi.txt", batch);
		}
        //Affichages
		batch.begin(); // Batch avec matrice de la camera
			niveau.draw(batch);
		batch.end();
		
		batchHUD.begin(); // Batch classique pour affichage sans tenir compte camera
			hud.afficher(batchHUD);
		batchHUD.end();
		
		// Supprime les ennemis morts
		// Supprime les projectiles sortie de l'ecran
		niveau.gestionNiveau();
		
	}
}
