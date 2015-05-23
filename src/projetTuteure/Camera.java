package projetTuteure;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Camera {


	private OrthographicCamera cam;
	private SpriteBatch batch;
	
	private Vector2 deplacementTotalCam;
	
	private Perso perso;
	
	private GestionEnnemi ennemis; 
	
	public Camera(SpriteBatch batch, Perso perso, GestionEnnemi ennemis)
	{
		this.batch = batch;
		this.perso = perso;
		this.ennemis = ennemis;

		deplacementTotalCam = new Vector2(0,0);
		
		cam = new OrthographicCamera(MyGdxGame.LARGEUR_ECRAN, MyGdxGame.HAUTEUR_ECRAN);
        
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();
	}
	
	public void update()
	{
		if (!presenceEnnemis())
		{
			if(perso.getPos().x + perso.getTaille().x > (MyGdxGame.LARGEUR_ECRAN + deplacementTotalCam.x) - 350)
			{
				cam.translate(perso.getVitesse(), 0);
				deplacementTotalCam.x += perso.getVitesse();
			}
			else if(perso.getPos().x < deplacementTotalCam.x + 350)
			{
				cam.translate(-perso.getVitesse(), 0);
				deplacementTotalCam.x -= perso.getVitesse();
			}
			
			if(perso.getPos().y + perso.getTaille().y> (MyGdxGame.HAUTEUR_ECRAN + deplacementTotalCam.y) - 150)
			{
				cam.translate(0, perso.getVitesse());
				deplacementTotalCam.y += perso.getVitesse();
			}
			else if(perso.getPos().y < deplacementTotalCam.y + 150)
			{
				cam.translate(0, -perso.getVitesse());
				deplacementTotalCam.y -= perso.getVitesse();
			} 
		}
		else 
		{
			if( perso.getPos().x + perso.getDeplacement().x <= deplacementTotalCam.x 
					|| perso.getPos().x + perso.getDeplacement().x + perso.getTaille().x >= deplacementTotalCam.x + MyGdxGame.LARGEUR_ECRAN)
				perso.setDeplacement(new Vector2(0, perso.getDeplacement().y));
			if(perso.getPos().y + perso.getDeplacement().y <= deplacementTotalCam.y 
					|| perso.getPos().y + perso.getDeplacement().y + perso.getTaille().y >= deplacementTotalCam.y + MyGdxGame.HAUTEUR_ECRAN)
				perso.setDeplacement(new Vector2(perso.getDeplacement().x, 0));
		}
		
		batch.setProjectionMatrix(cam.combined);
		cam.update();
	}
	
	public boolean presenceEnnemis()
	{
		boolean present = false;
		int i = 0;
		while (!present && i < ennemis.getNbEnnemis())
		{
			present = (((ennemis.getListeEnnemis().get(i).getPos().x + ennemis.getListeEnnemis().get(i).getTaille().x > deplacementTotalCam.x
								&& ennemis.getListeEnnemis().get(i).getPos().x + ennemis.getListeEnnemis().get(i).getTaille().x < MyGdxGame.LARGEUR_ECRAN + deplacementTotalCam.x)
							||(ennemis.getListeEnnemis().get(i).getPos().x < MyGdxGame.LARGEUR_ECRAN + deplacementTotalCam.x
								&& ennemis.getListeEnnemis().get(i).getPos().x > deplacementTotalCam.x))
						&&((ennemis.getListeEnnemis().get(i).getPos().y + ennemis.getListeEnnemis().get(i).getTaille().y > deplacementTotalCam.y
								&& ennemis.getListeEnnemis().get(i).getPos().y + ennemis.getListeEnnemis().get(i).getTaille().y < MyGdxGame.HAUTEUR_ECRAN + deplacementTotalCam.y)
							||(ennemis.getListeEnnemis().get(i).getPos().y < MyGdxGame.HAUTEUR_ECRAN + deplacementTotalCam.y
								&& ennemis.getListeEnnemis().get(i).getPos().y > deplacementTotalCam.y)));
			i++;
		}
		return present;
	}
	
	public Vector2 getDeplacementTotalCam()
	{ return deplacementTotalCam;}
}
