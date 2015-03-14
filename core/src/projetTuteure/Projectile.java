package projetTuteure;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
	//Commentaire pour que j'ai le 69ème commit ahahahahaha
    //Déclaration des variables de la classe
    protected Vector2 pos;
    protected Vector2 deplacement;

    protected Texture img;

    protected float vitesse;

    //Constructeur de la classe
    Projectile (Vector2 posPerso)
    {
        pos = new Vector2();
        deplacement = new Vector2();
        img = new Texture("carre.jpg");
        vitesse = 10;
        this.pos.x = posPerso.x;
        this.pos.y = posPerso.y;
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
        deplacement.x = 0;
    }

    //Procèdure d'affichage
    public void draw(SpriteBatch batch)
    {
        batch.draw(img, pos.x, pos.y);
    }
}