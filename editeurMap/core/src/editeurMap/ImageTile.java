package editeurMap;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ImageTile {
	
	private Texture img;
	private Vector2 nbTiles;
	private ArrayList<Tile> tiles;
	
	private String nomFichier;
	
	public ImageTile(String nomImg)
	{
		img = new Texture(nomImg);
		nbTiles = new Vector2();
		nbTiles.x = img.getWidth() / 32;
		nbTiles.y = img.getHeight() / 32;
		
		tiles = new ArrayList<Tile>();
		
		nomFichier = nomImg;
		
		initTiles();
	}
	
	public void initTiles()
	{
		int i;
		
		int x = 0;
		int y = 0;
		
		tiles.add(new Tile(new TextureRegion(img, 0, 0, 32, 32)));
		
		for(i = 0; i < nbTiles.x * nbTiles.y; i++)
		{
			TextureRegion imgTmp = new TextureRegion(img, x, y, 32, 32);
			
			tiles.add(new Tile(imgTmp));			 

			x += 32;
			if(x >= img.getWidth())
			{
				x = 0;
				y += 32;
			}
			
		}
	}

	public TextureRegion getImgTile(int id)
	{
		if(id > 0 && id < tiles.size())
		{
			return tiles.get(id).getImg();
		}
		
		return null;
	}
	
	public String getNomFichier()
	{ return new String(nomFichier); }

	public int getNbTile() 
	{ return tiles.size(); }
	
}
