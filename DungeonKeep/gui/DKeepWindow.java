package DungeonKeep.gui;

import DungeonKeep.logic.Game;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DKeepWindow {

	private JFrame DKeep;
	private JTextField numOgresTextField;
	DKeepScreen gameScreen;
	JLabel gameStatusLabel;
	JButton downButton;
	JButton upButton;
	JButton leftButton;
	JButton rightButton;
	private Game game;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DKeepWindow window = new DKeepWindow();
					window.DKeep.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DKeepWindow() {
		initialize();
	}
	
	/**
	 * Draw the game.
	 */
	public void drawGame() {
		gameScreen.repaint();
	}
	
	public void enablePlayButtons() {
		upButton.setEnabled(true);
		downButton.setEnabled(true);
		leftButton.setEnabled(true);
		rightButton.setEnabled(true);
	}
	
	public void disablePlayButtons() {
		upButton.setEnabled(false);
		downButton.setEnabled(false);
		leftButton.setEnabled(false);
		rightButton.setEnabled(false);	
	}
	
	public void updateGameStatusLabel(String message) {
		gameStatusLabel.setText(message);
	}
	
	public void updateGame() {
		Game.Status gameStatus = game.getStatus();
		
		switch(gameStatus) {
		case VICTORY:
			updateGameStatusLabel("Victory! You win!!");
			disablePlayButtons();
			break;
		case DEFEAT:
			updateGameStatusLabel("Defeat, you lost the game ...");
			disablePlayButtons();
			break;
		default:
			break;
		}
		
		drawGame();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		DKeep = new JFrame();
		DKeep.setBounds(100, 100, 920, 840);
		DKeep.setPreferredSize(new Dimension(920 , 840));
		DKeep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DKeep.getContentPane().setLayout(null);
		
		JLabel numOgresLabel = new JLabel("Number of Ogres");
		numOgresLabel.setBounds(29, 31, 145, 15);
		DKeep.getContentPane().add(numOgresLabel);
		
		numOgresTextField = new JTextField();
		numOgresTextField.setBounds(179, 27, 48, 24);
		DKeep.getContentPane().add(numOgresTextField);
		numOgresTextField.setColumns(10);
		
		JLabel guardPersonalityLabel = new JLabel("Guard Personality");
		guardPersonalityLabel.setBounds(29, 75, 158, 15);
		DKeep.getContentPane().add(guardPersonalityLabel);
		
		String[] guardPersonalities = {"Rookie" , "Drunken" , "Suspicious"};
		JComboBox<String> guardPersonalitiesComboBox = new JComboBox<String>(guardPersonalities);
		guardPersonalitiesComboBox.setBackground(Color.WHITE);
		guardPersonalitiesComboBox.setBounds(179, 70, 158, 24);
		DKeep.getContentPane().add(guardPersonalitiesComboBox);
		
		gameStatusLabel = new JLabel("You can start a new game.");
		gameStatusLabel.setBounds(30, 745, 443, 15);
		DKeep.getContentPane().add(gameStatusLabel);
		
		upButton = new JButton("Up");
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.update('W');	// Up Key
				updateGame();
			}
		});
		upButton.setBackground(Color.WHITE);
		upButton.setEnabled(false);
		upButton.setBounds(720, 217, 83, 25);
		DKeep.getContentPane().add(upButton);
		
		leftButton = new JButton("Left");
		leftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.update('A');	// Left Key
				updateGame();
			}
		});
		leftButton.setEnabled(false);
		leftButton.setBackground(Color.WHITE);
		leftButton.setBounds(665, 256, 83, 25);
		DKeep.getContentPane().add(leftButton);
		
		downButton = new JButton("Down");
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.update('S');	// Down Key
				updateGame();
			}
		});
		downButton.setEnabled(false);
		downButton.setBackground(Color.WHITE);
		downButton.setBounds(720, 294, 83, 25);
		DKeep.getContentPane().add(downButton);
		
		rightButton = new JButton("Right");
		rightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				game.update('D');	// Right Key
				updateGame();
			}
		});
		rightButton.setEnabled(false);
		rightButton.setBackground(Color.WHITE);
		rightButton.setBounds(770, 256, 83, 25);
		DKeep.getContentPane().add(rightButton);
		
		JButton newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// Parse Inputs
				String guardPersonality = (String) guardPersonalitiesComboBox.getSelectedItem();
				int numOgres;
				
				try {
					numOgres = Integer.parseInt(numOgresTextField.getText());
				}
				catch (Exception e) {
					numOgresTextField.setText("");	// Clear text area
					JOptionPane.showMessageDialog(DKeep, "Invalid number of Ogres.");
					return;
				}
				
				if (numOgres <= 0 || numOgres > 5) {
					numOgresTextField.setText("");	// Clear text area
					JOptionPane.showMessageDialog(DKeep, "Invalid number of Ogres.");
					return;
				}

				// Clear text area
				numOgresTextField.setText("");
				
				// Create the game
				game = new Game(numOgres , guardPersonality);
				
				// Enable Movement Buttons
				enablePlayButtons();
				
				// Update status message
				updateGameStatusLabel("You can play now.");
				
				// Draw the game for the first time
				gameScreen.setLevelToDraw(game.getCurrentLevel());
				drawGame();
			}
			
		});
		newGameButton.setBackground(Color.WHITE);
		newGameButton.setBounds(702, 120, 117, 25);
		DKeep.getContentPane().add(newGameButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		exitButton.setBackground(Color.WHITE);
		exitButton.setBounds(702, 429, 117, 25);
		DKeep.getContentPane().add(exitButton);
		
		gameScreen = new DKeepScreen();
		gameScreen.setBackground(Color.WHITE);
		gameScreen.setBounds(20, 120, 600, 600);
		gameScreen.setPreferredSize(new Dimension(600, 600));
		DKeep.getContentPane().add(gameScreen);
		DKeep.pack();
		DKeep.setVisible(true);
		
	}
}
