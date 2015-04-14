package editeurMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Event implements InputProcessor {
	
	private boolean getClicGauche;
	private Vector2 posSouris;
	
	private Vector2 posTileCible;
	
	private Texture imgCurseur;
	
	public Event()
	{
		posSouris = new Vector2();
		getClicGauche = false;
		
		posTileCible = new Vector2();
		
		Pixmap imgCurseurTmp = new Pixmap(32 , 32, Format.RGBA8888);
		imgCurseurTmp.setColor(0.2f, 0.2f, 1f, 0.5f);
		imgCurseurTmp.fillRectangle(0, 0, 32, 32);
		
		imgCurseur = new Texture(imgCurseurTmp);
		
		Gdx.input.setInputProcessor(this);
	}
	
	public void update()
	{
		posSouris.x = Gdx.input.getX();
		posSouris.y = MyGdxGame.TAILLE_FENETRE.y - Gdx.input.getY();
		
		posTileCible.x = (int) (posSouris.x / 32);
		posTileCible.y = (int) (posSouris.y / 32);
	}
	
	public void drawCurseur(SpriteBatch batch) {
		batch.draw(imgCurseur, posTileCible.x*32, posTileCible.y*32);
	}
	
	public Vector2 getPosSouris()
	{ return posSouris;	}
	
	public Vector2 getPosSourisEnUnite() 
	{ return new Vector2((int)(posSouris.x/32),(int)(posSouris.y/32)); }

	public boolean getClicGauche()
	{ return getClicGauche; }

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		getClicGauche = (button == Input.Buttons.LEFT);
		
		return true;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		if(button == Input.Buttons.LEFT)
			getClicGauche = false;
		
		return true;
	}

	
	
	
	
	
	
	public boolean keyDown(int keycode) {
		return false;
	}

	public boolean keyUp(int keycode) {
		return false;
	}

	public boolean keyTyped(char character) {
		return false;
	}
	
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	public boolean scrolled(int amount) {
		return false;
	}


}
