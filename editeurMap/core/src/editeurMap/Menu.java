package editeurMap;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Menu {
	
	private Texture zoneMenu;
	private Texture zoneTileSelection;
	
	private Vector2 taille;
	private Vector2 pos;
	
	private Vector2 posZoneTiles;
	private Vector2 tailleZoneTiles;
	private int nbTileAfficheParLigne;
	
	private Vector2 tileSelectionEnUnite;
	private Tile tileSelection;
	
	private ImageTile imgTile;
	
	private Event event;
	
	public Menu(ImageTile imgTile, Event event)
	{
		taille = new Vector2(320, MyGdxGame.TAILLE_FENETRE.y);
		pos = new Vector2(MyGdxGame.TAILLE_FENETRE.x - taille.x, 0);
		
		posZoneTiles = new Vector2(pos.x + 32, MyGdxGame.TAILLE_FENETRE.y - 128);
		tailleZoneTiles = new Vector2(taille.x - 32, taille.y/2);
		
		tileSelectionEnUnite = new Vector2();
		
		nbTileAfficheParLigne = (int) ((tailleZoneTiles.y-32*3)/32);
		
		this.imgTile = imgTile;
		this.event = event;
		
		// Creation panel visuel
		Pixmap imgZoneTmp = new Pixmap((int)taille.x , (int)taille.y, Format.RGBA8888);
		imgZoneTmp.setColor(1f, 0.6f, 0.6f, 0.2f);
		imgZoneTmp.fillRectangle(0, 0, (int)taille.x, (int)taille.y);
		zoneMenu = new Texture(imgZoneTmp);
		
		Pixmap imgCurseurTmp = new Pixmap(32 , 32, Format.RGBA8888);
		imgCurseurTmp.setColor(0.2f, 0.2f, 1f, 0.5f);
		imgCurseurTmp.fillRectangle(0, 0, 32, 32);
		zoneTileSelection = new Texture(imgCurseurTmp);
		
	}
	
	public void draw(SpriteBatch batch)
	{
		batch.draw(zoneMenu, pos.x, pos.y);
		afficheSelectionTile(batch);
	}

	private void afficheSelectionTile(SpriteBatch batch)
	{
		int i;
		
		int x = (int) posZoneTiles.x;
		int y = (int) posZoneTiles.y;
		
		for(i=1; i < imgTile.getNbTile(); i++)
		{
			batch.draw(imgTile.getImgTile(i), x, y);
			
			if(((x-posZoneTiles.x)/32 == tileSelectionEnUnite.x) && ((posZoneTiles.y - y)/32 == tileSelectionEnUnite.y))
			{
				batch.draw(zoneTileSelection, x, y);
			}

				//System.out.println((posZoneTiles.y - y)/32 + " - " + tileSelectionEnUnite.y);
		
			
			x += 32;
			if(x + 32 >= posZoneTiles.x + tailleZoneTiles.x)
			{
				x = (int) posZoneTiles.x;
				y -= 32;
				
				if(y < pos.y - tailleZoneTiles.y)
					System.out.println("Debordement en Y des tiles du menu");
				
			}
		}
		
	}
	
	public void update()
	{
		// S'il y a un clic dans la zone du menu
		if(event.getClicGauche() && event.getPosSouris().x > pos.x && event.getPosSouris().x < pos.x + taille.x)
		{
			// Plus precissement dans la zone des tiles
			if(event.getPosSouris().x > posZoneTiles.x && event.getPosSouris().x < posZoneTiles.x + tailleZoneTiles.x)
			{
				if(event.getPosSouris().y < posZoneTiles.y + 32 && event.getPosSouris().y >= posZoneTiles.y - tailleZoneTiles.y + 32)
				{
					Vector2 posClic = event.getPosSouris();
					
					Vector2 numTile = new Vector2();
					
					numTile.x = (int)((posClic.x - posZoneTiles.x) / 32);
					numTile.y = (int)((posZoneTiles.y + 32 - posClic.y) / 32);
					
					if(numTile.x + (numTile.y*nbTileAfficheParLigne) < imgTile.getNbTile()-1)
					{
						tileSelectionEnUnite = numTile;
						tileSelection = new Tile(imgTile.getImgTile((int) (numTile.x + (numTile.y*nbTileAfficheParLigne)+1)));
					}
						
					
				}
			}
		}
	}
	
	public Tile getTileSelection()
	{ return tileSelection; }
	
}
