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
		taille.x = img.getWidth();
		taille.y = img.getHeight();
		
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
		batch.draw(img, pos.x, pos.y);
	}

	public void setDeplacement(Vector2 deplacement) {
		this.deplacement = new Vector2(deplacement);
	}
}
