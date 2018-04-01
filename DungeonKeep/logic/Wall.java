package DungeonKeep.logic;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wall extends GameObject {
	
	private final char idSymbol = 'X';

	public Wall(int x_pos, int y_pos) {
		super(x_pos, y_pos);
		
		try {
			sprite = ImageIO.read(new File("./bin/Images/wall.png"));
		} 
		catch (IOException e) {
			System.out.println("\nWall sprite not found.");
			System.exit(1);
		}
	}
	
	@Override
	public char getIdSymbol() {
		return idSymbol;
	}

}
