package DungeonKeep.gui;

import DungeonKeep.logic.Game;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DKeepWindow implements KeyListener {

	private JFrame DKeep;
	LevelDrawer gameScreen;
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
	
	public void updateGame(char direction) {
		game.update(direction);
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
		case LEVELCHANGED:
			// Update level to draw
			gameScreen.setLevelToDraw(game.getCurrentLevel());
		default:
			break;
		}
		
		drawGame();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Main Frame
		DKeep = new JFrame();
		DKeep.setBounds(100, 100, 900, 750);
		DKeep.setPreferredSize(new Dimension(900 , 740));
		DKeep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DKeep.getContentPane().setLayout(null);
		
		
		// "Configuration Menu"
		JPanel ogreDataPanel = new JPanel();
		JTextField numOgresTextField = new JTextField();
		numOgresTextField.setColumns(10);
		ogreDataPanel.add(new JLabel("Number of Ogres"));
		ogreDataPanel.add(numOgresTextField);
		
		JPanel guardDataPanel = new JPanel();
		String[] guardPersonalities = {"Rookie" , "Drunken" , "Suspicious"};
		JComboBox<String> guardPersonalitiesComboBox = new JComboBox<String>(guardPersonalities);
		guardPersonalitiesComboBox.setBackground(Color.WHITE);	
		guardDataPanel.add(new JLabel("Guard Personality"));	
		guardDataPanel.add(guardPersonalitiesComboBox);	
		
		JPanel levelDataPanel = new JPanel();
		levelDataPanel.setLayout(new GridLayout(2,1));      
		levelDataPanel.add(ogreDataPanel);
		levelDataPanel.add(guardDataPanel);
		
		
		// Status Label
		gameStatusLabel = new JLabel("You can start a new game.");
		gameStatusLabel.setBounds(30, 655, 443, 15);
		DKeep.getContentPane().add(gameStatusLabel);
		
		
		// Up Button
		upButton = new JButton("Up");
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateGame('W');	// Up Key
				gameScreen.requestFocusInWindow();
			}
		});
		upButton.setBackground(Color.WHITE);
		upButton.setEnabled(false);
		upButton.setBounds(720, 127, 83, 25);
		DKeep.getContentPane().add(upButton);
		
		
		// Left Button
		leftButton = new JButton("Left");
		leftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateGame('A');	// Left Key
				gameScreen.requestFocusInWindow();
			}
		});
		leftButton.setEnabled(false);
		leftButton.setBackground(Color.WHITE);
		leftButton.setBounds(665, 166, 83, 25);
		DKeep.getContentPane().add(leftButton);
		
		
		// Down Button
		downButton = new JButton("Down");
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateGame('S');	// Down Key
				gameScreen.requestFocusInWindow();
			}
		});
		downButton.setEnabled(false);
		downButton.setBackground(Color.WHITE);
		downButton.setBounds(720, 204, 83, 25);
		DKeep.getContentPane().add(downButton);
		
		
		// Right Button
		rightButton = new JButton("Right");
		rightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				updateGame('D');	// Right Key
				gameScreen.requestFocusInWindow();
			}
		});
		rightButton.setEnabled(false);
		rightButton.setBackground(Color.WHITE);
		rightButton.setBounds(770, 166, 83, 25);
		DKeep.getContentPane().add(rightButton);
		
		
		// New Game Button
		JButton newGameButton = new JButton("New Game");
		newGameButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {	
				
				int levelDataResult = JOptionPane.showConfirmDialog(
						DKeep, 
						levelDataPanel,
						"Game Parameters", 
			            JOptionPane.DEFAULT_OPTION,
			            -1);
				
				if (levelDataResult != JOptionPane.OK_OPTION) {
					gameScreen.requestFocusInWindow();
					return;
				}
				
				// Parse Inputs
				String guardPersonality = (String) guardPersonalitiesComboBox.getSelectedItem();
				int numOgres;
				
				try {
					numOgres = Integer.parseInt(numOgresTextField.getText());
				}
				catch (Exception e) {
					numOgresTextField.setText("");	// Clear text area
					JOptionPane.showMessageDialog(DKeep, "Invalid number of Ogres.");
					gameScreen.requestFocusInWindow();
					return;
				}
				
				if (numOgres <= 0 || numOgres > 5) {
					numOgresTextField.setText("");	// Clear text area
					JOptionPane.showMessageDialog(DKeep, "Invalid number of Ogres.");
					gameScreen.requestFocusInWindow();
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
				gameScreen.requestFocusInWindow();
				drawGame();
			}
			
		});
		newGameButton.setBackground(Color.WHITE);
		newGameButton.setBounds(702, 30, 117, 25);
		DKeep.getContentPane().add(newGameButton);
		
		
		// Exit Button
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		exitButton.setBackground(Color.WHITE);
		exitButton.setBounds(702, 301, 117, 25);
		DKeep.getContentPane().add(exitButton);
		
		
		// Level Drawer Initialization
		gameScreen = new LevelDrawer();
		gameScreen.setBounds(20, 30, 600, 600);
		gameScreen.setPreferredSize(new Dimension(600, 600));
		DKeep.getContentPane().add(gameScreen);
		DKeep.pack();
		DKeep.setVisible(true);
		
		
		// Event Handlers
		gameScreen.addKeyListener(this);
	}

	private boolean isGameReadyToUpdate() {
		return !(game == null ||
				 game.getStatus() == Game.Status.VICTORY ||
				 game.getStatus() == Game.Status.DEFEAT);
	}
	
	
	@Override
	public void keyPressed(KeyEvent evt) {
		if (!isGameReadyToUpdate()) {
			return;
		}
		
		switch(evt.getKeyCode()){
		case KeyEvent.VK_LEFT:
			updateGame('A');
			break;
		case KeyEvent.VK_RIGHT:
			updateGame('D');
			break;
		case KeyEvent.VK_UP:
			updateGame('W');
			break;
		case KeyEvent.VK_DOWN:
			updateGame('S');
		 	break;
		}
	}

	
	@Override
	public void keyReleased(KeyEvent evt) {}	// Key Released Events are ignored

	@Override
	public void keyTyped(KeyEvent evt) {}		// Key Typed Events are ignored
}
