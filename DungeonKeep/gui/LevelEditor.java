package DungeonKeep.gui;

import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
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

	public LevelEditor() {
		super();
		
		initPanel();
		initLevelOptionsZone();
		initGameObjectsZone();
		initObjectOptionsZone();
		
		JButton btnNewButton = new JButton("This is simulating the 'Drawing' zone.");
		btnNewButton.setBounds(14, 140, 600, 600);
		add(btnNewButton);
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
		widthSpinner.setModel(new SpinnerNumberModel(5, 5, 10, 1));
		widthSpinner.setBounds(68, 46, 36, 26);
		add(widthSpinner);
		
		heightSpinner = new JSpinner();
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
		ogreRadioButton = new JRadioButton("Ogre");
		ogreRadioButton.setBounds(220, 71, 76, 23);
		add(ogreRadioButton);
		
		keyRadioButton = new JRadioButton("Key");
		keyRadioButton.setBounds(220, 98, 76, 23);
		add(keyRadioButton);
		
		heroRadioButton = new JRadioButton("Hero");
		heroRadioButton.setBounds(300, 43, 76, 23);
		add(heroRadioButton);
		
		wallRadioButton = new JRadioButton("Wall");
		wallRadioButton.setBounds(220, 43, 76, 23);
		add(wallRadioButton);
		
		doorRadioButton = new JRadioButton("Door");
		doorRadioButton.setBounds(300, 71, 76, 23);
		add(doorRadioButton);
		
		wallRadioButton.setSelected(true);
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
}
