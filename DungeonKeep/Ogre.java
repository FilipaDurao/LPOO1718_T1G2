public class Ogre extends GameObject {
	
	private final char idSymbol = 'O';

	public Ogre(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}
	
	@Override
	public char getIdSymbol() {
		return idSymbol;
	}

}