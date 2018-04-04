package DungeonKeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

import DungeonKeep.logic.*;

public class TestLevelCreation {

	@Test
	public void testDungeonLevel() {
		// Initialize the Hero
		Hero hero = new Hero(1,1,false);
		
		// Initialize Walls
		ArrayList<Wall> walls = new ArrayList<Wall>();
		for(int i=0 ; i<7 ; i++) {
			walls.add(new Wall(i, 0));
			if(i != 3 && i != 4) {
				walls.add(new Wall(i, 6));
			}
		}
		for(int i=1 ; i<6 ; i++) {
			walls.add(new Wall(0,i));
			walls.add(new Wall(6,i));
		}
		
		// Initialize Doors
		ArrayList<Door> doors = new ArrayList<Door>();
		Door d1 = new Door(3, 6, true);
		Door d2 = new Door(4, 6, false);
		doors.add(d1);
		doors.add(d2);
		
		// Initialize Guard
		Guard guard = new RookieGuard(5, 1, new ArrayList<MovableGameObject.MoveDirection>());
		
		// Initialize the Lever
		Lever lever = new Lever(4, 5);
		
		// Initialize Level itself
		DungeonLevel testLevel = new DungeonLevel(
				0,
				hero,
				walls,
				doors,
				guard,	
				lever,
				7,
				7);
		
		
		// Verify level size and ID
		assertEquals(testLevel.getWidth() , 7);
		assertEquals(testLevel.getHeight() , 7);
		assertEquals(testLevel.getID() , 0);
		testLevel.setWidth(10);
		testLevel.setHeight(10);
		assertEquals(testLevel.getWidth() , 10);
		assertEquals(testLevel.getHeight() , 10);
		
		// Verify Hero Coordinates and that it has no weapon
		assertEquals(testLevel.getHero().getX_pos() , 1);
		assertEquals(testLevel.getHero().getY_pos() , 1);
		assertFalse(testLevel.getHero().hasClub());
		
		// Verify lever position
		assertEquals(testLevel.getLever().getX_pos() , 4);
		assertEquals(testLevel.getLever().getY_pos() , 5);
		
		// Verify correct number of walls
		assertEquals(walls.size() , testLevel.getWalls().size());
		
		// Verify guard position
		assertEquals(testLevel.getGuard().getX_pos() , 5);
		assertEquals(testLevel.getGuard().getY_pos() , 1);
		
		// Verify doors attributes
		assertTrue(testLevel.getDoors().get(0).isExit());
		assertFalse(testLevel.getDoors().get(1).isExit());
		assertTrue(testLevel.getDoors().get(0).isClosed());
		assertFalse(testLevel.getDoors().get(1).isOpen());
		
		// Verify if given position has a barrier
		assertTrue(testLevel.doesPositionHaveBarrier(0, 0));
		assertTrue(testLevel.doesPositionHaveBarrier(1, 0));
		assertTrue(testLevel.doesPositionHaveBarrier(3, 6));
		assertTrue(testLevel.doesPositionHaveBarrier(4, 6));
		assertFalse(testLevel.doesPositionHaveBarrier(1, 1));
		assertFalse(testLevel.doesPositionHaveBarrier(2, 1));
		assertFalse(testLevel.doesPositionHaveBarrier(3, 1));
		assertFalse(testLevel.doesPositionHaveBarrier(1, 2));
		
		// Verify Objects symbols and sprites
		assertEquals(testLevel.getHero().getIdSymbol() , 'H');
		assertEquals(testLevel.getLever().getIdSymbol() , 'k');
		assertEquals(testLevel.getGuard().getIdSymbol() , 'G');
		assertEquals(testLevel.getDoors().get(0).getIdSymbol() , 'I');
		assertEquals(testLevel.getWalls().get(0).getIdSymbol() , 'X');	
		
		// Verify level "printing" correctness
		int numWalls = 0, numDoors = 0;
		boolean heroFound = false, leverFound = false, guardFound = false;
		
		for (char element : testLevel.getGameMatrix().toCharArray()) {
			switch(element) {
			case 'H':
			case 'A':
				heroFound = true;
				break;
			case 'k':
				leverFound = true;
				break;
			case 'G':
				guardFound = true;
				break;
			case 'I':
			case 'S':
				numDoors++;
				break;
			case 'X':
				numWalls++;
				break;
			}
		}
		
		assertTrue(heroFound);
		assertTrue(leverFound);
		assertTrue(guardFound);
		assertEquals(numDoors, testLevel.getDoors().size());
		assertEquals(numWalls, testLevel.getWalls().size());
	}
	
	@Test
	public void testKeepLevel() {
		// Initialize the Hero
		Hero hero = new Hero(1,1,true);
		
		// Initialize Walls
		ArrayList<Wall> walls = new ArrayList<Wall>();
		for(int i=0 ; i<7 ; i++) {
			walls.add(new Wall(i, 0));
			if(i != 3 && i != 4) {
				walls.add(new Wall(i, 6));
			}
		}
		for(int i=1 ; i<6 ; i++) {
			walls.add(new Wall(0,i));
			walls.add(new Wall(6,i));
		}
		
		// Initialize Doors
		ArrayList<Door> doors = new ArrayList<Door>();
		Door d1 = new Door(3, 6, true);
		Door d2 = new Door(4, 6, false);
		doors.add(d1);
		doors.add(d2);
		
		// Initialize Ogre
		Ogre ogre = new Ogre(3,1);
		int numOgresInLevel = 2;
		
		// Initialize the Key
		Key key = new Key(5, 5);
		
		// Initialize Level itself
		KeepLevel testLevel = new KeepLevel(
				0,
				hero,
				walls,
				doors,
				ogre,
				numOgresInLevel,
				key,
				7,
				7);
		
		// Verify level size and ID
		assertEquals(testLevel.getWidth() , 7);
		assertEquals(testLevel.getHeight() , 7);
		assertEquals(testLevel.getID() , 0);
		
		// Verify Hero Coordinates and that it has a weapon
		assertEquals(testLevel.getHero().getX_pos() , 1);
		assertEquals(testLevel.getHero().getY_pos() , 1);
		assertTrue(testLevel.getHero().hasClub());
		
		// Verify key position
		assertEquals(testLevel.getKey().getX_pos() , 5);
		assertEquals(testLevel.getKey().getY_pos() , 5);
		
		// Verify correct number of ogres
		assertEquals(numOgresInLevel , testLevel.getOgres().size());
		
		// Verify Objects symbols (others were verified in another test function)
		assertEquals(testLevel.getHero().getIdSymbol() , 'A');
		assertEquals(testLevel.getKey().getIdSymbol() , 'k');
		assertEquals(testLevel.getOgres().get(0).getIdSymbol() , '0');
		assertEquals(testLevel.getOgres().get(0).getClub().getIdSymbol() , '*');
		
		// Verify level "printing" correctness
		int numWalls = 0, numDoors = 0;
		boolean heroFound = false, keyFound = false;
		
		for (char element : testLevel.getGameMatrix().toCharArray()) {
			switch(element) {
			case 'H':
			case 'A':
				heroFound = true;
				break;
			case 'k':
				keyFound = true;
				break;
			case 'I':
			case 'S':
				numDoors++;
				break;
			case 'X':
					numWalls++;
					break;
				}
			}
			
			assertTrue(heroFound);
			assertTrue(keyFound);
			assertEquals(numDoors, testLevel.getDoors().size());
			assertEquals(numWalls, testLevel.getWalls().size());
}
}