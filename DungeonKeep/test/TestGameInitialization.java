package DungeonKeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

import DungeonKeep.logic.*;

public class TestGameInitialization {

	@Test
	public void testGameInitialization() {
		Game game = new Game();
		
		// Verify if the correct levels were created
		ArrayList<Level> gameLevels = game.getLevels();
		assertEquals(gameLevels.size() , 2);
		assertEquals(gameLevels.get(0).getID() , 1);
		assertEquals(gameLevels.get(1).getID() , 2);
	}
	
	@Test
	public void testGameMethods() {
		Game game = new Game();
		
		// Confirm the game is running
		assertTrue(game.isRunning());
		
		// Confirm correct key validation
		assertTrue(Game.keyIsValid('W'));
		assertTrue(Game.keyIsValid('w'));
		assertTrue(Game.keyIsValid('S'));
		assertTrue(Game.keyIsValid('s'));
		assertTrue(Game.keyIsValid('A'));
		assertTrue(Game.keyIsValid('a'));
		assertTrue(Game.keyIsValid('D'));
		assertTrue(Game.keyIsValid('d'));
		assertFalse(Game.keyIsValid('q'));
		assertFalse(Game.keyIsValid('6'));
		assertFalse(Game.keyIsValid('l'));
		assertFalse(Game.keyIsValid('ç'));
		assertFalse(Game.keyIsValid('P'));
	}

}
