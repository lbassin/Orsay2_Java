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
		Vector2 deplacementCam = new Vector2(0, 0);
		int e = 0;
		
		if (!presenceEnnemis())
		{
			if(persos.get(e).getPos().x + persos.get(e).getTaille().x > (MyGdxGame.LARGEUR_ECRAN + deplacementTotalCam.x) - 350)
			{
				deplacementCam.x = persos.get(e).getVitesse();
			}
			else if(persos.get(e).getPos().x < deplacementTotalCam.x + 350)
			{
				deplacementCam.x = -persos.get(e).getVitesse();
			}
			
			if(persos.get(e).getPos().y + persos.get(e).getTaille().y> (MyGdxGame.HAUTEUR_ECRAN + deplacementTotalCam.y) - 150)
			{
				deplacementCam.y = persos.get(e).getVitesse();
			}
			else if(persos.get(e).getPos().y < deplacementTotalCam.y + 150)
			{
				deplacementCam.y = -persos.get(e).getVitesse();
			} 
			
			int i = 0;
			boolean persoDehors = false;
			while(i<persos.size() && !persoDehors)
			{	
				// On cherche si un joueur sort de l'ecran à cause du deplacement de la camera
				persoDehors = ((persos.get(i).getPos().x + persos.get(i).getDeplacement().x + persos.get(i).getTaille().x > 
							deplacementCam.x + deplacementTotalCam.x + MyGdxGame.LARGEUR_ECRAN) ||
							(persos.get(i).getPos().x + persos.get(i).getDeplacement().x <
							deplacementCam.x + deplacementTotalCam.x) ||
							(persos.get(i).getPos().y + persos.get(i).getDeplacement().y + persos.get(i).getTaille().y >
							deplacementCam.y + deplacementTotalCam.y + MyGdxGame.HAUTEUR_ECRAN) ||
							(persos.get(i).getPos().y + persos.get(i).getDeplacement().y <
							deplacementCam.y + deplacementTotalCam.y )
						);			
				i++;
			}
			
			if(!persoDehors)
			{
				cam.translate(deplacementCam);
				deplacementTotalCam.add(deplacementCam);
			}
			System.out.println(deplacementCam + " - " + persoDehors);
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
