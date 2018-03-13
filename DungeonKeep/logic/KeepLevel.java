package DungeonKeep.logic;

import java.util.ArrayList;
import java.util.Arrays;

import DungeonKeep.logic.GameObject.MoveDirection;

public class KeepLevel extends Level {
	
	private ArrayList<Ogre> ogres;
	private Key key;

	public KeepLevel(
			int ID,
			Hero hero, 
			ArrayList<Wall> walls,
			ArrayList<Door> doors, 
			ArrayList<Ogre> ogres,
			Key key,
			int width, 
			int heigth) {
		
		super(ID, hero, walls, doors, width, heigth);
		this.ogres = ogres;
		this.key = key;
	}
	
	public ArrayList<Ogre> getOgres() {
		return ogres;
	}

	public Key getKey() {
		return key;
	}

	@Override
	public String getGameMatrix() {
		char[][] symbols = new char[heigth][width];
		
		// Initialize matrix with spaces
		for(char[] row : symbols) {
			Arrays.fill(row, ' ');
		}
		
		// Draw Walls
		for(Wall w : walls) {
			symbols[w.getY_pos()][w.getX_pos()] = w.getIdSymbol();
		}
		
		// Draw Doors
		for(Door d : doors) {
			symbols[d.getY_pos()][d.getX_pos()] = d.getIdSymbol();
		}
		
		// Draw Hero
		symbols[hero.getY_pos()][hero.getX_pos()] = hero.getIdSymbol();
		
		
		// Draw Ogres
		for(Ogre o : ogres) {
			symbols[o.getClub().getY_pos()][o.getClub().getX_pos()] = o.getClub().getIdSymbol();	// Draw the Ogre's Club
			symbols[o.getY_pos()][o.getX_pos()] = o.getIdSymbol();									// Draw the Ogre
		}
		
		// Draw Key	(if it hasn't been caught yet)
		// Check if square already has an ogre or club in it
		if (key != null) {
			Ogre ogre = ogres.get(0);
			
			if (symbols[key.getY_pos()][key.getX_pos()] == ogre.getIdSymbol() ||
				symbols[key.getY_pos()][key.getX_pos()] == ogre.getClub().getIdSymbol()) {
				
				symbols[key.getY_pos()][key.getX_pos()] = '$';
			}
			else {
				symbols[key.getY_pos()][key.getX_pos()] = key.getIdSymbol();
			}
		}
		
		// Parse the matrix to a string
		String gameMatrixString = "";
		for(char[] row : symbols) {
			for(char c : row) {
				gameMatrixString += Character.toString(c) + " ";
			}
			gameMatrixString += "\n";
		}		
		
		return gameMatrixString;
	}

	@Override
	public void update(MoveDirection heroDirection) {		
		// Move the Ogres
		for(Ogre o : ogres) {
			o.update(walls , doors , ogres);
		}
		
		// Move the Hero
		hero.update(heroDirection, walls, doors, ogres);
		
		// Check for collision with key (if existent)
		if(key != null  &&  hero.collidesWith(key)) {
			hero.catchKey();
			key = null;
		}
		
		// Check for proximity with ogres / ogres' club
		for(Ogre o : ogres) {
			if(heroIsNearOgre(o) || heroIsNearOgreClub(o)) {
				status = Status.DEFEAT;
				return;
			}
		}
		
		// Check with collision with open door
		if(heroCollidesWithOpenDoor()) {
			status = Status.VICTORY;
		}
	}
	
	private boolean heroIsNearOgre(Ogre ogre) {
		if (hero.hasClub()) {
			// When the hero has a weapon , the one affected is the ogre! Stun the ogre!
			if (hero.isNear(ogre)) {
				ogre.stun();
			}
			return false;
		}
		else {
			return (hero.isNear(ogre) || hero.collidesWith(ogre));
		}
	}


	private boolean heroIsNearOgreClub(Ogre ogre) {
		Club club = ogre.getClub();
		return (hero.isNear(club) || hero.collidesWith(club));
	}

}
