package DungeonKeep.logic;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Door extends GameObject {
	
	private final char closedSymbol = 'I';
	private final char openedSymbol = 'S';
	private boolean isClosed = true;
	private boolean isExit;

	public Door(int x_pos, int y_pos, boolean isExit) {
		super(x_pos, y_pos);
		this.isExit = isExit;
		
		try {
			sprite = ImageIO.read(new File("./bin/Images/door.png"));
		} 
		catch (IOException e) {
            e.printStackTrace();
		}
	}
	
	public void close() {
		this.isClosed = true;
	}
	
	public void open() {
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
