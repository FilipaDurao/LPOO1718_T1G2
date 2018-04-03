package DungeonKeep.logic;

import java.util.ArrayList;

public class SuspiciousGuard extends Guard {
	
	private static final long serialVersionUID = 1L;	// To allow file writing
	private final double reversePathProbability = 0.3;	/**< The probability of the guard reversing its path (30%) */
	private boolean isMovingForward = true;				/**< Variable to determine in which direction on the path is the guard going*/

	/**
	 * Creates a Suspicious Guard
	 * 
	 * @param x_pos the x position where the Suspicious Guard is created
	 * @param y_pos the y position where the Suspicious Guard is created
	 * @param path the path the Suspicious Guard is going to follow
	 */
	public SuspiciousGuard(int x_pos, int y_pos, ArrayList<MoveDirection> path) {
		super(x_pos, y_pos, path);
	}
	
	/**
	 * Verifies if the Guard is moving forward or not
	 * 
	 * @return True if the Guard is moving forward, False otherwise
	 */
	public boolean isMovingForward() {
		return isMovingForward;
	}
	
	/**
	 * Verifies the direction of the movement of the Guard and performs a step.
	 * Has a 30% chance to reverse the move direction
	 */
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
	
	/**
	 * Reverses the Guard's direction
	 */
	public void changeDirection() {
		isMovingForward = !isMovingForward;
	}


}
