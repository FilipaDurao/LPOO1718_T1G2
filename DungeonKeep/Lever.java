
public class Lever extends GameObject {
	
	private final char idSymbol = 'k';

	public Lever(byte x_pos, byte y_pos) {
		super(x_pos, y_pos);
	}
	
	public char getIdSymbol() {
		return idSymbol;
	}

}
