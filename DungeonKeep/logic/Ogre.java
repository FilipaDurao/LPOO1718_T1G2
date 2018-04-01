package DungeonKeep.logic;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Ogre extends GameObject implements Movable {
	
	private final char regularSymbol = '0';
	private final char stunnedSymbol = '8';
	private Club club = new Club(getX_pos()+1 , getY_pos());
	private boolean isStunned = false;
	private boolean disabled = false;
	private int stunnedTimer = 0;
	private BufferedImage spriteStunned;

	
	public Ogre(int x_pos, int y_pos) {
		super(x_pos, y_pos);
		
		try {
			sprite = ImageIO.read(new File("./bin/Images/ogre.png"));
			spriteStunned = ImageIO.read(new File("./bin/Images/ogreStunned.png"));
		} 
		catch (IOException e) {
			System.out.println("\nOgre sprite not found.");
			System.exit(1);
		}
	}
	
	
	public boolean isStunned() {
		return isStunned;
	}
	
	
	public void stun() {
		if (!isStunned) {
			this.isStunned = true;
			this.stunnedTimer = 2;
		}
	}
	
	
	public void disable() {
		disabled = true;
	}
	
	
	@Override
	public char getIdSymbol() {
		if(this.isStunned) {
			return stunnedSymbol;
		}
		else {
			return regularSymbol;
		}
	}
	
	
	public Club getClub() {
		return club;
	}
	
	
	public void update(ArrayList<Wall> walls , ArrayList<Door> doors , ArrayList<Ogre> ogres) {
		// Check if the Ogre is disabled
		if (this.disabled) {
			return;
		}
		
		// Check if the Ogre is stunned
		if (this.stunnedTimer > 0) {
			this.stunnedTimer--;
		}
		
		// Ogre is not stunned ; Move
		else {
			this.isStunned = false;
		
			ArrayList<MoveDirection> possibleMoveDirections = getPossibleDirections(walls, doors, ogres);
			
			// If there are no possible direction, the ogre does not move.
			if (possibleMoveDirections.isEmpty()) {
				return;
			}
			
			// Perform the step in one of the random directions
			MoveDirection dir = possibleMoveDirections.get( 
					(int) Math.floor(Math.random()*possibleMoveDirections.size()) );
			
			move(dir);
		}
		
		// Swing the club
		swingClub(walls,doors,ogres);
	}
	
	
	private void swingClub(ArrayList<Wall> walls , ArrayList<Door> doors , ArrayList<Ogre> ogres) {
		ArrayList<MoveDirection> possibleSwingDirections = getClubPossibleDirections(walls, doors, ogres);
		
		// If there are no possible directions for the club, the ogre does not swing it.
		if (possibleSwingDirections.isEmpty()) {
			return;
		}
		
		// Perform the swing in one of the random directions
		MoveDirection dir = possibleSwingDirections.get( 
				(int) Math.floor(Math.random()*possibleSwingDirections.size()) );
		
		performClubSwing(dir);
	}
	
	private ArrayList<MoveDirection> getPossibleDirections(ArrayList<Wall> walls , 
			ArrayList<Door> doors , ArrayList<Ogre> ogres) {
		
		ArrayList<MoveDirection> possibleDirections = new ArrayList<MoveDirection>();
		
		// Analise 4 possible moves
		if (!(positionHasWall(this.getX_pos() , this.getY_pos()-1 , walls) ||
			  positionHasDoor(this.getX_pos() , this.getY_pos()-1 , doors))) {
			possibleDirections.add(MoveDirection.UP);
		}
		if (!(positionHasWall(this.getX_pos() , this.getY_pos()+1 , walls) ||
			  positionHasDoor(this.getX_pos() , this.getY_pos()+1 , doors))) {
			possibleDirections.add(MoveDirection.DOWN);
		}
		if (!(positionHasWall(this.getX_pos()-1 , this.getY_pos() , walls) ||
			  positionHasDoor(this.getX_pos()-1 , this.getY_pos() , doors))) {
			possibleDirections.add(MoveDirection.LEFT);
		}
		if (!(positionHasWall(this.getX_pos()+1 , this.getY_pos() , walls) ||
			  positionHasDoor(this.getX_pos()+1 , this.getY_pos() , doors))) {
			possibleDirections.add(MoveDirection.RIGHT);
		}
		
		return possibleDirections;
	}
	
	private ArrayList<MoveDirection> getClubPossibleDirections(ArrayList<Wall> walls , 
			ArrayList<Door> doors , ArrayList<Ogre> ogres) {
		
		ArrayList<MoveDirection> possibleDirections = new ArrayList<MoveDirection>();
		
		// Analise 4 possible moves
		if (!(positionHasWall(this.getX_pos() , this.getY_pos()-1 , walls) ||
			  positionHasDoor(this.getX_pos() , this.getY_pos()-1 , doors) ||
			  positionHasOgre(this.getX_pos() , this.getY_pos()-1 , ogres))) {
			possibleDirections.add(MoveDirection.UP);
		}
		if (!(positionHasWall(this.getX_pos() , this.getY_pos()+1 , walls) ||
			  positionHasDoor(this.getX_pos() , this.getY_pos()+1 , doors) ||
			  positionHasOgre(this.getX_pos() , this.getY_pos()+1 , ogres))) {
			possibleDirections.add(MoveDirection.DOWN);
		}
		if (!(positionHasWall(this.getX_pos()-1 , this.getY_pos() , walls) ||
			  positionHasDoor(this.getX_pos()-1 , this.getY_pos() , doors) ||
			  positionHasOgre(this.getX_pos()-1 , this.getY_pos() , ogres))) {
			possibleDirections.add(MoveDirection.LEFT);
		}
		if (!(positionHasWall(this.getX_pos()+1 , this.getY_pos() , walls) ||
			  positionHasDoor(this.getX_pos()+1 , this.getY_pos() , doors) ||
			  positionHasOgre(this.getX_pos()+1 , this.getY_pos() , ogres))) {
			possibleDirections.add(MoveDirection.RIGHT);
		}
		
		return possibleDirections;
	}
	
	private boolean positionHasOgre(int x , int y , ArrayList<Ogre> ogres) {
		for (Ogre ogre : ogres) {
			if (ogre.getX_pos() == x  &&  ogre.getY_pos() == y) {
				return true;
			}
		}
		
		// No ogre found it the position (x,y)
		return false;
	}
	
	private boolean positionHasWall(int x , int y , ArrayList<Wall> walls) {
		for (Wall wall : walls) {
			if (wall.getX_pos() == x  &&  wall.getY_pos() == y) {
				return true;
			}
		}
		
		// No wall found it the position (x,y)
		return false;
	}
	
	private boolean positionHasDoor(int x , int y , ArrayList<Door> doors) {
		for (Door door : doors) {
			if (door.getX_pos() == x  &&  door.getY_pos() == y) {
				return true;
			}
		}
		
		// No door found it the position (x,y)
		return false;
	}
	
	
	public void move (MoveDirection dir) {
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
		default:
			return;
		}		
	}
	
	
	private void performClubSwing (MoveDirection dir) {
		switch (dir) {
		case UP:
			club.setX_pos(this.getX_pos());
			club.setY_pos(this.getY_pos() - 1);
			break;
		case DOWN:
			club.setX_pos(this.getX_pos());
			club.setY_pos(this.getY_pos() + 1);
			break;
		case RIGHT:
			club.setX_pos(this.getX_pos() + 1);
			club.setY_pos(this.getY_pos());
			break;
		case LEFT:
			club.setX_pos(this.getX_pos() - 1);
			club.setY_pos(this.getY_pos());
			break;
		default:
			return;
		}		
	}
	
	@Override
	public BufferedImage getSprite() {
		if(isStunned) {
			return spriteStunned;
		}
		else {
			return sprite;
		}
	}

}