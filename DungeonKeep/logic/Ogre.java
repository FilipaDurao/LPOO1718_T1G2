package DungeonKeep.logic;

import java.util.ArrayList;

public class Ogre extends MovableGameObject {

	private static final long serialVersionUID = 1L;	// To allow file writing
	private final char regularSymbol = '0';		/**< The symbol that represents an Ogre on the console version of the game */
	private final char stunnedSymbol = '8';		/**< The symbol that represents a stunned Ogre on the console version of the game */
	private Club club = new Club(getX_pos() , getY_pos());	/**< The Ogre's Club */
	private boolean isStunned = false;			/**< Variable to determine if the Ogre is stunned */
	private boolean disabled = false;			/**< Variable to determine if the Ogre is not enabled */
	private int stunnedTimer = 0;				/**< Variable to determine how many more moves is the Ogre going to be stunned */
	
	/**
	 * Creates an Ogre
	 * 
	 * @param x_pos the x position where the Ogre is created
	 * @param y_pos the y position where the Ogre is created
	 */
	public Ogre(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}
	
	/**
	 * Verifies if the Ogre is stunned
	 * 
	 * @return True if the Ogre is stunned, False otherwise
	 */
	public boolean isStunned() {
		return isStunned;
	}
	
	/**
	 * "Stuns" the Ogre for the next two moves
	 */
	public void stun() {
		if (!isStunned) {
			this.isStunned = true;
			this.stunnedTimer = 2;
		}
	}
	
	/**
	 * Disables the Ogre
	 */
	public void disable() {
		disabled = true;
	}
	
	/**
	 * @return The ID symbol that represents an Ogre, either regular or stunned
	 */
	@Override
	public char getIdSymbol() {
		if(this.isStunned) {
			return stunnedSymbol;
		}
		else {
			return regularSymbol;
		}
	}
	
	/**
	 * @return The Ogre's Club
	 */
	public Club getClub() {
		return club;
	}
	
	/**
	 * Updates the Ogre's position in the game
	 * 
	 * @param walls all the current Level's Walls
	 * @param doors all the current Level's Doors
	 * @param ogres all the current Level's Ogres
	 */
	public void update(ArrayList<Wall> walls , ArrayList<Door> doors , ArrayList<Ogre> ogres) {
		// Check if the Ogre is disabled
		if (this.disabled) {
			return;
		}
		
		// Check if the Ogre is stunned
		if (this.stunnedTimer > 0) {
			this.stunnedTimer--;
		}
		
		// Ogre is not stunned ; Move
		else {
			this.isStunned = false;
		
			ArrayList<MoveDirection> possibleMoveDirections = getPossibleDirections(walls, doors, ogres);
			
			// If there are no possible direction, the ogre does not move.
			if (possibleMoveDirections.isEmpty()) {
				return;
			}
			
			// Perform the step in one of the random directions
			MoveDirection dir = possibleMoveDirections.get( 
					(int) Math.floor(Math.random()*possibleMoveDirections.size()) );
			
			move(dir);
		}
		
		// Swing the club
		swingClub(walls,doors,ogres);
	}
	
	
	private void swingClub(ArrayList<Wall> walls , ArrayList<Door> doors , ArrayList<Ogre> ogres) {
		ArrayList<MoveDirection> possibleSwingDirections = getClubPossibleDirections(walls, doors, ogres);
		
		// If there are no possible directions for the club, the ogre does not swing it.
		if (possibleSwingDirections.isEmpty()) {
			return;
		}
		
		// Perform the swing in one of the random directions
		MoveDirection dir = possibleSwingDirections.get( 
				(int) Math.floor(Math.random()*possibleSwingDirections.size()) );
		
		performClubSwing(dir);
	}
	
	private ArrayList<MoveDirection> getPossibleDirections(ArrayList<Wall> walls , 
			ArrayList<Door> doors , ArrayList<Ogre> ogres) {
		
		ArrayList<MoveDirection> possibleDirections = new ArrayList<MoveDirection>();
		
		// Analise 4 possible moves
		if (!positionHasWallDoor(this.getX_pos(), this.getY_pos()-1, walls, doors)) {
			possibleDirections.add(MoveDirection.UP);
		}
		if (!positionHasWallDoor(this.getX_pos() , this.getY_pos()+1 , walls, doors)) {
			possibleDirections.add(MoveDirection.DOWN);
		}
		if (!positionHasWallDoor(this.getX_pos()-1 , this.getY_pos() , walls, doors)) {
			possibleDirections.add(MoveDirection.LEFT);
		}
		if (!positionHasWallDoor(this.getX_pos()+1 , this.getY_pos() , walls, doors)) {
			possibleDirections.add(MoveDirection.RIGHT);
		}
		
		return possibleDirections;
	}
	
	private ArrayList<MoveDirection> getClubPossibleDirections(ArrayList<Wall> walls , 
			ArrayList<Door> doors , ArrayList<Ogre> ogres) {
		
		ArrayList<MoveDirection> possibleDirections = new ArrayList<MoveDirection>();
		
		// Analise 4 possible moves
		if (!positionHasWallDoorOgre(this.getX_pos() , this.getY_pos()-1 , walls, doors, ogres)) {
			possibleDirections.add(MoveDirection.UP);
		}
		if (!positionHasWallDoorOgre(this.getX_pos() , this.getY_pos()+1 , walls, doors, ogres)) {
			possibleDirections.add(MoveDirection.DOWN);
		}
		if (!positionHasWallDoorOgre(this.getX_pos()-1 , this.getY_pos() , walls, doors, ogres)) {
			possibleDirections.add(MoveDirection.LEFT);
		}
		if (!positionHasWallDoorOgre(this.getX_pos()+1 , this.getY_pos() , walls, doors, ogres)) {
			possibleDirections.add(MoveDirection.RIGHT);
		}
		
		return possibleDirections;
	}
	
	private boolean positionHasWallDoorOgre(int x, int y, ArrayList<Wall> walls, 
			ArrayList<Door> doors, ArrayList<Ogre> ogres) {
		
		return (positionHasWallDoor(x , y , walls, doors) ||
				positionHasOgre(this.getX_pos()+1 , this.getY_pos() , ogres));
	}
	
	private boolean positionHasWallDoor(int x, int y, ArrayList<Wall> walls, ArrayList<Door> doors) {
		return (positionHasWall(x , y , walls) ||
				positionHasDoor(x , y , doors));
	}
	
	private boolean positionHasOgre(int x , int y , ArrayList<Ogre> ogres) {
		for (Ogre ogre : ogres) {
			if (ogre.getX_pos() == x  &&  ogre.getY_pos() == y) {
				return true;
			}
		}
		
		// No ogre found it the position (x,y)
		return false;
	}
	
	private boolean positionHasWall(int x , int y , ArrayList<Wall> walls) {
		for (Wall wall : walls) {
			if (wall.getX_pos() == x  &&  wall.getY_pos() == y) {
				return true;
			}
		}
		
		// No wall found it the position (x,y)
		return false;
	}
	
	private boolean positionHasDoor(int x , int y , ArrayList<Door> doors) {
		for (Door door : doors) {
			if (door.getX_pos() == x  &&  door.getY_pos() == y) {
				return true;
			}
		}
		
		// No door found it the position (x,y)
		return false;
	}	
	
	private void performClubSwing (MoveDirection dir) {
		switch (dir) {
		case UP:
			club.setX_pos(this.getX_pos());
			club.setY_pos(this.getY_pos() - 1);
			break;
		case DOWN:
			club.setX_pos(this.getX_pos());
			club.setY_pos(this.getY_pos() + 1);
			break;
		case RIGHT:
			club.setX_pos(this.getX_pos() + 1);
			club.setY_pos(this.getY_pos());
			break;
		case LEFT:
			club.setX_pos(this.getX_pos() - 1);
			club.setY_pos(this.getY_pos());
			break;
		default:
			return;
		}		
	}
	
	/**
	 * Moves the Ogre in the direction passed by parameter
	 * 
	 * @param dir the direction to which we want to move to
	 */
	@Override
	public void move (MoveDirection dir) {
		if(!isStunned) {
			super.move(dir);
		}
	}

}