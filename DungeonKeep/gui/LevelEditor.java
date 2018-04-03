package DungeonKeep.gui;

import java.awt.Graphics;
import java.awt.event.*;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import DungeonKeep.gui.LevelEditingPanel.ObjectType;

import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class LevelEditor extends JPanel {

	private static final long serialVersionUID = 1L;
	JRadioButton ogreRadioButton;
	JRadioButton keyRadioButton;
	JRadioButton heroRadioButton;
	JRadioButton wallRadioButton;
	JRadioButton doorRadioButton;
	JCheckBox heroArmedCheckBox;
	JSpinner numOgresSpinner;
	JSpinner widthSpinner;
	JSpinner heightSpinner;
	LevelEditingPanel editingPanel;

	public LevelEditor() {
		super();
		
		initPanel();
		initLevelOptionsZone();
		initEditingPanel();
		initGameObjectsZone();
		initObjectOptionsZone();
	}
	
	private void initPanel() {
		setLayout(null);
		setBounds(0, 0, 630, 750);
		setPreferredSize(new Dimension(630, 750));
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
		widthSpinner.setModel(new SpinnerNumberModel(5, 5, 10, 1));
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
		heightSpinner.setModel(new SpinnerNumberModel(5, 5, 10, 1));
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
		heroArmedCheckBox.setHorizontalAlignment(SwingConstants.TRAILING);
		heroArmedCheckBox.setBounds(550, 47, 29, 23);
		add(heroArmedCheckBox);
		
		JLabel numOgresLabel = new JLabel("Num Ogres");
		numOgresLabel.setBounds(446, 85, 80, 15);
		add(numOgresLabel);
		
		numOgresSpinner = new JSpinner();
		numOgresSpinner.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		numOgresSpinner.setBounds(540, 80, 36, 26);
		add(numOgresSpinner);
	}
	
	private void initEditingPanel() {
		editingPanel = new LevelEditingPanel(
				(int) widthSpinner.getValue(), 
				(int) heightSpinner.getValue() );
		
		editingPanel.setBounds(14, 140, 600, 600);
		editingPanel.setPreferredSize(new Dimension(600, 600));
		add(editingPanel);
	}

}
















