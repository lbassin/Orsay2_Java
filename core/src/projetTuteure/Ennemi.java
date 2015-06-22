package projetTuteure;


import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Ennemi {
	
	private Vector2 pos;
	private Vector2 deplacement;
	private Vector2 taille;
	
	private float vitesse;
	
	//Variables pour la gestion de l'image
	private Texture img;
	private int nbImgParAnim;
	private int imgActuelle;
	private int ralentissementAnim;
	
	private int orientation;
	
	private boolean estMort;
	
	private Texture cadreVie;
	private Texture barreVie;
	
	private int vie;
	private int vieMax;
	private Vector2 tailleBarreVie;
	
	private String type;

	Ennemi(int posX, int posY, String type) {
		
		this.pos = new Vector2(posX, posY);
		deplacement = new Vector2();
		this.type= type;
		
		orientation = Perso.GAUCHE;
		
		switch (this.type)
		{
		case "Tank" :
			vitesse = 2f;
			
			img = new Texture("../core/assets/ennemiTank.png");
			nbImgParAnim = 21;
			imgActuelle = 0;
			ralentissementAnim = 1;
			
			taille = new Vector2();
			taille.x = 82*2;
			taille.y = 96*2;
			
			estMort = false;
			
			vieMax = 250;
			vie = 200;

			break;
		case "Rapide" :
			vitesse = 7f;
			
			img = new Texture("../core/assets/ennemi.png");
			
			nbImgParAnim = 1;
			imgActuelle = 0;
			ralentissementAnim = 1;
			
			taille = new Vector2();
			taille.x = img.getWidth();
			taille.y = img.getHeight();
			
			estMort = false;
			
			vieMax = 100;
			vie = 75;
			break;
		case "Normal" :	
			vitesse = 5f;
			
			img = new Texture("../core/assets/ennemiNormal.png");
			nbImgParAnim = 21;
			imgActuelle = 0;
			ralentissementAnim = 1;
			
			taille = new Vector2();
			taille.x = 82;
			taille.y = 96;
			
			estMort = false;
			
			vieMax = 150;
			vie = 100;
			break;
		}
		
		
		
		// Genere img pour cadre jauge
		Vector2 tailleCadre = new Vector2(this.taille.x, 14);
		int tailleBordure = 2;
		
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
		tailleBarreVie = new Vector2(tailleCadre.x-tailleBordure, tailleCadre.y-tailleBordure*2);
		Pixmap barreVieTmp = new Pixmap((int)tailleBarreVie.x , (int)tailleBarreVie.y, Format.RGBA8888);
		barreVieTmp.setColor(0f, 0f, 0.3f, 1);
		barreVieTmp.fillRectangle(0, 0, (int)tailleBarreVie.x - tailleBordure, (int)tailleBarreVie.y);
		
		cadreVie = new Texture(carreJaugeTmp);
		barreVie = new Texture(barreVieTmp);
		
	}
	
	// Determine le joueur le plus proche de l'ennemi
	private Perso persoProche(ArrayList<Perso> persos) {
		
		float distance[] = new float[persos.size()];
		int indicePlusProche = 0;
		
		for(int i = 0; i < persos.size(); i++)
			distance[i] = persos.get(i).getPos().dst(this.pos); // Calcul la distance entre lui et chaque joueur
		
		for(int i = 0; i < persos.size(); i++)
		{
			if(distance[indicePlusProche] > distance[i])
				indicePlusProche = i;
		}
		
		return persos.get(indicePlusProche);
	}
	
	public void update(ArrayList<Perso> persos, Camera cam)
	{
		// L'ennemi attaque le joueur le plus proche
		Perso cible = persoProche(persos);
		
		Vector2 posCible = cible.getPos();
		Vector2 posEnnemi = new Vector2(this.pos);
		Vector2 ennemiToPerso = new Vector2();
		
		// Ajout la motié de la taille dans 2 axes
		posCible.add(cible.getTaille().scl(0.5f)); // Vise le milieu du perso
		
		posEnnemi.add(new Vector2(this.taille).scl(0.5f));

		ennemiToPerso = posCible.sub(posEnnemi); // Calcul vecteur entre ennemi et le joueur
		
		ennemiToPerso.nor(); // Normalize le vecteur ( 0 < vec < 1 )
		ennemiToPerso.scl(vitesse); // Multiplie par la vitesse dans 2 axes
		
		// Applique le deplacement seulement s'il est visible sur l'ecran (dans la zone de la camera)
		if(pos.x + taille.x >= cam.getDeplacementTotalCam().x && pos.x <= cam.getDeplacementTotalCam().x + MyGdxGame.LARGEUR_ECRAN)
		{
			if(pos.y + taille.y >= cam.getDeplacementTotalCam().y && pos.y <= cam.getDeplacementTotalCam().y + MyGdxGame.HAUTEUR_ECRAN)
			{
				deplacement = ennemiToPerso; // Definit le deplacement
			}
		}
		
		// Test collision avec joueurs 
		for(Perso perso : persos)
		{
			if(perso.collision(new Vector2(this.pos).add(this.deplacement), this.taille))
			{
				deplacement = new Vector2(0, 0);
				cible.subitAttaque(1, 0);
			}
		}
		
		if(deplacement.x > 0)
			orientation = Perso.DROITE;
		else
			orientation = Perso.GAUCHE;
		
		imgActuelle++;
		if(imgActuelle >= nbImgParAnim*ralentissementAnim)
			imgActuelle = 0;
	}

	public boolean collision(Vector2 pos, Vector2 taille)
	{
		if((pos.x <= this.pos.x + this.taille.x + this.deplacement.x && pos.x >= this.pos.x + this.deplacement.x) 
				|| (pos.x + taille.x <= this.pos.x + this.taille.x + this.deplacement.x && pos.x + taille.x >= this.pos.x + this.deplacement.x))
			{
				// collisions y
				if((pos.y <= this.pos.y + (this.taille.y / 2) + this.deplacement.y && pos.y >= this.pos.y + this.deplacement.y ) 
				|| (pos.y + (taille.y/2) <= this.pos.y + this.taille.y + this.deplacement.y && pos.y + (taille.y/2) >= this.pos.y + this.deplacement.y ))
				{
					return true;
				}
			}
		
		return false;
	}	
	
	public void deplacement()
	{
		
		pos.x += deplacement.x;
		pos.y += deplacement.y;
		
		deplacement.x = 0;
		deplacement.y = 0;
	}
	
	public void afficher(SpriteBatch batch)
	{
		int tailleBarreVieRestant = (int) ((getPourcentageVieRestant() * tailleBarreVie.x) / 100);
		
		//batch.draw(img, pos.x, pos.y);
		
		TextureRegion imgAffiche;
		
		imgAffiche = new TextureRegion(img, (imgActuelle/ralentissementAnim)*((int)taille.x), (int)(orientation*(taille.y)), (int)taille.x, (int)taille.y);
		
		if(imgAffiche != null)
			batch.draw(imgAffiche, pos.x, pos.y);
		
		
		
		batch.draw(cadreVie, pos.x, pos.y + taille.y + 10);
		batch.draw(barreVie, pos.x+2, pos.y + taille.y + 10 + 2, 0, 0, tailleBarreVieRestant, (int)tailleBarreVie.y);	
	}

	public void setDeplacement(Vector2 deplacement) {
		this.deplacement = new Vector2(deplacement);
	}
	
	public float getPourcentageVieRestant()
	{ return ((float)vie/(float)vieMax) * 100; }

	public void enleverVie(int degat) {
		this.vie -= degat;
		
		if(this.vie < 0)
			this.vie = 0;
	}
	
	public Vector2 getPos() {
		return new Vector2(pos);
	}

	public Vector2 getDeplacement()
	{
		return new Vector2(deplacement);
	}

	public Vector2 getTaille()
	{
		return new Vector2(taille);
	}
	public boolean getMort()
	{
		return estMort;
	}
	public void setMort(boolean mort)
	{
		this.estMort = mort;
	}
	public int getVieRestant()
	{ return vie; }
	
	public String getType()
	{ return type; }
}
