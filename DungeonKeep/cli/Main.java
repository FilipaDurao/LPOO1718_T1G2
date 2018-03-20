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
		Game dungeonKeep = new Game(1 , "Rookie");
		char keyPressed;
		System.out.print(dungeonKeep.getGameMatrixString());	// Draw the game for the first time
		
		while(dungeonKeep.getStatus() == Game.Status.RUNNING) {
			keyPressed = getUserInput();
			
			if (keyIsValid(keyPressed)) {
				dungeonKeep.update(keyPressed);
				System.out.print(dungeonKeep.getGameMatrixString());
			}
		}
		
		if (dungeonKeep.getStatus() == Game.Status.DEFEAT) {
			System.out.println("\nYou lost the game ...");
		}
		else {
			System.out.println("\nYou won the game!! :D");
		}
		
		// Close the scanner
		inputReader.close();
	}

}
