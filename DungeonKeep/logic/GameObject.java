package DungeonKeep.logic;

import java.io.Serializable;

public abstract class GameObject implements Serializable {

	private static final long serialVersionUID = 1L;	// To allow file writing
	private int x_pos;		/**< The x position of the object */
	private int y_pos;		/**< The y position of the object */
	
	/**
	 * Creates a Game Object
	 * 
	 * @param x_pos the x position of the object
	 * @param y_pos the y position of the object
	 */
	public GameObject(int x_pos, int y_pos) {
		super();
		this.x_pos = x_pos;
		this.y_pos = y_pos;
	}

	/**
	 * @return The x position of the object
	 */
	public int getX_pos() {
		return x_pos;
	}

	/**
	 * Sets the attribute x_pos to the value passed by parameter
	 * 
	 * @param x_pos the new x position of the object
	 */
	public void setX_pos(int x_pos) {
		this.x_pos = x_pos;
	}

	/**
	 * @return The y position of the object
	 */
	public int getY_pos() {
		return y_pos;
	}

	/**
	 * Sets the attribute y_pos to the value passed by parameter
	 * 
	 * @param y_pos the new y position of the object
	 */
	public void setY_pos(int y_pos) {
		this.y_pos = y_pos;
	}
	
	/**
	 * Verifies the collision between this Game Object and other passed by parameter
	 * 
	 * @param other the other game object to verify the collision with
	 * 
	 * @return True if there is a collision between the objects, False otherwise
	 */
	public boolean collidesWith(GameObject other) {
		return (this.x_pos == other.x_pos &&
				this.y_pos == other.y_pos);
	}
	
	/**
	 * Verifies the proximity between this Game Object and other passed by parameter, that is, if they are adjacent
	 * 
	 * @param other the other game object to verify the proximity with
	 * 
	 * @return True if the objects are adjacent, False otherwise
	 */
	public boolean isNear(GameObject other) {
		return ((this.x_pos == other.x_pos && this.y_pos == other.y_pos + 1) ||
				(this.x_pos == other.x_pos && this.y_pos == other.y_pos - 1) ||
				(this.x_pos == other.x_pos + 1 && this.y_pos == other.y_pos) ||
				(this.x_pos == other.x_pos - 1 && this.y_pos == other.y_pos));
	}
	
	/**
	 * @return The ID symbol that represents this Game Object on the console version of the Game
	 */
	public abstract char getIdSymbol();
	
}
