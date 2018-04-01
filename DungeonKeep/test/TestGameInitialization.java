package DungeonKeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;

import DungeonKeep.logic.*;

public class TestGameInitialization {

	@Test
	public void testGameInitialization() {
		Game game = new Game(1,"Rookie");
		
		// Verify if the correct levels were created
		ArrayList<Level> gameLevels = game.getLevels();
		assertEquals(gameLevels.size() , 2);
		assertEquals(gameLevels.get(0).getID() , 1);
		assertEquals(gameLevels.get(1).getID() , 2);
	}
	
	@Test
	public void testGameMethods() {
		Game game = new Game(2,"Suspicious");
		
		// Confirm the game is running
		assertTrue(game.getStatus() == Game.Status.RUNNING);
		
		// Test parsing key pressed by the user
		assertEquals(Game.parseKeyPressed('W') , Hero.MoveDirection.UP);
		assertEquals(Game.parseKeyPressed('w') , Hero.MoveDirection.UP);
		assertEquals(Game.parseKeyPressed('S') , Hero.MoveDirection.DOWN);
		assertEquals(Game.parseKeyPressed('s') , Hero.MoveDirection.DOWN);
		assertEquals(Game.parseKeyPressed('A') , Hero.MoveDirection.LEFT);
		assertEquals(Game.parseKeyPressed('a') , Hero.MoveDirection.LEFT);
		assertEquals(Game.parseKeyPressed('D') , Hero.MoveDirection.RIGHT);
		assertEquals(Game.parseKeyPressed('d') , Hero.MoveDirection.RIGHT);
		assertEquals(Game.parseKeyPressed('H') , Hero.MoveDirection.INVALID);
		assertEquals(Game.parseKeyPressed('ç') , Hero.MoveDirection.INVALID);
		
		// Confirm that the game returns a Game matrix
		assertFalse(game.getGameMatrixString().isEmpty());
		
		// Confirm that the game starts in the Dungeon Level
		assertTrue(game.getCurrentLevel() instanceof DungeonLevel);
	}

}
