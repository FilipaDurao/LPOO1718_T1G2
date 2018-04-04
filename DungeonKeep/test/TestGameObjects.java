package DungeonKeep.test;

import static org.junit.Assert.*;

import org.junit.Test;

import DungeonKeep.logic.GameObject;
import DungeonKeep.logic.Hero;
import DungeonKeep.logic.MovableGameObject;
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
		Hero hero = new Hero(5, 5, false);
		
		// Confirm hero position
		assertEquals(hero.getX_pos(), 5);
		assertEquals(hero.getY_pos(), 5);
		
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

}
