package DungeonKeep.logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class RookieGuard extends Guard {
	

	public RookieGuard(int x_pos, int y_pos, ArrayList<MoveDirection> path) {
		super(x_pos, y_pos, path);
		
		try {
			sprite = ImageIO.read(new File("./bin/Images/rookieGuard.png"));
		} 
		catch (IOException e) {
            e.printStackTrace();
		}
	}
	
	@Override
	public void performStep() {
		if(!path.isEmpty()) {
			move(path.get(pathStep));
			pathStep = (pathStep + 1) % path.size();
		}
	}
	
}
