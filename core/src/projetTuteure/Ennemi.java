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
		Vector2 ennemiToPerso = new Vector2();
		
		if(this.pos.x + this.taille.x <= cible.getPos().x)
			posCible.x -= 66;
		
		System.out.println("Taille Enn : " + this.taille);
		System.out.println("Taille Perso : " + cible.getTaille());
		
		ennemiToPerso = posCible.sub(this.pos); // Calcul vecteur entre ennemi et le joueur
		
		ennemiToPerso.nor(); // Normalize le vecteur ( 0 < vec < 1 )
		ennemiToPerso.scl(vitesse); // Multiplie par la vitesse dans 2 axes
		
		deplacement = ennemiToPerso; // Definit le deplacement
		
		if(cible.getPos().sub(this.pos).len2() < 25) // S'il est proche de la destination
			deplacement = Vector2.Zero; // On arrete pour eviter le harlem shake
	}
	
	public boolean collision(Vector2 pos, Vector2 taille)
	{
		if((pos.x < this.pos.x + this.taille.x && pos.x > this.pos.x) 
				|| (pos.x + taille.x < this.pos.x + this.taille.x && pos.x + taille.x > this.pos.x))
			{
				if((pos.y < this.pos.y + this.taille.y && pos.y > this.pos.y) 
				|| (pos.y + taille.y < this.pos.y + this.taille.y && pos.y + taille.y > this.pos.y))
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
