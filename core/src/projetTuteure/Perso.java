package projetTuteure;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Perso {
	
	static final int DROITE = 0;
	static final int GAUCHE = 1;
	
	static final int NB_JOUEURS_MAX = 4;
	static int nbJoueurs = 0;
	
	//Déclaration des variables de la classe
	
	private Vector2 pos;
	private Vector2 deplacement;
	
	private Texture img;
	private int nbImgParAnim;
	private int imgActuelle;
	private Texture portrait;
	
	private Event event;

	private float vitesse;
	
	private ArrayList <Projectile> projectiles;
	
	private long dateLancementSort[];
	
	private Vector2 taille;
	
	private int vie;
	private int vieMax;
	
	private int manaMax;
	private int mana;
	
	private int orientation;
	private int ralentissementAnim;
	
	private boolean finiLevel;
	
		
	
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
		nbImgParAnim = 8;
		portrait = new Texture("portrait.png");
		taille = new Vector2();
		taille.x = 82;
		taille.y = 96;
		projectiles = new ArrayList <Projectile>();
		this.pos = pos;
		vitesse = 10; // 12
		dateLancementSort = new long [4];
		
		vie = 70;
		vieMax = 100;
		
		manaMax = 100;
		mana = 80;
		
		orientation = DROITE;
		imgActuelle = 0;
		ralentissementAnim = 3;
		
		finiLevel = false;
	}
	
	//Getteur de la position
	public Vector2 getPos()
	{
		// Copie de protection
		// Empeche de modifier sa pos n'importe comment
		return new Vector2(pos);
		
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
	public void update(ArrayList <Ennemi> ennemis, Camera camera, Map map)
	{
		int i;
		
		if(event.getTypeController() == Event.CLAVIER)
		{
			if (event.getToucheDeplacement(Event.TOUCHE_HAUT))
				deplacement.y+=vitesse;
			else if (event.getToucheDeplacement(Event.TOUCHE_BAS))
				deplacement.y-=vitesse;
	
			if (event.getToucheDeplacement(Event.TOUCHE_GAUCHE))
			{
				deplacement.x-=vitesse;
			}
			else if (event.getToucheDeplacement(Event.TOUCHE_DROITE))
			{
				deplacement.x+=vitesse;
				
				imgActuelle++;
				if(imgActuelle >= nbImgParAnim*ralentissementAnim)
					imgActuelle = 0;
			
			}
				
			if (event.getAction(0) && (System.currentTimeMillis() - dateLancementSort[0]) > 1000)
			{
					projectiles.add(new Projectile(new Vector2(pos.x + taille.x, pos.y), 0, orientation));
					dateLancementSort[0] = System.currentTimeMillis();
			}
			if (event.getAction(1) && (System.currentTimeMillis() - dateLancementSort[1]) > 1000)
			{
				projectiles.add(new Projectile(new Vector2(pos.x + taille.x, pos.y), 1, orientation));
				dateLancementSort[1] = System.currentTimeMillis();
			}
			if (event.getAction(2) && (System.currentTimeMillis() - dateLancementSort[2]) > 1000)
			{
				projectiles.add(new Projectile(new Vector2(pos.x + taille.x, pos.y), 2, orientation));
				dateLancementSort[2] = System.currentTimeMillis();
			}
			if (event.getAction(3) && (System.currentTimeMillis() - dateLancementSort[3]) > 1000)
			{
				projectiles.add(new Projectile(new Vector2(pos.x + taille.x, pos.y), 3, orientation));
				dateLancementSort[3] = System.currentTimeMillis();
			}
			
			for (i=0; i< projectiles.size(); i++)
			{
				projectiles.get(i).update(camera);
				projectiles.get(i).collision(ennemis);
			}
		}
		else if(event.getTypeController() == Event.MANETTE)
		{
			deplacement.x =   vitesse * event.getValJoystick(Event.JOYSTICK_GAUCHE, 0);
			deplacement.y = -(vitesse * event.getValJoystick(Event.JOYSTICK_GAUCHE, 1));
			if (event.getAction(0) && (System.currentTimeMillis() - dateLancementSort[0]) > 1000)
			{
				projectiles.add(new Projectile(new Vector2(pos.x + taille.x, pos.y), 0, orientation));
				dateLancementSort[0] = System.currentTimeMillis();
			}
			if (event.getAction(1) && (System.currentTimeMillis() - dateLancementSort[1]) > 1000)
			{
				projectiles.add(new Projectile(new Vector2(pos.x + taille.x, pos.y), 1, orientation));
				dateLancementSort[1] = System.currentTimeMillis();
			}
			if (event.getAction(2) && (System.currentTimeMillis() - dateLancementSort[2]) > 1000)
			{
				projectiles.add(new Projectile(new Vector2(pos.x + taille.x, pos.y), 2, orientation));
				dateLancementSort[2] = System.currentTimeMillis();
			}
			if (event.getAction(3) && (System.currentTimeMillis() - dateLancementSort[3]) > 1000)
			{
				projectiles.add(new Projectile(new Vector2(pos.x + taille.x, pos.y), 3, orientation));
				dateLancementSort[3] = System.currentTimeMillis();
			}
		}
		
		for(i=0; i < ennemis.size(); i++)
		{
			// Si il y a une collision avec ennemi
			if(collision(ennemis.get(i).getPos(), ennemis.get(i).getTaille()))
			{
				if(ennemis.get(i).getPos().x > this.pos.x) // Qu'il est devant le joueur
				{
					if(deplacement.x > 0) // Et que le joueur veut avancer
						deplacement.x = 0; // On annule deplacement
				}
				else if(ennemis.get(i).getPos().x < this.pos.x) // Qu'il est derrière le joueur
				{
					if(deplacement.x < 0) // Et que le joueur veut reculer
						deplacement.x = 0; // On annule deplacement
				}
			}
		}
		
		if(deplacement.x > 0)
			orientation = DROITE;
		else if(deplacement.x < 0)
			orientation = GAUCHE;
		
		finiLevel = ((ennemis.size()==0) && (pos.x >= map.getTailleMap().x - 434) && (pos.y <= -670));
	}
	
	//Procèdure de déplacement
	public void deplacement()
	{
		pos.x += deplacement.x;
		pos.y += deplacement.y;
		
		deplacement.x = 0;
		deplacement.y = 0;

		for (int i=0; i < projectiles.size() ; i++)
		{
			projectiles.get(i).deplacement();
		}
	}
	public void gestionProjectile(Camera camera)
	{
		for (int i=0; i < projectiles.size() ; i++)
		{
			if ((projectiles.get(i).getPos().x > 1312 + camera.getDeplacementTotalCam().x) || 
					(projectiles.get(i).getPos().x < -35 + camera.getDeplacementTotalCam().x) || projectiles.get(i).aTouche())
				projectiles.remove(i);
		}
	}
	
	public boolean collision(Vector2 pos, Vector2 taille)
	{
		if((pos.x < this.pos.x + this.taille.x && pos.x > this.pos.x) 
				|| (pos.x + taille.x < this.pos.x + this.taille.x && pos.x + taille.x > this.pos.x))
			{
				// collisions y
				if((pos.y < this.pos.y + this.taille.y && pos.y > this.pos.y) 
				|| (pos.y + taille.y < this.pos.y + this.taille.y && pos.y + taille.y > this.pos.y))
				{
					return true;
				}
			}
		
		return false;
	}
	
	//Procèdure d'affichage du personnage
	public void draw(SpriteBatch batch)
	{
		TextureRegion imgAffiche;
		
		// Pour prendre en compte chaque orientation
		// imgAffiche = new TextureRegion(img, (imgActuelle/ralentissementAnim)*((int)taille.x), orientation*(taille.y), (int)taille.x, (int)taille.y);
		imgAffiche = new TextureRegion(img, (imgActuelle/ralentissementAnim)*((int)taille.x), 0, (int)taille.x, (int)taille.y);
		
		if(imgAffiche != null)
			batch.draw(imgAffiche, pos.x, pos.y);
	}

	public void drawProjectile(SpriteBatch batch)
	{
		for (int i=0; i < projectiles.size() ; i++)
		{
			projectiles.get(i).draw(batch);
		}
	}
	
	public void subitAttaque(int nbPv, int distanceRecul)
	{
		if(this.vie - nbPv >= 0)
			this.vie -= nbPv;
	}
	
	public Vector2 getTaille()
	{ return taille; }
	
	public void setDeplacement(Vector2 deplacement)
	{ 
		this.deplacement = deplacement;
	}

	public Vector2 getDeplacement()
	{ return new Vector2(deplacement); }
	
	public Texture getPortrait()
	{ return portrait; }
	
	public float getPourcentageVieRestant()
	{ return ((float)vie/(float)vieMax) * 100; }
	
	public float getPourcentageManaRestant()
	{ return ((float)mana/(float)manaMax) * 100; }

	public boolean aFiniLevel()
	{
		return finiLevel;
	}

	public void resetPerso()
	{
		pos.x = 400;
		pos.y = 200;
		vie = 70;
		mana = 80;
	}
}
