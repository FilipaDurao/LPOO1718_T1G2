package DungeonKeep.logic;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Lever extends GameObject {
	
	private static final long serialVersionUID = 1L;	// To allow file writing
	private final char idSymbol = 'k';

	public Lever(int x_pos, int y_pos) {
		super(x_pos, y_pos);
		
		try {
			sprite = ImageIO.read(new File("./bin/Images/lever.png"));
		} 
		catch (IOException e) {
			System.out.println("\nLever sprite not found.");
			System.exit(1);
		}
	}
	
	public char getIdSymbol() {
		return idSymbol;
	}

}
