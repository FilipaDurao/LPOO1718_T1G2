package DungeonKeep.logic;

public class Club extends GameObject {

	private static final long serialVersionUID = 1L;	// To allow file writing	//TODO document all these
	private final char idSymbol = '*';	/**< The symbol that represents a Club on the console version of the game */
	
	/**
	 * Creates a Club
	 * 
	 * @param x_pos - the x position where the Club is created
	 * @param y_pos - the y position where the Club is created
	 */
	public Club(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}

	/**
	 * @return The ID symbol that represents a Club
	 */
	@Override
	public char getIdSymbol() {
		return idSymbol;
	}

}
