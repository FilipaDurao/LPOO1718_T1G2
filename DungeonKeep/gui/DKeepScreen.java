package DungeonKeep.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import DungeonKeep.logic.Club;
import DungeonKeep.logic.Door;
import DungeonKeep.logic.DungeonLevel;
import DungeonKeep.logic.Guard;
import DungeonKeep.logic.Hero;
import DungeonKeep.logic.KeepLevel;
import DungeonKeep.logic.Key;
import DungeonKeep.logic.Level;
import DungeonKeep.logic.Lever;
import DungeonKeep.logic.Ogre;
import DungeonKeep.logic.Wall;

public class DKeepScreen extends JPanel {

	private static final long serialVersionUID = 1L;
	private Level levelToDraw = null;
	private static final int spriteSize = 60;

	public DKeepScreen() {
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
	
		// Draw Background
		for (int i=0 ; i<levelToDraw.getHeigth()*3 ; i++) {
			for (int j=0 ; j<levelToDraw.getWidth()*3 ; j++) {
				if ((i+j)%2 == 0) {
					g.setColor(new Color(220, 220, 220));
				}
				else {
					g.setColor(new Color(195, 195, 195));
				}
				g.fillRect(i*spriteSize/3, j*spriteSize/3, spriteSize/3, spriteSize/3);
			}
		}
		
		// Draw Walls
		for (Wall wall : levelToDraw.getWalls()) {
			g.drawImage(wall.getSprite() , wall.getX_pos()*spriteSize , wall.getY_pos()*spriteSize , null);
		}
		
		// Draw doors
		for (Door door : levelToDraw.getDoors()) {
			if (door.isClosed()) {
				g.drawImage(door.getSprite() , door.getX_pos()*spriteSize , door.getY_pos()*spriteSize , null);
			}
		}
		
		Hero hero = levelToDraw.getHero();
		
		if (levelToDraw instanceof DungeonLevel) {
			// Draw lever
			Lever lever = ((DungeonLevel) levelToDraw).getLever();
			g.drawImage(lever.getSprite() , lever.getX_pos()*spriteSize , lever.getY_pos()*spriteSize , null);
			
			// Draw hero
			g.drawImage(hero.getSprite() , hero.getX_pos()*spriteSize , hero.getY_pos()*spriteSize , null);
			
			// Draw Guard
			Guard guard = ((DungeonLevel) levelToDraw).getGuard();
			g.drawImage(guard.getSprite() , guard.getX_pos()*spriteSize , guard.getY_pos()*spriteSize , null);
		}
		else {
			// Draw key
			Key key = ((KeepLevel) levelToDraw).getKey();
			if(key != null) {
				g.drawImage(key.getSprite() , key.getX_pos()*spriteSize , key.getY_pos()*spriteSize , null);
			}
			
			// Draw hero
			g.drawImage(hero.getSprite() , hero.getX_pos()*spriteSize , hero.getY_pos()*spriteSize , null);
			
			// Draw Ogres
			Club ogreClub;
			for (Ogre ogre : ((KeepLevel) levelToDraw).getOgres()) {
				g.drawImage(ogre.getSprite() , ogre.getX_pos()*spriteSize , ogre.getY_pos()*spriteSize , null);
				ogreClub = ogre.getClub();
				g.drawImage(ogreClub.getSprite() , ogreClub.getX_pos()*spriteSize , ogreClub.getY_pos()*spriteSize , null);
			}	
		}
		
	}
}
