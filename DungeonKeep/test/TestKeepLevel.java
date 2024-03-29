package DungeonKeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

import DungeonKeep.logic.*;

public class TestKeepLevel {

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
	public void testMoveHeroNextToOgre() {	
		KeepLevel testKeepLevel = initializeTestKeepLevel();
		Hero hero = testKeepLevel.getHero();
		
		// Disable the ogre
		Ogre ogre = testKeepLevel.getOgres().get(0);
		ogre.disable();
		
		// Verify club starts at with the ogre
		assertEquals(ogre.getX_pos() , ogre.getClub().getX_pos());
		assertEquals(ogre.getY_pos() , ogre.getClub().getY_pos());
		assertNotEquals(ogre.getX_pos()-1 , ogre.getClub().getX_pos());
		assertNotEquals(ogre.getY_pos()-1 , ogre.getClub().getY_pos());
		
		// Test if hero begins in position (1, 1)
		assertEquals(1, hero.getX_pos());
		assertEquals(1, hero.getY_pos());		
		
		// Move hero right -> right -> right
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(4, hero.getX_pos());
		assertEquals(1, hero.getY_pos());
		assertTrue(testKeepLevel.getStatus() == KeepLevel.Status.DEFEAT);
		
		// Reset the KeepLevel
		testKeepLevel = initializeTestKeepLevel();
		hero = testKeepLevel.getHero();
		
		// Disable the ogre
		testKeepLevel.getOgres().get(0).disable();

		// Move hero down -> right -> right
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(5, hero.getX_pos());
		assertEquals(2, hero.getY_pos());
		assertTrue(testKeepLevel.getStatus() == KeepLevel.Status.DEFEAT);
	}
	
	@Test
	public void testMoveHeroToKey() {	
		KeepLevel testKeepLevel = initializeTestKeepLevel();
		Hero hero = testKeepLevel.getHero();
		
		// Disable the ogre
		testKeepLevel.getOgres().get(0).disable();
		
		// Test if hero begins in position (1, 1)
		assertEquals(1, hero.getX_pos());
		assertEquals(1, hero.getY_pos());		
		
		// Move hero down -> down -> down -> down -> right -> right -> right -> right
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(5, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		
		// Test if the hero has the Key
		assertTrue(hero.hasKey());
		assertEquals('K', hero.getIdSymbol());
	}
	
	@Test
	public void testOpenDoorWithoutKey() {	
		KeepLevel testKeepLevel = initializeTestKeepLevel();
		Hero hero = testKeepLevel.getHero();
		Door door = testKeepLevel.getDoors().get(0);
		
		// Disable the ogre
		testKeepLevel.getOgres().get(0).disable();
		
		// Verify that the door is closed
		assertTrue(door.isClosed());
		
		// Move hero down -> down -> down -> down -> right -> right
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(3, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		
		// Try to, unsuccessfully, open the door
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(3, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		assertTrue(door.isClosed());
	}
	
	@Test
	public void testOpenDoorWithKey() {	
		KeepLevel testKeepLevel = initializeTestKeepLevel();
		Hero hero = testKeepLevel.getHero();
		Door door = testKeepLevel.getDoors().get(0);
		
		// Disable the ogre
		testKeepLevel.getOgres().get(0).disable();
		
		// Verify that the door is closed
		assertTrue(door.isClosed());
		
		// Move hero down -> down -> down -> down -> right -> right -> right -> right
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(5, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		
		// Test if the hero has the Key
		assertTrue(hero.hasKey());
		
		// Move next to the door
		testKeepLevel.update(Hero.MoveDirection.LEFT);
		testKeepLevel.update(Hero.MoveDirection.LEFT);
		
		// Open door and Verify that the door is open
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(3, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		assertTrue(door.isOpen());
	}
	
	@Test
	public void testEnterDoorAndWinKeepLevel() {	
		KeepLevel testKeepLevel = initializeTestKeepLevel();
		Hero hero = testKeepLevel.getHero();
		Door door = testKeepLevel.getDoors().get(0);
		
		// Disable the ogre
		testKeepLevel.getOgres().get(0).disable();
		
		// Verify that the door is closed
		assertTrue(door.isClosed());
		
		// Move hero down -> down -> down -> down -> right -> right -> right -> right
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(1, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		testKeepLevel.update(Hero.MoveDirection.RIGHT);
		assertEquals(5, hero.getX_pos());
		assertEquals(5, hero.getY_pos());
		
		// Test if the hero has the Key
		assertTrue(hero.hasKey());
		
		// Move next to the door
		testKeepLevel.update(Hero.MoveDirection.LEFT);
		testKeepLevel.update(Hero.MoveDirection.LEFT);
		
		// Open door and Verify that the door is open
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		assertTrue(door.isOpen());
		
		// Enter door and win KeepLevel
		testKeepLevel.update(Hero.MoveDirection.DOWN);
		assertEquals(3, hero.getX_pos());
		assertEquals(6, hero.getY_pos());
		assertTrue(testKeepLevel.getStatus() == KeepLevel.Status.VICTORY);
	}
	
	@Test
	public void testHeroClub() {
		Hero hero = new Hero(0 , 0 , false);	// Hero starts without the club
		
		// Verify that hero has no club and no key
		assertFalse(hero.hasClub());
		assertFalse(hero.hasKey());
		assertEquals(hero.getIdSymbol() , 'H');
		
		// Catch the club and verify representation changes
		hero.catchClub();
		assertTrue(hero.hasClub());
		assertFalse(hero.hasKey());
		assertEquals(hero.getIdSymbol() , 'A');
		
		// Catch the key and verify representation changes
		hero.catchKey();
		assertTrue(hero.hasClub());
		assertTrue(hero.hasKey());
		assertEquals(hero.getIdSymbol() , 'K');
		
		// Test hero constructor with club
		Hero armedHero = new Hero(1 , 1 , true);
		assertTrue(armedHero.hasClub());
		assertFalse(armedHero.hasKey());
	}
	
	@Test
	public void testOgreNumberResizing() {
		KeepLevel testKeepLevel = initializeTestKeepLevel();
		
		// Verify that level start with one ogre only
		assertEquals(testKeepLevel.getOgres().size(), 1);
		
		// Increase the number of ogres within the level
		int newNumOgres = 5;
		testKeepLevel.setNumOgres(newNumOgres);
		assertEquals(testKeepLevel.getOgres().size(), newNumOgres);
		
		// Decrease the number of ogres within the level
		newNumOgres = 3;
		testKeepLevel.setNumOgres(newNumOgres);
		assertEquals(testKeepLevel.getOgres().size(), newNumOgres);
		
		// Verify that all ogres are in the same place
		ArrayList<Ogre> ogres = testKeepLevel.getOgres();
		Ogre ogre = ogres.get(0);
		for (Ogre o : ogres) {
			assertEquals(ogre.getX_pos(), 
						 o.getX_pos());
			assertEquals(ogre.getY_pos(),
						 o.getY_pos());
		}
	}
	
}























