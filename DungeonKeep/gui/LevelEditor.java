package DungeonKeep.gui;

import java.awt.event.*;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import DungeonKeep.gui.LevelEditingPanel.ObjectType;
import DungeonKeep.logic.KeepLevel;

import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class LevelEditor extends JDialog {

	private static final long serialVersionUID = 1L;
	JRadioButton ogreRadioButton;
	JRadioButton keyRadioButton;
	JRadioButton heroRadioButton;
	JRadioButton wallRadioButton;
	JRadioButton doorRadioButton;
	JCheckBox heroArmedCheckBox;
	JSpinner widthSpinner;
	JSpinner heightSpinner;
	LevelEditingPanel editingPanel;
	boolean hasLevelAvailable = false;

	public LevelEditor() {
		super();
		setTitle("Keep Level Editor");
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		initPanel();
		initLevelOptionsZone();
		initEditingPanel();
		initGameObjectsZone();
		initObjectOptionsZone();
		initSubmitButton();
		initCancelButton();
		initClearMapButton();
	}
	
	public boolean hasLevelAvailable() {
		return hasLevelAvailable; 
	}
	
	private void initPanel() {
		setLayout(null);
		setBounds(0, 0, 630, 840);
		setPreferredSize(new Dimension(630, 840));
		setResizable(false);
	}
	
	private void initLevelOptionsZone() {
		JLabel levelOptionsLabel = new JLabel("Level Options");
		levelOptionsLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		levelOptionsLabel.setBounds(12, 12, 119, 22);
		add(levelOptionsLabel);
		
		JLabel widthLabel = new JLabel("Width");
		widthLabel.setBounds(12, 51, 50, 15);
		add(widthLabel);
		
		JLabel heightLabel = new JLabel("Height");
		heightLabel.setBounds(12, 95, 50, 15);
		add(heightLabel);
		
		widthSpinner = new JSpinner();
		widthSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				editingPanel.setWidth((int) widthSpinner.getValue()); 
				editingPanel.repaint();
			}
		});
		widthSpinner.setModel(new SpinnerNumberModel(7, 5, 10, 1));
		widthSpinner.setBounds(68, 46, 36, 26);
		add(widthSpinner);
		
		heightSpinner = new JSpinner();
		heightSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				editingPanel.setHeight((int) heightSpinner.getValue()); 
				editingPanel.repaint();
			}
		});
		heightSpinner.setFont(new Font("Dialog", Font.BOLD, 12));
		heightSpinner.setModel(new SpinnerNumberModel(7, 5, 10, 1));
		heightSpinner.setBounds(68, 90, 36, 26);
		add(heightSpinner);
	}
	
	private void initGameObjectsZone() {
		JLabel gameObjectsLabel = new JLabel("Game Objects");
		gameObjectsLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		gameObjectsLabel.setBounds(222, 16, 119, 15);
		add(gameObjectsLabel);
		
		initRadioButtons();
		
	    ButtonGroup group = new ButtonGroup();
	    group.add(ogreRadioButton);
	    group.add(keyRadioButton);
	    group.add(heroRadioButton);
	    group.add(wallRadioButton);
	    group.add(doorRadioButton);
	}
	
	private void initRadioButtons() {
		initOgreRadioButton();
		initKeyRadioButton();
		initHeroRadioButton();
		initWallRadioButton();
		initDoorRadioButton();
		
		wallRadioButton.setSelected(true);
		editingPanel.setCurrentObjectType(ObjectType.WALL);
	}
	
	private void initOgreRadioButton() {
		ogreRadioButton = new JRadioButton("Ogre");
		ogreRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ogreRadioButton.isSelected()) {
					editingPanel.setCurrentObjectType(ObjectType.OGRE);
				}
			}
		});
		ogreRadioButton.setBounds(220, 71, 76, 23);
		add(ogreRadioButton);
	}
	
	private void initKeyRadioButton() {
		keyRadioButton = new JRadioButton("Key");
		keyRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (keyRadioButton.isSelected()) {
					editingPanel.setCurrentObjectType(ObjectType.KEY);
				}
			}
		});
		keyRadioButton.setBounds(220, 98, 76, 23);
		add(keyRadioButton);
	}
	
	private void initHeroRadioButton() {
		heroRadioButton = new JRadioButton("Hero");
		heroRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (heroRadioButton.isSelected()) {
					editingPanel.setCurrentObjectType(ObjectType.HERO);
				}
			}
		});
		heroRadioButton.setBounds(300, 43, 76, 23);
		add(heroRadioButton);
	}
	
	private void initWallRadioButton() {
		wallRadioButton = new JRadioButton("Wall");
		wallRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (wallRadioButton.isSelected()) {
					editingPanel.setCurrentObjectType(ObjectType.WALL);
				}
			}
		});
		wallRadioButton.setBounds(220, 43, 76, 23);
		add(wallRadioButton);
	}
	
	private void initDoorRadioButton() {
		doorRadioButton = new JRadioButton("Door");
		doorRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (doorRadioButton.isSelected()) {
					editingPanel.setCurrentObjectType(ObjectType.DOOR);
				}
			}
		});
		doorRadioButton.setBounds(300, 71, 76, 23);
		add(doorRadioButton);
	}
	
	private void initObjectOptionsZone() {
		JLabel objectOptions = new JLabel("Object Options");
		objectOptions.setFont(new Font("Dialog", Font.BOLD, 14));
		objectOptions.setBounds(446, 16, 123, 15);
		add(objectOptions);
		
		JLabel heroArmedLabel = new JLabel("Hero is Armed");
		heroArmedLabel.setBounds(446, 51, 114, 15);
		add(heroArmedLabel);
		
		heroArmedCheckBox = new JCheckBox("");
		heroArmedCheckBox.setBounds(550, 47, 29, 23);
		add(heroArmedCheckBox);
	}
	
	private void initEditingPanel() {
		editingPanel = new LevelEditingPanel(
				(int) widthSpinner.getValue(), 
				(int) heightSpinner.getValue() );
		
		editingPanel.setBounds(14, 140, 600, 600);
		editingPanel.setPreferredSize(new Dimension(600, 600));
		add(editingPanel);
	}
	
	private void initSubmitButton() {
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!editingPanel.areAllElementsPresent()) {
					popElementsMissingMsg();
				}
				else if (!editingPanel.isLevelClosed()) {
					popLevelNotSurrounded();
				}
				else {
					hasLevelAvailable = true;
					dispose();
				}
			}
		});
		submitButton.setBackground(Color.WHITE);
		submitButton.setBounds(108, 760, 100, 25);
		add(submitButton);
	}
	
	private void initCancelButton() {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		cancelButton.setBackground(Color.WHITE);
		cancelButton.setBounds(422, 760, 100, 25);
		add(cancelButton);
	}
	
	private void initClearMapButton() {
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnValue = JOptionPane.showConfirmDialog(editingPanel, 
						"Are you sure you want to clear the map?", 
						"Clear Map", 
						JOptionPane.YES_NO_OPTION);
			
				if (returnValue == JOptionPane.OK_OPTION) {
					editingPanel.clearMap();
				}
			}
		});
		clearButton.setBackground(Color.WHITE);
		clearButton.setBounds(265, 760, 100, 25);
		add(clearButton);
	}
	
	private void popElementsMissingMsg() {
		JOptionPane.showMessageDialog(
				this, 
				"All elements must be present within the Level.", 
				"Failed Level Creation", 
				JOptionPane.WARNING_MESSAGE
		);
	}
	
	private void popLevelNotSurrounded() {
		JOptionPane.showMessageDialog(
				this, 
				"The level must be surrounded by Walls/Doors to be valid.", 
				"Failed Level Creation", 
				JOptionPane.WARNING_MESSAGE
		);
	}
	
	public KeepLevel getKeepLevel() {
		KeepLevel level = editingPanel.parseLevel();
		
		if (heroArmedCheckBox.isSelected()) {
			level.getHero().catchClub();
		}
		
		return level;
	}
}
















