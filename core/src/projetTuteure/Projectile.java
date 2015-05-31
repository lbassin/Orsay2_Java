package projetTuteure;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    //Declaration des variables de la classe
	
	// TODO : Pq protected ?
	
    protected Vector2 pos;
    protected Vector2 deplacement;
    protected float vitesse;
    protected Vector2 taille;
    
    protected int id;
    
    protected Texture img;

    protected boolean touche;
    
    protected int orientation;
    
    protected int degat;
    
    protected int coutMana;

    //Constructeur de la classe
    Projectile (Perso perso, int id)
    {
        pos = new Vector2();
        orientation = perso.getOrientation();
        this.id=id;
        switch (id)
        {
        	case 0 : img = new Texture("carreGreen.jpg"); coutMana = 5; degat = 10; break;
        	case 1 : img = new Texture("carreRed.jpg"); coutMana = 15; degat = 25;break;
        	case 2 : img = new Texture("carreBlue.jpg"); coutMana = 35; degat = 50;break;
        	case 3 : img = new Texture("carreYellow.jpg"); coutMana = 65; degat = 30;break;
        }
        taille = new Vector2();
        taille.x = img.getHeight();
		taille.y = img.getWidth();
		
        if (orientation == Perso.DROITE)
        {
            this.pos.x = perso.getPos().x + perso.getTaille().x;
            this.pos.y = perso.getPos().y;
        }
        else
        {
            this.pos.x = perso.getPos().x - taille.x;
            this.pos.y = perso.getPos().y;
        }
        deplacement = new Vector2();
        vitesse = 13;
       
		touche = false;
		
		
		
    }

    public Vector2 getPos()
    { return pos; }

    //Procedure de mise a jour
    public void update(Camera camera)
    {
    	if(id == 0){
	    	if(orientation == Perso.DROITE)
	    	{
	    		taille.x = vitesse;
	    	}
	    	else if(orientation == Perso.GAUCHE)
	    	{
	    		taille.x = -vitesse;
	    	}
    	}
    	else if(id == 1){
	    	if(orientation == Perso.DROITE)
	    	{
		        deplacement.x = vitesse;
	    	}
	    	else if(orientation == Perso.GAUCHE)
	    	{
	    		deplacement.x = -vitesse;
	    	}
    	}
    	else if(id == 2){
	    	if(orientation == Perso.DROITE)
	    	{
		        deplacement.x = vitesse;
	    	}
	    	else if(orientation == Perso.GAUCHE)
	    	{
	    		deplacement.x = -vitesse;
	    	}
    	}
    	else if(id == 3){
	    	if(orientation == Perso.DROITE)
	    	{
	    		taille.x += vitesse*2;
	    	}
	    	else if(orientation == Perso.GAUCHE)
	    	{
	    		taille.x += -vitesse*2;
	    	}
    	}
    }

    //Procedure de deplacement du projectile
    public void deplacement()
    {
        pos.x += deplacement.x;
        pos.y += deplacement.y;
        
        deplacement.x = 0;
        deplacement.y = 0;
    }
    
    //Gestion de la collision
    public void collision (ArrayList <Ennemi>ennemis)
    {   
 	   ArrayList <Vector2> posEnnemies = new ArrayList <Vector2>();
 	   ArrayList <Vector2> tailleEnnemies = new ArrayList <Vector2>();
 	   
 	   for (int i=0; i<ennemis.size(); i++)
 	   {
 		   posEnnemies.add(ennemis.get(i).getPos());
 		   
 		   // Ajoute le deplacement pour anticiper la position
 		   // Pour ne pas se retrouver dans l'ennemi
 				posEnnemies.get(i).x += ennemis.get(i).getDeplacement().x;
 				posEnnemies.get(i).y += ennemis.get(i).getDeplacement().y;
 			
 			tailleEnnemies.add(ennemis.get(i).getTaille());
 			
 			if ((pos.x < posEnnemies.get(i).x + tailleEnnemies.get(i).x && pos.x > posEnnemies.get(i).x) 
 					|| (pos.x + taille.x < posEnnemies.get(i).x + tailleEnnemies.get(i).x && pos.x + taille.x > posEnnemies.get(i).x))
 			{
 				if ((pos.y < posEnnemies.get(i).y + tailleEnnemies.get(i).y && pos.y > posEnnemies.get(i).y) 
 						|| (pos.y + taille.y < posEnnemies.get(i).y + tailleEnnemies.get(i).y && pos.y + taille.y > posEnnemies.get(i).y))
 				{
 					ennemis.get(i).enleverVie(degat);
 					
 					if(ennemis.get(i).getVieRestant() <= 0)
 						ennemis.get(i).setMort(true);
 					
 					touche = true;
 				}
 			}
 	   }
    }
    
    public int getId()
    {
    	return (id);
    }
    
    public int getCoutMana()
    {
    	return (coutMana);
    }
    
    public Vector2 getTaille()
    {
    	return (taille);
    }
    
    //Procedure d'affichage
    public void draw(SpriteBatch batch)
    {
        batch.draw(img, pos.x, pos.y, taille.x, taille.y);
    }
    
    public boolean aTouche()
    { return touche; }
}