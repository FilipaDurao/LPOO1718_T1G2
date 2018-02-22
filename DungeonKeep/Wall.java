
public class Wall extends GameObject {
	
	private final char idSymbol = 'X';

	public Wall(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}
	
	@Override
	public char getIdSymbol() {
		return idSymbol;
	}

}
