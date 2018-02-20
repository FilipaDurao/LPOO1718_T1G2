
public class Guard extends GameObject {
	
	private final char idSymbol = 'G';

	public Guard(byte x_pos, byte y_pos) {
		super(x_pos, y_pos);
	}
	
	public char getIdSymbol() {
		return idSymbol;
	}

}
