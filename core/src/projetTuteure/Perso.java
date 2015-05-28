package projetTuteure;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Perso {
	
	//Variables pour l'orientation du personnage
	static final int DROITE = 0;
	static final int GAUCHE = 1;
	
	public static final int LARGEUR_ECRAN = 1312;
    public static final int HAUTEUR_ECRAN = 640;
    
	//Variables pour la gestion du nombre de joueurs
	static final int NB_JOUEURS_MAX = 4;
	static int nbJoueurs = 0;
	
	//Variables caracteristiques du personnage
	private Vector2 pos;
	private Vector2 deplacement;
	private float vitesse;
	private Vector2 taille;
	
	//Variables pour la gestion de l'image
	private Texture img;
	private int nbImgParAnim;
	private int imgActuelle;
	private Texture portrait;
	
	//Variable pour la gestion des evenements
	private Event event;
	
	//Varaibles pour la gestion des projectiles
	private ArrayList <Projectile> projectiles;
	private long dateLancementSort[];
	
	//Variables pour la gestion de la vie et du mana
	private int vie;
	private int vieMax;
	
	private int manaMax;
	private int mana;
	
	//Variables pour l'orientation
	private int orientation;
	private int ralentissementAnim;
	
	//Booleens pour gerer la mort et la fin d'un niveau
	private boolean finiLevel;
	private boolean mort;
	
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
	
	//Variable d'initialisation du perso
	public void init(Vector2 pos)
	{
		this.pos = new Vector2();
		this.pos = pos;
		deplacement = new Vector2();
		vitesse = 10; // 12
		
		img = new Texture("perso.png");
		nbImgParAnim = 4;
		portrait = new Texture("portrait.png");
		imgActuelle = 0;
		
		taille = new Vector2();
		taille.x = 82;
		taille.y = 96;
		
		projectiles = new ArrayList <Projectile>();
		dateLancementSort = new long [4];
		
		System.out.println(pos);
		vie = 70;
		vieMax = 100;
		
		manaMax = 100;
		mana = 80;
		
		orientation = DROITE;
		
		ralentissementAnim = 5;
		
		finiLevel = false;
		mort = false;
	}
	
	//Mise a jour des evenements
	public void updateEvent()
	{
		event.update();
	}
	
	//Calcul du deplacement
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
				orientation = GAUCHE;
			}
			else if (event.getToucheDeplacement(Event.TOUCHE_DROITE))
			{
				deplacement.x+=vitesse;
				orientation = DROITE;
			}
			
			if(!deplacement.isZero())
			{
				
				imgActuelle++;
				if(imgActuelle >= nbImgParAnim*ralentissementAnim)
					imgActuelle = 0;
			}
			else
				imgActuelle = 0;

			
			for (i=0; i<4; i++)
			{
				if (event.getAction(i) && (System.currentTimeMillis() - dateLancementSort[i]) > 1000)
				{
					projectiles.add(new Projectile(this, i));
					dateLancementSort[i] = System.currentTimeMillis();
				}
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
			for (i=0; i<4; i++)
			{
				if (event.getAction(i) && (System.currentTimeMillis() - dateLancementSort[i]) > 1000)
				{
					projectiles.add(new Projectile(this, i));
					dateLancementSort[i] = System.currentTimeMillis();
				}
			}
		}
		
		for(i=0; i < ennemis.size(); i++)
		{
			// Si il y a une collision avec ennemi
			if(collision(ennemis.get(i).getPos().add(ennemis.get(i).getDeplacement()), ennemis.get(i).getTaille()))
			{		
				if((ennemis.get(i).getPos().y >= this.pos.y + (this.taille.y/2) || // Si le joueur est dessous ou dessus
						(ennemis.get(i).getPos().y + (ennemis.get(i).getTaille().y/2)) <= this.pos.y))
					deplacement.y = 0; // Il peut toujours se deplacer en x
					
				if((ennemis.get(i).getPos().x >= this.pos.x + this.taille.x) || // Si le joueur est à gauche ou à droite
						(ennemis.get(i).getPos().x + ennemis.get(i).getTaille().x < this.pos.x))
					deplacement.x = 0; // Il peut toujours se deplacer en y
			}
		}
		finiLevel = ((ennemis.size()==0) && (pos.x >= map.getTailleMap().x * map.getTailleTile().x - 500) && (pos.y <= -(map.getTailleMap().y * map.getTailleTile().y) + HAUTEUR_ECRAN + 300));
		mort = (vie == 0);
	}
	
	//Procedure de deplacement
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

	//Calcul de la collision
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
	
	//Procedure de gestion du projectile (s'il sort de l'ecran ou il touche un ennemi)
	public void gestionProjectile(Camera camera)
	{
		for (int i=0; i < projectiles.size() ; i++)
		{
			if ((projectiles.get(i).getPos().x > 1312 + camera.getDeplacementTotalCam().x) || 
					(projectiles.get(i).getPos().x < -35 + camera.getDeplacementTotalCam().x) || projectiles.get(i).aTouche())
				projectiles.remove(i);
		}
	}
	
	public void subitAttaque(int nbPv, int distanceRecul)
	{
		if(this.vie - nbPv >= 0)
			this.vie -= nbPv;
	}
	
	//Procedure d'affichage du personnage
	public void draw(SpriteBatch batch)
	{
		TextureRegion imgAffiche;
		
		// Pour prendre en compte chaque orientation
		imgAffiche = new TextureRegion(img, (imgActuelle/ralentissementAnim)*((int)taille.x), (int)(orientation*(taille.y)), (int)taille.x, (int)taille.y);
		System.out.println(new Vector2( (imgActuelle/ralentissementAnim)*((int)taille.x), (int)(orientation*(taille.y))));
		if(imgAffiche != null)
			batch.draw(imgAffiche, pos.x, pos.y);
	}

	//Procedure d'affichage du personnage
	public void drawProjectile(SpriteBatch batch)
	{
		for (int i=0; i < projectiles.size() ; i++)
		{
			projectiles.get(i).draw(batch);
		}
	}

	public Vector2 getPos()
	{
		// Copie de protection
		// Empeche de modifier sa pos n'importe comment
		return new Vector2(pos);	
	}
	
	public float getVitesse()
	{ return vitesse; }
	
	public Vector2 getTaille()
	{ return new Vector2(taille); }
	
	public void setDeplacement(Vector2 deplacement)
	{ this.deplacement = deplacement; }

	public Vector2 getDeplacement()
	{ return new Vector2(deplacement); }
	
	public Texture getPortrait()
	{ return portrait; }
	
	public float getPourcentageVieRestant()
	{ return ((float)vie/(float)vieMax) * 100; }
	
	public float getPourcentageManaRestant()
	{ return ((float)mana/(float)manaMax) * 100; }
	
	public boolean estMort()
	{ return mort; }
	
	public boolean aFiniLevel()
	{ return finiLevel; }
	public int getOrientation()
	{ return orientation; }
}
