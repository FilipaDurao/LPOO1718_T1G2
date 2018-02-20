import java.util.HashSet;
import java.util.Scanner;

public class Game {
	
	public Game() {
		super();
	}

	public static void main(String[] args) {
		
		HashSet<Wall> level1walls = new HashSet<Wall>();
		HashSet<Door> level1doors = new HashSet<Door>();
		
		// Initialize level 1 objects ///////////
		
		level1doors.add(new Door(0, 5, true));
		level1doors.add(new Door(0, 6, true));
		level1doors.add(new Door(2, 3, false));
		level1doors.add(new Door(4, 3, false));
		level1doors.add(new Door(4, 1, false));
		level1doors.add(new Door(2, 8, false));
		level1doors.add(new Door(4, 8, false));
		level1walls.add(new Wall(1, 2));
		level1walls.add(new Wall(2, 2));
		level1walls.add(new Wall(4, 2));
		level1walls.add(new Wall(5, 2));
		level1walls.add(new Wall(6, 2));
		level1walls.add(new Wall(1, 4));
		level1walls.add(new Wall(2, 4));
		level1walls.add(new Wall(4, 4));
		level1walls.add(new Wall(5, 4));
		level1walls.add(new Wall(6, 4));
		level1walls.add(new Wall(6, 1));
		level1walls.add(new Wall(6, 3));
		level1walls.add(new Wall(1, 7));
		level1walls.add(new Wall(2, 7));
		level1walls.add(new Wall(4, 7));
		level1walls.add(new Wall(5, 7));
		level1walls.add(new Wall(6, 7));
		level1walls.add(new Wall(7, 7));
		level1walls.add(new Wall(6, 8));
		level1walls.add(new Wall(0, 1));
		level1walls.add(new Wall(0, 2));
		level1walls.add(new Wall(0, 3));
		level1walls.add(new Wall(0, 4));
		level1walls.add(new Wall(0, 7));
		level1walls.add(new Wall(0, 8));
		
		
		for(int i=0 ; i<10 ; i++) {
			level1walls.add(new Wall(i, 0));
			level1walls.add(new Wall(i, 9));
		}
		
		for(int i=1 ; i<9 ; i++) {
			level1walls.add(new Wall(9, i));
		}
		
		// Initialize level 1
		Level level1 = new Level(
				new Hero(1, 1),
				level1walls,
				level1doors,
				new Guard(8, 1),
				new Lever(7, 8),
				null, 
				null,
				10,
				10);
		
		Scanner reader = new Scanner(System.in);
		char keyPressed;
		
		do {
			level1.draw();
			System.out.print("\nEnter a move: ");
			keyPressed = reader.next().charAt(0);			
		} while (level1.update(keyPressed));
		level1.draw();
		
		reader.close();
	}

}











