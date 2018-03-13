package DungeonKeep.logic;
import java.util.ArrayList;

public class Game implements IStatus {
	
	private ArrayList<Level> levels;
	private int currentLevelIndex;
	private Status status = Status.RUNNING;
	
	public Game(int numOgres , String guardPersonality) {
		super();
		
		// Initialize the levels
		levels = new ArrayList<Level>();
		//levels.add(initializeDungeonLevel(guardPersonality));
		levels.add(initializeKeepLevel(numOgres));
		
		currentLevelIndex = 0;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public ArrayList<Level> getLevels() {
		return levels; 
	}
	
	public String getGameMatrixString() {
		return levels.get(currentLevelIndex).getGameMatrix();
	}
	
	private static DungeonLevel initializeDungeonLevel(String guardPersonality) {
		ArrayList<Wall> walls = new ArrayList<Wall>();
		ArrayList<Door> doors = new ArrayList<Door>();
		
		// Initialize the doors
		doors.add(new Door(0, 5, true));
		doors.add(new Door(0, 6, true));
		doors.add(new Door(2, 3, false));
		doors.add(new Door(4, 3, false));
		doors.add(new Door(4, 1, false));
		doors.add(new Door(2, 8, false));
		doors.add(new Door(4, 8, false));
		
		// Initialize the walls
		walls.add(new Wall(1, 2));
		walls.add(new Wall(2, 2));
		walls.add(new Wall(4, 2));
		walls.add(new Wall(5, 2));
		walls.add(new Wall(6, 2));
		walls.add(new Wall(1, 4));
		walls.add(new Wall(2, 4));
		walls.add(new Wall(4, 4));
		walls.add(new Wall(5, 4));
		walls.add(new Wall(6, 4));
		walls.add(new Wall(6, 1));
		walls.add(new Wall(6, 3));
		walls.add(new Wall(1, 7));
		walls.add(new Wall(2, 7));
		walls.add(new Wall(4, 7));
		walls.add(new Wall(5, 7));
		walls.add(new Wall(6, 7));
		walls.add(new Wall(7, 7));
		walls.add(new Wall(6, 8));
		walls.add(new Wall(0, 1));
		walls.add(new Wall(0, 2));
		walls.add(new Wall(0, 3));
		walls.add(new Wall(0, 4));
		walls.add(new Wall(0, 7));
		walls.add(new Wall(0, 8));
		
		for(int i=0 ; i<10 ; i++) {
			walls.add(new Wall(i, 0));
			walls.add(new Wall(i, 9));
		}
		
		for(int i=1 ; i<9 ; i++) {
			walls.add(new Wall(9, i));
		}
		
		// Initialize the guard
		Guard guard;
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
		
		switch(guardPersonality) {
		case "Drunken":
			guard = new DrunkenGuard(8, 1, guardMoves);
			break;
		case "Suspicious":
			guard = new SuspiciousGuard(8, 1, guardMoves);
			break;
		default:	// Rookie
			guard = new RookieGuard(8, 1, guardMoves);
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
			}
			// Last level completed!
			else {
				this.status = Status.VICTORY;
			}
		}
		
	}

}