package projetTuteure;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Niveau {
	private Map map;
	private Perso perso;
	private GestionEnnemi ennemis;
	private Camera camera;
	private int nbMusique;
	private boolean musique;
	private ArrayList<Sound> sound;
	
	Niveau(String nomMap, String nomFichierCollision, Perso perso, String nomFichierEnnemi, SpriteBatch batch)
	{
		map = new Map(nomMap,nomFichierCollision);
		this.perso = perso;
		ennemis = new GestionEnnemi (nomFichierEnnemi);
		camera = new Camera (batch, this.perso, ennemis);
		perso.init(new Vector2(400, 200));
		nbMusique = 2;
		musique = false;
		sound = new ArrayList<Sound>();
		sound.add(Gdx.audio.newSound(Gdx.files.internal("../core/assets/mort.mp3")));
		sound.add(Gdx.audio.newSound(Gdx.files.internal("../core/assets/fin.mp3")));
	}
	
	public void niveauUpdate()
	{

		perso.updateEvent();
		perso.update(ennemis.getListeEnnemis(), camera, map);
		ennemis.update(perso, camera);
		camera.update();
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
		
			long idSound = sound.get(0).play();
			sound.get(0).setLooping(idSound, true);
			musique = true;
		}
	}
	
	public void victoirePerso()
	{
		if(!musique)
		{
			ennemis.supprimerTousEnnemis();
			//map = null;
			perso = null;
			
			long idSound = sound.get(1).play();
			sound.get(1).setLooping(idSound, true);
			musique = true;
		}
	}
	
	public Vector2 getCameraDeplacement()
	{
		return camera.getDeplacementTotalCam();
	}
	
	public void stopMusique()
	{ 
		for(int i=0; i < nbMusique; i++)
			sound.get(i).stop(); 
		musique = false;
	}
	
	public boolean lastLevel()
	{
		return map.getNum()== Map.NB_MAP;
	}
}
