package DungeonKeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

import DungeonKeep.logic.*;

public class TestOgreRandomMovement {

	private static Level initializeTestLevel() {
			
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
		ArrayList<Ogre> ogres = new ArrayList<Ogre>();
		ogres.add(new Ogre(5, 1));
		
		// Initialize Level itself
		Level level = new Level(
				0,
				new Hero(1, 1, false),
				walls,
				doors,
				new ArrayList<Guard>(),	// No guards
				ogres,	
				null,					// No lever
				new Key(5, 5),
				7,
				7);
		
		return level;
	}
	
	@Test(timeout = 1000)
	public void testOgreRandomMovement() {
		Level testLevel = initializeTestLevel();
		Ogre ogre = testLevel.getOgres().get(0);
		ArrayList<Position> freePositions = new ArrayList<Position>();
		
		int x, y;
		
		while(true) {
		
			x = ogre.getX_pos();
			y = ogre.getY_pos();
			
			// Check the position to the right of the ogre
			if(!testLevel.doesPositionHaveBarrier(x+1, y)) {
				freePositions.add(new Position(x+1,y));
			}
			
			// Check the position to the left of the ogre
			if(!testLevel.doesPositionHaveBarrier(x-1, y)) {
				freePositions.add(new Position(x-1,y));
			}
			
			// Check the position above the ogre
			if(!testLevel.doesPositionHaveBarrier(x, y-1)) {
				freePositions.add(new Position(x,y-1));
			}
			
			// Check the position below the ogre
			if(!testLevel.doesPositionHaveBarrier(x, y+1)) {
				freePositions.add(new Position(x,y+1));
			}
			
			
			
			
		}
		
		
	}
	
	
}
