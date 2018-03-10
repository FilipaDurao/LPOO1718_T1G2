package DungeonKeep.cli;
import java.util.Scanner;

import DungeonKeep.logic.Game;

public class Main {

	private static Scanner inputReader = new Scanner(System.in);

	public Main() {
		super();
	}
	
	public static char getUserInput() {
		System.out.print("\n\nEnter a move: ");	
		return (inputReader.next().charAt(0));
	}
	
	public static boolean keyIsValid(char keyPressed) {
		return (keyPressed=='w' || keyPressed=='W' || keyPressed=='s' || keyPressed=='S' ||
				keyPressed=='a' || keyPressed=='A' || keyPressed=='d' || keyPressed=='D');
	}

	public static void main(String[] args) {
		System.out.println("Welcome to Dungeon Keep!\n");
		
		// Initialize the Game
		Game dungeonKeep = new Game();
		char keyPressed;
		
		while(dungeonKeep.isRunning()) {
			keyPressed = getUserInput();
			
			if (keyIsValid(keyPressed)) {
				dungeonKeep.update(keyPressed);
			}
		}
		
		// Close the scanner
		inputReader.close();
	}

}
