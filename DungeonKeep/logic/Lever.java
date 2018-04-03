package DungeonKeep.logic;

public class Lever extends GameObject {
	
	private static final long serialVersionUID = 1L;	// To allow file writing
	private final char idSymbol = 'k';		/**< The symbol that represents a Lever on the console version of the game */

	/**
	 * Creates a Lever
	 * 
	 * @param x_pos the x position where the Lever is created
	 * @param y_pos the y position where the Lever is created
	 */
	public Lever(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}
	
	/**
	 * @return The ID symbol that represents a Lever
	 */
	public char getIdSymbol() {
		return idSymbol;
	}

}