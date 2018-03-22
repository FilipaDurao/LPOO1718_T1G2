package DungeonKeep.logic;

import java.awt.image.BufferedImage;

public abstract class GameObject implements Drawable {

	public enum MoveDirection {UP, DOWN, RIGHT, LEFT, INVALID}
	
	private int x_pos;
	private int y_pos;
	protected BufferedImage sprite;
	
	public GameObject(int x_pos, int y_pos) {
		super();
		this.x_pos = x_pos;
		this.y_pos = y_pos;
	}

	public int getX_pos() {
		return x_pos;
	}

	public void setX_pos(int x_pos) {
		this.x_pos = x_pos;
	}

	public int getY_pos() {
		return y_pos;
	}

	public void setY_pos(int y_pos) {
		this.y_pos = y_pos;
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
	
	public boolean collidesWith(GameObject other) {
		return (this.x_pos == other.x_pos &&
				this.y_pos == other.y_pos);
	}
	
	public boolean isNear(GameObject other) {
		return ((this.x_pos == other.x_pos && this.y_pos == other.y_pos + 1) ||
				(this.x_pos == other.x_pos && this.y_pos == other.y_pos - 1) ||
				(this.x_pos == other.x_pos + 1 && this.y_pos == other.y_pos) ||
				(this.x_pos == other.x_pos - 1 && this.y_pos == other.y_pos));
	}
	
	public abstract char getIdSymbol();
	
	public BufferedImage getSprite() {
		return sprite;
	}
	
}
