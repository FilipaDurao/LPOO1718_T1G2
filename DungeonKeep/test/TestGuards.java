package DungeonKeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

import DungeonKeep.logic.*;

public class TestGuards {
	
//	private static Level initializeTestLevel1() {
//		// Initialize Walls
//		ArrayList<Wall> walls = new ArrayList<Wall>();
//		for(int i=0 ; i<5 ; i++) {
//			walls.add(new Wall(0,i));
//			walls.add(new Wall(4,i));
//		}
//		for(int i=1 ; i<4 ; i++) {
//			walls.add(new Wall(i,0));
//			walls.add(new Wall(i,4));
//		}
//		
//		// Initialize guard
//		ArrayList<Guard> guards = new ArrayList<Guard>();
//		guards.add(new RookieGuard(1 , 1 , getGuardMoves()));
//	}
	
	private static ArrayList<GameObject.MoveDirection> getGuardMoves(){
		ArrayList<GameObject.MoveDirection> guardMoves = new ArrayList<GameObject.MoveDirection>();
		guardMoves.add(GameObject.MoveDirection.RIGHT);
		guardMoves.add(GameObject.MoveDirection.RIGHT);
		guardMoves.add(GameObject.MoveDirection.DOWN);
		guardMoves.add(GameObject.MoveDirection.DOWN);
		guardMoves.add(GameObject.MoveDirection.LEFT);
		guardMoves.add(GameObject.MoveDirection.LEFT);
		guardMoves.add(GameObject.MoveDirection.UP);
		guardMoves.add(GameObject.MoveDirection.UP);
		
		return guardMoves;
	}

	@Test
	public void testRookieGuard() {
		RookieGuard guard = new RookieGuard(1 , 1 , getGuardMoves());
		
		// Test guard's initial position
		assertEquals(guard.getX_pos() , 1);
		assertEquals(guard.getY_pos() , 1);
		
		// Move the guard a few steps
		guard.performStep();
		guard.performStep();
		assertEquals(guard.getX_pos() , 3);
		assertEquals(guard.getY_pos() , 1);
		
		// Move the guard a few more steps
		guard.performStep();
		guard.performStep();
		assertEquals(guard.getX_pos() , 3);
		assertEquals(guard.getY_pos() , 3);
		
		// Move the guard a few more steps
		guard.performStep();
		guard.performStep();
		assertEquals(guard.getX_pos() , 1);
		assertEquals(guard.getY_pos() , 3);
		
		// Move the guard the "final steps" to the initial position
		guard.performStep();
		guard.performStep();
		assertEquals(guard.getX_pos() , 1);
		assertEquals(guard.getY_pos() , 1);
	}
	
	@Test
	public void testSuspiciousGuard() {
		SuspiciousGuard guard = new SuspiciousGuard(1 , 1 , getGuardMoves());
		
		// Test guard's initial position
		assertEquals(guard.getX_pos() , 1);
		assertEquals(guard.getY_pos() , 1);
		
		// Confirm the guard beggins in the stantard direction ("moving forward")
		assertTrue(guard.isMovingForward());
		
		// Reverse the direction and confirm the guard is moving in reverse direction
		guard.changeDirection();
		assertFalse(guard.isMovingForward());
		
		// Perform a few steps and confirm the guard isn't in the beggining place
		for (int i = 0 ; i<25 ; i++) {
			guard.performStep();
		}
		assertFalse(guard.getX_pos() == 1   &&   guard.getY_pos() == 1);
	}
	
	@Test
	public void testDrunkenGuard() {
		DrunkenGuard guard = new DrunkenGuard(1 , 1 , getGuardMoves());
		
		// Test guard's initial position
		assertEquals(guard.getX_pos() , 1);
		assertEquals(guard.getY_pos() , 1);
		
		// Confirm the guard beggins in the stantard direction ("moving forward")
		assertTrue(guard.isMovingForward());
		
		// Reverse the direction and confirm the guard is moving in reverse direction
		guard.changeDirection();
		assertFalse(guard.isMovingForward());
		
		// Verify sleeping related methods
		assertTrue(guard.isAwake());
		assertEquals(guard.getIdSymbol() , 'G');
		guard.fallAsleep();
		assertTrue(guard.isAsleep());
		assertEquals(guard.getIdSymbol() , 'g');
		guard.wakeUp();
		assertTrue(guard.isAwake());
		assertEquals(guard.getIdSymbol() , 'G');
		
		// Perform a few steps and confirm the guard isn't in the beggining place
		for (int i = 0 ; i<25 ; i++) {
			guard.performStep();
		}
	}

}
