package DungeonKeep.logic;

import java.util.ArrayList;

public class RookieGuard extends Guard {
	
	private static final long serialVersionUID = 1L;	// To allow file writing

	public RookieGuard(int x_pos, int y_pos, ArrayList<MoveDirection> path) {
		super(x_pos, y_pos, path);
	}
	
	@Override
	public void performStep() {
		if(!path.isEmpty()) {
			move(path.get(pathStep));
			pathStep = (pathStep + 1) % path.size();
		}
	}
	
}
