package DungeonKeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

import DungeonKeep.logic.*;

public class TestGuards {
		
	private static ArrayList<MovableGameObject.MoveDirection> getGuardMoves(){
		ArrayList<MovableGameObject.MoveDirection> guardMoves = new ArrayList<MovableGameObject.MoveDirection>();
		guardMoves.add(MovableGameObject.MoveDirection.RIGHT);
		guardMoves.add(MovableGameObject.MoveDirection.RIGHT);
		guardMoves.add(MovableGameObject.MoveDirection.DOWN);
		guardMoves.add(MovableGameObject.MoveDirection.DOWN);
		guardMoves.add(MovableGameObject.MoveDirection.LEFT);
		guardMoves.add(MovableGameObject.MoveDirection.LEFT);
		guardMoves.add(MovableGameObject.MoveDirection.UP);
		guardMoves.add(MovableGameObject.MoveDirection.UP);
		
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
		
		// Perform a few more steps and confirm the guard is moving
		int x;
		int y;
		for (int i = 0 ; i<100 ; i++) {
			x = guard.getX_pos();
			y = guard.getY_pos();
			
			guard.performStep();
			
			// Verify that guard HAS moved
			assertTrue(guard.getX_pos() != x ||
					   guard.getY_pos() != y);
		}
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
		
		// Re-change the guard's direction
		guard.changeDirection();
		assertTrue(guard.isMovingForward());
		
		// Perform a few steps and confirm the guard is moving
		int x;
		int y;
		for (int i = 0 ; i<100 ; i++) {
			x = guard.getX_pos();
			y = guard.getY_pos();
			
			guard.performStep();
			
			// Verify that guard HAS moved
			assertTrue(guard.getX_pos() != x ||
					   guard.getY_pos() != y);
		}
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
		
		// Re-change the guard's direction
		guard.changeDirection();
		assertTrue(guard.isMovingForward());
		
		// Verify sleeping related methods
		assertTrue(guard.isAwake());
		assertEquals(guard.getIdSymbol() , 'G');
		guard.fallAsleep();
		assertTrue(guard.isAsleep());
		assertEquals(guard.getIdSymbol() , 'g');
		guard.wakeUp();
		assertTrue(guard.isAwake());
		assertEquals(guard.getIdSymbol() , 'G');
		
		// Perform a few steps and confirm the guard movement
		int x;
		int y;
		for (int i = 0 ; i<100 ; i++) {
			x = guard.getX_pos();
			y = guard.getY_pos();
			
			guard.performStep();
			
			if (guard.isAsleep()) {	// Verify that guard hasn't moved
				assertEquals(guard.getX_pos(), x);
				assertEquals(guard.getY_pos(), y);
			}
			else {					// Verify that guard HAS moved
				assertTrue(guard.getX_pos() != x ||
						   guard.getY_pos() != y);
			}
		}
	}

}
