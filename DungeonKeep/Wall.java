
public class Wall extends GameObject {
	
	private final char idSymbol = 'X';

	public Wall(byte x_pos, byte y_pos) {
		super(x_pos, y_pos);
	}
	
	public char getIdSymbol() {
		return idSymbol;
	}

}
