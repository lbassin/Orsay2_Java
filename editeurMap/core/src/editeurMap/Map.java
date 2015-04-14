package editeurMap;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Map {
	
	private Vector2 taille;
	// Pas une arrayList car on stock les coord en même temps
	private Tile[][] tiles;
	
	public Map(Vector2 taille)
	{
		tiles = new Tile[(int)taille.x][(int)taille.y];

		this.taille = new Vector2(taille);
	}

	public void ajoutTile(Vector2 posEnUnite, Tile tile)
	{
		// Verifie que la position n'est pas en dehors du tableau
		if(posEnUnite.x >= 0 && posEnUnite.y >= 0 && posEnUnite.x < taille.x && posEnUnite.y < taille.y)
		{
			tiles[(int)posEnUnite.x][(int)posEnUnite.y] = new Tile(tile);
		}
	}

	public void draw(SpriteBatch batch) {
		int i, e;
		
		for(i=0; i < taille.x; i++)
		{
			for(e=0; e < taille.y; e++)
			{
				// Affiche uniquement les tiles où il y a une image associé
				if(tiles[i][e] != null)
					batch.draw(tiles[i][e].getImg(), i*32, e*32);
			}
		}
		
	}
	
}
