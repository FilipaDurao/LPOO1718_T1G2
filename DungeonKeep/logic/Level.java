package DungeonKeep.logic;
import java.util.Arrays;
import java.util.HashSet;

public class Level {
	
	public enum LevelStatus { RUNNING , DEFEAT , VICTORY }

	private int ID;
	private Hero hero;
	private HashSet<Wall> walls;
	private HashSet<Door> doors;
	private HashSet<Guard> guards;
	private HashSet<Ogre> ogres;
	private Lever lever;
	private Key key;
	private int width;
	private int heigth;
	private LevelStatus status = LevelStatus.RUNNING;
	
	public Level(
			int ID,
			Hero hero,
			HashSet<Wall> walls,
			HashSet<Door> doors,
			HashSet<Guard> guards,
			HashSet<Ogre> ogres,
			Lever lever,
			Key key,
			int width, 
			int heigth) {
		
		super();
		this.ID = ID;
		this.hero = hero;
		this.walls = walls;
		this.doors = doors;
		this.guards = guards;
		this.lever = lever;
		this.ogres = ogres;
		this.key = key;
		this.width = width;
		this.heigth = heigth;
	}
	
	public int getID() {
		return ID;
	}
	
	public LevelStatus getStatus() {
		return status;
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

	public Hero getHero() {
		return hero;
	}

	public HashSet<Wall> getWalls() {
		return walls;
	}

	public HashSet<Door> getDoors() {
		return doors;
	}

	public HashSet<Guard> getGuards() {
		return guards;
	}

	public HashSet<Ogre> getOgres() {
		return ogres;
	}

	public Lever getLever() {
		return lever;
	}

	public Key getKey() {
		return key;
	}

	public void draw() {
		char[][] symbols = new char[heigth][width];
		
		// Initialize matrix with spaces
		for(char[] row : symbols) {
			Arrays.fill(row, ' ');
		}
		
		// Draw Lever		
		if(lever != null) {
			symbols[lever.getY_pos()][lever.getX_pos()] = lever.getIdSymbol();
		}
		
		// Draw Walls
		for(Wall w : walls) {
			symbols[w.getY_pos()][w.getX_pos()] = w.getIdSymbol();
		}
		
		// Draw Doors
		for(Door d : doors) {
			symbols[d.getY_pos()][d.getX_pos()] = d.getIdSymbol();
		}
		
		// Draw Hero
		symbols[hero.getY_pos()][hero.getX_pos()] = hero.getIdSymbol();
		
		// Draw Guards
		for(Guard g : guards) {
			symbols[g.getY_pos()][g.getX_pos()] = g.getIdSymbol();
		}
		
		// Draw Ogres
		for(Ogre o : ogres) {
			symbols[o.getY_pos()][o.getX_pos()] = o.getIdSymbol();									// Draw the Ogre
			symbols[o.getClub().getY_pos()][o.getClub().getX_pos()] = o.getClub().getIdSymbol();	// Draw the Ogre's Club
		}
		
		// Draw Key
		if(key != null) {
			symbols[key.getY_pos()][key.getX_pos()] = key.getIdSymbol();
			
			// If Key and Ogre or Club are colliding, draw a '$' colliding symbol instead of the key symbol
			for (Ogre o : ogres) {
				if (symbols[key.getY_pos()][key.getX_pos()] == o.getIdSymbol() || 
					symbols[key.getY_pos()][key.getX_pos()] == o.getClub().getIdSymbol()) {
					
					symbols[key.getY_pos()][key.getX_pos()] = '$';
					break;
				}
			}
		}
		
		//Print the matrix to the screen
		for(char[] row : symbols) {
			System.out.println();
			for(char c : row) {
				System.out.print(c);
				System.out.print(' ');
			}
		}		
	}
	
	
	public void update(char keyPressed) {
		
		// Move the Hero
		hero.move(keyPressed, walls, doors);
		
		// Move the Guards
		for(Guard g : guards) {
			g.performStep();
		}
		
		// Move the Ogres
		for(Ogre o : ogres) {
			o.move(walls , doors);
		}
		
		// Check for collision with lever (if existent)
		if(lever != null && hero.collidesWith(lever)) {
			openExitDoors();
		}
		
		// Check for collision with key (if existent)
		if(key != null && hero.collidesWith(key)) {
			hero.catchKey();
			key = null;
		}
		
		// Check for proximity with guards
		for(Guard g : guards) {
			if(heroIsNearGuard(g)) {
				status = LevelStatus.DEFEAT;
				draw();
				return;
			}
		}
		
		// Check for proximity with ogres / ogres' club
		for(Ogre o : ogres) {
			if(heroIsNearOgre(o) || heroIsNearOgreClub(o)) {
				status = LevelStatus.DEFEAT;
				draw();
				return;
			}
		}
		
		// Check with collision with open door
		if(heroCollidesWithOpenDoor()) {
			status = LevelStatus.VICTORY;
		}
		
		draw();
	}
	
	
	private boolean heroCollidesWithOpenDoor() {
		for(Door d : doors) {	// Iterate through all the doors
			if(hero.collidesWith(d) && d.isOpen()) {
				return true;	// Colliding with open door!
			}
		}
		
		// No collision was found
		return false;	
	}
	
	
	private boolean heroIsNearGuard(Guard guard) {
		
		// Check if the guard is of the Drunken Type and asleep
		if (guard instanceof DrunkenGuard  &&  ((DrunkenGuard) guard).isAsleep()) {
			// If hero collides with the guard's exact position, the guard wakes up and catches the hero
			if(hero.collidesWith(guard)) {
				((DrunkenGuard) guard).wakeUp();
				return true;
			}
			else {
				return false;
			}
		}
		
		// Regular Guard / Awaken Guard
		else {
			return (hero.isNear(guard) || hero.collidesWith(guard));
		}
	}
	
	
	private boolean heroIsNearOgre(Ogre ogre) {
		// Verify if the Hero is horizontally/vertically next to the Ogre
		return (hero.isNear(ogre) || hero.collidesWith(ogre));
	}
	
	
	private boolean heroIsNearOgreClub(Ogre ogre) {
		Club club = ogre.getClub();
		
		return (hero.isNear(club) || hero.collidesWith(club));
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
