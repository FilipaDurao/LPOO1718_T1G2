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
			
		// Verify correct guard personality and number of ogres
		assertTrue(((DungeonLevel) game.getLevels().get(0)).getGuard() instanceof RookieGuard);
		assertFalse(((DungeonLevel) game.getLevels().get(0)).getGuard() instanceof DrunkenGuard);
		assertFalse(((DungeonLevel) game.getLevels().get(0)).getGuard() instanceof SuspiciousGuard);
		assertEquals(((KeepLevel) game.getLevels().get(1)).getOgres().size() , 1);
		assertNotEquals(((KeepLevel) game.getLevels().get(1)).getOgres().size() , 2);
		assertNotEquals(((KeepLevel) game.getLevels().get(1)).getOgres().size() , 3);
		
		// Verify correct number of walls and doors
		assertEquals(game.getLevels().get(0).getWalls().size(), 53);
		assertNotEquals(game.getLevels().get(0).getWalls().size(), 50);
		assertEquals(game.getLevels().get(0).getDoors().size(), 7);
		assertNotEquals(game.getLevels().get(0).getDoors().size(), 5);
		assertEquals(game.getLevels().get(1).getWalls().size(), 31);
		assertNotEquals(game.getLevels().get(1).getWalls().size(), 30);
		assertEquals(game.getLevels().get(1).getDoors().size(), 1);
		assertNotEquals(game.getLevels().get(1).getDoors().size(), 0);
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
