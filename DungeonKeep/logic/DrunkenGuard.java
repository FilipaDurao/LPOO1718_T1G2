package DungeonKeep.logic;

import java.util.ArrayList;

public class DrunkenGuard extends Guard {

	private static final long serialVersionUID = 1L;	// To allow file writing
	protected final char asleepSymbol = 'g';			/**< The symbol that represents a sleeping Drunken Guard on the console version of the game */
	private final double reversePathProbability = 0.5;	/**< The probability of the guard reversing its path direction after waking up (50%) */
	private final double wakeUpProbability = 0.2;		/**< The probability of the guard waking up when he/she is asleep (20%) */
	private final double fallAsleepProbability = 0.15;	/**< The probability of the guard falling asleep (15%) */
	private boolean isMovingForward = true;				/**< Variable to determine in which direction on the path is the guard going*/
	private boolean isAsleep = false;					/**< Variable to determine whether the guard is asleep or not */
	
	/**
	 * Creates a DrunkenGuard
	 * 
	 * @param x_pos - the x position where the DrunkenGuard is created
	 * @param y_pos - the y position where the DrunkenGuard is created
	 * @param path - the sequence of moves that the guard will do
	 */
	public DrunkenGuard(int x_pos, int y_pos, ArrayList<MoveDirection> path) {
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
	 * Reverses the Guard's direction
	 */
	public void changeDirection() {
		isMovingForward = !isMovingForward;
	}
	
	/**
	 * Verifies if the Guard is asleep
	 * 
	 * @return True if it's asleep, False otherwise
	 */
	public boolean isAsleep() {
		return isAsleep;
	}
	
	/**
	 * Verifies if the Guard is awake
	 * 
	 * @return True if it's awake, False otherwise
	 */
	public boolean isAwake() {
		return !isAsleep;
	}
	
	/**
	 * Changes the Guard's status to asleep
	 */
	public void fallAsleep() {
		isAsleep = true;
	}
	
	/**
	 * Has a 50% chance of changing the Guard's status to awake
	 */
	public void wakeUp() {
		isAsleep = false;
		
		// Guard has a 50% chance to change direction after waking up
		if(Math.random() < reversePathProbability) {
			changeDirection();
		}
	}

	/**
	 * @return The ID symbol that represents a DrunkenGuard, either asleep or awake
	 */
	@Override
	public char getIdSymbol() {
		if(this.isAsleep) {
			return asleepSymbol;
		}
		else {
			return idSymbol;
		}
	}
	
	/**
	 * Checks the Guard's status (asleep or awake) and the direction on the path he is taking and performs a step
	 * if possible
	 */
	@Override
	public void performStep() {
		// If the guard is asleep, there's a chance he/she wakes up
		if(this.isAsleep() && Math.random() < wakeUpProbability) {
			this.wakeUp();
		}
		// If the guard is awake, there's a chance he/she falls asleep
		else if(this.isAwake() && Math.random() < fallAsleepProbability) {
			this.fallAsleep();
		}
		
		// If the guard is asleep, don't perform a step
		if(this.isAsleep) {
			return;
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
}
