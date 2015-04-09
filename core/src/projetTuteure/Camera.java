package projetTuteure;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Camera {


	private OrthographicCamera cam;
	private SpriteBatch batch;
	
	private Vector2 deplacementTotalCam;
	
	private Perso perso;
	
	public Camera(SpriteBatch batch, Perso perso)
	{
		this.batch = batch;
		this.perso = perso;

		deplacementTotalCam = new Vector2(0,0);
		
		cam = new OrthographicCamera(MyGdxGame.LARGEUR_ECRAN, MyGdxGame.HAUTEUR_ECRAN);
        
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
	}
	
	public void update()
	{
		if(perso.getPos().x > (MyGdxGame.LARGEUR_ECRAN + deplacementTotalCam.x) - 350)
		{
			cam.translate(perso.getVitesse(), 0);
			deplacementTotalCam.x += perso.getVitesse();
		}
		else if(perso.getPos().x < deplacementTotalCam.x + 350)
		{
			cam.translate(-perso.getVitesse(), 0);
			deplacementTotalCam.x -= perso.getVitesse();
		}

		batch.setProjectionMatrix(cam.combined);
		cam.update();
	}
}
