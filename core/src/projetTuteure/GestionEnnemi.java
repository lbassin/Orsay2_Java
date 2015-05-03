package projetTuteure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

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
					if(ennemis.get(i).collision(ennemis.get(e).getPos().add(ennemis.get(e).getDeplacement()), ennemis.get(e).getTaille()))
					{
						if((ennemis.get(e).getPos().y >= ennemis.get(i).getPos().y + ennemis.get(i).getTaille().y) || // Si i est dessous ou dessus de e
								(ennemis.get(e).getPos().y + ennemis.get(e).getTaille().y <= ennemis.get(i).getPos().y))
							ennemis.get(i).setDeplacement(new Vector2(ennemis.get(i).getDeplacement().x, 0));
							
						if((ennemis.get(e).getPos().x >= ennemis.get(i).getPos().x + ennemis.get(i).getTaille().x) || // Si i est à gauche ou à droite de e
								(ennemis.get(e).getPos().x + ennemis.get(e).getTaille().x < ennemis.get(i).getPos().x))
							ennemis.get(i).setDeplacement(new Vector2(0, ennemis.get(i).getDeplacement().y));
					}
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