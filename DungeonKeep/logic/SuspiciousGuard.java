package DungeonKeep.logic;

import java.util.ArrayList;

public class SuspiciousGuard extends Guard {
	
	private final double reversePathProbability = 0.3;	// 30% 
	private boolean isMovingForward = true;

	
	public SuspiciousGuard(int x_pos, int y_pos, ArrayList<MoveDirection> path) {
		super(x_pos, y_pos, path);
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
	
	
	private void changeDirection() {
		if(isMovingForward) {
			isMovingForward = false;
		}
		else {
			isMovingForward = true;
		}
	}


}