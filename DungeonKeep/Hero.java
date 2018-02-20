
public class Hero extends GameObject {
	
	private final char idSymbol = 'H';

	public Hero(byte x_pos, byte y_pos) {
		super(x_pos, y_pos);
	}
	
	public char getIdSymbol() {
		return idSymbol;
	}

}
