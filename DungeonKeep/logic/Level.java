package DungeonKeep.logic;
import java.util.ArrayList;

public abstract class Level implements IStatus {

	protected int ID;
	protected Hero hero;
	protected ArrayList<Wall> walls;
	protected ArrayList<Door> doors;
	protected int width;
	protected int heigth;
	protected Status status = Status.RUNNING;
	
	public Level(
			int ID,
			Hero hero,
			ArrayList<Wall> walls,
			ArrayList<Door> doors,
			int width, 
			int heigth) {
		
		super();
		this.ID = ID;
		this.hero = hero;
		this.walls = walls;
		this.doors = doors;
		this.width = width;
		this.heigth = heigth;
	}
	
	public int getID() {
		return ID;
	}
	
	public Status getStatus() {
		return status;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return heigth;
	}

	public void setHeight(int height) {
		this.heigth = height;
	}

	public Hero getHero() {
		return hero;
	}

	public ArrayList<Wall> getWalls() {
		return walls;
	}

	public ArrayList<Door> getDoors() {
		return doors;
	}

	public abstract String getGameMatrix();	
	
	public abstract void update(Hero.MoveDirection heroDirection);
	
	protected boolean heroCollidesWithOpenDoor() {
		for(Door d : doors) {	// Iterate through all the doors
			if(hero.collidesWith(d) && d.isOpen()) {
				return true;	// Colliding with open door!
			}
		}
		
		// No collision was found
		return false;	
	}
	
	public boolean doesPositionHaveBarrier(int x, int y) {
		
		// Check if the position has walls
		for(Wall w : walls) {
			if(w.getX_pos() == x && w.getY_pos() == y) {
				return true;
			}
		}
		
		// Check if the position has doors
		for(Door d : doors) {
			if(d.getX_pos() == x && d.getY_pos() == y) {
				return true;
			}
		}
		
		// If the position doesn't have walls or doors then it's free to move to
		return false;
	}
	 
}
