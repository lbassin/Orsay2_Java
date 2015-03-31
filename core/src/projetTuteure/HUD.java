package projetTuteure;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class HUD {
	
	private Perso[] joueurs;
	private int nbJoueur;
	
	private Texture cadrePortrait;
	
	public HUD()
	{
		nbJoueur = 0;

		// Genere img d'une couleur unie pour faire cadre
		Vector2 tailleCadre = new Vector2(81, 81);
		Pixmap carrePortraitTmp = new Pixmap((int)tailleCadre.x , (int)tailleCadre.y, Format.RGB888);
		carrePortraitTmp.setColor(1f,0.75f,0,1);
		carrePortraitTmp.fillRectangle(0, 0, (int)tailleCadre.x, (int)tailleCadre.y);
		
		// Genere texture a partir de l'img generé
		cadrePortrait = new Texture(carrePortraitTmp);

		joueurs = new Perso[MyGdxGame.NB_JOUEUR_MAX];
	}
	
	public void addJoueur(Perso perso)
	{
		joueurs[nbJoueur++] = perso;
		System.out.println("Perso ajoute : " + nbJoueur);
	}
	
	public void afficher(SpriteBatch batch)
	{
		Vector2 pos = new Vector2(30, MyGdxGame.HAUTEUR_ECRAN - cadrePortrait.getHeight() - 20);
		
		afficherInfosPerso(batch, new Vector2(pos.x, pos.y), 0);
	}
		
	private void afficherInfosPerso(SpriteBatch batch, Vector2 pos, int numJoueur)
	{
		if(numJoueur < nbJoueur && numJoueur >= 0)
		{
			batch.draw(cadrePortrait, pos.x, pos.y);
			batch.draw(joueurs[numJoueur].getPortrait(), pos.x + 3, pos.y + 3);
		}
	}

}
