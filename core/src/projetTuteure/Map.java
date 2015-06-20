package projetTuteure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Map {
	
    public static final int NB_MAP = 3;
	
	private Vector2 tailleMap;
	private Vector2 tailleTile;
	
	private String nomImg;
	private int num;
	
	private ArrayList<Tile> tiles;
	private ArrayList<Integer> tilePassable;
	private int nbTilePassable;
	
	private Texture img;

	Map(String nom, String nomCollision)
	{
		tailleMap = new Vector2();
		tailleTile = new Vector2();
		
		nomImg = new String();
		
		tiles = new ArrayList<Tile>();
		tilePassable = new ArrayList<Integer>();
		
		Scanner fichier;
		
		int i;
		
		try 
		{
			fichier = new Scanner(new File("../core/assets/"+nom));
			
			num = fichier.nextInt();
			tailleMap.x = fichier.nextInt();
			tailleMap.y = fichier.nextInt();
			tailleTile.x = fichier.nextInt();
			tailleTile.y = fichier.nextInt();
			
			fichier.nextLine();
			nomImg = fichier.nextLine();
			
			img = new Texture("../core/assets/" + nomImg);

			int nbTileTexWidth = (int) (img.getWidth()/tailleTile.x);
			
			for(i = 0; i < tailleMap.y; i++)
			{
				for(int j = 0; j < tailleMap.x; j++)
				{
					// Recupere id du tile
					int numTile = fichier.nextInt();
					
					
					// Calcul position du tile en fonction du nombre de tile
					// dans l'img de texture
					Vector2 posTile = new Vector2(
											(numTile%nbTileTexWidth - 1) * tailleTile.y,
												(numTile/nbTileTexWidth) * tailleTile.x);
					
					tiles.add(new Tile(
								new Vector2(j*tailleTile.x, 
										MyGdxGame.HAUTEUR_ECRAN - i*tailleTile.y - tailleTile.y),
									new TextureRegion(img, (int)posTile.x, (int)posTile.y, (int)tailleTile.x, (int)tailleTile.y),
										numTile));
				}
			}
			
			fichier = new Scanner(new File("../core/assets/"+nomCollision));
			
			nbTilePassable = fichier.nextInt();
			
			for(i = 0; i < nbTilePassable; i++)
				tilePassable.add(fichier.nextInt());
			
		} 
		catch (FileNotFoundException e) 
		{
			System.err.println("Fichier de carte non trouv�");
			System.exit(4);
		}	
		
	}
	
	public void collision(Perso perso)
	{
		Vector2 posPerso = new Vector2(perso.getPos());
		
		// Ajoute le deplacement pour anticiper la position
		// Pour ne pas se retrouver dans le mur
		posPerso.x += perso.getDeplacement().x;
		posPerso.y += perso.getDeplacement().y;		
		
		Vector2 taillePerso = new Vector2(perso.getTaille());
		
		taillePerso.y /= 2;
		
		int cptTile = 0;
		//Optimisation : while(tile.y < perso.y)
		for(int i = 0; i < tailleMap.y; i++)
		{
			for(int j = 0; j < tailleMap.x; j++)
			{
				Vector2 posTile = new Vector2(tiles.get(cptTile).getPos());
				// Test la collision de la hitbox du tile et du perso
				
				// collisions x
				if((posTile.x < posPerso.x + taillePerso.x && posTile.x > posPerso.x) 
					|| (posTile.x + tailleTile.x < posPerso.x + taillePerso.x && posTile.x + tailleTile.x > posPerso.x))
				{
					// collisions y
					if((posTile.y < posPerso.y + taillePerso.y && posTile.y > posPerso.y) 
					|| (posTile.y + tailleTile.y < posPerso.y + taillePerso.y && posTile.y + tailleTile.y > posPerso.y))
					{
						// TODO : Remplacer par dichotomie
						int e = 0;
						boolean tileTrouve = false;
						while(e < tilePassable.size() && !tileTrouve)
						{
							tileTrouve = (tilePassable.get(e) == tiles.get(cptTile).getNum());
							e++;
						}
						
						if(!tileTrouve)
							perso.setDeplacement(new Vector2(0, 0));
					}
				}
				cptTile++;
			}
		}
	}
	
	public boolean estMapFinale()
	{
		return (num == NB_MAP);
	}
	
	public void draw(SpriteBatch batch)
	{		
		for(int i = 0; i < tiles.size(); i++)
		{
			tiles.get(i).afficher(batch);
		}
	}
	
	public Vector2 getTailleMap()
	{ return tailleMap; }
	public Vector2 getTailleTile()
	{ return tailleTile; }
	public int getNum()
	{ return num; }
}
