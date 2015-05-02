package projetTuteure;


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

	Ennemi(int posX, int posY) {
	
		this.pos = new Vector2(posX, posY);
		deplacement = new Vector2();
		
		vitesse = 5f;
		
		img = new Texture("../core/assets/ennemi.png");
		
		taille = new Vector2();
		taille.x = img.getHeight();
		taille.y = img.getWidth();
		
		estMort = false;
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
		// Position du perso
		Vector2 posPerso = cible.getPos();
		
		// S'arrete devant ou derriere le perso
		// Ne lui rentre pas dedans
		if(this.pos.x > posPerso.x)
			posPerso.x += cible.getTaille().x/2 + 20;
		else if(this.pos.x < posPerso.x)
			posPerso.x -= cible.getTaille().x/2 + 20;
		
		// Position devant l'ennemi
		Vector2 avantEnnemi = new Vector2();
		avantEnnemi.x = this.pos.x + 1;
		avantEnnemi.y = this.pos.y;
		
		// Vecteur entre ennemi et le joueur
		Vector2 ennemiPerso = new Vector2();
		
		ennemiPerso.x = posPerso.x - this.pos.x;
		ennemiPerso.y = posPerso.y - this.pos.y;
		
		// Vecteur entre ennemi et le point devant lui
		Vector2 ennemiDevant = new Vector2();
		
		ennemiDevant.x = avantEnnemi.x - this.pos.x;
		ennemiDevant.y = avantEnnemi.y - this.pos.y;
		
		// Produit scalaire
		float prodScalaire = (ennemiPerso.x*ennemiDevant.x) + (ennemiPerso.y*ennemiDevant.y);
		
		double prodNormes = Math.sqrt(ennemiPerso.x*ennemiPerso.x + ennemiPerso.y*ennemiPerso.y);
		prodNormes *= Math.sqrt(ennemiDevant.x*ennemiDevant.x + ennemiDevant.y*ennemiDevant.y);
		
		double angle = (prodScalaire/prodNormes);
		
		// Domaine depart arcCos : [-1;1]
		// Erreur si hors du domaine
		if(angle > 1)
			angle = 1;
		else if(angle < -1)
			angle = -1;
		
		angle = Math.acos(angle);		

		deplacement.x = (float) Math.cos(angle) * vitesse;
		
		// Si le perso est au dessus de l'ennemi 
		if(posPerso.y > this.pos.y)
			deplacement.y = (float) Math.sin(angle) * vitesse;			
		else // Si il est en dessous on inverse deplacement en y car l'angle n'est pas dans sens trigo
			deplacement.y = -(float) Math.sin(angle) * vitesse;	
		
		// Si l'ennemie est trop proche en x, on annule le deplacement pour eviter son mini harlem shake
		if(Math.abs((this.pos.x + this.taille.x + deplacement.x ) - (posPerso.x + cible.getTaille().x)) < 4)
			deplacement.x = 0;
		
	}
	
	public boolean collision(Vector2 pos, Vector2 taille)
	{
		System.out.println("test");
		if((pos.x < this.pos.x + this.taille.x && pos.x > this.pos.x) 
				|| (pos.x + taille.x < this.pos.x + this.taille.x && pos.x + taille.x > this.pos.x))
			{
			System.out.println("valide");
				if((pos.y < this.pos.y + this.taille.y && pos.y > this.pos.y) 
				|| (pos.y + taille.y < this.pos.y + this.taille.y && pos.y + taille.y > this.pos.y))
				{
					System.out.println("This : " + this.pos + " - " + this.taille);
					System.out.println("Test : " + pos + " - " + taille);
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
			batch.draw(img, pos.x, pos.y);
	}

	public void setDeplacement(Vector2 deplacement) {
		this.deplacement = new Vector2(deplacement);
	}
}
