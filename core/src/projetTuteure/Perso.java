package projetTuteure;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Perso {
	
	// Debug
	
	static final int NB_JOUEURS_MAX = 4;
	static int nbJoueurs = 0;
	
	//Déclaration des variables de la classe
	
	private Vector2 pos;
	private Vector2 deplacement;
	
	private Texture img;
	
	private Event event;

	private float vitesse;
	
	private ArrayList <Projectile> projectiles;
	
	private long dateLancementSort;
	
	//Constructeur de la classe	Perso(Vector2 pos)
	Perso(Vector2 pos)
	{
			init(pos);
		
		event = new Event();
		nbJoueurs++;
	}
	
	Perso(Vector2 pos, int numManette)
	{
		init(pos);
		
		event = new Event(numManette);
		nbJoueurs++;
	}
	
	private void init(Vector2 pos)
	{
		this.pos = new Vector2();
		deplacement = new Vector2();
		img = new Texture("perso.png");
		projectiles = new ArrayList <Projectile>();
		this.pos = pos;
		vitesse = 8;
		dateLancementSort = 0;
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
			if (event.getToucheDeplacement(Event.TOUCHE_HAUT))
				deplacement.y+=vitesse;
			else if (event.getToucheDeplacement(Event.TOUCHE_BAS))
				deplacement.y-=vitesse;
	
			if (event.getToucheDeplacement(Event.TOUCHE_GAUCHE))
				deplacement.x-=vitesse;
			else if (event.getToucheDeplacement(Event.TOUCHE_DROITE))
				deplacement.x+=vitesse;
			//TODO Suppression du projectile
			if (event.getAction(0) && (System.currentTimeMillis() - dateLancementSort) > 1000)
				{
					Projectile projectile;
					projectile = new Projectile(pos);
					projectiles.add(projectile);
					dateLancementSort = System.currentTimeMillis();
				}
			int i;
			for (i=0; i< projectiles.size(); i++)
			{
				projectiles.get(i).update();
			}
		}
		else if(event.getTypeController() == Event.MANETTE)
		{
			deplacement.x =   vitesse * event.getValJoystick(Event.JOYSTICK_GAUCHE, 0);
			deplacement.y = -(vitesse * event.getValJoystick(Event.JOYSTICK_GAUCHE, 1));
			if (event.getAction(0))
			{
				Projectile projectile;
				projectile = new Projectile(pos);
				projectiles.add(projectile);
			}
		}
		
	}
	
	//Procèdure de gestion de la collision
	
	//Procèdure de déplacement
	public void deplacement()
	{
		pos.x += deplacement.x;
		pos.y += deplacement.y;
		
		deplacement.x = 0;
		deplacement.y = 0;
		
		int i;
		for (i=0; i < projectiles.size() ; i++)
		{
			projectiles.get(i).deplacement();
		}
	}
	
	//Procèdure d'affichage du personnage
	public void draw(SpriteBatch batch)
	{
		batch.draw(img, pos.x, pos.y);
	}
	public void drawProjectile(SpriteBatch batch)
	{
			int i;
			for (i=0; i < projectiles.size() ; i++)
			{
				projectiles.get(i).draw(batch);
			}
	}
}