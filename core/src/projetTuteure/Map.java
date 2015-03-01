package projetTuteure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Map {
	private Vector2 tailleMap;
	private Vector2 tailleTile;
	private String nomImg;
	
	Map(String nom)
	{
		tailleMap = new Vector2();
		tailleTile = new Vector2();
		nomImg = new String();
		
		Scanner fichier;
		
		try {
			fichier = new Scanner(new File("assets/"+nom));
			
			tailleMap.x = fichier.nextInt();
			tailleMap.y = fichier.nextInt();
			
			tailleTile.x = fichier.nextInt();
			tailleTile.y = fichier.nextInt();
			
			nomImg = fichier.nextLine();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
	}
	public static void main (String[] arg) {
		Map map = new Map("map.txt");
		
		System.out.println(map.tailleMap.x);
		System.out.println(map.tailleMap.y);
		System.out.println(map.tailleTile.x);
		System.out.println(map.tailleTile.y);
		System.out.println(map.nomImg);
	}
}
