package DungeonKeep.logic;

public abstract class MovableGameObject extends GameObject {
	
	private static final long serialVersionUID = 1L;	// To allow file writing

	public enum MoveDirection {UP, DOWN, RIGHT, LEFT, INVALID}
	
	public MovableGameObject(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}

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
