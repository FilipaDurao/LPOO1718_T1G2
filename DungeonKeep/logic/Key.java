package DungeonKeep.logic;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Key extends GameObject {
	
	private static final long serialVersionUID = 1L;	// To allow file writing
	private final char idSymbol = 'k';
	

	public Key(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}
	
	@Override
	public char getIdSymbol() {
		return idSymbol;
	}

}
