package DungeonKeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

import DungeonKeep.logic.*;

public class TestDungeonLevel {

	private static DungeonLevel initializeTestDungeonLevel() {
		
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
		Guard guard = new RookieGuard(3, 1, new ArrayList<MovableGameObject.MoveDirection>());
		
		// Initialize DungeonLevel itself
		DungeonLevel DungeonLevel = new DungeonLevel(
				0,
				new Hero(1, 1, false),
				walls,
				doors,
				guard,
				new Lever(3, 3),
				5,
				5);
		
		return DungeonLevel;
	}
		
	@Test
	public void testMoveHeroIntoFreeCell() {	
		DungeonLevel testDungeonLevel = initializeTestDungeonLevel();
		Hero hero = testDungeonLevel.getHero();

		// Test if hero begins in position (1, 1)
		assertEquals(1, hero.getX_pos());
		assertEquals(1, hero.getY_pos());		
		
		// Move hero down successfully
		testDungeonLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
	}
		
	@Test
	public void testMoveHeroIntoWall() {	
		DungeonLevel testDungeonLevel = initializeTestDungeonLevel();
		Hero hero = testDungeonLevel.getHero();

		// Test if hero begins in position (1, 1)
		assertEquals(1, hero.getX_pos());
		assertEquals(1, hero.getY_pos());
		
		// Try to move hero up
		testDungeonLevel.update(Hero.MoveDirection.UP);
		assertEquals(1, hero.getX_pos());
		assertEquals(1, hero.getY_pos());
		
		// Try to move hero left
		testDungeonLevel.update(Hero.MoveDirection.LEFT);
		assertEquals(1, hero.getX_pos());
		assertEquals(1, hero.getY_pos());
	}
	
	@Test
	public void testMoveHeroNextToGuard() {	
		DungeonLevel testDungeonLevel = initializeTestDungeonLevel();
		Hero hero = testDungeonLevel.getHero();
		
		// Move hero to the right
		testDungeonLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(2, hero.getX_pos());
		assertEquals(1, hero.getY_pos());
		assertTrue(testDungeonLevel.getStatus() == DungeonLevel.Status.DEFEAT);
		
		// Reset the DungeonLevel
		testDungeonLevel = initializeTestDungeonLevel();
		hero = testDungeonLevel.getHero();

		// Move hero down -> right -> right
		testDungeonLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
		testDungeonLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(2, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
		testDungeonLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(3, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
		assertTrue(testDungeonLevel.getStatus() == DungeonLevel.Status.DEFEAT);
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
		DungeonLevel testDungeonLevel = initializeTestDungeonLevel();
		Hero hero = testDungeonLevel.getHero();
		
		// Move hero down 
		testDungeonLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
		
		// Try to walk into first closed door
		testDungeonLevel.update(Hero.MoveDirection.LEFT);
		assertEquals(1, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
		
		// Move hero down
		testDungeonLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		
		// Try to walk into second closed door
		testDungeonLevel.update(Hero.MoveDirection.LEFT);
		assertEquals(1, hero.getX_pos());
		assertEquals(3, hero.getY_pos());	
	}
	
	@Test
	public void testMoveHeroIntoLeverAndOpenDoors() {	
		DungeonLevel testDungeonLevel = initializeTestDungeonLevel();
		Hero hero = testDungeonLevel.getHero();
		
		// Move hero down -> down -> right -> right
		testDungeonLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
		testDungeonLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		testDungeonLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(2, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		testDungeonLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(3, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		
		// Test if the exit doors are open
		for(Door d : testDungeonLevel.getDoors()) {
			if(d.isExit()) {
				assertTrue(d.isOpen());
			}
		}
	}
	
	@Test
	public void testMoveHeroIntoOpenDoors() {	
		DungeonLevel testDungeonLevel = initializeTestDungeonLevel();
		Hero hero = testDungeonLevel.getHero();
		
		// Move hero down -> down -> right -> right (to the lever)
		testDungeonLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
		testDungeonLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		testDungeonLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(2, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		testDungeonLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(3, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		
		// Move into the "southest" door
		testDungeonLevel.update(Hero.MoveDirection.LEFT);
		assertEquals(2, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		testDungeonLevel.update(Hero.MoveDirection.LEFT);
		assertEquals(1, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		testDungeonLevel.update(Hero.MoveDirection.LEFT);
		assertEquals(0, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		assertTrue(testDungeonLevel.getStatus() == DungeonLevel.Status.VICTORY);
		
		// Reset the DungeonLevel
		testDungeonLevel = initializeTestDungeonLevel();
		hero = testDungeonLevel.getHero();
		
		// Move hero down -> down -> right -> right (to the lever)
		testDungeonLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
		testDungeonLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		testDungeonLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(2, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		testDungeonLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(3, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		
		// Move into the "northest" door
		testDungeonLevel.update(Hero.MoveDirection.LEFT);
		assertEquals(2, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		testDungeonLevel.update(Hero.MoveDirection.LEFT);
		assertEquals(1, hero.getX_pos());
		assertEquals(3, hero.getY_pos());
		testDungeonLevel.update(Hero.MoveDirection.UP);
		assertEquals(1, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
		testDungeonLevel.update(Hero.MoveDirection.LEFT);
		assertEquals(0, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
		assertTrue(testDungeonLevel.getStatus() == DungeonLevel.Status.VICTORY);
	}
	
}























