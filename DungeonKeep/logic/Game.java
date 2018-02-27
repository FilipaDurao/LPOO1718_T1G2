package DungeonKeep.logic;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	
	private boolean running;
	private ArrayList<Level> levels;
	private int currentLevelIndex;
	
	public Game() {
		super();
		
		// Initialize the levels
		levels = new ArrayList<Level>();
		levels.add(initializeLevel1());
		levels.add(initializeLevel2());
		
		running = true;
		currentLevelIndex = 0;
		
		// Print the first level the first time
		levels.get(currentLevelIndex).draw();
	}
	
	public boolean isRunning() {
		return running;
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
		
		// Initialize the guard
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
		
		
		// Initialize level itself
		return new Level(
				new Hero(1, 1),
				level1walls,
				level1doors,
				new Guard(8, 1, guardMoves),
				new Lever(7, 8),
				null, 
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
		
		// Initialize level itself
		return new Level(
				new Hero(1, 7),
				level2walls,
				level2doors,
				null,
				null,
				new Ogre(4, 1), 
				new Key(7, 1),
				9,
				9);
		
	}
	
	private static boolean keyIsValid(char keyPressed) {
		return (keyPressed=='w' || keyPressed=='W' || keyPressed=='s' || keyPressed=='S' ||
				keyPressed=='a' || keyPressed=='A' || keyPressed=='d' || keyPressed=='D');
	}
	
/*
 *  TODO: Delete this
	private static void playLevel(Level level) {
		// Play the game until the Player wins or loses
		Scanner reader = new Scanner(System.in);
		Level.LevelStatus levelStat;
		char keyPressed;
		level.draw();
		
		while(true) {
			// Get a move from the user
			System.out.print("\n\nEnter a move: ");	
			keyPressed = reader.next().charAt(0);
			
			// Verify if pressed key is valid
			if (!keyIsValid(keyPressed)) {
				continue;
			}
			
			// Update the game
			levelStat = level.update(keyPressed);
			level.draw();
			
			// Check if level has terminated
			if(levelStat != Level.LevelStatus.RUNNING) {
				if(levelStat == Level.LevelStatus.VICTORY) {	// Player finished this level
					break;
				}
				else {	// Player Lost the Game
					System.out.println("\nYou lose.");
					System.exit(0);
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		// Play level 1
		Level level1 = initializeLevel1();
		playLevel(level1);
		
		System.out.println("\n\nLevel 1 Completed!");
		
		// Play level 2
		Level level2 = initializeLevel2();
		playLevel(level2);
		
		// All levels terminated! Player won the game
		System.out.println("\n\nVictory! You win! :D");
	}	
*/
	
	public void update(char keyPressed) {
		// Check if the key is relevant
		if(!keyIsValid(keyPressed)) {
			return;
		}
		
		// Update the level
		levels.get(currentLevelIndex).update(keyPressed);
		
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
				levels.get(currentLevelIndex).draw();
			}
		}
		
	}

}











