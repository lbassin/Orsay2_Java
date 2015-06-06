package projetTuteure;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Camera {


	private OrthographicCamera cam;
	private SpriteBatch batch;
	
	private Vector2 deplacementTotalCam;
	
	private ArrayList<Perso> persos;
	
	private GestionEnnemi ennemis; 
	
	public Camera(SpriteBatch batch, ArrayList<Perso> persos, GestionEnnemi ennemis)
	{
		this.batch = batch;
		this.persos = persos;
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
			if(persos.get(0).getPos().x + persos.get(0).getTaille().x > (MyGdxGame.LARGEUR_ECRAN + deplacementTotalCam.x) - 350)
			{
				cam.translate(persos.get(0).getVitesse(), 0);
				deplacementTotalCam.x += persos.get(0).getVitesse();
			}
			else if(persos.get(0).getPos().x < deplacementTotalCam.x + 350)
			{
				cam.translate(-persos.get(0).getVitesse(), 0);
				deplacementTotalCam.x -= persos.get(0).getVitesse();
			}
			
			if(persos.get(0).getPos().y + persos.get(0).getTaille().y> (MyGdxGame.HAUTEUR_ECRAN + deplacementTotalCam.y) - 150)
			{
				cam.translate(0, persos.get(0).getVitesse());
				deplacementTotalCam.y += persos.get(0).getVitesse();
			}
			else if(persos.get(0).getPos().y < deplacementTotalCam.y + 150)
			{
				cam.translate(0, -persos.get(0).getVitesse());
				deplacementTotalCam.y -= persos.get(0).getVitesse();
			} 
		}
		else 
		{
			if( persos.get(0).getPos().x + persos.get(0).getDeplacement().x <= deplacementTotalCam.x 
					|| persos.get(0).getPos().x + persos.get(0).getDeplacement().x + persos.get(0).getTaille().x >= deplacementTotalCam.x + MyGdxGame.LARGEUR_ECRAN)
				persos.get(0).setDeplacement(new Vector2(0, persos.get(0).getDeplacement().y));
			if(persos.get(0).getPos().y + persos.get(0).getDeplacement().y <= deplacementTotalCam.y 
					|| persos.get(0).getPos().y + persos.get(0).getDeplacement().y + persos.get(0).getTaille().y >= deplacementTotalCam.y + MyGdxGame.HAUTEUR_ECRAN)
				persos.get(0).setDeplacement(new Vector2(persos.get(0).getDeplacement().x, 0));
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
