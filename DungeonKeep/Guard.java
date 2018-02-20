
public class Guard extends GameObject {
	
	private final char idSymbol = 'G';

	public Guard(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}
	
	@Override
	public char getIdSymbol() {
		return idSymbol;
	}

}
