package DungeonKeep.logic;

public class Wall extends GameObject {
	
	private static final long serialVersionUID = 1L;	// To allow file writing
	private final char idSymbol = 'X';

	public Wall(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}
	
	@Override
	public char getIdSymbol() {
		return idSymbol;
	}

}
