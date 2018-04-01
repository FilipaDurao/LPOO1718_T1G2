package DungeonKeep.test;

import static org.junit.Assert.*;

import org.junit.Test;

import DungeonKeep.logic.GameObject;
import DungeonKeep.logic.Hero;
import DungeonKeep.logic.MovableGameObject;
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

}
