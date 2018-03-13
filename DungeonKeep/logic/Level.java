package DungeonKeep.logic;
import java.util.Arrays;
import java.util.ArrayList;

public class Level implements IStatus {

	private int ID;
	private Hero hero;
	private ArrayList<Wall> walls;
	private ArrayList<Door> doors;
	private ArrayList<Guard> guards;
	private ArrayList<Ogre> ogres;
	private Lever lever;
	private Key key;
	private int width;
	private int heigth;
	private Status status = Status.RUNNING;
	
	public Level(
			int ID,
			Hero hero,
			ArrayList<Wall> walls,
			ArrayList<Door> doors,
			ArrayList<Guard> guards,
			ArrayList<Ogre> ogres,
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
	
	public Status getStatus() {
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

	public ArrayList<Wall> getWalls() {
		return walls;
	}

	public ArrayList<Door> getDoors() {
		return doors;
	}

	public ArrayList<Guard> getGuards() {
		return guards;
	}

	public ArrayList<Ogre> getOgres() {
		return ogres;
	}

	public Lever getLever() {
		return lever;
	}

	public Key getKey() {
		return key;
	}

	public String getGameMatrix() {
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
			symbols[o.getClub().getY_pos()][o.getClub().getX_pos()] = o.getClub().getIdSymbol();	// Draw the Ogre's Club
			symbols[o.getY_pos()][o.getX_pos()] = o.getIdSymbol();									// Draw the Ogre
		}
		
		// Draw Key
		if(key != null) {			
			// Check if square already has an ogre or club in it
			if (!ogres.isEmpty()) {
				Ogre ogre = ogres.get(0);
				
				if (symbols[key.getY_pos()][key.getX_pos()] == ogre.getIdSymbol() ||
					symbols[key.getY_pos()][key.getX_pos()] == ogre.getClub().getIdSymbol()) {
					
					symbols[key.getY_pos()][key.getX_pos()] = '$';
				}
				else {
					symbols[key.getY_pos()][key.getX_pos()] = key.getIdSymbol();
				}
			}
			else {
				symbols[key.getY_pos()][key.getX_pos()] = key.getIdSymbol();
			}
		}
		
		// Parse the matrix to a string
		String gameMatrixString = "";
		for(char[] row : symbols) {
			for(char c : row) {
				gameMatrixString += Character.toString(c) + " ";
			}
			gameMatrixString += "\n";
		}		
		
		return gameMatrixString;
	}
	
	
	public void update(Hero.MoveDirection heroDirection) {
		
		// Move the Guards
		for(Guard g : guards) {
			g.performStep();
		}
		
		// Move the Ogres
		for(Ogre o : ogres) {
			o.update(walls , doors , ogres);
		}
		
		// Move the Hero
		hero.update(heroDirection, walls, doors, ogres);
		
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
				status = Status.DEFEAT;
				return;
			}
		}
		
		// Check for proximity with ogres / ogres' club
		for(Ogre o : ogres) {
			if(heroIsNearOgre(o) || heroIsNearOgreClub(o)) {
				status = Status.DEFEAT;
				return;
			}
		}
		
		// Check with collision with open door
		if(heroCollidesWithOpenDoor()) {
			status = Status.VICTORY;
		}
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
		if (hero.hasClub()) {
			// When the hero has a weapon , the one affected is the ogre! Stun the ogre!
			if (hero.isNear(ogre)) {
				ogre.stun();
			}
			return false;
		}
		else {
			return (hero.isNear(ogre) || hero.collidesWith(ogre));
		}
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
