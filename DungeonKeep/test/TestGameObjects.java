package DungeonKeep.test;

import static org.junit.Assert.*;

import org.junit.Test;

import DungeonKeep.logic.Club;
import DungeonKeep.logic.Door;
import DungeonKeep.logic.GameObject;
import DungeonKeep.logic.Hero;
import DungeonKeep.logic.Key;
import DungeonKeep.logic.Lever;
import DungeonKeep.logic.MovableGameObject;
import DungeonKeep.logic.Ogre;
import DungeonKeep.logic.Wall;

public class TestGameObjects {

	@Test
	public void testGameObjectPosition() {
		GameObject heroObj = new Hero(0, 0, false);
		GameObject wallObj1 = new Wall(0, 1);
		GameObject wallObj2 = new Wall(2, 2);
		
		// Test Game object position getters and setters
		assertEquals(wallObj2.getX_pos() , 2);
		assertEquals(wallObj2.getY_pos() , 2);
		assertNotEquals(wallObj2.getX_pos() , 3);
		assertNotEquals(wallObj2.getY_pos() , 3);
		wallObj2.setX_pos(3);
		wallObj2.setY_pos(3);
		assertEquals(wallObj2.getX_pos() , 3);
		assertEquals(wallObj2.getY_pos() , 3);
		assertNotEquals(wallObj2.getX_pos() , 2);
		assertNotEquals(wallObj2.getY_pos() , 2);
		
		// Test object collisions
		GameObject wallObj3 = new Wall(3, 3);
		assertTrue(wallObj2.collidesWith(wallObj3));
		assertTrue(wallObj3.collidesWith(wallObj2));
		assertFalse(wallObj1.collidesWith(heroObj));
		assertFalse(heroObj.collidesWith(wallObj1));
		
		// Test object proximity
		GameObject obj1 = new Wall(5, 5);
		GameObject obj2 = new Wall(6, 5);
		GameObject obj3 = new Wall(4, 5);
		GameObject obj4 = new Wall(5, 6);	
		GameObject obj5 = new Wall(5, 4);

		assertTrue(obj1.isNear(obj2));
		assertTrue(obj1.isNear(obj3));
		assertTrue(obj1.isNear(obj4));
		assertTrue(obj1.isNear(obj5));
		assertTrue(obj2.isNear(obj1));
		assertTrue(obj3.isNear(obj1));
		assertTrue(obj4.isNear(obj1));
		assertTrue(obj5.isNear(obj1));
		
		assertFalse(obj2.isNear(obj3));
		assertFalse(obj3.isNear(obj2));
		assertFalse(obj4.isNear(obj5));
		assertFalse(obj5.isNear(obj4));
		assertFalse(obj2.isNear(obj5));
		assertFalse(obj5.isNear(obj2));		
	}
	
	@Test
	public void testGameObjectDirections() {
		assertEquals(MovableGameObject.getOppositeMoveDirection(MovableGameObject.MoveDirection.UP), 
					 MovableGameObject.MoveDirection.DOWN);
		
		assertNotEquals(MovableGameObject.getOppositeMoveDirection(MovableGameObject.MoveDirection.UP), 
						MovableGameObject.MoveDirection.LEFT);
		
		assertNotEquals(MovableGameObject.getOppositeMoveDirection(MovableGameObject.MoveDirection.UP), 
						MovableGameObject.MoveDirection.RIGHT);
		
		assertNotEquals(MovableGameObject.getOppositeMoveDirection(MovableGameObject.MoveDirection.UP), 
						MovableGameObject.MoveDirection.UP);

		assertEquals(MovableGameObject.getOppositeMoveDirection(MovableGameObject.MoveDirection.DOWN) , 
				     MovableGameObject.MoveDirection.UP);
		
		assertNotEquals(MovableGameObject.getOppositeMoveDirection(MovableGameObject.MoveDirection.DOWN) , 
						MovableGameObject.MoveDirection.LEFT);
		
		assertNotEquals(MovableGameObject.getOppositeMoveDirection(MovableGameObject.MoveDirection.DOWN) , 
						MovableGameObject.MoveDirection.RIGHT);
		
		assertNotEquals(MovableGameObject.getOppositeMoveDirection(MovableGameObject.MoveDirection.DOWN) , 
						MovableGameObject.MoveDirection.DOWN);

		assertEquals(MovableGameObject.getOppositeMoveDirection(MovableGameObject.MoveDirection.LEFT) , 
					 MovableGameObject.MoveDirection.RIGHT);
		
		assertNotEquals(MovableGameObject.getOppositeMoveDirection(MovableGameObject.MoveDirection.LEFT) , 
						MovableGameObject.MoveDirection.LEFT);
		
		assertNotEquals(MovableGameObject.getOppositeMoveDirection(MovableGameObject.MoveDirection.LEFT) , 
						MovableGameObject.MoveDirection.UP);
		
		assertNotEquals(MovableGameObject.getOppositeMoveDirection(MovableGameObject.MoveDirection.LEFT) , 
						MovableGameObject.MoveDirection.DOWN);

		assertEquals(MovableGameObject.getOppositeMoveDirection(MovableGameObject.MoveDirection.RIGHT) , 
					 MovableGameObject.MoveDirection.LEFT);
		
		assertNotEquals(MovableGameObject.getOppositeMoveDirection(MovableGameObject.MoveDirection.RIGHT) , 
						MovableGameObject.MoveDirection.RIGHT);
		
		assertNotEquals(MovableGameObject.getOppositeMoveDirection(MovableGameObject.MoveDirection.RIGHT) , 
						MovableGameObject.MoveDirection.UP);
		
		assertNotEquals(MovableGameObject.getOppositeMoveDirection(MovableGameObject.MoveDirection.RIGHT) , 
						MovableGameObject.MoveDirection.DOWN);
	}
	
	@Test
	public void testGameObjectMovement() {
		int hero_xPos = 5;
		int hero_yPos = 5;
		boolean heroHasWeapon = false;
		MovableGameObject obj = new Hero(hero_xPos, hero_yPos, heroHasWeapon);
		
		// Move the game object in North direction
		obj.move(MovableGameObject.MoveDirection.UP);
		assertEquals(obj.getX_pos(), 5);
		assertEquals(obj.getY_pos(), 4);
		obj.move(MovableGameObject.MoveDirection.UP);
		assertEquals(obj.getX_pos(), 5);
		assertEquals(obj.getY_pos(), 3);
		
		// Move the game object in South direction
		obj.move(MovableGameObject.MoveDirection.DOWN);
		assertEquals(obj.getX_pos(), 5);
		assertEquals(obj.getY_pos(), 4);
		obj.move(MovableGameObject.MoveDirection.DOWN);
		assertEquals(obj.getX_pos(), 5);
		assertEquals(obj.getY_pos(), 5);
		
		// Move the game object in East direction
		obj.move(MovableGameObject.MoveDirection.RIGHT);
		assertEquals(obj.getX_pos(), 6);
		assertEquals(obj.getY_pos(), 5);
		obj.move(MovableGameObject.MoveDirection.RIGHT);
		assertEquals(obj.getX_pos(), 7);
		assertEquals(obj.getY_pos(), 5);
		
		// Move the game object in West direction
		obj.move(MovableGameObject.MoveDirection.LEFT);
		assertEquals(obj.getX_pos(), 6);
		assertEquals(obj.getY_pos(), 5);
		obj.move(MovableGameObject.MoveDirection.LEFT);
		assertEquals(obj.getX_pos(), 5);
		assertEquals(obj.getY_pos(), 5);
		
		// Verify that the game object doesn't move in 'Invalid' direction
		obj.move(MovableGameObject.MoveDirection.INVALID);
		assertEquals(obj.getX_pos(), 5);
		assertEquals(obj.getY_pos(), 5);
	}
	
	@Test
	public void testHero() {
		int hero_xPos = 5;
		int hero_yPos = 5;
		boolean heroHasWeapon = false;
		Hero hero = new Hero(hero_xPos, hero_yPos, heroHasWeapon);
		
		// Confirm hero position
		assertEquals(hero.getX_pos(), 5);
		assertEquals(hero.getY_pos(), 5);
		assertNotEquals(hero.getX_pos(), 0);
		assertNotEquals(hero.getY_pos(), 0);
		
		// Verify that hero does not have a weapon
		assertFalse(hero.hasClub());
		
		// Verify that hero does not have a key
		assertFalse(hero.hasKey());
		
		// Catch weapon and verify hero has weapon
		hero.catchClub();
		assertTrue(hero.hasClub());
		
		// Catch key and verify hero has key
		hero.catchKey();
		assertTrue(hero.hasKey());
	}
	
	@Test
	public void testDoor() {
		int door_xPos = 4;
		int door_yPos = 4;
		boolean isExitDoor = true;
		Door door = new Door(door_xPos, door_yPos, isExitDoor);
		
		// Confirm Door position
		assertEquals(door.getX_pos(), 4);
		assertEquals(door.getY_pos(), 4);
		assertNotEquals(door.getX_pos(), 0);
		assertNotEquals(door.getY_pos(), 0);
		
		// Confirm that door IS an exit door
		assertTrue(door.isExit());
		
		// Confirm that the door is closed
		assertTrue(door.isClosed());
		assertFalse(door.isOpen());
		assertEquals(door.getIdSymbol(), 'I');
		
		// Open the door
		door.open();
		
		// Confirm that the door is now open
		assertFalse(door.isClosed());
		assertTrue(door.isOpen());
		assertEquals(door.getIdSymbol(), 'S');
		
		// Re-Close the door
		door.close();
		
		// Confirm that the door is now open
		assertTrue(door.isClosed());
		assertFalse(door.isOpen());
		assertEquals(door.getIdSymbol(), 'I');
		
	}
	
	@Test
	public void testOgre() {
		int ogre_xPos = 3;
		int ogre_yPos = 3;
		Ogre ogre = new Ogre(ogre_xPos, ogre_yPos);
		
		// Confirm Ogre position
		assertEquals(ogre.getX_pos(), 3);
		assertEquals(ogre.getY_pos(), 3);
		assertNotEquals(ogre.getX_pos(), 0);
		assertNotEquals(ogre.getY_pos(), 0);
		
		// Confirm that ogre is not stunned
		assertFalse(ogre.isStunned());
		
		// Confirm that the ogre can move when he is not stunned
		ogre.move(Ogre.MoveDirection.UP);
		assertEquals(ogre.getX_pos(), 3);
		assertEquals(ogre.getY_pos(), 2);
		ogre.move(Ogre.MoveDirection.LEFT);
		assertEquals(ogre.getX_pos(), 2);
		assertEquals(ogre.getY_pos(), 2);
		ogre.move(Ogre.MoveDirection.DOWN);
		assertEquals(ogre.getX_pos(), 2);
		assertEquals(ogre.getY_pos(), 3);
		ogre.move(Ogre.MoveDirection.RIGHT);
		assertEquals(ogre.getX_pos(), 3);
		assertEquals(ogre.getY_pos(), 3);
		
		// Stun the ogre
		ogre.stun();
		assertTrue(ogre.isStunned());
		
		// Confirm that the ogre can't move when he is stunned
		ogre.move(Ogre.MoveDirection.UP);
		assertEquals(ogre.getX_pos(), 3);
		assertEquals(ogre.getY_pos(), 3);
		ogre.move(Ogre.MoveDirection.LEFT);
		assertEquals(ogre.getX_pos(), 3);
		assertEquals(ogre.getY_pos(), 3);
		ogre.move(Ogre.MoveDirection.DOWN);
		assertEquals(ogre.getX_pos(), 3);
		assertEquals(ogre.getY_pos(), 3);
		ogre.move(Ogre.MoveDirection.RIGHT);
		assertEquals(ogre.getX_pos(), 3);
		assertEquals(ogre.getY_pos(), 3);
		
	}
	
	@Test
	public void testKey() {
		Key key = new Key(7,7);
		
		// Confirm Key position
		assertEquals(key.getX_pos(), 7);
		assertEquals(key.getY_pos(), 7);
		assertNotEquals(key.getX_pos(), 0);
		assertNotEquals(key.getY_pos(), 0);
		
		// Confirm key symbol
		assertEquals(key.getIdSymbol(), 'k');
		
	}
	
	@Test
	public void testLever() {
		Lever lever = new Lever(5,5);
		
		// Confirm Key position
		assertEquals(lever.getX_pos(), 5);
		assertEquals(lever.getY_pos(), 5);
		assertNotEquals(lever.getX_pos(), 0);
		assertNotEquals(lever.getY_pos(), 0);
		
		// Confirm key symbol
		assertEquals(lever.getIdSymbol(), 'k');
		
	}
	
	@Test
	public void testWall() {
		Wall wall = new Wall(10,10);
		
		// Confirm Key position
		assertEquals(wall.getX_pos(), 10);
		assertEquals(wall.getY_pos(), 10);
		assertNotEquals(wall.getX_pos(), 0);
		assertNotEquals(wall.getY_pos(), 0);
		
		// Confirm key symbol
		assertEquals(wall.getIdSymbol(), 'X');
		
	}
	
	@Test
	public void testClub() {
		Club club = new Club(1, 1);
		
		// Confirm Key position
		assertEquals(club.getX_pos(), 1);
		assertEquals(club.getY_pos(), 1);
		assertNotEquals(club.getX_pos(), 0);
		assertNotEquals(club.getY_pos(), 0);
		
		// Confirm key symbol
		assertEquals(club.getIdSymbol(), '*');
		
	}

}
