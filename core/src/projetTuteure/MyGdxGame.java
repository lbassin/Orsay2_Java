package projetTuteure;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
    
	private gestionJoueurs persos;
	private Niveau niveau;
	
	@Override
	public void create () {
			batch = new SpriteBatch();
			batchHUD = new SpriteBatch();
			
			persos = new gestionJoueurs();
			
			niveau = new Niveau ("map1.txt", "collision1.txt", persos.getPersos(), "ennemis1.txt", batch, new Vector2(400, 200));

	}

	@Override
	public void render () {
		//Initialisation de la fenetre
		Gdx.gl.glClearColor(0, 0, 0.48f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		//Affichage seulement de l'image si le perso est mort
		if (persos.unJoueurMort())
		{
			niveau.mortPerso();
			
			if(Gdx.input.isKeyJustPressed(Keys.G))
			{
				niveau.stopMusique();
				niveau = new Niveau ("map1.txt", "collision1.txt", persos.getPersos(), "ennemis1.txt", batch, new Vector2(400, 200));
			}
				
			batchHUD.begin();
				batchHUD.draw(new Texture("../core/assets/imgMort.jpg"), 0, 0);
			batchHUD.end();
		}
		//Affichage seulement si le perso gagn�
		else if (persos.unJoueurFiniLevel() && niveau.lastLevel())
		{
			niveau.victoirePerso();
			
			if(Gdx.input.isKeyJustPressed(Keys.G))
			{
				niveau.stopMusique();
				niveau = new Niveau ("map1.txt", "collision1.txt", persos.getPersos(), "ennemis1.txt", batch, new Vector2(400, 200));
			}
			
			batchHUD.begin();
				niveau.afficheImgFin(batchHUD);
			batchHUD.end();
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
			
			//Determine si le perso a gagn�
			
			
			//Si le joueur fini le niveau
			if (persos.unJoueurFiniLevel() && !niveau.lastLevel())
			{
				niveau.stopMusique();
				niveau = new Niveau ("map2.txt", "collision2.txt", persos.getPersos(), "ennemis2.txt", batch, new Vector2(130, 80));
			}
			
			//Affichages
			batch.begin(); // Batch avec matrice de la camera
				niveau.draw(batch);
			batch.end();
			
			batchHUD.begin();
				niveau.afficherHUD(batchHUD);
			batchHUD.end();
			
			// Supprime les ennemis morts
			// Supprime les projectiles sortie de l'ecran
			niveau.gestionNiveau();
			}
	}
}
