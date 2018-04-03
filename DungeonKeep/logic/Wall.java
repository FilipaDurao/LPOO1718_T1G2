package DungeonKeep.logic;

public class Wall extends GameObject {
	
	private static final long serialVersionUID = 1L;	// To allow file writing
	private final char idSymbol = 'X';		/**< The symbol that represents a Wall on the console version of the game */

	/**
	 * Creates a Wall
	 * 
	 * @param x_pos the x position where the Wall is created
	 * @param y_pos the y position where the Wall is created
	 */
	public Wall(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}
	
	/**
	 * @return The ID symbol that represents a Wall
	 */
	@Override
	public char getIdSymbol() {
		return idSymbol;
	}

}
