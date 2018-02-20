
public class Ogre extends GameObject {
	
	private final char idSymbol = 'O';

	public Ogre(byte x_pos, byte y_pos) {
		super(x_pos, y_pos);
	}
	
	public char getIdSymbol() {
		return idSymbol;
	}

}
