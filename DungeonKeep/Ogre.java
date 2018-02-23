import java.util.HashSet;

public class Ogre extends GameObject {
	
	private final char idSymbol = 'O';

	public Ogre(int x_pos, int y_pos) {
		super(x_pos, y_pos);
	}
	
	
	@Override
	public char getIdSymbol() {
		return idSymbol;
	}
	
	
	public void move(HashSet<Wall> walls , HashSet<Door> doors) {
		int new_x_pos , new_y_pos;
		MoveDirection dir;

		// Get a random valid move (can't go through doors or walls) for the Ogre
		do {
			dir = getRandomMoveDirection();
			
			// Perform "Hypothetical" move
			if(dir == MoveDirection.UP) {
				new_x_pos = this.getX_pos();
				new_y_pos = this.getY_pos() - 1;
			}
			else if(dir == MoveDirection.DOWN) {
				new_x_pos = this.getX_pos();
				new_y_pos = this.getY_pos() + 1;
			}
			else if(dir == MoveDirection.RIGHT) {
				new_x_pos = this.getX_pos() + 1;
				new_y_pos = this.getY_pos();
			}
			else {
				new_x_pos = this.getX_pos() - 1;
				new_y_pos = this.getY_pos();
			}
					
		} while(ogreCollidesWithWalls(new_x_pos , new_y_pos , walls) ||
				ogreCollidesWithDoors(new_x_pos , new_y_pos , doors));
		
		// Perform the step in the given direction
		step(dir);
	}
	
	
	private boolean ogreCollidesWithWalls(int new_x_pos , int new_y_pos , HashSet<Wall> walls) {
		for(Wall w : walls) {
			// Check if ogre collides with any of the walls
			if (new_x_pos == w.getX_pos() &&
				new_y_pos == w.getY_pos()) {
				
				return true;
			}
		}
		
		// No collision was found
		return false;
	}
	
	
	private boolean ogreCollidesWithDoors(int new_x_pos , int new_y_pos , HashSet<Door> doors) {
		for(Door d : doors) {
			// Check if ogre collides with any of the closed doors
			if (new_x_pos == d.getX_pos() &&
				new_y_pos == d.getY_pos()) {
				
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
	
	
	private static MoveDirection getRandomMoveDirection() {
		int randomDir = (int) Math.ceil(Math.random()*4);
		
		if (randomDir == 1) {
			return MoveDirection.UP;
		}
		else if (randomDir == 2) {
			return MoveDirection.DOWN;
		}
		else if (randomDir == 3) {
			return MoveDirection.RIGHT;
		}
		else {
			return MoveDirection.LEFT;
		}
	}

}