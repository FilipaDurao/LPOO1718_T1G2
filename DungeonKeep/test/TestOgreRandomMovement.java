package DungeonKeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

import DungeonKeep.logic.*;

public class TestOgreRandomMovement {

	private static KeepLevel initializeTestKeepLevel() {
			
		// Initialize Walls
		ArrayList<Wall> walls = new ArrayList<Wall>();
		for(int i=0 ; i<7 ; i++) {
			walls.add(new Wall(i, 0));
			if(i != 3) {
				walls.add(new Wall(i, 6));
			}
		}
		
		for(int i=1 ; i<6 ; i++) {
			walls.add(new Wall(0,i));
			walls.add(new Wall(6,i));
		}
		
		// Initialize Doors
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(3, 6, true));
		
		// Initialize Ogre
		Ogre ogre = new Ogre(5, 1);
		int numOgresInLevel = 1;
		
		// Initialize KeepLevel itself
		KeepLevel KeepLevel = new KeepLevel(
				0,
				new Hero(1, 1, false),
				walls,
				doors,
				ogre,	
				numOgresInLevel,
				new Key(5, 5),
				7,
				7);
		
		return KeepLevel;
	}
	
	@Test
	public void testOgreVariables() {
		KeepLevel testKeepLevel = initializeTestKeepLevel();
		Ogre ogre = testKeepLevel.getOgres().get(0);
		
		// Test if the ogre symbol is correct
		assertEquals('0', ogre.getIdSymbol());
		assertFalse(ogre.isStunned());
		
		// Test the ogre's stunned symbol
		ogre.stun();
		assertEquals('8', ogre.getIdSymbol());
		assertTrue(ogre.isStunned());
	}
	
	
	@Test //(timeout = 1000)
	public void testOgreRandomMovement() {
		KeepLevel testKeepLevel = initializeTestKeepLevel();							// Initialize the KeepLevel
		Ogre ogre = testKeepLevel.getOgres().get(0);							// Get the KeepLevel's ogre
		
		// ArayList in which we will store the freePositions in each one of the ogre's locations
		ArrayList<Position> freePositions = new ArrayList<Position>();
		
		// Variables for easier access to the KeepLevel's components
		ArrayList<Door> doors = testKeepLevel.getDoors();
		ArrayList<Wall> walls = testKeepLevel.getWalls();
		ArrayList<Ogre> ogres = new ArrayList<Ogre>();
		
		// Auxiliar variables
		int x, y;
		boolean isValid = false;	// To check at the end of an iteration if it was a valid position
		int counter = 0;			// Iteration counter
		
		// Run the cicle 10.000.000 times to make sure we cover many options
		while(counter < 10000000) {
		
			x = ogre.getX_pos();
			y = ogre.getY_pos();
			
			// Check the position to the right of the ogre
			if(!testKeepLevel.doesPositionHaveBarrier(x+1, y)) {
				freePositions.add(new Position(x+1,y));
			}
			
			// Check the position to the left of the ogre
			if(!testKeepLevel.doesPositionHaveBarrier(x-1, y)) {
				freePositions.add(new Position(x-1,y));
			}
			
			// Check the position above the ogre
			if(!testKeepLevel.doesPositionHaveBarrier(x, y-1)) {
				freePositions.add(new Position(x,y-1));
			}
			
			// Check the position below the ogre
			if(!testKeepLevel.doesPositionHaveBarrier(x, y+1)) {
				freePositions.add(new Position(x,y+1));
			}
			
			// Actually move the ogre
			ogre.update(walls, doors, ogres);
			
			// Check if the position was valid
			for (Position p : freePositions) {
				if(p.getX() == ogre.getX_pos() && p.getY() == ogre.getY_pos()) {
					isValid = true;
					break;
				}
			}
			
			// Fail the test if the position wasn't valid
			if(!isValid) {
				fail("Test failed");
			}
			
			// Prepare the variables for the next iteration
			isValid = false;		
			freePositions.clear();
			counter++;
		}
	}
	
}
