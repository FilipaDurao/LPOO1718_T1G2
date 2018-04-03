package DungeonKeep.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import DungeonKeep.logic.DungeonLevel;
import DungeonKeep.logic.Hero;
import DungeonKeep.logic.KeepLevel;
import DungeonKeep.logic.Key;
import DungeonKeep.logic.Ogre;
import DungeonKeep.logic.Wall;

public class LevelEditingPanel extends JPanel implements MouseListener{

	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private static final int SPRITE_SIZE = 60;
	private static final int BORDER_SIZE = 1;
	private ObjectType currentObjectType = ObjectType.NO_OBJECT;
	
	private ObjectType levelMatrix[][] = new ObjectType[10][10];
	public enum ObjectType { NO_OBJECT, WALL, HERO, OGRE, DOOR, KEY }
	
	private static BufferedImage doorSprite;
	private static BufferedImage wallSprite;
	private static BufferedImage heroSprite;
	private static BufferedImage keySprite;
	private static BufferedImage ogreSprite;
	
	static {
		try {
			doorSprite = ImageIO.read(new File("./bin/Images/door.png"));
			wallSprite = ImageIO.read(new File("./bin/Images/wall.png"));
			heroSprite = ImageIO.read(new File("./bin/Images/hero.png"));
			keySprite = ImageIO.read(new File("./bin/Images/key.png"));
			ogreSprite = ImageIO.read(new File("./bin/Images/ogre.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("\nFailed to load sprites.");
			System.exit(1);
		}
	}	
	
	public LevelEditingPanel(int width, int height) {
		this.width = width;
		this.height = height;
		this.setBackground(Color.WHITE);
		
		initPanel();
		
		addMouseListener(this);
	}
	
	public void setWidth(int width) {
		this.width = width;
		
		// Clear Elements outside current Level Dimensions
		for (int i = width; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				levelMatrix[i][j] = ObjectType.NO_OBJECT;
			}
		}
	}

	public void setHeight(int height) {
		this.height = height;
		
		// Clear Elements outside current Level Dimensions
		for (int i = 0; i < 10; i++) {
			for (int j = height; j < 10; j++) {
				levelMatrix[i][j] = ObjectType.NO_OBJECT;
			}
		}
	}
	
	public void setCurrentObjectType(ObjectType objType) {
		this.currentObjectType = objType;
	}
	
	@Override
	public void paintComponent(Graphics g){ 
		super.paintComponent(g);		
		paintGrid(g);
		paintObjects(g);
	}
	
	private void paintGrid(Graphics g) {
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, width*SPRITE_SIZE, height*SPRITE_SIZE);
		
		g.setColor(Color.WHITE);
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				g.fillRect(i*SPRITE_SIZE + BORDER_SIZE, 
						   j*SPRITE_SIZE + BORDER_SIZE, 
						   SPRITE_SIZE - 2*BORDER_SIZE,
						   SPRITE_SIZE - 2*BORDER_SIZE);
			}
		}	
	}
	
	private void paintObjects(Graphics g) {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				paintObject(g, i, j);
			}
		}
	}
	
	private void paintObject(Graphics g, int x, int y) {
		switch(levelMatrix[x][y]) {
		case WALL:
			g.drawImage(wallSprite , x*SPRITE_SIZE, y*SPRITE_SIZE , null);
			break;
		case DOOR:
			g.drawImage(doorSprite , x*SPRITE_SIZE, y*SPRITE_SIZE , null);
			break;
		case KEY:
			g.drawImage(keySprite , x*SPRITE_SIZE, y*SPRITE_SIZE , null);
			break;
		case HERO:
			g.drawImage(heroSprite , x*SPRITE_SIZE, y*SPRITE_SIZE , null);
			break;
		case OGRE:
			g.drawImage(ogreSprite , x*SPRITE_SIZE, y*SPRITE_SIZE , null);
			break;		
		default:
			return;
		}
	}
	
	private void initPanel() {
		// Initialize matrix with zeros
		for(ObjectType[] row : levelMatrix) {
			Arrays.fill(row, ObjectType.NO_OBJECT);
		}
		
		// Surround Level with walls
		for(int i = 0; i < width; i++) {
			levelMatrix[i][0] = ObjectType.WALL;
			levelMatrix[i][height-1] = ObjectType.WALL;
				
		}
		for(int j = 1; j < height-1; j++) {
			levelMatrix[0][j] = ObjectType.WALL;
			levelMatrix[width-1][j] = ObjectType.WALL;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX()/SPRITE_SIZE;
		int y = e.getY()/SPRITE_SIZE;
		
		if(!mousePosIsValid(x,y)) {
			return;
		}
		
		if (e.getButton() == MouseEvent.BUTTON3) {
			levelMatrix[x][y] = ObjectType.NO_OBJECT;	// Clear the square of the matrix
		} else if (e.getButton() == MouseEvent.BUTTON1) {
			switch(currentObjectType) {
			case HERO:
				addHero(x,y);
				break;
			case KEY:
				addKey(x,y);
				break;
			case OGRE:
				addOgre(x,y);
				break;
			default:
				levelMatrix[x][y] = currentObjectType;
			}
		}

		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	private boolean mousePosIsValid(int x, int y) {
		return (x < width &&
				y < height);
	}
	
	private void addHero(int x, int y) {
		removeExistingHero();
		levelMatrix[x][y] = ObjectType.HERO;
	}
	
	private void addKey(int x, int y) {
		removeExistingKey();
		levelMatrix[x][y] = ObjectType.KEY;
	}
	
	private void addOgre(int x, int y) {
		removeExistingOgre();
		levelMatrix[x][y] = ObjectType.OGRE;
	}
	
	private void removeExistingOgre() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(levelMatrix[i][j] == ObjectType.OGRE) {
					levelMatrix[i][j] = ObjectType.NO_OBJECT;
					return;
				}
			}
		}
	}
	
	private void removeExistingHero() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(levelMatrix[i][j] == ObjectType.HERO) {
					levelMatrix[i][j] = ObjectType.NO_OBJECT;
					return;
				}
			}
		}
	}
	
	private void removeExistingKey() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(levelMatrix[i][j] == ObjectType.KEY) {
					levelMatrix[i][j] = ObjectType.NO_OBJECT;
					return;
				}
			}
		}
	}
	
	public boolean areAllElementsPresent() {
		boolean heroExists = false, ogreExists = false,
				doorExists = false, keyExists = false;
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				switch(levelMatrix[i][j]) {
				case HERO:
					heroExists = true;
					break;
				case OGRE:
					ogreExists = true;
					break;
				case DOOR:
					doorExists = true;
					break;
				case KEY:
					keyExists = true;
					break;
				default:
					break;
				}
			}
		}
		
		return (heroExists && ogreExists &&
				doorExists && keyExists);
	}
	
	public boolean isLevelClosed() {
		for(int i = 0; i < width; i++) {
			if ((levelMatrix[i][0]!=ObjectType.WALL && levelMatrix[i][0]!=ObjectType.DOOR) ||
				(levelMatrix[i][height-1]!=ObjectType.WALL && levelMatrix[i][height-1]!=ObjectType.DOOR)) {
				return false;
			}
		}
		for(int j = 1; j < height-1; j++) {
			if ((levelMatrix[0][j]!=ObjectType.WALL && levelMatrix[0][j]!=ObjectType.DOOR) ||
				(levelMatrix[width-1][j]!=ObjectType.WALL && levelMatrix[width-1][j]!=ObjectType.DOOR)) {
				return false;
			}
		}
		return true;
	}
	
	public KeepLevel parseLevel() {
		if (!areAllElementsPresent() || !isLevelClosed()) {
			return null;
		}
		else {
			return null;
		}	
	}
	
}
