package projetTuteure;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Tile {

	private int numTile;
	private Vector2 pos;
	private TextureRegion img;
	
	Tile(Vector2 pos, TextureRegion img, int numTile)
	{
		this.pos = pos;
		this.img = img;
		this.numTile = numTile;
	}
	
	public void afficher(SpriteBatch batch)
	{
		batch.draw(img, pos.x, pos.y);
	}
	
	public Vector2 getPos()
	{ return pos; }
	
	public int getNum()
	{ return numTile; }
	
}
