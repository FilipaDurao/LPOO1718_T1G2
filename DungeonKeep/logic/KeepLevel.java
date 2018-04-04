package DungeonKeep.logic;

import java.util.ArrayList;
import java.util.Arrays;

public class KeepLevel extends Level {
	
	private static final long serialVersionUID = 1L;			// To allow file writing
	private ArrayList<Ogre> ogres = new ArrayList<Ogre>();		/**< All the Ogres in the Level */
	private Key key;											/**< The Key of the Level */

	/**
	 * Creates a Keep Level
	 * 
	 * @param ID the Level's ID
	 * @param hero the Hero of the Level
	 * @param walls all the Walls of the Level
	 * @param doors all the Doors of the Level
	 * @param an Ogre in the correct position
	 * @param the number of Ogres to be placed in the level
	 * @param key the Key of the Level
	 * @param width the width of the Level
	 * @param heigth the height of the Level
	 */
	public KeepLevel(
			int ID,
			Hero hero, 
			ArrayList<Wall> walls,
			ArrayList<Door> doors, 
			Ogre ogre,
			int numOgres,
			Key key,
			int width, 
			int heigth) {
		
		super(ID, hero, walls, doors, width, heigth);
		
		ogres.add(ogre);
		setNumOgres(numOgres);
		
		this.key = key;
	}
	
	/**
	 * Sets the level's number of ogres to 'numOgres'
	 * 
	 * @param numOgres New number of ogres
	 */
	public void setNumOgres(int numOgres) {
		if (ogres.size() < numOgres) {
			for (int i=1 ; i<numOgres ; i++) {
				ogres.add(new Ogre(ogres.get(0).getX_pos(), 
								   ogres.get(0).getY_pos()));
			}
		}
		else {
			while(ogres.size() > numOgres) {
				ogres.remove(ogres.size()-1);
			}
		}
	}
	
	/**
	 * @return All the Ogres of the Level
	 */
	public ArrayList<Ogre> getOgres() {
		return ogres;
	}

	/**
	 * @return The Key of the Level
	 */
	public Key getKey() {
		return key;
	}

	/**
	 * Fills a matrix with the correct game symbols and returns a String representation of it
	 * 
	 * @return Returns a string representing all the Level objects
	 */
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
		return parseMatrixToString(symbols);
	}

	/**
	 * Updates the Level based on the direction the player chose to move.
	 * 
	 * @param heroDirection the direction the player chose
	 */
	@Override
	public void update(Hero.MoveDirection heroDirection) {		
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
