package projetTuteure;

import java.util.ArrayList;

import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.math.Vector2;

public class gestionJoueurs {

	private ArrayList<Perso> joueurs;
	
	public gestionJoueurs()
	{
		joueurs = new ArrayList<Perso>();
		
		if(Perso.nbJoueurs <= Perso.NB_JOUEURS_MAX)
		{
			// Si une manette est connecte, le perso est controlle avec la manette
			if(Controllers.getControllers().size == 0)
				joueurs.add(new Perso(new Vector2(400, 000)));
			else
				joueurs.add(new Perso(new Vector2(0, 0), 0)); // Cette position ne sert à rien, direct reinit dans niveau
			
			joueurs.add(new Perso(new Vector2(0, 0)));
		}
	}
	
	public boolean unJoueurMort()
	{
		boolean mort = false;
		
		int i = 0;
		while(i< joueurs.size() && !mort)
		{
			mort = joueurs.get(i).estMort();
			i++;
		}
		
		return mort;
	}

	public boolean unJoueurFiniLevel() {
		
		boolean lvlFini = false;
		
		int i = 0;
		while(i< joueurs.size() && !lvlFini)
		{
			lvlFini = joueurs.get(i).aFiniLevel();
			i++;
		}
			
		return lvlFini;
	}
	
	public ArrayList<Perso> getPersos()
	{
		return joueurs;
	}
	
}
