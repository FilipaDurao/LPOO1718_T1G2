package DungeonKeep.logic;

import java.util.ArrayList;
import java.util.Arrays;

public class DungeonLevel extends Level {

	private static final long serialVersionUID = 1L;	// To allow file writing
	private Guard guard;	/**< The Guard present on the Level */
	private Lever lever;	/**< The Lever present on the Lever */

	/**
	 * Creates a Dungeon Level
	 * 
	 * @param ID the Level's ID
	 * @param hero the Hero of the Level
	 * @param walls all the Walls of the Level
	 * @param doors all the Doors of the Level
	 * @param guard the Guard of the Level
	 * @param lever the Lever of the Level
	 * @param width the width of the Level
	 * @param heigth the heigth of the Level
	 */
	public DungeonLevel(
			int ID, 
			Hero hero,
			ArrayList<Wall> walls, 
			ArrayList<Door> doors, 
			Guard guard,
			Lever lever,
			int width, 
			int heigth) {
		
		super(ID, hero, walls, doors, width, heigth);
		this.guard = guard;
		this.lever = lever;
	}
	
	/**
	 * @return Returns the Guard present on the Level
	 */
	public Guard getGuard() { 
		return guard;
	}

	/**
	 * @return Returns the Lever of the Level
	 */
	public Lever getLever() { 
		return lever;
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
		
		// Draw Lever		
		symbols[lever.getY_pos()][lever.getX_pos()] = lever.getIdSymbol();
		
		// Draw Hero
		symbols[hero.getY_pos()][hero.getX_pos()] = hero.getIdSymbol();
		
		// Draw Guard
		symbols[guard.getY_pos()][guard.getX_pos()] = guard.getIdSymbol();
				
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
		// Move the Guard
		guard.performStep();
		
		// Move the Hero
		hero.update(heroDirection, walls, doors, new ArrayList<Ogre>());
		
		// Check for collision with lever (if existent)
		if(hero.collidesWith(lever)) {
			openExitDoors();
		}
		
		// Check for proximity with guard
		if(heroIsNearGuard(guard)) {
			status = Status.DEFEAT;
			return;
		}
		
		// Check with collision with open door
		if(heroCollidesWithOpenDoor()) {
			status = Status.VICTORY;
		}
	}
	
	private boolean heroIsNearGuard(Guard guard) { 
		// Check if the guard is of the Drunken Type and asleep
		if (guard instanceof DrunkenGuard  &&  ((DrunkenGuard) guard).isAsleep()) {
			// If hero collides with the guard's exact position, the guard wakes up and catches the hero
			if(hero.collidesWith(guard)) {
				((DrunkenGuard) guard).wakeUp();
				return true;
			}
			else {
				return false;
			}
		}
		
		// Regular Guard / Awaken Guard
		else {
			return (hero.isNear(guard) || hero.collidesWith(guard));
		}
	}
	
	private void openExitDoors() {
		// Iterate though all the doors and open the ones that are Exit Doors
		for (Door d : doors) {
			if(d.isExit()) {
				d.open();
			}
		}
	}

}
