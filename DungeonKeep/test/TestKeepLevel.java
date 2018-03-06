package DungeonKeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.ArrayList;

import DungeonKeep.logic.*;

public class TestKeepLevel {

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
		
		// Initialize Guard
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
		
	@Test
	public void testMoveHeroNextToOgre() {	
		Level testLevel = initializeTestLevel();
		Hero hero = testLevel.getHero();
		
		// Disable the ogre
		testLevel.getOgres().get(0).disable();
		
		// Test if hero begins in position (1, 1)
		assertEquals(1, hero.getX_pos());
		assertEquals(1, hero.getY_pos());		
		
		// Move hero right -> right -> right
		testLevel.update('D');
		testLevel.update('D');
		testLevel.update('D');
		assertEquals(4, hero.getX_pos());
		assertEquals(1, hero.getY_pos());
		assertTrue(testLevel.getStatus() == Level.LevelStatus.DEFEAT);
		
		// Reset the level
		testLevel = initializeTestLevel();
		hero = testLevel.getHero();
		
		// Disable the ogre
		testLevel.getOgres().get(0).disable();

		// Move hero down -> right -> right
		testLevel.update('S');
		testLevel.update('D');
		testLevel.update('D');
		testLevel.update('D');
		testLevel.update('D');
		assertEquals(5, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
		assertTrue(testLevel.getStatus() == Level.LevelStatus.DEFEAT);
	}
	
	@Test
	public void testMoveHeroToKey() {	
		Level testLevel = initializeTestLevel();
		Hero hero = testLevel.getHero();
		
		// Disable the ogre
		testLevel.getOgres().get(0).disable();
		
		// Test if hero begins in position (1, 1)
		assertEquals(1, hero.getX_pos());
		assertEquals(1, hero.getY_pos());		
		
		// Move hero down -> down -> down -> down -> right -> right -> right -> right
		testLevel.update('S');
		testLevel.update('S');
		testLevel.update('S');
		testLevel.update('S');
		assertEquals(1, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		testLevel.update('D');
		testLevel.update('D');
		testLevel.update('D');
		testLevel.update('D');
		assertEquals(5, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		
		// Test if the hero has the Key
		assertTrue(hero.hasKey());
		assertEquals('K', hero.getIdSymbol());
	}
	
	@Test
	public void testOpenDoorWithoutKey() {	
		Level testLevel = initializeTestLevel();
		Hero hero = testLevel.getHero();
		Door door = testLevel.getDoors().get(0);
		
		// Disable the ogre
		testLevel.getOgres().get(0).disable();
		
		// Verify that the door is closed
		assertTrue(door.isClosed());
		
		// Move hero down -> down -> down -> down -> right -> right
		testLevel.update('S');
		testLevel.update('S');
		testLevel.update('S');
		testLevel.update('S');
		assertEquals(1, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		testLevel.update('D');
		testLevel.update('D');
		assertEquals(3, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		
		// Try to, unsuccessfully, open the door
		testLevel.update('S');
		assertEquals(3, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		assertTrue(door.isClosed());
	}
	
	@Test
	public void testOpenDoorWithKey() {	
		Level testLevel = initializeTestLevel();
		Hero hero = testLevel.getHero();
		Door door = testLevel.getDoors().get(0);
		
		// Disable the ogre
		testLevel.getOgres().get(0).disable();
		
		// Verify that the door is closed
		assertTrue(door.isClosed());
		
		// Move hero down -> down -> down -> down -> right -> right -> right -> right
		testLevel.update('S');
		testLevel.update('S');
		testLevel.update('S');
		testLevel.update('S');
		assertEquals(1, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		testLevel.update('D');
		testLevel.update('D');
		testLevel.update('D');
		testLevel.update('D');
		assertEquals(5, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		
		// Test if the hero has the Key
		assertTrue(hero.hasKey());
		
		// Move next to the door
		testLevel.update('A');
		testLevel.update('A');
		
		// Open door and Verify that the door is open
		testLevel.update('S');
		assertEquals(3, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		assertTrue(door.isOpen());
	}
	
	@Test
	public void testEnterDoorAndWinLevel() {	
		Level testLevel = initializeTestLevel();
		Hero hero = testLevel.getHero();
		Door door = testLevel.getDoors().get(0);
		
		// Disable the ogre
		testLevel.getOgres().get(0).disable();
		
		// Verify that the door is closed
		assertTrue(door.isClosed());
		
		// Move hero down -> down -> down -> down -> right -> right -> right -> right
		testLevel.update('S');
		testLevel.update('S');
		testLevel.update('S');
		testLevel.update('S');
		assertEquals(1, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		testLevel.update('D');
		testLevel.update('D');
		testLevel.update('D');
		testLevel.update('D');
		assertEquals(5, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		
		// Test if the hero has the Key
		assertTrue(hero.hasKey());
		
		// Move next to the door
		testLevel.update('A');
		testLevel.update('A');
		
		// Open door and Verify that the door is open
		testLevel.update('S');
		assertTrue(door.isOpen());
		
		// Enter door and win level
		testLevel.update('S');
		assertEquals(3, hero.getX_pos());
		assertEquals(6, hero.getY_pos());
		assertTrue(testLevel.getStatus() == Level.LevelStatus.VICTORY);
	}
	
}






















