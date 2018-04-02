package DungeonKeep.logic;

public class Key extends GameObject {
	
	private static final long serialVersionUID = 1L;	// To allow file writing
	private final char idSymbol = 'k';		/**< The symbol that represents a Key on the console version of the game */
	
	/**
	 * Creates a Key
	 * 
	 * @param x_pos - the x position where the Key is created
	 * @param y_pos - the y position where the Key is created
	 */
	public Key(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}
	
	/**
	 * @return The ID symbol that represents a Key
	 */
	@Override
	public char getIdSymbol() {
		return idSymbol;
	}

}
