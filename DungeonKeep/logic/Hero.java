package DungeonKeep.logic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Hero extends MovableGameObject {
	
	private final char standartHeroSymbol = 'H';
	private final char heroWithKeySymbol = 'K';
	private final char heroWithClubSymbol = 'A';
	private boolean hasKey = false;
	private boolean hasClub;
	private BufferedImage spriteArmed;

	public Hero(int x_pos, int y_pos, boolean hasClub) {
		super(x_pos, y_pos);
		this.hasClub = hasClub;
		
		try {
			sprite = ImageIO.read(new File("./bin/Images/hero.png"));
			spriteArmed = ImageIO.read(new File("./bin/Images/heroWithWeapon.png"));
		} 
		catch (IOException e) {
			System.out.println("\nHero sprite not found.");
			System.exit(1);
		}
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
	

	public boolean hasClub() {
		return hasClub;
	}
	
	
	public void catchKey() {
		this.hasKey = true;
	}
	
	
	public boolean hasKey() {
		return hasKey;	
	}
	
	
	public void catchClub() {
		this.hasClub = true;
	}
	
	
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
		if (!heroCollidesWithWalls(new_x_pos , new_y_pos , walls) &&
			!heroCollidesWithDoors(new_x_pos , new_y_pos , doors) &&
			!heroCollidesWithOgres(new_x_pos , new_y_pos , ogres)) {
			
			move(dir);
		}
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
	
	@Override
	public BufferedImage getSprite() {
		if(hasClub) {
			return spriteArmed;
		}
		else {
			return sprite;
		}
	}

}
