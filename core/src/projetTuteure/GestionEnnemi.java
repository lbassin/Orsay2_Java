package projetTuteure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GestionEnnemi {
	private ArrayList <Ennemi> ennemis;
	
	GestionEnnemi(String nom)
	{
		ennemis = new ArrayList <Ennemi>();
		Scanner fichier;
		int posX, posY;
		int nbEnnemis;
		try
		{
			fichier = new Scanner (new File("../core/assets/"+nom));
			nbEnnemis = fichier.nextInt();
			for (int i=0; i<nbEnnemis; i++)
			{
				posX = fichier.nextInt();
				posY = fichier.nextInt();
				ennemis.add(new Ennemi(posX, posY));
			}
		}
		catch (FileNotFoundException e)
		{
			System.err.println("Fichier d'ennemi non trouv�");
			System.exit(5);
		}
	}
	
	public ArrayList<Ennemi> getListeEnnemis()
	{
		return ennemis;
	}
	
	public void update(Perso cible)
	{
		for(int i=0; i<ennemis.size(); i++)
			ennemis.get(i).update(cible);
		
		// Test collision entre ennemis
		int i, e;
		for(i=0; i<ennemis.size(); i++)
		{
			for(e=0; e<ennemis.size(); e++) 
			{
				// Test entre i et e
				// Test entre e et i
				if(i != e) // Ne pas tester avec soit même
				{
					// Si l'autre ennemis est dans une direction et qu'il se dirige vers l'autre ennemis, on empeche deplacement
					if(ennemis.get(i).collision(ennemis.get(e).getPos().add(ennemis.get(e).getDeplacement()), ennemis.get(e).getTaille()))
					{
						// Test sur l'axe x
						if((ennemis.get(e).getPos().x >= ennemis.get(i).getPos().x + ennemis.get(i).getTaille().x) && // e est à droite de i
								(ennemis.get(i).getDeplacement().x > 0)) // et i vas à droite (vers e)
							ennemis.get(i).setDeplacement(new Vector2(0, ennemis.get(i).getDeplacement().y)); // i se deplace pas
						
						else if((ennemis.get(e).getPos().x + ennemis.get(e).getTaille().x < ennemis.get(i).getPos().x) && // e est à gauche de i
								(ennemis.get(i).getDeplacement().x < 0)) // et i vas à gauche (vers e)
							ennemis.get(i).setDeplacement(new Vector2(0, ennemis.get(i).getDeplacement().y)); // i se deplace pas
						
						// Test sur l'axe y
						else if((ennemis.get(e).getPos().y >= ennemis.get(i).getPos().y + ennemis.get(i).getTaille().y) && // e est au dessus de i
								(ennemis.get(i).getDeplacement().y > 0)) // et i vas vers le haut (vers e)
							ennemis.get(i).setDeplacement(new Vector2(ennemis.get(i).getDeplacement().x, 0)); // i se deplace pas
						
						else if((ennemis.get(e).getPos().y + ennemis.get(e).getTaille().y <= ennemis.get(i).getPos().y) && // e est en dessous de i
								(ennemis.get(i).getDeplacement().y < 0)) // et i vas vers le bas (vers e)
							ennemis.get(i).setDeplacement(new Vector2(ennemis.get(i).getDeplacement().x, 0)); // i se deplace pas
						
						// Si aucun des cas, on annule tout (evite emboitage)
						else
							ennemis.get(i).setDeplacement(new Vector2(0,0));
					}
				}
			}
			
		}
	}
	
	public void deplacement ()
	{
		for(int i=0; i<ennemis.size(); i++)
			ennemis.get(i).deplacement();
	}
	
	public void draw(SpriteBatch batch)
	{
		for(int i=0; i<ennemis.size(); i++)
			ennemis.get(i).afficher(batch);
	}
	
	public void suppressionEnnemi()
	{
		for(int i=0; i<ennemis.size(); i++)
			if (ennemis.get(i).getMort())
			{
				ennemis.remove(i);
			}	
	}
	
	public void supprimerTousEnnemis()
	{
		ennemis.removeAll(ennemis);
	}
	
	public int getNbEnnemis()
	{
		return ennemis.size();
	}
}