package projetTuteure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Ennemi {
	
	// Debug
	
	private Vector2 pos;
	private Vector2 deplacement;
	
	private float vitesse;
	
	private Texture img;

	Ennemi() {
	
		this.pos = new Vector2(MyGdxGame.LARGEUR_ECRAN - 150, 50);
		deplacement = new Vector2();
		
		vitesse = 5f;
		
		img = new Texture("../core/assets/ennemi.png");
		
	}
	
	public void update(Perso cible)
	{
		// Position du perso
		Vector2 posPerso = cible.getPos();
		
		// Position devant le perso
		Vector2 avantPerso = new Vector2();
		avantPerso.x = this.pos.x + 1;
		avantPerso.y = this.pos.y;
		
		// Vecteur entre ennemi et le joueur
		Vector2 ennemiPerso = new Vector2();
		
		ennemiPerso.x = posPerso.x - this.pos.x;
		ennemiPerso.y = posPerso.y - this.pos.y;
		
		// Vecteur entre ennemi et le point devant lui
		Vector2 ennemiDevant = new Vector2();
		
		ennemiDevant.x = avantPerso.x - this.pos.x;
		ennemiDevant.y = avantPerso.y - this.pos.y;
		
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
		
		// TODO Quand perso va à 1 unité devant ennemi -> Division / zero
		// TODO Erreur quand on va devant l'ennemi

		System.out.println(Math.cos(angle) + " - " + Math.sin(angle));
		

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

}
