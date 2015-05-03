package projetTuteure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GestionEnnemi {
	private ArrayList <Ennemi> ennemis;
	private int nbEnnemis;
	
	GestionEnnemi(String nom)
	{
		ennemis = new ArrayList <Ennemi>();
		Scanner fichier;
		int posX, posY;
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
	public int getNbEnnemis()
	{
		return nbEnnemis;
	}
	public void update(Perso cible)
	{
		for(int i=0; i<nbEnnemis; i++)
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
					// TODO : Test collision entre 2 ennemis
				}
			}
			
		}
	}
	public void deplacement ()
	{
		for(int i=0; i<nbEnnemis; i++)
			ennemis.get(i).deplacement();
	}
	public void draw(SpriteBatch batch)
	{
		for(int i=0; i<nbEnnemis; i++)
			ennemis.get(i).afficher(batch);
	}
	public void suppressionEnnemi()
	{
		for(int i=0; i<nbEnnemis; i++)
			if (ennemis.get(i).getMort())
			{
				ennemis.remove(i);
				nbEnnemis--;
			}	
	}
}