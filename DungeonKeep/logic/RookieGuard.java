package DungeonKeep.logic;

import java.util.ArrayList;

public class RookieGuard extends Guard {
	
	private static final long serialVersionUID = 1L;	// To allow file writing

	/**
	 * Creates a Rookie Guard
	 * 
	 * @param x_pos the x position where the Rookie Guard is created
	 * @param y_pos the y position where the Rookie Guard is created
	 * @param path the path the Rookie Guard is going to follow
	 */
	public RookieGuard(int x_pos, int y_pos, ArrayList<MoveDirection> path) {
		super(x_pos, y_pos, path);
	}
	
	/**
	 * Performs a step on the path the Rookie Guard is following
	 */
	@Override
	public void performStep() {
		if(!path.isEmpty()) {
			move(path.get(pathStep));
			pathStep = (pathStep + 1) % path.size();
		}
	}
	
}
