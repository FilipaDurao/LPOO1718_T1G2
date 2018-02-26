package DungeonKeep.logic;
import java.util.ArrayList;

public class Guard extends GameObject {
	
	private final char idSymbol = 'G';
	private ArrayList<MoveDirection> path;
	private int pathStep = 0;

	public Guard(int x_pos, int y_pos , ArrayList<MoveDirection> path) {
		super(x_pos, y_pos);
		this.path = path;
	}
	
	@Override
	public char getIdSymbol() {
		return idSymbol;
	}
	
	public void performStep() {
		move(path.get(pathStep));
		pathStep = (pathStep + 1) % path.size();
	}
	
	public void move (MoveDirection dir) {
		switch (dir) {
		case UP:
			this.setY_pos(this.getY_pos() - 1);
			break;
		case DOWN:
			this.setY_pos(this.getY_pos() + 1);
			break;
		case RIGHT:
			this.setX_pos(this.getX_pos() + 1);
			break;
		case LEFT:
			this.setX_pos(this.getX_pos() - 1);
			break;
		}		
	}

}
