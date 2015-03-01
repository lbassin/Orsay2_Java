package projetTuteure;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Tile {

	private Vector2 pos;
	private TextureRegion img;
	
	Tile(Vector2 pos, TextureRegion img)
	{
		this.pos = pos;
		this.img = img;
	}
	
	public void afficher(SpriteBatch batch)
	{
		batch.draw(img, pos.x, pos.y);
	}
	
}
