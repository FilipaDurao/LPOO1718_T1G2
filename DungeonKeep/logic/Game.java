package DungeonKeep.logic;
import java.util.HashSet;
import java.util.ArrayList;

public class Game {
	
	private boolean running;
	private ArrayList<Level> levels;
	private int currentLevelIndex;
	
	public Game() {
		super();
		
		// Initialize the levels
		levels = new ArrayList<Level>();
		//levels.add(initializeLevel1());
		//levels.add(initializeLevel2());
		levels.add(initializeTestLevel());
		
		running = true;
		currentLevelIndex = 0;
		
		// Print the first level the first time
		levels.get(currentLevelIndex).draw();
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public ArrayList<Level> getLevels() {
		return levels; 
	}
	
	private static Level initializeLevel1() {
		HashSet<Wall> level1walls = new HashSet<Wall>();
		HashSet<Door> level1doors = new HashSet<Door>();
		
		// Initialize the doors
		level1doors.add(new Door(0, 5, true));
		level1doors.add(new Door(0, 6, true));
		level1doors.add(new Door(2, 3, false));
		level1doors.add(new Door(4, 3, false));
		level1doors.add(new Door(4, 1, false));
		level1doors.add(new Door(2, 8, false));
		level1doors.add(new Door(4, 8, false));
		
		// Initialize the walls
		level1walls.add(new Wall(1, 2));
		level1walls.add(new Wall(2, 2));
		level1walls.add(new Wall(4, 2));
		level1walls.add(new Wall(5, 2));
		level1walls.add(new Wall(6, 2));
		level1walls.add(new Wall(1, 4));
		level1walls.add(new Wall(2, 4));
		level1walls.add(new Wall(4, 4));
		level1walls.add(new Wall(5, 4));
		level1walls.add(new Wall(6, 4));
		level1walls.add(new Wall(6, 1));
		level1walls.add(new Wall(6, 3));
		level1walls.add(new Wall(1, 7));
		level1walls.add(new Wall(2, 7));
		level1walls.add(new Wall(4, 7));
		level1walls.add(new Wall(5, 7));
		level1walls.add(new Wall(6, 7));
		level1walls.add(new Wall(7, 7));
		level1walls.add(new Wall(6, 8));
		level1walls.add(new Wall(0, 1));
		level1walls.add(new Wall(0, 2));
		level1walls.add(new Wall(0, 3));
		level1walls.add(new Wall(0, 4));
		level1walls.add(new Wall(0, 7));
		level1walls.add(new Wall(0, 8));
		
		for(int i=0 ; i<10 ; i++) {
			level1walls.add(new Wall(i, 0));
			level1walls.add(new Wall(i, 9));
		}
		
		for(int i=1 ; i<9 ; i++) {
			level1walls.add(new Wall(9, i));
		}
		
		// Initialize the guards
		HashSet<Guard> level1guards = new HashSet<Guard>();
		ArrayList<Guard.MoveDirection> guardMoves = new ArrayList<Guard.MoveDirection>();
		guardMoves.add(Guard.MoveDirection.LEFT);
		guardMoves.add(Guard.MoveDirection.DOWN);
		guardMoves.add(Guard.MoveDirection.DOWN);
		guardMoves.add(Guard.MoveDirection.DOWN);
		guardMoves.add(Guard.MoveDirection.DOWN);
		guardMoves.add(Guard.MoveDirection.LEFT);
		guardMoves.add(Guard.MoveDirection.LEFT);
		guardMoves.add(Guard.MoveDirection.LEFT);
		guardMoves.add(Guard.MoveDirection.LEFT);
		guardMoves.add(Guard.MoveDirection.LEFT);
		guardMoves.add(Guard.MoveDirection.LEFT);
		guardMoves.add(Guard.MoveDirection.DOWN);
		guardMoves.add(Guard.MoveDirection.RIGHT);
		guardMoves.add(Guard.MoveDirection.RIGHT);
		guardMoves.add(Guard.MoveDirection.RIGHT);
		guardMoves.add(Guard.MoveDirection.RIGHT);
		guardMoves.add(Guard.MoveDirection.RIGHT);
		guardMoves.add(Guard.MoveDirection.RIGHT);
		guardMoves.add(Guard.MoveDirection.RIGHT);
		guardMoves.add(Guard.MoveDirection.UP);
		guardMoves.add(Guard.MoveDirection.UP);
		guardMoves.add(Guard.MoveDirection.UP);
		guardMoves.add(Guard.MoveDirection.UP);
		guardMoves.add(Guard.MoveDirection.UP);
		level1guards.add(new RookieGuard(8, 1, guardMoves));
		
		
		// Initialize level itself
		return new Level(
				1,
				new Hero(1, 1, false),
				level1walls,
				level1doors,
				level1guards,
				new HashSet<Ogre>(),	// No ogres in this level
				new Lever(7, 8),
				null,
				10,
				10);
	}
	
	private static Level initializeLevel2() {
		HashSet<Wall> level2walls = new HashSet<Wall>();
		HashSet<Door> level2doors = new HashSet<Door>();
		
		// Initialize the door
		level2doors.add(new Door(0, 1, true));
		
		// Initialize the walls
		for(int i=0 ; i<9 ; i++) {
			level2walls.add(new Wall(i, 0));
			level2walls.add(new Wall(i, 8));
		}
		
		for(int i=1 ; i<8 ; i++) {
			level2walls.add(new Wall(8, i));
			
			if (i != 1) {
				level2walls.add(new Wall(0, i));
			}
		}
		
		// Initialize the ogres
		HashSet<Ogre> level2ogres = new HashSet<Ogre>();
		level2ogres.add(new Ogre(4,1));
		
		// Multi-Ogre testing
		//level2ogres.add(new Ogre(4,3));
		//level2ogres.add(new Ogre(1,1));
		
		// Initialize level itself
		return new Level(
				2,
				new Hero(1, 7, true),
				level2walls,
				level2doors,
				new HashSet<Guard>(),
				level2ogres, 
				null,
				new Key(7, 1),
				9,
				9);
		
	}
	
	private static Level initializeTestLevel() {		
		// Initialize the door
		HashSet<Door> doors = new HashSet<Door>();
		doors.add(new Door(9, 0, true));
		
		// Initialize the walls
		HashSet<Wall> walls = new HashSet<Wall>();
		
		for(int i=0 ; i<19 ; i++) {
			if (i != 9) {
				walls.add(new Wall(i, 0));
			}
			walls.add(new Wall(i, 39));
		}
		
		for(int i=1 ; i<39 ; i++) {
			walls.add(new Wall(18, i));
			walls.add(new Wall(0, i));
		}
		
		for(int i=1 ; i<18 ; i++) {
			if (i != 3) {
				walls.add(new Wall(i , 10));
			}
		}
		
		for(int i=1 ; i<18 ; i++) {
			if (i != 15) {
				walls.add(new Wall(i , 14));
			}
		}
		
		// Initialize the ogres
		HashSet<Ogre> ogres = new HashSet<Ogre>();
		ogres.add(new Ogre(8,1));
		ogres.add(new Ogre(10,1));
		ogres.add(new Ogre(6,1));
		ogres.add(new Ogre(12,1));
		
		// Initialize the guards
		HashSet<Guard> guards = new HashSet<Guard>();
		ArrayList<Guard.MoveDirection> guard1Moves = new ArrayList<Guard.MoveDirection>();
		ArrayList<Guard.MoveDirection> guard2Moves = new ArrayList<Guard.MoveDirection>();
		
		for(int i=0 ; i<16 ; i++) {
			guard1Moves.add(Guard.MoveDirection.RIGHT);
			guard2Moves.add(Guard.MoveDirection.LEFT);
		}
		
		for(int i=0 ; i<16 ; i++) {
			guard1Moves.add(Guard.MoveDirection.LEFT);
			guard2Moves.add(Guard.MoveDirection.RIGHT);
		}
		
		guards.add(new RookieGuard(1, 35, guard1Moves));
		guards.add(new RookieGuard(17, 33, guard2Moves));
		guards.add(new RookieGuard(1, 31, guard1Moves));
		guards.add(new RookieGuard(17, 29, guard2Moves));
		guards.add(new RookieGuard(1, 27, guard1Moves));
		guards.add(new DrunkenGuard(1, 11, guard1Moves));
		guards.add(new DrunkenGuard(17, 13, guard2Moves));
		
		// Initialize level itself
		return new Level(
				2,
				new Hero(9, 38, true),
				walls,
				doors,
				guards,
				ogres, 
				null,
				new Key(9, 12),
				19,
				40);
		
	}
	
	private static boolean keyIsValid(char keyPressed) {
		return (keyPressed=='w' || keyPressed=='W' || keyPressed=='s' || keyPressed=='S' ||
				keyPressed=='a' || keyPressed=='A' || keyPressed=='d' || keyPressed=='D');
	}
	
	private void drawLevel() {
		levels.get(currentLevelIndex).draw();
	}
	
	
	public void update(char keyPressed) {
		// Check if the key is relevant
		if(!keyIsValid(keyPressed)) {
			return;
		}
		
		// Update the level
		levels.get(currentLevelIndex).update(keyPressed);
		drawLevel();
		
		// Check if level ended
		Level.LevelStatus levelStatus = levels.get(currentLevelIndex).getStatus();
		
		if (levelStatus == Level.LevelStatus.DEFEAT) {
			// Game Over
			System.out.println("\n\nYou lost...");
			running = false;
			return;
		}
		else if (levelStatus == Level.LevelStatus.VICTORY) {
			// Advance to next level
			System.out.println("\n\nLevel Completed!\n");
			currentLevelIndex++;
			
			// Check if all levels are complete
			if (currentLevelIndex >= levels.size()) {
				System.out.println("\n\nVictory! You win!\n");
				running = false;
			}
			else {
				// Draw the new level for the player
				drawLevel();
			}
		}
		
	}

}