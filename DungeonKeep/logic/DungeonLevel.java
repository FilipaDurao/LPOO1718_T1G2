package DungeonKeep.logic;

import java.util.ArrayList;
import java.util.Arrays;

import DungeonKeep.logic.GameObject.MoveDirection;

public class DungeonLevel extends Level {
	
	private Guard guard;
	private Lever lever;

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
	
	public Guard getGuards() { 
		return guard;
	}

	public Lever getLever() { 
		return lever;
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
		
		// Draw Lever		
		symbols[lever.getY_pos()][lever.getX_pos()] = lever.getIdSymbol();
		
		// Draw Hero
		symbols[hero.getY_pos()][hero.getX_pos()] = hero.getIdSymbol();
		
		// Draw Guard
		symbols[guard.getY_pos()][guard.getX_pos()] = guard.getIdSymbol();
				
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
