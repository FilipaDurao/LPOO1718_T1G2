package DungeonKeep.logic;
import java.io.Serializable;
import java.util.ArrayList;

public class Game implements IStatus , Serializable {
	
	private static final long serialVersionUID = 1L;	// To allow file writing
	private ArrayList<Level> levels;					/**< All the Game's Levels */
	private int currentLevelIndex;						/**< Variable used to identify the current Level */
	private Status status = Status.RUNNING;				/**< The Game's Status */
	
	/**
	 * Creates a Game based on the user's inputs
	 * 
	 * @param numOgres the number of Ogres that will be present on the Keep Level
	 * @param guardPersonality the personality of the Guard that will be present on the Dungeon Level
	 */
	public Game(int numOgres , String guardPersonality) {
		super();
		
		// Initialize the levels
		levels = new ArrayList<Level>();
		levels.add(initializeDungeonLevel(guardPersonality));
		levels.add(initializeKeepLevel(numOgres));
		
		currentLevelIndex = 0;
	}
	
	/**
	 * Returns if the Game is Running or if it has ended
	 * 
	 * @return Returns the Game's Status
	 */
	public Status getStatus() {
		return status;
	}
	
	/** 
	 * @return All the Levels of the Game
	 */
	public ArrayList<Level> getLevels() {
		return levels; 
	}
	
	/**
	 * Returns the game matrix of the current Level being played
	 * 
	 * @return A game matrix
	 */
	public String getGameMatrixString() {
		return levels.get(currentLevelIndex).getGameMatrix();
	}
	
	/**
	 * @return The current Level
	 */
	public Level getCurrentLevel() {
		return levels.get(currentLevelIndex);
	}
	
	/** 
	 * Changes the game's default Keep Level to 'newKeepLevel'
	 * 
	 * @param newKeepLevel New game's Keep Level
	 */
	public void setKeepLevel(KeepLevel newKeepLevel) {
		levels.set(1, newKeepLevel);
	}
	
	private static ArrayList<Guard.MoveDirection> initGuardPath() {
		ArrayList<Guard.MoveDirection> guardMoves = new ArrayList<Guard.MoveDirection>();
		guardMoves.add(Guard.MoveDirection.LEFT);
		
		for (int i=0 ; i<4 ; i++) {
			guardMoves.add(Guard.MoveDirection.DOWN);
		}
		
		for (int i=0 ; i<6 ; i++) {
			guardMoves.add(Guard.MoveDirection.LEFT);
		}
		
		guardMoves.add(Guard.MoveDirection.DOWN);
		
		for (int i=0 ; i<7 ; i++) {
			guardMoves.add(Guard.MoveDirection.RIGHT);
		}
		
		for (int i=0 ; i<5 ; i++) {
			guardMoves.add(Guard.MoveDirection.UP);
		}
		
		return guardMoves;
	}
	
	private static ArrayList<Wall> initDungeonLevelWalls() {
		ArrayList<Wall> walls = new ArrayList<Wall>();
		
		walls.add(new Wall(1, 2)); walls.add(new Wall(2, 2)); walls.add(new Wall(4, 2));
		walls.add(new Wall(5, 2)); walls.add(new Wall(6, 2)); walls.add(new Wall(1, 4));
		walls.add(new Wall(2, 4)); walls.add(new Wall(4, 4)); walls.add(new Wall(5, 4));
		walls.add(new Wall(6, 4)); walls.add(new Wall(6, 1)); walls.add(new Wall(6, 3));
		walls.add(new Wall(1, 7)); walls.add(new Wall(2, 7)); walls.add(new Wall(4, 7));
		walls.add(new Wall(5, 7)); walls.add(new Wall(6, 7)); walls.add(new Wall(7, 7)); 
		walls.add(new Wall(6, 8)); walls.add(new Wall(0, 1)); walls.add(new Wall(0, 2));
		walls.add(new Wall(0, 3)); walls.add(new Wall(0, 4)); walls.add(new Wall(0, 7));
		walls.add(new Wall(0, 8));
		
		for(int i=0 ; i<10 ; i++) {
			walls.add(new Wall(i, 0));
			walls.add(new Wall(i, 9));
		}
		
		for(int i=1 ; i<9 ; i++) {
			walls.add(new Wall(9, i));
		}
		
		return walls;
	}
	
	private static ArrayList<Door> initDungeonLevelDoors() {
		ArrayList<Door> doors = new ArrayList<Door>();
		
		doors.add(new Door(0, 5, true));
		doors.add(new Door(0, 6, true));
		doors.add(new Door(2, 3, false));
		doors.add(new Door(4, 3, false));
		doors.add(new Door(4, 1, false));
		doors.add(new Door(2, 8, false));
		doors.add(new Door(4, 8, false));
		
		return doors;
	}
	
	private static DungeonLevel initializeDungeonLevel(String guardPersonality) {
		ArrayList<Wall> walls = initDungeonLevelWalls();
		ArrayList<Door> doors = initDungeonLevelDoors();
		
		// Initialize the guard
		Guard guard;		
		
		switch(guardPersonality) {
		case "Drunken":
			guard = new DrunkenGuard(8, 1, initGuardPath());
			break;
		case "Suspicious":
			guard = new SuspiciousGuard(8, 1, initGuardPath());
			break;
		default:	// Rookie
			guard = new RookieGuard(8, 1, initGuardPath());
			break;
		}
		
		// Initialize level itself
		return new DungeonLevel(
				1,
				new Hero(1, 1, false),
				walls,
				doors,
				guard,
				new Lever(7, 8),
				10,
				10);
	}
	
	private static KeepLevel initializeKeepLevel(int numOgres) {
		ArrayList<Wall> walls = new ArrayList<Wall>();
		ArrayList<Door> doors = new ArrayList<Door>();
		
		// Initialize the door
		doors.add(new Door(0, 1, true));
		
		// Initialize the walls
		for(int i=0 ; i<9 ; i++) {
			walls.add(new Wall(i, 0));
			walls.add(new Wall(i, 8));
		}
		
		for(int i=1 ; i<8 ; i++) {
			walls.add(new Wall(8, i));
			
			if (i != 1) {
				walls.add(new Wall(0, i));
			}
		}
		
		// Initialize the ogres
		ArrayList<Ogre> level2ogres = new ArrayList<Ogre>();
		for (int i=0 ; i<numOgres ; i++) {
			level2ogres.add(new Ogre(4,1));
		}
		
		// Initialize level itself
		return new KeepLevel(
				2,
				new Hero(1, 7, true),
				walls,
				doors,
				level2ogres, 
				new Key(7, 1),
				9,
				9);
		
	}
	
	/**
	 * Receives the key that the player pressed and processes it to know to which direction it corresponds
	 * 
	 * @param keyPressed the key that the player pressed
	 * 
	 * @return the MoveDirection correspondent to the keyPressed
	 */
	public static Hero.MoveDirection parseKeyPressed(char keyPressed) {
		keyPressed = Character.toUpperCase(keyPressed);
		
		switch(keyPressed) {
		case 'W':
			return Hero.MoveDirection.UP;
		case 'S':
			return Hero.MoveDirection.DOWN;
		case 'D':
			return Hero.MoveDirection.RIGHT;
		case 'A':
			return Hero.MoveDirection.LEFT;
		default:
			return Hero.MoveDirection.INVALID;
		}
	}	
	/**
	 * Receives the key pressed by the player and updates the whole game based on this input
	 * 
	 * @param keyPressed the key pressed by the player
	 */
	public void update(char keyPressed) {
		// Check if the key is relevant
		Hero.MoveDirection dir = parseKeyPressed(keyPressed);
		
		// Update the level
		levels.get(currentLevelIndex).update(dir);
		
		// Check if level ended
		Level.Status levelStatus = levels.get(currentLevelIndex).getStatus();
		
		if (levelStatus == Status.DEFEAT) {
			// Game Over
			this.status = Status.DEFEAT;
		}
		else if (levelStatus == Status.VICTORY) {
			// Advance to next level
			if (currentLevelIndex != levels.size()-1) {
				currentLevelIndex++;
				this.status = Status.LEVELCHANGED;
			}
			// Last level completed!
			else {
				this.status = Status.VICTORY;
			}
		}
		
	}

}