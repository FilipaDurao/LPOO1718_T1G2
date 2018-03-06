package DungeonKeep.test;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.ArrayList;

import DungeonKeep.logic.*;

public class TestLevelCreation {

	@Test
	public void testLevelInit() {
		// Initialize the Hero
		Hero hero = new Hero(1,1,false);
		
		// Initialize Walls
		ArrayList<Wall> walls = new ArrayList<Wall>();
		for(int i=0 ; i<7 ; i++) {
			walls.add(new Wall(i, 0));
			if(i != 3 && i != 4) {
				walls.add(new Wall(i, 6));
			}
		}
		for(int i=1 ; i<6 ; i++) {
			walls.add(new Wall(0,i));
			walls.add(new Wall(6,i));
		}
		
		// Initialize Doors
		ArrayList<Door> doors = new ArrayList<Door>();
		Door d1 = new Door(3, 6, true);
		Door d2 = new Door(3, 6, false);
		doors.add(d1);
		doors.add(d2);
		
		// Initialize Guards
		ArrayList<Guard> guards = new ArrayList<Guard>();
		Guard g1 = new RookieGuard(5, 1, new ArrayList<GameObject.MoveDirection>());
		Guard g2 = new RookieGuard(5, 1, new ArrayList<GameObject.MoveDirection>());
		Guard g3 = new RookieGuard(5, 1, new ArrayList<GameObject.MoveDirection>());
		guards.add(g1);
		guards.add(g2);
		guards.add(g3);
		
		// Initialize Ogres
		ArrayList<Ogre> ogres = new ArrayList<Ogre>();
		Ogre o1 = new Ogre(3, 1);
		Ogre o2 = new Ogre(3, 2);
		ogres.add(o1);
		ogres.add(o2);
		
		// Initialize the Key
		Key key = new Key(5, 5);
		
		// Initialize the Lever
		Lever lever = new Lever(4, 5);
		
		// Initialize Level itself
		Level testLevel = new Level(
				0,
				hero,
				walls,
				doors,
				guards,
				ogres,	
				lever,
				key,
				7,
				7);
		
		
		// Verify level size and ID
		assertEquals(testLevel.getWidth() , 7);
		assertEquals(testLevel.getHeigth() , 7);
		assertEquals(testLevel.getID() , 0);
		
		// Verify Hero Coordinates and that it has no weapon
		assertEquals(testLevel.getHero().getX_pos() , 1);
		assertEquals(testLevel.getHero().getY_pos() , 1);
		assertFalse(testLevel.getHero().hasClub());
		
		// Verify key and lever position
		assertEquals(testLevel.getKey().getX_pos() , 5);
		assertEquals(testLevel.getKey().getY_pos() , 5);
		assertEquals(testLevel.getLever().getX_pos() , 4);
		assertEquals(testLevel.getLever().getY_pos() , 5);
		
		// Verify ogre position
		// TODO complete this
		
	}
	
}