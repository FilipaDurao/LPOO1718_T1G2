package DungeonKeep.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import DungeonKeep.logic.Club;
import DungeonKeep.logic.Door;
import DungeonKeep.logic.DrunkenGuard;
import DungeonKeep.logic.DungeonLevel;
import DungeonKeep.logic.Guard;
import DungeonKeep.logic.Hero;
import DungeonKeep.logic.KeepLevel;
import DungeonKeep.logic.Key;
import DungeonKeep.logic.Level;
import DungeonKeep.logic.Lever;
import DungeonKeep.logic.Ogre;
import DungeonKeep.logic.RookieGuard;
import DungeonKeep.logic.SuspiciousGuard;
import DungeonKeep.logic.Wall;

public class LevelDrawer extends JPanel {

	private static final long serialVersionUID = 1L;
	private Level levelToDraw = null;
	private static final int SPRITE_SIZE = 60;
	private static BufferedImage clubSprite;
	private static BufferedImage doorSprite;
	private static BufferedImage wallSprite;
	private static BufferedImage rookieGuardSprite;
	private static BufferedImage suspiciousGuardSprite;
	private static BufferedImage drunkenGuardSprite;
	private static BufferedImage drunkenGuardAsleepSprite;
	private static BufferedImage heroSprite;
	private static BufferedImage heroKeySprite;
	private static BufferedImage heroKeyArmedSprite;
	private static BufferedImage heroArmedSprite;
	private static BufferedImage keySprite;
	private static BufferedImage leverSprite;
	private static BufferedImage ogreSprite;
	private static BufferedImage ogreStunnedSprite;
	
	static {
		try {
			clubSprite = ImageIO.read(new File("./bin/Images/club.png"));
			doorSprite = ImageIO.read(new File("./bin/Images/door.png"));
			wallSprite = ImageIO.read(new File("./bin/Images/wall.png"));
			rookieGuardSprite = ImageIO.read(new File("./bin/Images/rookieGuard.png"));
			suspiciousGuardSprite = ImageIO.read(new File("./bin/Images/suspiciousGuard.png"));
			drunkenGuardSprite = ImageIO.read(new File("./bin/Images/drunkenGuard.png"));
			drunkenGuardAsleepSprite = ImageIO.read(new File("./bin/Images/drunkenGuardSleeping.png"));
			heroSprite = ImageIO.read(new File("./bin/Images/hero.png"));
			heroArmedSprite = ImageIO.read(new File("./bin/Images/heroWithWeapon.png"));
			heroKeySprite = ImageIO.read(new File("./bin/Images/heroWithKey.png"));
			heroKeyArmedSprite = ImageIO.read(new File("./bin/Images/heroWithWeaponAndKey.png"));
			keySprite = ImageIO.read(new File("./bin/Images/key.png"));
			leverSprite = ImageIO.read(new File("./bin/Images/lever.png"));
			ogreSprite = ImageIO.read(new File("./bin/Images/ogre.png"));
			ogreStunnedSprite = ImageIO.read(new File("./bin/Images/ogreStunned.png"));
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("\nFailed to load sprites.");
			System.exit(1);
		}
	}

	public LevelDrawer() {
		super();
	}
	
	void setLevelToDraw(Level levelToDraw) {
		this.levelToDraw = levelToDraw;
	}
	
	@Override
	public void paintComponent(Graphics g){ 
		super.paintComponent(g);
		
		// If no level is set, return
		if (levelToDraw == null) {
			return;
		}
	
		paintBackground(g);
		
		paintWallsDoors(g);
		
		if (levelToDraw instanceof DungeonLevel) {
			paintDungeonLevel(g);
		}
		else {
			paintKeepLevel(g);
		}
		
	}
	
	private void paintBackground(Graphics g) {
		for (int i=0 ; i<levelToDraw.getWidth()*3 ; i++) {
			for (int j=0 ; j<levelToDraw.getHeight()*3 ; j++) {
				if ((i+j)%2 == 0) {
					g.setColor(new Color(222, 190, 160));	
				}
				else {
					g.setColor(new Color(242, 229, 217));
				}
				g.fillRect(i*SPRITE_SIZE/3, j*SPRITE_SIZE/3, SPRITE_SIZE/3, SPRITE_SIZE/3);
			}
		}
	}
	
	private void paintWallsDoors(Graphics g) {
		for (Wall wall : levelToDraw.getWalls()) {
			g.drawImage(wallSprite , wall.getX_pos()*SPRITE_SIZE , wall.getY_pos()*SPRITE_SIZE , null);
		}
		
		for (Door door : levelToDraw.getDoors()) {
			if (door.isClosed()) {
				g.drawImage(doorSprite , door.getX_pos()*SPRITE_SIZE , door.getY_pos()*SPRITE_SIZE , null);
			}
		}
	}
	
	private void paintDungeonLevel(Graphics g) {
		paintLever(g);
		
		paintHero(g);
		
		paintGuard(g);
	}
	
	private void paintKeepLevel(Graphics g) {	
		paintKey(g);
		
		paintHero(g);
		
		paintOgres(g);
	}
	
	private void paintHero(Graphics g) {
		Hero hero = levelToDraw.getHero();
		if (hero.hasKey()) {
			g.drawImage(
				hero.hasClub() ? heroKeyArmedSprite : heroKeySprite, 
				hero.getX_pos()*SPRITE_SIZE , 
				hero.getY_pos()*SPRITE_SIZE , null);
		}
		else {
			g.drawImage(
				hero.hasClub() ? heroArmedSprite : heroSprite, 
				hero.getX_pos()*SPRITE_SIZE , 
				hero.getY_pos()*SPRITE_SIZE , null);
		}
	}
	
	private void paintGuard(Graphics g) {
		Guard guard = ((DungeonLevel) levelToDraw).getGuard();
		
		if (guard instanceof RookieGuard) {
			g.drawImage(rookieGuardSprite , guard.getX_pos()*SPRITE_SIZE , guard.getY_pos()*SPRITE_SIZE , null);
		}
		else if (guard instanceof SuspiciousGuard) {
			g.drawImage(suspiciousGuardSprite , guard.getX_pos()*SPRITE_SIZE , guard.getY_pos()*SPRITE_SIZE , null);
		}
		else {
			g.drawImage(
					((DrunkenGuard) guard).isAsleep() ? drunkenGuardAsleepSprite : drunkenGuardSprite,
					guard.getX_pos()*SPRITE_SIZE , 
					guard.getY_pos()*SPRITE_SIZE , 
					null);
		}
	}
	
	private void paintKey(Graphics g) {
		Key key = ((KeepLevel) levelToDraw).getKey();
		if(key != null) {
			g.drawImage(keySprite , key.getX_pos()*SPRITE_SIZE , key.getY_pos()*SPRITE_SIZE , null);
		}
	}
	
	private void paintLever(Graphics g) {
		Lever lever = ((DungeonLevel) levelToDraw).getLever();
		g.drawImage(leverSprite , lever.getX_pos()*SPRITE_SIZE , lever.getY_pos()*SPRITE_SIZE , null);
	}
	
	private void paintOgres(Graphics g) {
		Club ogreClub;
		for (Ogre ogre : ((KeepLevel) levelToDraw).getOgres()) {
			g.drawImage(
					ogre.isStunned() ? ogreStunnedSprite : ogreSprite,
					ogre.getX_pos()*SPRITE_SIZE , 
					ogre.getY_pos()*SPRITE_SIZE , 
					null);
			
			ogreClub = ogre.getClub();
			
			if(!ogreClub.collidesWith(ogre)) {
				g.drawImage(clubSprite , ogreClub.getX_pos()*SPRITE_SIZE , ogreClub.getY_pos()*SPRITE_SIZE , null);
			}
		}	
	}
}
