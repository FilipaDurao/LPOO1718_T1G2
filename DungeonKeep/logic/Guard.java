package DungeonKeep.logic;
import java.util.ArrayList;

public abstract class Guard extends MovableGameObject {
	
	protected final char idSymbol = 'G';
	protected ArrayList<MoveDirection> path;
	protected int pathStep = 0;

	public Guard(int x_pos, int y_pos , ArrayList<MoveDirection> path) {
		super(x_pos, y_pos);
		this.path = path;
	}
	
	@Override
	public char getIdSymbol() {
		return idSymbol;
	}
	
	public abstract void performStep();

}
