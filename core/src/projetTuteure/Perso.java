package projetTuteure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Perso {
	
	/// Laurent (a faire) : Ajout d'un numero de joueur
	/// 					pour distinger les 4 joueurs
	
	//Déclaration des variables de la classe
	private Vector2 pos;
	private Vector2 deplacement;
	
	private Texture img;
	
	private Event event;

	private float vitesse;
	
	//Constructeur de la classe
	Perso(Vector2 pos)
	{
		init(pos);
		
		event = new Event();
	}
	
	Perso(Vector2 pos, int numManette)
	{
		init(pos);
		
		event = new Event(numManette);
	}
	
	private void init(Vector2 pos)
	{
		this.pos = new Vector2();
		deplacement = new Vector2();
		img = new Texture("perso.png");
		
		this.pos = pos;
		vitesse = 8;
	}
	
	//Getteur de la position
	public Vector2 getPos()
	{
		return pos;
	}
	
	//Getteur de la vitesse; 
	public float getVitesse()
	{
		return vitesse;
	}
	
	//Mise à jour des événements
	public void updateEvent()
	{
		event.update();
	}
	
	//Calcul du déplacement
	public void update()
	{
		
		if(event.getTypeController() == Event.CLAVIER)
		{
			if (event.getTouche(Event.TOUCHE_HAUT))
				deplacement.y+=vitesse;
			else if (event.getTouche(Event.TOUCHE_BAS))
				deplacement.y-=vitesse;
	
			if (event.getTouche(Event.TOUCHE_GAUCHE))
				deplacement.x-=vitesse;
			else if (event.getTouche(Event.TOUCHE_DROITE))
				deplacement.x+=vitesse;
		}
		else if(event.getTypeController() == Event.MANETTE)
		{
			deplacement.x =   vitesse * event.getValJoystick(Event.JOYSTICK_GAUCHE, 0);
			deplacement.y = -(vitesse * event.getValJoystick(Event.JOYSTICK_GAUCHE, 1));
		}
		
	}
	
	//Méthode de gestion de la collision
	
	//Méthode de déplacement
	public void deplacement()
	{
		pos.x += deplacement.x;
		pos.y += deplacement.y;
		
		deplacement.x = 0;
		deplacement.y = 0;
	}
	
	//Méthode d'affichage du personnage
	public void draw(SpriteBatch batch)
	{
		batch.draw(img, pos.x, pos.y);
	}
}