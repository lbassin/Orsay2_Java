package projetTuteure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

public class Map {
	
	// Debug
	
	private Vector2 tailleMap;
	private Vector2 tailleTile;
	private String nomImg;
	private ArrayList<Tile> tiles;
	private int nb;
	private Texture img;
	
	Map(String nom)
	{
		tailleMap = new Vector2();
		tailleTile = new Vector2();
		nomImg = new String();
		tiles = new ArrayList<Tile>();
		
		Scanner fichier;
		
		int i,j;
		
		try 
		{
			fichier = new Scanner(new File("../core/assets/"+nom));
			
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
				for(j = 0; j < tailleMap.x; j++)
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
									new TextureRegion(img, (int)posTile.x, (int)posTile.y, (int)tailleTile.x, (int)tailleTile.y)));
				}
			}
			
		} 
		catch (FileNotFoundException e) 
		{
			System.err.println("Fichier de carte non trouvé");
			System.exit(4);
		}	
		
	}
	
	public void draw(SpriteBatch batch)
	{
		int i;
		
		for(i = 0; i < tiles.size(); i++)
		{
			tiles.get(i).afficher(batch);
		}
	}
}
