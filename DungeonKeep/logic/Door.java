package DungeonKeep.logic;

public class Door extends GameObject {

	private static final long serialVersionUID = 1L;	// To allow file writing
	private final char closedSymbol = 'I';		/**< The symbol that represents a closed Door on the console version of the game */
	private final char openedSymbol = 'S';		/**< The symbol that represents an open Door on the console version of the game */
	private boolean isClosed = true;			/**< Variable to determine whether the Door is open or closed */
	private boolean isExit;						/**< Variable to determine whether the door is an exit Door or just a useless Door */

	/**
	 * Creates a Door
	 * 
	 * @param x_pos - the x position where the Door is created
	 * @param y_pos - the y position where the Door is created
	 * @param isExit - whether the Door is an exit Door or a useless Door
	 */
	public Door(int x_pos, int y_pos, boolean isExit) {
		super(x_pos, y_pos);
		this.isExit = isExit;
	}
	
	/**
	 * "Close" a Door (by setting to True the Class attribute isClosed)
	 */
	public void close() {
		this.isClosed = true;
	}
	
	/**
	 * "Open" a Door (by setting to False the Class attribute isClosed)
	 */
	public void open() {
		this.isClosed = false;
	}
	
	/**
	 * Verifies if the Door is an exit Door, that is, if it is openable by a lever
	 * 
	 * @return True if the Door is an exit Door, False otherwise
	 */
	public boolean isExit() {
		return isExit;
	}
	
	/**
	 * Verifies if the Door is open
	 * 
	 * @return True if the Door is open, False otherwise
	 */
	public boolean isOpen() {
		return !isClosed;
	}
	
	/**
	 * Verifies if the Door is closed
	 * 
	 * @return True if the Door is closed, False otherwise
	 */
	public boolean isClosed() {
		return isClosed;
	}

	/**
	 * @return The ID symbol that represents a Door, either closed or open
	 */
	@Override
	public char getIdSymbol() {
		if (isClosed) {
			return closedSymbol;
		}
		else {
			return openedSymbol;
		}
	}
	
}
