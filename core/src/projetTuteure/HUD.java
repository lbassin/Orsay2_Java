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
	private Texture cadreJauge;
	
	private Texture barreVie;
	private Vector2 tailleBarreVie;
	
	private Texture barreMana;
	private Vector2 tailleBarreMana;
	
	public HUD()
	{
		nbJoueur = 0;

		// Genere img d'une couleur unie pour faire cadre portrait
		Vector2 tailleCadre = new Vector2(81, 81);
		Pixmap carrePortraitTmp = new Pixmap((int)tailleCadre.x , (int)tailleCadre.y, Format.RGBA8888);
		carrePortraitTmp.setColor(0.61f, 0.46f, 0.24f, 1);
		carrePortraitTmp.fillRectangle(0, 0, (int)tailleCadre.x, (int)tailleCadre.y);
		
		// Genere img pour cadre jauge
		tailleCadre = new Vector2(130, 14);
		int tailleBordure = 2;
		//Pixmap carreJaugeTmp = new Pixmap((int)tailleCadre.x , (int)tailleCadre.y, Format.RGB888);
		Pixmap carreJaugeTmp = new Pixmap((int)tailleCadre.x , (int)tailleCadre.y, Format.RGBA8888);
		carreJaugeTmp.setColor(0.61f, 0.46f, 0.24f, 1);
		// Colorie les 4 lignes qui font le contour
		carreJaugeTmp.fillRectangle(0, 0, (int)tailleCadre.x, tailleBordure);
		carreJaugeTmp.fillRectangle(0, 0, tailleBordure, (int)tailleCadre.y);
		carreJaugeTmp.fillRectangle((int)tailleCadre.x - tailleBordure, 0, tailleBordure, (int)tailleCadre.y);
		carreJaugeTmp.fillRectangle(0, (int)tailleCadre.y - tailleBordure, (int)tailleCadre.x, tailleBordure);
		// Colorie le milieu en transparant
		carreJaugeTmp.setColor(1f, 1f, 1f, 0.2f);
		carreJaugeTmp.fillRectangle(tailleBordure, tailleBordure, (int)tailleCadre.x - tailleBordure*2, (int)tailleCadre.y - tailleBordure*2);
		
		// Genere img barreVie
		tailleBarreVie = new Vector2(130-tailleBordure, 14-tailleBordure*2);
		Pixmap barreVieTmp = new Pixmap((int)tailleBarreVie.x , (int)tailleBarreVie.y, Format.RGBA8888);
		barreVieTmp.setColor(1f, 0f, 0f, 1);
		barreVieTmp.fillRectangle(0, 0, (int)tailleBarreVie.x - tailleBordure, (int)tailleBarreVie.y);
		
		
		// Genere img barreMana
		tailleBarreMana = new Vector2(130-tailleBordure, 14-tailleBordure*2);
		Pixmap barreManaTmp = new Pixmap((int)tailleBarreMana.x , (int)tailleBarreMana.y, Format.RGBA8888);
		barreManaTmp.setColor(0.17f, 0.13f, 0.79f, 1); // 44 32 201
		barreManaTmp.fillRectangle(0, 0, (int)tailleBarreMana.x - tailleBordure, (int)tailleBarreMana.y);
				
		// Genere texture a partir de l'img generé
		cadrePortrait = new Texture(carrePortraitTmp);
		cadreJauge = new Texture(carreJaugeTmp);
		barreVie = new Texture(barreVieTmp);
		barreMana = new Texture(barreManaTmp);
		
		joueurs = new Perso[MyGdxGame.NB_JOUEUR_MAX];
	}
	
	public void addJoueur(Perso perso)
	{
		if(nbJoueur < MyGdxGame.NB_JOUEUR_MAX)
		{
			joueurs[nbJoueur++] = perso;
			System.out.println("Perso ajoute : " + nbJoueur);
		}
	}
	
	public void afficher(SpriteBatch batch)
	{
		Vector2 pos = new Vector2(30, MyGdxGame.HAUTEUR_ECRAN - cadrePortrait.getHeight() - 20);
		
		int i;
		for(i=0; i < nbJoueur; i++)
		{
			afficherInfosPerso(batch, new Vector2(pos.x, pos.y), i);
			pos.x += 300;
		}
	}
		
	private void afficherInfosPerso(SpriteBatch batch, Vector2 pos, int numJoueur)
	{
		if(numJoueur < nbJoueur && numJoueur >= 0)
		{
			// Portrait
			batch.draw(cadrePortrait, pos.x, pos.y);
			batch.draw(joueurs[numJoueur].getPortrait(), pos.x + 3, pos.y + 3);
			
			// Vie
			int tailleBarreVieRestant = (int) ((joueurs[numJoueur].getPourcentageVieRestant() * tailleBarreVie.x) / 100);
			batch.draw(cadreJauge, pos.x + 90, pos.y + 50); // Vie
			batch.draw(barreVie, pos.x + 90 + 2, pos.y + 50 + 2, 0, 0, tailleBarreVieRestant, (int)tailleBarreVie.y);
			
			// Mana
			int tailleBarreManaRestant = (int) ((joueurs[numJoueur].getPourcentageManaRestant() * tailleBarreMana.x) / 100);
			batch.draw(cadreJauge, pos.x + 90, pos.y + 25); // Mana
			batch.draw(barreMana, pos.x + 90 + 2, pos.y + 25 + 2, 0, 0, tailleBarreManaRestant, (int)tailleBarreMana.y);

		}
	}

}
