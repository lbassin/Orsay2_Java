package editeurMap;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Tile {
	
	private Vector2 taille;
	private TextureRegion img;
	
	public Tile(TextureRegion img)
	{
		this.taille = new Vector2(32, 32);
		this.img = img;
	}
	
	public Tile(Tile tile) {
		if(tile != null)
		{
			this.taille = new Vector2(tile.taille);
			this.img = new TextureRegion(tile.img);			
		}
	}

	public TextureRegion getImg()
	{ return img; }
	
	public Vector2 getTaille()
	{ return taille; }

}
