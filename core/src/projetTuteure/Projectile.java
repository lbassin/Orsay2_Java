package projetTuteure;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    //Déclaration des variables de la classe
	
	// TODO : Pq protected ?
	
    protected Vector2 pos;
    protected Vector2 deplacement;

    protected Texture img;

    protected float vitesse;
    protected Vector2 taille;
    protected boolean touche;
    
    protected int orientation;

    //Constructeur de la classe
    Projectile (Vector2 posPerso, int id, int orientation)
    {
        pos = new Vector2();
        deplacement = new Vector2();
        switch (id)
        {
        	case 0 : img = new Texture("carreGreen.jpg"); break;
        	case 1 : img = new Texture("carreRed.jpg"); break;
        	case 2 : img = new Texture("carreBlue.jpg"); break;
        	case 3 : img = new Texture("carreYellow.jpg"); break;
        }
        vitesse = 10;
        this.pos.x = posPerso.x;
        this.pos.y = posPerso.y;
        taille = new Vector2();
        taille.x = img.getHeight();
		taille.y = img.getWidth();
		touche = false;
		this.orientation = orientation;
    }

    public Vector2 getPos()
    {
    	return pos;
    }
    public boolean aTouche()
    {
    	return touche;
    }
    //Procèdure de mise à jour
    public void update(Camera camera)
    {
    	if(orientation == Perso.DROITE)
    	{
	        if (this.pos.x < 1315 + camera.getDeplacementTotalCam().x)
	            deplacement.x = vitesse;
    	}
    	else if(orientation == Perso.GAUCHE)
    	{
    		if (this.pos.x > -35 + camera.getDeplacementTotalCam().x)
	            deplacement.x = -vitesse;
    	}
    }

    //Procèdure de déplacement du projectile
    public void deplacement()
    {
        pos.x += deplacement.x;
        pos.y += deplacement.y;
        
        deplacement.x = 0;
        deplacement.y = 0;
    }

    //Procèdure d'affichage
    public void draw(SpriteBatch batch)
    {
        batch.draw(img, pos.x, pos.y);
    }
    
   public void collision (ArrayList <Ennemi>ennemis)
   {
	   int i;
	   for (i=0; i<ennemis.size(); i++)
	   {
		   ArrayList <Vector2> posEnnemies = new ArrayList <Vector2>();
		   posEnnemies.add(ennemis.get(i).getPos());
		   
		   // Ajoute le deplacement pour anticiper la position
		   // Pour ne pas se retrouver dans l'ennemi
				posEnnemies.get(i).x += ennemis.get(i).getDeplacement().x;
				posEnnemies.get(i).y += ennemis.get(i).getDeplacement().y;
			
			ArrayList <Vector2> tailleEnnemies = new ArrayList <Vector2>();
			tailleEnnemies.add(ennemis.get(i).getTaille());
			
			
			if ((pos.x < posEnnemies.get(i).x + tailleEnnemies.get(i).x && pos.x > posEnnemies.get(i).x) 
					|| (pos.x + taille.x < posEnnemies.get(i).x + tailleEnnemies.get(i).x && pos.x + taille.x > posEnnemies.get(i).x))
			{
				if ((pos.y < posEnnemies.get(i).y + tailleEnnemies.get(i).y && pos.y > posEnnemies.get(i).y) 
						|| (pos.y + taille.y < posEnnemies.get(i).y + tailleEnnemies.get(i).y && pos.y + taille.y > posEnnemies.get(i).y))
				{
					ennemis.get(i).setMort(true);
					touche = true;
				}
			}
	   }
   }
}