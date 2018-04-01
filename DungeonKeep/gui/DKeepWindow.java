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
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DKeepWindow implements KeyListener {

	private JFrame DKeep;
	private LevelDrawer gameScreen;
	private JLabel gameStatusLabel;
	private JButton downButton;
	private JButton upButton;
	private JButton leftButton;
	private JButton rightButton;
	private JTextField numOgresTextField;
	private JComboBox<String> guardPersonalitiesComboBox;
	private JPanel levelDataPanel;
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
		initFrame();
		initLevelDataPanel();
		initUpButton();
		initLeftButton();
		initRightButton();
		initDownButton();
		initNewGameButton();
		initExitButton();
		initSaveGameButton();
		initLoadGameButton();
		initStatusLabel();
		initLevelDrawer();
		
		gameScreen.addKeyListener(this);
	}

	private boolean isGameReadyToUpdate() {
		return !(game == null ||
				 game.getStatus() == Game.Status.VICTORY ||
				 game.getStatus() == Game.Status.DEFEAT);
	}
	
	private void initUpButton() {
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
	}
	
	private void initDownButton() {
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
	}
	
	private void initLeftButton() {
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
	}

	private void initRightButton() {
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
	}
	
	private void initExitButton() {
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		exitButton.setBackground(Color.WHITE);
		exitButton.setBounds(702, 607, 117, 25);
		DKeep.getContentPane().add(exitButton);
	}
	
	private void initNewGameButton() {
		JButton newGameButton = new JButton("New Game");
		newGameButton.addActionListener(initNewGameButtonActionListener());
		newGameButton.setBackground(Color.WHITE);
		newGameButton.setBounds(702, 30, 117, 25);
		DKeep.getContentPane().add(newGameButton);
	}
	
	private void initSaveGameButton() {
		JButton saveGameButton = new JButton("Save Game");
		saveGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (game == null) {
					JOptionPane.showMessageDialog(DKeep, "No game to save.");
					return;
				}
				
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showSaveDialog(DKeep);
				
				if (result == JFileChooser.APPROVE_OPTION) {
					// TODO Complete
				}
			}
		});
		saveGameButton.setBackground(Color.WHITE);
		saveGameButton.setBounds(702, 420, 117, 25);
		DKeep.getContentPane().add(saveGameButton);
	}
	
	private void initLoadGameButton() {
		JButton loadGameButton = new JButton("Load Game");
		loadGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(DKeep);
				
				if (result == JFileChooser.APPROVE_OPTION) {
					// TODO Complete
				}
			}
		});
		loadGameButton.setBackground(Color.WHITE);
		loadGameButton.setBounds(702, 470, 117, 25);
		DKeep.getContentPane().add(loadGameButton);
	}
	
	private int popLevelDataPanel() {
		return JOptionPane.showConfirmDialog(
				DKeep, 
				levelDataPanel,
				"Game Parameters", 
				JOptionPane.DEFAULT_OPTION,-1);
	}
	
	private void initLevelDrawer() {
		gameScreen = new LevelDrawer();
		gameScreen.setBounds(20, 30, 600, 600);
		gameScreen.setPreferredSize(new Dimension(600, 600));
		DKeep.getContentPane().add(gameScreen);
		DKeep.pack();
		DKeep.setVisible(true);
	}
	
	private void initFrame() {
		DKeep = new JFrame();
		DKeep.setBounds(100, 100, 900, 750);
		DKeep.setPreferredSize(new Dimension(900 , 740));
		DKeep.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		DKeep.getContentPane().setLayout(null);
	}
	
	private void initLevelDataPanel() {
		JPanel ogreDataPanel = new JPanel();
		numOgresTextField = new JTextField();
		numOgresTextField.setColumns(10);
		ogreDataPanel.add(new JLabel("Number of Ogres"));
		ogreDataPanel.add(numOgresTextField);
		
		JPanel guardDataPanel = new JPanel();
		String[] guardPersonalities = {"Rookie" , "Drunken" , "Suspicious"};
		guardPersonalitiesComboBox = new JComboBox<String>(guardPersonalities);
		guardPersonalitiesComboBox.setBackground(Color.WHITE);	
		guardDataPanel.add(new JLabel("Guard Personality"));	
		guardDataPanel.add(guardPersonalitiesComboBox);	
		
		levelDataPanel = new JPanel();
		levelDataPanel.setLayout(new GridLayout(2,1));      
		levelDataPanel.add(ogreDataPanel);
		levelDataPanel.add(guardDataPanel);
	}
	
	private void initStatusLabel() {
		gameStatusLabel = new JLabel("You can start a new game.");
		gameStatusLabel.setBounds(30, 655, 443, 15);
		DKeep.getContentPane().add(gameStatusLabel);	
	}
	
	private void initGame(String guardPersonality, int numOgres) {
		numOgresTextField.setText("");
		game = new Game(numOgres , guardPersonality);
		enablePlayButtons();
		updateGameStatusLabel("You can play now.");
		
		gameScreen.setLevelToDraw(game.getCurrentLevel());
		gameScreen.requestFocusInWindow();
		drawGame();
	}
	
	private ActionListener initNewGameButtonActionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				int levelDataResult = popLevelDataPanel();
				
				if (levelDataResult != JOptionPane.OK_OPTION) {
					gameScreen.requestFocusInWindow();
					return;
				}
				
				String guardPersonality = (String) guardPersonalitiesComboBox.getSelectedItem();
				int numOgres;
				
				try {
					numOgres = Integer.parseInt(numOgresTextField.getText());
				}
				catch (Exception e) {
					popInvalidNumOgres();
					return;
				}
				
				if (numOgres <= 0 || numOgres > 5) {
					popInvalidNumOgres();
					return;
				}

				initGame(guardPersonality, numOgres);
			}
		};
	}
	
	private void popInvalidNumOgres() {
		numOgresTextField.setText("");	// Clear text area
		JOptionPane.showMessageDialog(DKeep, "Invalid number of Ogres.");
		gameScreen.requestFocusInWindow();
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
