package DungeonKeep.test;

import static org.junit.Assert.*;

import org.junit.Test;

import DungeonKeep.logic.GameObject;
import DungeonKeep.logic.Hero;
import DungeonKeep.logic.Wall;

public class TestGameObject {

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
		assertTrue(heroObj.isNear(wallObj1));
		assertTrue(wallObj1.isNear(heroObj));
		assertFalse(wallObj3.isNear(wallObj2));
		assertFalse(wallObj2.isNear(wallObj3));
		assertFalse(wallObj1.isNear(wallObj3));
		assertFalse(wallObj3.isNear(wallObj1));	
		
	}
	
	@Test
	public void testGameObjectDirections() {
		assertEquals(GameObject.getOppositeMoveDirection(GameObject.MoveDirection.UP) , GameObject.MoveDirection.DOWN);
		assertNotEquals(GameObject.getOppositeMoveDirection(GameObject.MoveDirection.UP) , GameObject.MoveDirection.LEFT);
		assertNotEquals(GameObject.getOppositeMoveDirection(GameObject.MoveDirection.UP) , GameObject.MoveDirection.RIGHT);
		assertNotEquals(GameObject.getOppositeMoveDirection(GameObject.MoveDirection.UP) , GameObject.MoveDirection.UP);

		assertEquals(GameObject.getOppositeMoveDirection(GameObject.MoveDirection.DOWN) , GameObject.MoveDirection.UP);
		assertNotEquals(GameObject.getOppositeMoveDirection(GameObject.MoveDirection.DOWN) , GameObject.MoveDirection.LEFT);
		assertNotEquals(GameObject.getOppositeMoveDirection(GameObject.MoveDirection.DOWN) , GameObject.MoveDirection.RIGHT);
		assertNotEquals(GameObject.getOppositeMoveDirection(GameObject.MoveDirection.DOWN) , GameObject.MoveDirection.DOWN);

		assertEquals(GameObject.getOppositeMoveDirection(GameObject.MoveDirection.LEFT) , GameObject.MoveDirection.RIGHT);
		assertNotEquals(GameObject.getOppositeMoveDirection(GameObject.MoveDirection.LEFT) , GameObject.MoveDirection.LEFT);
		assertNotEquals(GameObject.getOppositeMoveDirection(GameObject.MoveDirection.LEFT) , GameObject.MoveDirection.UP);
		assertNotEquals(GameObject.getOppositeMoveDirection(GameObject.MoveDirection.LEFT) , GameObject.MoveDirection.DOWN);

		assertEquals(GameObject.getOppositeMoveDirection(GameObject.MoveDirection.RIGHT) , GameObject.MoveDirection.LEFT);
		assertNotEquals(GameObject.getOppositeMoveDirection(GameObject.MoveDirection.RIGHT) , GameObject.MoveDirection.RIGHT);
		assertNotEquals(GameObject.getOppositeMoveDirection(GameObject.MoveDirection.RIGHT) , GameObject.MoveDirection.UP);
		assertNotEquals(GameObject.getOppositeMoveDirection(GameObject.MoveDirection.RIGHT) , GameObject.MoveDirection.DOWN);
	}

}
