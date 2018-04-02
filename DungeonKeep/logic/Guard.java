package DungeonKeep.logic;
import java.util.ArrayList;

public abstract class Guard extends MovableGameObject {

	private static final long serialVersionUID = 1L;	// To allow file writing
	protected final char idSymbol = 'G';		/**< The symbol that represents a Guard on the console version of the game */
	protected ArrayList<MoveDirection> path;	/**< The path followed by the Guard */
	protected int pathStep = 0;					/**< The step of the path the Guard is at */

	/**
	 * Creates a Guard
	 * 
	 * @param x_pos - the x position where the Guard is created
	 * @param y_pos - the y position where the Guard is created
	 * @param path - the path the Guard will follow
	 */
	public Guard(int x_pos, int y_pos , ArrayList<MoveDirection> path) {
		super(x_pos, y_pos);
		this.path = path;
	}
	
	/**
	 * @return The ID symbol that represents this Game Object on the console version of the Game
	 */
	@Override
	public char getIdSymbol() {
		return idSymbol;
	}

	/**
	 * Performs a step on the Guard's path if it is possible given its specifications a the moment
	 */
	public abstract void performStep();

}
