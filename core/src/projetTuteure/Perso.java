package projetTuteure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Perso {
	//Déclaration des variables de la classe
	private Vector2 pos;
	private Texture img;
	private float vitesse;
	
	//Constructeur de la classe
	Perso()
	{
		pos = new Vector2();
		img = new Texture("perso.png");
	}
	//Getteur de la position
	public Vector2 getPos()
	{
		return pos;
	}
	//Getteur de l'image
	public Texture getImg()
	{
		return img;
	}
	//Getteur de la vitesse; 
	public float getVitesse()
	{
		return vitesse;
	}
	public void draw(SpriteBatch batch)
	{
		batch.begin();
		batch.draw(img, pos.x, pos.y);
		batch.end();
	}
	// cdfgj
	
}
