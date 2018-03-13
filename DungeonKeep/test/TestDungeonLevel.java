package DungeonKeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

import DungeonKeep.logic.*;

public class TestDungeonLevel {

	private static Level initializeTestLevel() {
		
		// Initialize Walls
		ArrayList<Wall> walls = new ArrayList<Wall>();
		for(int i=0 ; i<5 ; i++) {
			walls.add(new Wall(i, 0));
			walls.add(new Wall(i, 4));
		}
		
		for(int i=1 ; i<4 ; i++) {
			if(i != 2 && i != 3) {
				walls.add(new Wall(0,i));
			}
			walls.add(new Wall(4,i));
		}
		
		// Initialize Doors
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(0, 2, true));
		doors.add(new Door(0, 3, true));
		
		// Initialize Guard
		ArrayList<Guard> guards = new ArrayList<Guard>();
		guards.add(new RookieGuard(3, 1, new ArrayList<GameObject.MoveDirection>()));
		
		// Initialize Level itself
		Level level = new Level(
				0,
				new Hero(1, 1, false),
				walls,
				doors,
				guards,
				new ArrayList<Ogre>(),	// No ogres
				new Lever(3, 3),
				null,					// No key
				5,
				5);
		
		return level;
	}
		
	@Test
	public void testMoveHeroIntoFreeCell() {	
		Level testLevel = initializeTestLevel();
		Hero hero = testLevel.getHero();

		// Test if hero begins in position (1, 1)
		assertEquals(1, hero.getX_pos());
		assertEquals(1, hero.getY_pos());		
		
		// Move hero down successfully
		testLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
	}
		
	@Test
	public void testMoveHeroIntoWall() {	
		Level testLevel = initializeTestLevel();
		Hero hero = testLevel.getHero();

		// Test if hero begins in position (1, 1)
		assertEquals(1, hero.getX_pos());
		assertEquals(1, hero.getY_pos());
		
		// Try to move hero up
		testLevel.update(Hero.MoveDirection.UP);
		assertEquals(1, hero.getX_pos());
		assertEquals(1, hero.getY_pos());
		
		// Try to move hero left
		testLevel.update(Hero.MoveDirection.LEFT);
		assertEquals(1, hero.getX_pos());
		assertEquals(1, hero.getY_pos());
	}
	
	@Test
	public void testMoveHeroNextToGuard() {	
		Level testLevel = initializeTestLevel();
		Hero hero = testLevel.getHero();
		
		// Move hero to the right
		testLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(2, hero.getX_pos());
		assertEquals(1, hero.getY_pos());
		assertTrue(testLevel.getStatus() == Level.Status.DEFEAT);
		
		// Reset the level
		testLevel = initializeTestLevel();
		hero = testLevel.getHero();

		// Move hero down -> right -> right
		testLevel.update(Hero.MoveDirection.DOWN);
		testLevel.update(Hero.MoveDirection.RIGHT);
		testLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(3, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
		assertTrue(testLevel.getStatus() == Level.Status.DEFEAT);
	}
	
	@Test 
	public void testDoors() {
		Door door = new Door(0 , 0 , true);
		
		// Verify that the door is closed
		assertTrue(door.isClosed());
		assertEquals(door.getIdSymbol() , 'I');
		
		// Open the door
		door.open();
		assertTrue(door.isOpen());
		assertEquals(door.getIdSymbol() , 'S');
		
		// Re-close the door
		door.close();
		assertTrue(door.isClosed());
		assertEquals(door.getIdSymbol() , 'I');
	}
	
	@Test
	public void testMoveHeroIntoClosedDoors() {	
		Level testLevel = initializeTestLevel();
		Hero hero = testLevel.getHero();
		
		// Move hero down 
		testLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
		
		// Try to walk into first closed door
		testLevel.update(Hero.MoveDirection.LEFT);
		assertEquals(1, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
		
		// Move hero down
		testLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		
		// Try to walk into second closed door
		testLevel.update(Hero.MoveDirection.LEFT);
		assertEquals(1, hero.getX_pos());
		assertEquals(3, hero.getY_pos());	
	}
	
	@Test
	public void testMoveHeroIntoLeverAndOpenDoors() {	
		Level testLevel = initializeTestLevel();
		Hero hero = testLevel.getHero();
		
		// Move hero down -> down -> right -> right
		testLevel.update(Hero.MoveDirection.DOWN);
		testLevel.update(Hero.MoveDirection.DOWN);
		testLevel.update(Hero.MoveDirection.RIGHT);
		testLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(3, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		
		// Test if the exit doors are open
		for(Door d : testLevel.getDoors()) {
			if(d.isExit()) {
				assertTrue(d.isOpen());
			}
		}
	}
	
	@Test
	public void testMoveHeroIntoOpenDoors() {	
		Level testLevel = initializeTestLevel();
		Hero hero = testLevel.getHero();
		
		// Move hero down -> down -> right -> right (to the lever)
		testLevel.update(Hero.MoveDirection.DOWN);
		testLevel.update(Hero.MoveDirection.DOWN);
		testLevel.update(Hero.MoveDirection.RIGHT);
		testLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(3, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		
		// Move into the "southest" door
		testLevel.update(Hero.MoveDirection.LEFT);
		testLevel.update(Hero.MoveDirection.LEFT);
		testLevel.update(Hero.MoveDirection.LEFT);
		assertEquals(0, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		assertTrue(testLevel.getStatus() == Level.Status.VICTORY);
		
		// Reset the level
		testLevel = initializeTestLevel();
		hero = testLevel.getHero();
		
		// Move hero down -> down -> right -> right (to the lever)
		testLevel.update(Hero.MoveDirection.DOWN);
		testLevel.update(Hero.MoveDirection.DOWN);
		testLevel.update(Hero.MoveDirection.RIGHT);
		testLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(3, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		
		// Move into the "northest" door
		testLevel.update(Hero.MoveDirection.LEFT);
		testLevel.update(Hero.MoveDirection.LEFT);
		testLevel.update(Hero.MoveDirection.UP);
		testLevel.update(Hero.MoveDirection.LEFT);
		assertEquals(0, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
		assertTrue(testLevel.getStatus() == Level.Status.VICTORY);
	}
	
}























