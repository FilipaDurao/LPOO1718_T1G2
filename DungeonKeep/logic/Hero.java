package DungeonKeep.logic;
import java.util.HashSet;

public class Hero extends GameObject {
	
	private final char standartHeroSymbol = 'H';
	private final char heroWithKeySymbol = 'K';
	private final char heroWithClubSymbol = 'A';
	private boolean hasKey = false;
	private boolean hasClub;

	public Hero(int x_pos, int y_pos, boolean hasClub) {
		super(x_pos, y_pos);
		this.hasClub = hasClub;
	}
	
	@Override
	public char getIdSymbol() {
		if(this.hasKey) {
			return heroWithKeySymbol;
		}
		else if(this.hasClub) {
			return heroWithClubSymbol;
		}
		else {
			return standartHeroSymbol;
		}
	}
	
	public void catchKey() {
		this.hasKey = true;
	}
	
	
	public boolean hasKey() {
		return hasKey;	
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
			!heroCollidesWithDoors(new_x_pos , new_y_pos , doors)) {
			
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
	
	
	private boolean heroCollidesWithDoors(int new_x_pos , int new_y_pos , HashSet<Door> doors) {
		for(Door d : doors) {
			// Check if hero collides with any of the closed doors
			if (new_x_pos == d.getX_pos() &&
				new_y_pos == d.getY_pos() &&
				d.isClosed()) {
				
				// If hero has the key, open the door!
				if (this.hasKey()) {
					d.open();
				}
				
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
