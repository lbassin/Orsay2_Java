package projetTuteure;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Niveau {
	private Map map;
	private Perso perso;
	private GestionEnnemi ennemis;
	private Camera camera;
	private boolean musique;
	private Sound sound;
	
	Niveau(String nomMap, String nomFichierCollision, Perso perso, String nomFichierEnnemi, SpriteBatch batch)
	{
		map = new Map(nomMap,nomFichierCollision);
		this.perso = perso;
		ennemis = new GestionEnnemi (nomFichierEnnemi);
		camera = new Camera (batch, this.perso);
		perso.init(new Vector2(400, 200));
		musique = false;
		sound = Gdx.audio.newSound(Gdx.files.internal("../core/assets/mort.mp3"));
	}
	
	public void niveauUpdate()
	{
		camera.update();
		perso.updateEvent();
		perso.update(ennemis.getListeEnnemis(), camera, map);
		ennemis.update(perso);
	}	
	
	public void collision()
	{
		map.collision(perso);
	}
	
	public void deplacement()
	{
		perso.deplacement();
		ennemis.deplacement();	
	}
	
	public void draw(SpriteBatch batch)
	{
		map.draw(batch);
		perso.draw(batch);
		perso.drawProjectile(batch);
		ennemis.draw(batch);
	}
	
	public void gestionNiveau()
	{
		ennemis.suppressionEnnemi();
		perso.gestionProjectile(camera);
	}
	
	public void mortPerso()
	{
		if(!musique)
		{
			ennemis.supprimerTousEnnemis();
			map = null;
			perso = null;
		
			sound.play();
			musique = true;
		}
	}
	
	public Vector2 getCameraDeplacement()
	{
		return camera.getDeplacementTotalCam();
	}
	
	public void stopMusique()
	{ 
		sound.stop(); 
		musique = false;
	}
}
