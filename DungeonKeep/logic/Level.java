package DungeonKeep.logic;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Level implements IStatus, Serializable {

	private static final long serialVersionUID = 1L;	// To allow file writing
	protected int ID;								/**< The Level's ID */
	protected Hero hero;							/**< The Level's Hero */
	protected ArrayList<Wall> walls;				/**< The Level's Walls */
	protected ArrayList<Door> doors;				/**< The Level's Doors */
	protected int width;							/**< The Level's width */
	protected int heigth;							/**< The Level's height */
	protected Status status = Status.RUNNING;		/**< The current Level Status */
	
	/**
	 * Creates a Level
	 * 
	 * @param ID the Level's ID
	 * @param hero the Level's Hero
	 * @param walls all the Level's walls 
	 * @param doors all the Level's doors
	 * @param width the Level's width
	 * @param heigth the Level's height
	 */
	public Level(
			int ID,
			Hero hero,
			ArrayList<Wall> walls,
			ArrayList<Door> doors,
			int width, 
			int heigth) {
		
		super();
		this.ID = ID;
		this.hero = hero;
		this.walls = walls;
		this.doors = doors;
		this.width = width;
		this.heigth = heigth;
	}
	
	/**
	 * @return The Level's ID
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * @return The Level's Status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @return The Level's width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the Level's width
	 * 
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return The Level's height
	 */
	public int getHeight() {
		return heigth;
	}

	/**
	 * Sets the Level's height
	 * 
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.heigth = height;
	}

	/**
	 * @return The Level's Hero
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * @return All the Walls of the Level
	 */
	public ArrayList<Wall> getWalls() {
		return walls;
	}

	/**
	 * @return All the Doors of the Level
	 */
	public ArrayList<Door> getDoors() {
		return doors;
	}

	/**
	 * Fills a matrix with the correct game symbols and returns a String representation of it
	 * 
	 * @return Returns a string representing all the Level objects
	 */
	public abstract String getGameMatrix();	
	
	/**
	 * Updates the Level based on the direction the player chose to move.
	 * 
	 * @param heroDirection the direction the player chose
	 */
	public abstract void update(Hero.MoveDirection heroDirection);
	
	/**
	 * Verifies if there is a collision between the Hero and an open Door
	 * 
	 * @return True if there is a collision between the Hero and an open Door, False otherwise
	 */
	protected boolean heroCollidesWithOpenDoor() {
		for(Door d : doors) {	// Iterate through all the doors
			if(hero.collidesWith(d) && d.isOpen()) {
				return true;	// Colliding with open door!
			}
		}
		
		// No collision was found
		return false;	
	}
	
	/**
	 * Verifies if a given position has a barrier in it (Walls or Doors)
	 * 
	 * @param x the x position to verify
	 * @param y the y position to verify
	 * 
	 * @return True if there is a barrier on the position, False otherwise
	 */
	public boolean doesPositionHaveBarrier(int x, int y) {
		
		// Check if the position has walls
		for(Wall w : walls) {
			if(w.getX_pos() == x && w.getY_pos() == y) {
				return true;
			}
		}
		
		// Check if the position has doors
		for(Door d : doors) {
			if(d.getX_pos() == x && d.getY_pos() == y) {
				return true;
			}
		}
		
		// If the position doesn't have walls or doors then it's free to move to
		return false;
	}
	
	/**
	 * Parses a matrix to a string
	 * 
	 * @param matrix the matrix to parse
	 * 
	 * @return The parsed matrix in a string
	 */
	protected String parseMatrixToString(char[][] matrix) {
		String gameMatrixString = "";
		
		for(char[] row : matrix) {
			for(char c : row) {
				gameMatrixString += Character.toString(c) + " ";
			}
			gameMatrixString += "\n";
		}
		
		return gameMatrixString;
	}
	 
}
