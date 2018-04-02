package DungeonKeep.logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class DrunkenGuard extends Guard {

	private static final long serialVersionUID = 1L;	// To allow file writing
	protected final char asleepSymbol = 'g';
	private final double reversePathProbability = 0.5;	// 50%
	private final double wakeUpProbability = 0.2;		// 20%
	private final double fallAsleepProbability = 0.15;	// 15%
	private boolean isMovingForward = true;
	private boolean isAsleep = false;
	
	public DrunkenGuard(int x_pos, int y_pos, ArrayList<MoveDirection> path) {
		super(x_pos, y_pos, path);
	}
	
	public boolean isMovingForward() {
		return isMovingForward;
	}
	
	public void changeDirection() {
		isMovingForward = !isMovingForward;
	}
	
	public boolean isAsleep() {
		return isAsleep;
	}
	
	public boolean isAwake() {
		return !isAsleep;
	}
	
	public void fallAsleep() {
		
		isAsleep = true;
	}
	
	public void wakeUp() {
		isAsleep = false;
		
		// Guard has a 50% chance to change direction after waking up
		if(Math.random() < reversePathProbability) {
			changeDirection();
		}
	}

	@Override
	public char getIdSymbol() {
		if(this.isAsleep) {
			return asleepSymbol;
		}
		else {
			return idSymbol;
		}
	}
	
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
