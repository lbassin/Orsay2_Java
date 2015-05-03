package projetTuteure;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Niveau {
	private Map map;
	private Perso perso;
	private GestionEnnemi ennemis;
	private Camera camera;
	
	Niveau(Vector2 pos, String nomMap, String nomFichierCollision, Perso perso, String nomFichierEnnemi, SpriteBatch batch)
	{
		map = new Map(nomMap,nomFichierCollision);
		this.perso = perso;
		ennemis = new GestionEnnemi (nomFichierEnnemi);
		camera = new Camera (batch, this.perso);
		perso.resetPerso(pos);
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
}
