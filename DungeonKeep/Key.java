
public class Key extends GameObject {
	
	private final char idSymbol = 'k';

	public Key(byte x_pos, byte y_pos) {
		super(x_pos, y_pos);
	}
	
	public char getIdSymbol() {
		return idSymbol;
	}

}
