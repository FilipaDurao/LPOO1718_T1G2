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
	
	@Test
	public void testSetKeepLevel() {
		Game game = new Game(4, "Rookie");
		
		// Initialize new custom level
		int levelWidth = 5;
		int levelHeight = 5;
		int newLevelId = 100;
		Hero hero = new Hero(1, 1, true);
		Ogre ogre = new Ogre(3, 3);
		int numOgres = 5;
		Key key = new Key(1, 2);
		
		ArrayList<Door> doors = new ArrayList<Door>();
		doors.add(new Door(3, 0, true));
		
		ArrayList<Wall> walls = new ArrayList<Wall>();
		for (int i=0 ; i<levelWidth ; i++) {
			walls.add(new Wall(0, i));
			walls.add(new Wall(levelHeight-1, i));
		}
		for (int i=1 ; i<levelHeight-1 ; i++) {
			if (i != 3) {
				walls.add(new Wall(i, 0));
			}
			walls.add(new Wall(i, levelWidth-1));
		}
		
		KeepLevel newLevel = new KeepLevel(
				newLevelId,
				hero,
				walls,
				doors,
				ogre,
				numOgres,
				key,
				levelWidth,
				levelHeight);
		
		// Set the new Level
		game.setKeepLevel(newLevel);
		
		// Verify set level 'correctness'
		KeepLevel l = (KeepLevel) game.getLevels().get(1);
				
		assertEquals(l.getID(), newLevelId);
		assertEquals(l.getWidth(), levelWidth);
		assertEquals(l.getHeight(), levelHeight);
		assertEquals(l.getOgres().size(), numOgres);
		assertEquals(l.getDoors().size(), 1);
		assertEquals(l.getWalls().size(), 15);
		
		assertEquals(l.getHero().getX_pos(), hero.getX_pos());
		assertEquals(l.getHero().getY_pos(), hero.getY_pos());
		assertTrue(l.getHero().hasClub());
		assertEquals(l.getKey().getY_pos(), key.getY_pos());
		assertEquals(l.getKey().getY_pos(), key.getY_pos());
	}
	
	@Test
	public void testGameConstructor() {
		Game game = new Game(2, "Rookie");
		
		assertEquals(((KeepLevel) game.getLevels().get(1)).getOgres().size(), 2);
		assertTrue(((DungeonLevel) game.getLevels().get(0)).getGuard() instanceof RookieGuard);
		
		
		game = new Game(3, "Suspicious");
		
		assertEquals(((KeepLevel) game.getLevels().get(1)).getOgres().size(), 3);
		assertTrue(((DungeonLevel) game.getLevels().get(0)).getGuard() instanceof SuspiciousGuard);
		
		
		game = new Game(4, "Drunken");
		
		assertEquals(((KeepLevel) game.getLevels().get(1)).getOgres().size(), 4);
		assertTrue(((DungeonLevel) game.getLevels().get(0)).getGuard() instanceof DrunkenGuard);
		
		
		game = new Game(10, "Rookie");
		
		assertEquals(((KeepLevel) game.getLevels().get(1)).getOgres().size(), 10);
		assertTrue(((DungeonLevel) game.getLevels().get(0)).getGuard() instanceof RookieGuard);
	}

}
