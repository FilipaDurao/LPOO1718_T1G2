import java.util.Arrays;
import java.util.HashSet;

public class Level {
	
	public enum LevelStatus { RUNNING , DEFEAT , VICTORY }

	private Hero hero;
	private HashSet<Wall> walls;
	private HashSet<Door> doors;
	private Guard guard;
	private Lever lever;
	private Ogre ogre;
	private Key key;
	private int width;
	private int heigth;
	
	
	public Level(
			Hero hero,
			HashSet<Wall> walls,
			HashSet<Door> doors,
			Guard guard,
			Lever lever,
			Ogre ogre,
			Key key,
			int width, 
			int heigth) {
		
		super();
		this.hero = hero;
		this.walls = walls;
		this.doors = doors;
		this.guard = guard;
		this.lever = lever;
		this.ogre = ogre;
		this.key = key;
		this.width = width;
		this.heigth = heigth;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeigth() {
		return heigth;
	}

	public void setHeigth(int heigth) {
		this.heigth = heigth;
	}

	public void draw() {
		char[][] symbols = new char[heigth][width];
		
		// Initialize matrix with spaces
		for(char[] row : symbols) {
			Arrays.fill(row, ' ');
		}
		
		// Fill the matrix with the game objects
		if(guard != null) {
			symbols[guard.getY_pos()][guard.getX_pos()] = guard.getIdSymbol();
		}
		
		if(lever != null) {
			symbols[lever.getY_pos()][lever.getX_pos()] = lever.getIdSymbol();
		}
		
		if(ogre != null) {
			symbols[ogre.getY_pos()][ogre.getX_pos()] = ogre.getIdSymbol();
		}
		
		if(key != null) {
			symbols[key.getY_pos()][key.getX_pos()] = key.getIdSymbol();
		}
		
		for(Wall w : walls) {
			symbols[w.getY_pos()][w.getX_pos()] = w.getIdSymbol();
		}
		
		for(Door d : doors) {
			symbols[d.getY_pos()][d.getX_pos()] = d.getIdSymbol();
		}
		
		symbols[hero.getY_pos()][hero.getX_pos()] = hero.getIdSymbol();
		
		//Print the matrix to the screen
		for(char[] row : symbols) {
			for(char c : row) {
				System.out.print(c);
			}
			System.out.println();
		}
		
	}
	
	
	public LevelStatus update(char keyPressed) {
		
		// Move the Hero
		hero.move(keyPressed, walls, doors);
		
		// Move the Guard (if existant)
		if (guard != null) {
			guard.performStep();
		}
		
		// Move the Ogre (if existant)
		if (ogre != null) {
			ogre.move(walls , doors);
		}
		
		// Check for collision with lever (if existant)
		if(lever != null && heroCollidesWithLever()) {
			openExitDoors();
		}
		
		// Check for proximity with guard (if existant)
		if(guard != null && heroIsNearGuard()) {
			return LevelStatus.DEFEAT;
		}
		
		// Check with collision with open door
		if(heroCollidesWithOpenDoor()) {
			return LevelStatus.VICTORY;
		}
		
		return LevelStatus.RUNNING;
	}
	
	
	private boolean heroCollidesWithOpenDoor() {
		for(Door d : doors) {	// Iterate through all the doors
			if(d.getX_pos() == hero.getX_pos() &&
			   d.getY_pos() == hero.getY_pos() && 
			   d.isOpen()) {
				return true;	// Colliding with open door!
			}
		}
		
		// No collision was found
		return false;	
	}
	
	
	private boolean heroIsNearGuard() {
		// Verify if the Hero is horizontally/vertically next to the Guard
		return ((hero.getX_pos()==guard.getX_pos()+1 && hero.getY_pos()==guard.getY_pos()) ||
				(hero.getX_pos()==guard.getX_pos()-1 && hero.getY_pos()==guard.getY_pos()) ||
				(hero.getX_pos()==guard.getX_pos() && hero.getY_pos()==guard.getY_pos()+1) ||
				(hero.getX_pos()==guard.getX_pos() && hero.getY_pos()==guard.getY_pos()-1));
	}
	
	
	private boolean heroCollidesWithLever() {
		// Verify if the Hero is colliding with the Lever
		return (hero.getX_pos() == lever.getX_pos() && 
				hero.getY_pos() == lever.getY_pos());
	}
	
	
	private void openExitDoors() {
		// Iterate though all the doors and open the ones that are Exit Doors
		for (Door d : doors) {
			if(d.isExit()) {
				d.open();
			}
		}
	}
	 
}
