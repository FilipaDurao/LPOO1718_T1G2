
public class Door extends GameObject {
	
	private final char closedSymbol = 'I';
	private final char openedSymbol = 'S';
	private boolean isClosed = true;

	public Door(byte x_pos, byte y_pos) {
		super(x_pos, y_pos);
	}
	
	void close() {
		this.isClosed = true;
	}
	
	void open() {
		this.isClosed = false;
	}
	
	public char getIdSymbol() {
		if (isClosed) {
			return closedSymbol;
		}
		else {
			return openedSymbol;
		}
	}
	
}
