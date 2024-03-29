package DungeonKeep.gui;

import DungeonKeep.logic.Game;
import DungeonKeep.logic.KeepLevel;

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
	private JButton levelEditorButton;
	private JTextField numOgresTextField;
	private JComboBox<String> guardPersonalitiesComboBox;
	private JPanel levelDataPanel;
	private Game game;
	private KeepLevel customKeepLevel = null;

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
	
	public void enableButtons() {
		upButton.setEnabled(true);
		downButton.setEnabled(true);
		leftButton.setEnabled(true);
		rightButton.setEnabled(true);
		levelEditorButton.setEnabled(false);
	}
	
	public void disableButtons() {
		upButton.setEnabled(false);
		downButton.setEnabled(false);
		leftButton.setEnabled(false);
		rightButton.setEnabled(false);	
		levelEditorButton.setEnabled(true);
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
			disableButtons();
			break;
		case DEFEAT:
			updateGameStatusLabel("Defeat, you lost the game ...");
			disableButtons();
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
		initLevelEditorButton();
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
					JOptionPane.showMessageDialog(DKeep, "No game to save.", "Saving Failure", JOptionPane.WARNING_MESSAGE);
					return;
				} else if (game.getStatus() == Game.Status.VICTORY) {
					JOptionPane.showMessageDialog(DKeep, "Can't save finished Game.", "Saving Failure", JOptionPane.WARNING_MESSAGE);
					return; 
				} else if (game.getStatus() == Game.Status.DEFEAT) {
					JOptionPane.showMessageDialog(DKeep, "Can't save lost Game.", "Saving Failure", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showSaveDialog(DKeep);
				
				if (result == JFileChooser.APPROVE_OPTION) {
					saveGame(fileChooser.getSelectedFile());
					gameScreen.requestFocusInWindow();
				}
			}
		});
		saveGameButton.setBackground(Color.WHITE);
		saveGameButton.setBounds(702, 380, 117, 25);
		DKeep.getContentPane().add(saveGameButton);
	}
	
	private void initLoadGameButton() {
		JButton loadGameButton = new JButton("Load Game");
		loadGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				JFileChooser fileChooser = new JFileChooser();
				int result = fileChooser.showOpenDialog(DKeep);
				
				if (result == JFileChooser.APPROVE_OPTION) {
					loadGame(fileChooser.getSelectedFile());					
				}
			}
		});
		loadGameButton.setBackground(Color.WHITE);
		loadGameButton.setBounds(702, 430, 117, 25);
		DKeep.getContentPane().add(loadGameButton);
	}
	
	private void initLevelEditorButton() {		
		levelEditorButton = new JButton("Level Editor");
		levelEditorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LevelEditor editor = new LevelEditor();
				editor.pack();
				editor.setLocationRelativeTo(DKeep);
				editor.setVisible(true);	
				
				if (editor.hasLevelAvailable()) {
					customKeepLevel = editor.getKeepLevel();
				}
			}
		});
		levelEditorButton.setBackground(Color.WHITE);
		levelEditorButton.setBounds(702, 480, 117, 25);
		DKeep.getContentPane().add(levelEditorButton);
	}
	
	private void saveGame(File saveFile) {
		try {
			FileOutputStream fstream = new FileOutputStream(saveFile);
			ObjectOutputStream objstream = new ObjectOutputStream(fstream);
			
			objstream.writeObject(game);
			objstream.close();
			fstream.close();
		} 
		catch (IOException e) {
			popFailedSaveGame();
		}
	}
	
	private void loadGame(File loadFile) {
		try {
			FileInputStream fstream = new FileInputStream(loadFile);
			ObjectInputStream objstream = new ObjectInputStream(fstream);
			game = (Game) objstream.readObject();
			objstream.close();
			fstream.close();
			initGameScreen();
		} catch (IOException e) {
			popFailedLoadGame();
		} catch (ClassNotFoundException e) {
			popFailedLoadGame();
		}
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
		DKeep.setResizable(false);
		DKeep.setTitle("Dungeon Keep");
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
		
		if (customKeepLevel != null) {
			customKeepLevel.setNumOgres(numOgres);
			game.setKeepLevel(customKeepLevel);
		}
		
		initGameScreen();
	}
	
	private void initGameScreen() {
		enableButtons();
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
		JOptionPane.showMessageDialog(DKeep, "Invalid number of Ogres.", "Failed Game Creation", JOptionPane.ERROR_MESSAGE);
		gameScreen.requestFocusInWindow();
	}
	
	private void popFailedLoadGame() {
		JOptionPane.showMessageDialog(DKeep, "Failed to load game.", "Loading Failure", JOptionPane.ERROR_MESSAGE);
		gameScreen.requestFocusInWindow();
	}
	
	private void popFailedSaveGame() {
		JOptionPane.showMessageDialog(DKeep, "Failed to save game.", "Saving Failure", JOptionPane.ERROR_MESSAGE);
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
