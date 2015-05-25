package projetTuteure;


import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Ennemi {
	
	// Debug
	
	private Vector2 pos;
	private Vector2 deplacement;
	private Vector2 taille;
	
	private float vitesse;
	
	private Texture img;
	private boolean estMort;
	
	private Texture cadreVie;
	private Texture barreVie;
	
	private int vie;
	private int vieMax;
	private Vector2 tailleBarreVie;

	Ennemi(int posX, int posY) {
		
		this.pos = new Vector2(posX, posY);
		deplacement = new Vector2();
		
		vitesse = 5f;
		
		img = new Texture("../core/assets/ennemi.png");
		
		taille = new Vector2();
		taille.x = img.getWidth();
		taille.y = img.getHeight();
		
		estMort = false;
		
		vieMax = 150;
		vie = 100;
		
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
	
	public void update(Perso cible)
	{
		Vector2 posCible = cible.getPos();
		Vector2 posEnnemi = new Vector2(this.pos);
		Vector2 ennemiToPerso = new Vector2();
		
		// Ajout la motié de la taille dans 2 axes
		posCible.add(cible.getTaille().scl(0.5f)); // Vise le milieu du perso
		
		posEnnemi.add(new Vector2(this.taille).scl(0.5f));

		ennemiToPerso = posCible.sub(posEnnemi); // Calcul vecteur entre ennemi et le joueur
		
		ennemiToPerso.nor(); // Normalize le vecteur ( 0 < vec < 1 )
		ennemiToPerso.scl(vitesse); // Multiplie par la vitesse dans 2 axes
		
		deplacement = ennemiToPerso; // Definit le deplacement
		
		// Test collision avec joueurs :
		// TODO : Boucle pour tous les joueurs
		
		if(cible.collision(new Vector2(this.pos).add(this.deplacement), this.taille))
		{
			deplacement = new Vector2(0, 0);
			//cible.subitAttaque(1, 0);
		}
	}
	
	public boolean collision(Vector2 pos, Vector2 taille)
	{
		if((pos.x <= this.pos.x + this.taille.x + this.deplacement.x && pos.x >= this.pos.x + this.deplacement.x) 
				|| (pos.x + taille.x <= this.pos.x + this.taille.x + this.deplacement.x && pos.x + taille.x >= this.pos.x + this.deplacement.x))
			{
				// collisions y
				if((pos.y <= this.pos.y + this.taille.y + this.deplacement.y && pos.y >= this.pos.y + this.deplacement.y ) 
				|| (pos.y + taille.y <= this.pos.y + this.taille.y + this.deplacement.y && pos.y + taille.y >= this.pos.y + this.deplacement.y ))
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
		
		batch.draw(img, pos.x, pos.y);
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
	
	public int getVieRestant()
	{ return vie; }
}
