package projetTuteure;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Niveau {
	private Map map;
	private ArrayList<Perso> persos;
	private GestionEnnemi ennemis;
	private Camera camera;
	private HUD hud;
	
	private boolean musique;
	private ArrayList<Sound> sound;
	
	private Texture imgFin[];
	private int imgFinActuelle;
	private int ralentiAnimFin;
	
	Niveau(String nomMap, String nomFichierCollision, ArrayList<Perso> persos, String nomFichierEnnemi, SpriteBatch batch, Vector2 posJoueur)
	{
		map = new Map(nomMap,nomFichierCollision);
		this.persos = persos;
		ennemis = new GestionEnnemi (nomFichierEnnemi);
		camera = new Camera (batch, this.persos, ennemis, map);
		hud = new HUD();
		
		float tmp = posJoueur.y;
		for(Perso perso : persos)
		{
			perso.init(new Vector2(posJoueur.x, tmp));
			hud.addJoueur(perso);
			tmp-=100;
		}
		
		musique = false;
		sound = new ArrayList<Sound>();
		sound.add(Gdx.audio.newSound(Gdx.files.internal("../core/assets/mort.mp3")));
		sound.add(Gdx.audio.newSound(Gdx.files.internal("../core/assets/fin.mp3")));
		
		sound.add(Gdx.audio.newSound(Gdx.files.internal("../core/assets/inGame.mp3")));
		/*
		long soundId = sound.get(2).play();
		sound.get(2).setLooping(soundId, true);
		*/
		imgFin = new Texture[2];
		imgFin[0] = new Texture("../core/assets/imgFin1.png");
		imgFin[1] = new Texture("../core/assets/imgFin2.png");
		imgFinActuelle = 0;
		ralentiAnimFin = 27/2; // 130 BPM

	}
	
	public void niveauUpdate()
	{
		for(Perso perso : persos)
		{
			perso.updateEvent();
			perso.update(ennemis.getListeEnnemis(), camera, map);
		}
		ennemis.update(persos, camera);
		camera.update();
	}	
	
	public void collision()
	{
		for(Perso perso : persos)
		{
			map.collision(perso);
			perso.collision(persos);		
		}
		for(Ennemi ennemi : ennemis.getListeEnnemis())
		{
			map.collision(ennemi);		
		}
	}
		
	public void deplacement()
	{
		for(Perso perso : persos)
			perso.deplacement();
		ennemis.deplacement();	
	}
	
	public void draw(SpriteBatch batch)
	{
		map.draw(batch);

		for(Perso perso : persos)
		{
			perso.draw(batch);
			perso.drawProjectile(batch);
		}
		ennemis.draw(batch);
		
	}
	
	public void gestionNiveau()
	{
		ennemis.suppressionEnnemi();
		for(Perso perso : persos)
			perso.gestionProjectile(camera);
	}
	
	public void mortPerso()
	{
		if(!musique)
		{
			ennemis.supprimerTousEnnemis();
			map = null;

		
			sound.get(2).stop();
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
			
			sound.get(2).stop();
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
		for(int i=0; i < sound.size(); i++)
			sound.get(i).stop(); 
		musique = false;
	}
	
	public boolean lastLevel()
	{
		return map.getNum() == Map.NB_MAP;
	}
	
	public void afficheImgFin(SpriteBatch batch)
	{		
		batch.draw(imgFin[imgFinActuelle/ralentiAnimFin], 0, 0);
		imgFinActuelle += 1;
		
		if(imgFinActuelle >= 2*ralentiAnimFin)
			imgFinActuelle = 0;
		
	}

	public void afficherHUD(SpriteBatch batchHUD) {
		hud.afficher(batchHUD);
	}
}
