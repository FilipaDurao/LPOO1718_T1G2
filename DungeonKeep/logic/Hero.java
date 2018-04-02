package DungeonKeep.logic;

import java.util.ArrayList;

public class Hero extends MovableGameObject {
	
	private static final long serialVersionUID = 1L;	// To allow file writing
	private final char standartHeroSymbol = 'H';	/**< The symbol that represents a Hero on the console version of the game */
	private final char heroWithKeySymbol = 'K';		/**< The symbol that represents a Hero with a Key on the console version of the game */
	private final char heroWithClubSymbol = 'A';	/**< The symbol that represents a Hero with a Club on the console version of the game */
	private boolean hasKey = false;					/**< Variable to determine whether the Hero has a Key */
	private boolean hasClub;						/**< Variable to determine if the Hero has a Club */

	/**
	 * Creates a Hero
	 * 
	 * @param x_pos - the x position where the Hero is created
	 * @param y_pos - the y position where the Hero is created
	 * @param hasClub - whether the Hero has a Club or not
	 */
	public Hero(int x_pos, int y_pos, boolean hasClub) {
		super(x_pos, y_pos);
		this.hasClub = hasClub;
	}
	
	/**
	 * @return Returns the ID symbol of the Hero, differentiating between a standard Hero, a Hero with a Key and a Hero with a Club 
	 */
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
	
	/**
	 * Verifies if the Hero has a Club
	 * 
	 * @return True if the Hero has a Club, False otherwise
	 */
	public boolean hasClub() {
		return hasClub;
	}
	
	/**
	 * Hero "catches" the Key, becoming able to open Doors
	 */
	public void catchKey() {
		this.hasKey = true;
	}
	
	/**
	 * Verifies if the Hero has a Key
	 * 
	 * @return True if the Hero has a Key, False otherwise
	 */
	public boolean hasKey() {
		return hasKey;	
	}
	
	/**
	 * Hero "catches" the Club, becoming able to stun certain characters
	 */
	public void catchClub() {
		this.hasClub = true;
	}
	
	/**
	 * Updates the game based on the move direction of the Hero if that is possible., that is, if after the move he doesn't
	 * collide with a wall or a closed Door
	 * 
	 * @param dir - the move direction
	 * @param walls - all the Game's Walls
	 * @param doors - all the Game's Doors
	 * @param ogres - all the Game's Ogres
	 */
	public void update(MoveDirection dir , ArrayList<Wall> walls , ArrayList<Door> doors , ArrayList<Ogre> ogres) {
		int new_x_pos = this.getX_pos();
		int new_y_pos = this.getY_pos();
		
		switch(dir) {
		case UP:
			new_y_pos--;
			break;
		case DOWN:
			new_y_pos++;
			break;
		case LEFT:
			new_x_pos--;
			break;
		case RIGHT:
			new_x_pos++;
			break;
		default:
			return;
		}
		
		// Move, if the hero isn't colliding with any wall or closed door
		if (!heroCollides(new_x_pos, new_y_pos, walls, doors, ogres)) {
			move(dir);
		}
	}
	
	
	private boolean heroCollides(int new_x_pos, int new_y_pos, ArrayList<Wall> walls,
			ArrayList<Door> doors, ArrayList<Ogre> ogres) {
		
		return (heroCollidesWithWalls(new_x_pos , new_y_pos , walls) ||
				heroCollidesWithDoors(new_x_pos , new_y_pos , doors) ||
				heroCollidesWithOgres(new_x_pos , new_y_pos , ogres));
	}
	
	
	private boolean heroCollidesWithWalls(int new_x_pos , int new_y_pos , ArrayList<Wall> walls) {
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
	
	
	private boolean heroCollidesWithDoors(int new_x_pos , int new_y_pos , ArrayList<Door> doors) {
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
	
	private boolean heroCollidesWithOgres(int new_x_pos , int new_y_pos , ArrayList<Ogre> ogres) {
		for(Ogre o : ogres) {
			// Check if hero collides with any of the ogres
			if (new_x_pos == o.getX_pos() &&
				new_y_pos == o.getY_pos()) {
				
				return true;
			}
		}
		
		// No collision was found
		return false;
	}

}
