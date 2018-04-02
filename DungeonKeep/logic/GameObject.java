package DungeonKeep.logic;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class GameObject implements Serializable {

	private static final long serialVersionUID = 1L;	// To allow file writing
	private int x_pos;
	private int y_pos;
	
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
	
}
