package projetTuteure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    //Déclaration des variables de la classe
    protected Vector2 pos;
    protected Vector2 deplacement;

    protected Texture img;

    protected float vitesse;

    //Constructeur de la classe
    Projectile (Vector2 posPerso, int id)
    {
        pos = new Vector2();
        deplacement = new Vector2();
        switch (id)
        {
        	case 0 : img = new Texture("carreGreen.jpg"); break;
        	case 1 : img = new Texture("carreRed.jpg"); break;
        	case 2 : img = new Texture("carreBlue.jpg"); break;
        	case 3 : img = new Texture("carreYellow.jpg"); break;
        }
        vitesse = 10;
        this.pos.x = posPerso.x;
        this.pos.y = posPerso.y;
    }

    public Vector2 getPos()
    {
    	return pos;
    }
    //Procèdure de mise à jour
    public void update()
    {
        if (this.pos.x < 1315)
        {
            deplacement.x = +vitesse;
            System.out.println(pos.x + "En Y :" + pos.y);
        }
    }

    //Procèdure de déplacement du projectile
    public void deplacement()
    {
        pos.x += deplacement.x;
        pos.y += deplacement.y;
        
        deplacement.x = 0;
        deplacement.y = 0;
    }

    //Procèdure d'affichage
    public void draw(SpriteBatch batch)
    {
        batch.draw(img, pos.x, pos.y);
    }
}