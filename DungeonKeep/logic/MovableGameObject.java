package DungeonKeep.logic;

public abstract class MovableGameObject extends GameObject {
	
	private static final long serialVersionUID = 1L;	// To allow file writing

	public enum MoveDirection {UP, DOWN, RIGHT, LEFT, INVALID}	/**< The possible move directions that a movable object can have */
	
	/**
	 * Creates a Movable object
	 * 
	 * @param x_pos the x position where the Movable Object is created
	 * @param y_pos the y position where the Movable Object is created
	 */
	public MovableGameObject(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}

	/**
	 * Moves the Object in the direction passed by parameter
	 * 
	 * @param dir the direction to which we want to move to
	 */
	public void move (MoveDirection dir) {
		switch (dir) {
		case UP:
			this.setY_pos(this.getY_pos() - 1);
			break;
		case DOWN:
			this.setY_pos(this.getY_pos() + 1);
			break;
		case RIGHT:
			this.setX_pos(this.getX_pos() + 1);
			break;
		case LEFT:
			this.setX_pos(this.getX_pos() - 1);
			break;
		default:
			return;
		}		
	}
	
	/**
	 * Reverses a move direction, that is, returns the opposite direction to the one we pass as parameter
	 * 
	 * @param dir the direction we want the opposite to
	 * 
	 * @return A move direction opposed to the one we passed as parameter
	 */
	public static MoveDirection getOppositeMoveDirection(MoveDirection dir) {
		if (dir == MoveDirection.UP) {
			return MoveDirection.DOWN;
		}
		else if (dir == MoveDirection.DOWN) {
			return MoveDirection.UP;
		}
		else if (dir == MoveDirection.RIGHT) {
			return MoveDirection.LEFT;
		}
		else {
			return MoveDirection.RIGHT;
		}
	}
}
