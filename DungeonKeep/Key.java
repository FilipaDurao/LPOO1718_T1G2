
public class Key extends GameObject {
	
	private final char idSymbol = 'k';

	public Key(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}
	
	@Override
	public char getIdSymbol() {
		return idSymbol;
	}

}
