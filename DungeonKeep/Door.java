
public class Door extends GameObject {
	
	private final char closedSymbol = 'I';
	private final char openedSymbol = 'S';
	private boolean isClosed = true;
	private boolean isExit;

	public Door(int x_pos, int y_pos, boolean isExit) {
		super(x_pos, y_pos);
		this.isExit = isExit;
	}
	
	void close() {
		this.isClosed = true;
	}
	
	void open() {
		this.isClosed = false;
	}
	
	public boolean isExit() {
		return isExit;
	}
	
	public boolean isOpen() {
		return !isClosed;
	}
	
	public boolean isClosed() {
		return isClosed;
	}

	@Override
	public char getIdSymbol() {
		if (isClosed) {
			return closedSymbol;
		}
		else {
			return openedSymbol;
		}
	}
	
}
