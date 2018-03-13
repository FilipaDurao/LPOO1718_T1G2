package DungeonKeep.logic;
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
		levels.add(initializeLevel2());
		
		running = true;
		currentLevelIndex = 0;
		
		// Print the first level the first time
		drawLevel();
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public ArrayList<Level> getLevels() {
		return levels; 
	}
	
	private static Level initializeLevel1() {
		ArrayList<Wall> level1walls = new ArrayList<Wall>();
		ArrayList<Door> level1doors = new ArrayList<Door>();
		
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
		ArrayList<Guard> level1guards = new ArrayList<Guard>();
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
				new ArrayList<Ogre>(),	// No ogres in this level
				new Lever(7, 8),
				null,
				10,
				10);
	}
	
	private static Level initializeLevel2() {
		ArrayList<Wall> level2walls = new ArrayList<Wall>();
		ArrayList<Door> level2doors = new ArrayList<Door>();
		
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
		ArrayList<Ogre> level2ogres = new ArrayList<Ogre>();
		level2ogres.add(new Ogre(4,1));
		
		// Initialize level itself
		return new Level(
				2,
				new Hero(1, 7, true),
				level2walls,
				level2doors,
				new ArrayList<Guard>(),
				level2ogres, 
				null,
				new Key(7, 1),
				9,
				9);
		
	}
	
	public static Hero.MoveDirection parseKeyPressed(char keyPressed) {
		switch(keyPressed) {
		case 'W':
		case 'w':
			return Hero.MoveDirection.UP;
		case 'S':
		case 's':
			return Hero.MoveDirection.DOWN;
		case 'D':
		case 'd':
			return Hero.MoveDirection.RIGHT;
		case 'A':
		case 'a':
			return Hero.MoveDirection.LEFT;
		default:
			return Hero.MoveDirection.INVALID;
		}
	}
	
	private void drawLevel() {
		levels.get(currentLevelIndex).draw();
	}
	
	
	public void update(char keyPressed) {
		// Check if the key is relevant
		Hero.MoveDirection dir = parseKeyPressed(keyPressed);
		
		// Update the level
		levels.get(currentLevelIndex).update(dir);
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