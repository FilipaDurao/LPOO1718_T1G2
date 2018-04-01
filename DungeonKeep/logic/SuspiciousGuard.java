package DungeonKeep.logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SuspiciousGuard extends Guard {
	
	private static final long serialVersionUID = 1L;	// To allow file writing
	private final double reversePathProbability = 0.3;	// 30% 
	private boolean isMovingForward = true;

	
	public SuspiciousGuard(int x_pos, int y_pos, ArrayList<MoveDirection> path) {
		super(x_pos, y_pos, path);
		
		try {
			sprite = ImageIO.read(new File("./bin/Images/suspiciousGuard.png"));
		} 
		catch (IOException e) {
			System.out.println("\nSuspicious Guard sprite not found.");
			System.exit(1);
		}
	}
	
	
	public boolean isMovingForward() {
		return isMovingForward;
	}
	
	
	@Override
	public void performStep() {
		// Guard has a 30% chance to change direction
		if(Math.random() < reversePathProbability) {
			changeDirection();
		}
		
		// Regular Path Step
		if(isMovingForward) {
			move(path.get(pathStep));
			pathStep = (pathStep + 1) % path.size();
		}
		// Reversed Path Step
		else {
			if (pathStep == 0) {
				pathStep = path.size() - 1;
			}
			else {
				pathStep--;
			}
			
			move(getOppositeMoveDirection(path.get(pathStep)));
		}
	}
	
	
	public void changeDirection() {
		isMovingForward = !isMovingForward;
	}


}
