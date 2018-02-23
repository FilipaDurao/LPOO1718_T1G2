import java.util.HashSet;

public class Hero extends GameObject {
	
	private final char idSymbol = 'H';

	public Hero(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}
	
	
	@Override
	public char getIdSymbol() {
		return idSymbol;
	}
	
	
	public void move(char keyPressed , HashSet<Wall> walls , HashSet<Door> doors) {
		int new_x_pos = this.getX_pos();
		int new_y_pos = this.getY_pos();
		MoveDirection dir;
		
		if (keyPressed=='w' || keyPressed=='W') {		// Walk up
			new_y_pos--;
			dir = MoveDirection.UP;
		}
		else if (keyPressed=='s' || keyPressed=='S') {	// Walk down
			new_y_pos++;
			dir = MoveDirection.DOWN;
		}
		else if (keyPressed=='d' || keyPressed=='D') {	// Walk right
			new_x_pos++;
			dir = MoveDirection.RIGHT;
		}
		else if (keyPressed=='a' || keyPressed=='A') {	// Walk left
			new_x_pos--;
			dir = MoveDirection.LEFT;
		}
		else {
			return;
		}
		
		// Move, if the hero isn't colliding with any wall or closed door
		if (!heroCollidesWithWalls(new_x_pos , new_y_pos , walls) &&
			!heroCollidesWithClosedDoors(new_x_pos , new_y_pos , doors)) {
			
			step(dir);
		}
	}
	
	
	private boolean heroCollidesWithWalls(int new_x_pos , int new_y_pos , HashSet<Wall> walls) {
		for(Wall w : walls) {
			// Check if hero collides with any of the walls
			if (new_x_pos == w.getX_pos() &&
				new_y_pos == w.getY_pos()) {
				
				return true;
			}
		}
		
		// No collision was found
		return false;
	}
	
	
	private boolean heroCollidesWithClosedDoors(int new_x_pos , int new_y_pos , HashSet<Door> doors) {
		for(Door d : doors) {
			// Check if hero collides with any of the closed doors
			if (new_x_pos == d.getX_pos() &&
				new_y_pos == d.getY_pos() &&
				d.isClosed()) {
				
				return true;
			}
		}
		
		// No collision was found
		return false;
	}
	
	
	private void step (MoveDirection dir) {
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
