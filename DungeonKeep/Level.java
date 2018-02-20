import java.util.Arrays;
import java.util.HashSet;

public class Level {

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
		
		symbols[hero.getY_pos()][hero.getX_pos()] = hero.getIdSymbol();
		
		for(Wall w : walls) {
			symbols[w.getY_pos()][w.getX_pos()] = w.getIdSymbol();
		}
		
		for(Door d : doors) {
			symbols[d.getY_pos()][d.getX_pos()] = d.getIdSymbol();
		}
		
		//Print the matrix to the screen
		for(char[] row : symbols) {
			for(char c : row) {
				System.out.print(c);
			}
			System.out.println();
		}
		
	}
	
	public boolean update(char keyPressed) {
		Hero.MoveDirection dir;
		int heroX = hero.getX_pos();
		int heroY = hero.getY_pos();
		
		// Analyse the direction
		switch(keyPressed) {
		case 'w': 
		case 'W':
			dir = Hero.MoveDirection.UP;
			heroY--;
			break;
		case 's': 
		case 'S':
			dir = Hero.MoveDirection.DOWN;
			heroY++;
			break;
		case 'a': 
		case 'A':
			dir = Hero.MoveDirection.LEFT;
			heroX--;
			break;
		case 'd': 
		case 'D':
			dir = Hero.MoveDirection.RIGHT;
			heroX++;
			break;
		default:
			return true;
		}
		
		// Check if the hero collides with Walls / Closed Doors after moving
		if (!(posColidesWithWalls(heroX, heroY) || posColidesWithClosedDoors(heroX, heroY))) {
			hero.move(dir);
		}
		
		// Check for collision with lever
		if(heroCollidesWithLever()) {
			openExitDoors();
		}
		
		// Check for proximity with guard
		if(heroIsNearGuard()) {
			System.out.print("You lost!");
			return false;
		}
		
		// Check with collision with open door
		if(heroCollidesWithOpenDoor()) {
			System.out.print("You win!");
			return false;
		}
		
		return true;
	}
	
	private boolean heroCollidesWithOpenDoor() {
		for(Door d : doors) {
			if(d.getX_pos() == hero.getX_pos() &&
			   d.getY_pos() == hero.getY_pos() && 
			   d.isOpen()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean heroIsNearGuard() {
		return ((hero.getX_pos()==guard.getX_pos()+1 && hero.getY_pos()==guard.getY_pos()) ||
				(hero.getX_pos()==guard.getX_pos()-1 && hero.getY_pos()==guard.getY_pos()) ||
				(hero.getX_pos()==guard.getX_pos() && hero.getY_pos()==guard.getY_pos()+1) ||
				(hero.getX_pos()==guard.getX_pos() && hero.getY_pos()==guard.getY_pos()-1));
	}
	
	private boolean heroCollidesWithLever() {
		if (hero.getX_pos() == lever.getX_pos() && 
			hero.getY_pos() == lever.getY_pos()) {
			
			return true;
		}
		return false;
	}
	
	private void openExitDoors() {
		for (Door d : doors) {
			if(d.isExit()) {
				d.open();
			}
		}
	}
	
	private boolean posColidesWithWalls(int x, int y) {
		for(Wall w : walls) {
			if(w.getX_pos() == x && w.getY_pos() == y) {
				return true;
			}
		}
		return false;
	}
	
	private boolean posColidesWithClosedDoors(int x, int y) {
		for(Door d : doors) {
			if(d.getX_pos() == x && d.getY_pos() == y && d.isClosed()) {
				return true;
			}
		}
		return false;
	}
	 
}
