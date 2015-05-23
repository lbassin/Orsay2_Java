package projetTuteure;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
	private boolean fin;
	
	@Override
	public void create () {
			batch = new SpriteBatch();
			batchHUD = new SpriteBatch();
			
			if(Perso.nbJoueurs <= Perso.NB_JOUEURS_MAX)
			{
				// Si une manette est connecte, le perso est controlle avec la manette
				if(Controllers.getControllers().size == 0)
					perso = new Perso(new Vector2(400, 000));
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
		//Affichage seulement de l'image si le perso est mort
		if (perso.estMort())
		{
			niveau.mortPerso();
			
			if(Gdx.input.isKeyJustPressed(Keys.G))
			{
				niveau.stopMusique();
				niveau = new Niveau ("map2.txt", "collision.txt", perso, "initEnnemi.txt", batch);
			}
				
			batch.begin();
					batch.draw(new Texture("../core/assets/imgMort.jpg"), niveau.getCameraDeplacement().x, niveau.getCameraDeplacement().y);
			batch.end();
		}
		//Affichage seulement si le perso gagné
		else if (fin)
		{
			niveau.victoirePerso();
			
			if(Gdx.input.isKeyJustPressed(Keys.G))
			{
				niveau.stopMusique();
				niveau = new Niveau ("map2.txt", "collision.txt", perso, "initEnnemi.txt", batch);
			}
			
			batch.begin();
					batch.draw(new Texture("../core/assets/imgFin.jpg"), niveau.getCameraDeplacement().x, niveau.getCameraDeplacement().y);
			batch.end();
		}
		else 
		{
			//Actualise la camera
			//Recuperation des evenements		
			//Calcul le deplacement
			niveau.niveauUpdate();
			
			// Collision sur le niveau
			niveau.collision();
			
	        //Deplacement sur le niveau
			niveau.deplacement();
			
			//Determine si le perso a gagné
			fin=(perso.aFiniLevel() && niveau.getMap().estMapFinale());
			
			
			//Si le joueur fini le niveau
			if (perso.aFiniLevel() && !niveau.getMap().estMapFinale())
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
}
