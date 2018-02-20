
public class Lever extends GameObject {
	
	private final char idSymbol = 'k';

	public Lever(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}
	
	public char getIdSymbol() {
		return idSymbol;
	}

}
