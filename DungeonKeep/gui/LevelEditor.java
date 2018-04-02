package DungeonKeep.gui;

import java.awt.Graphics;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class LevelEditor extends JPanel {

	private static final long serialVersionUID = 1L;

	public LevelEditor() {
		super();
		setLayout(null);
		
		JSpinner widthSpinner = new JSpinner();
		widthSpinner.setModel(new SpinnerNumberModel(5, 5, 10, 1));
		widthSpinner.setBounds(68, 46, 36, 26);
		add(widthSpinner);
		
		JSpinner heightSpinner = new JSpinner();
		heightSpinner.setFont(new Font("Dialog", Font.BOLD, 12));
		heightSpinner.setModel(new SpinnerNumberModel(5, 5, 10, 1));
		heightSpinner.setBounds(68, 90, 36, 26);
		add(heightSpinner);
		
		JLabel widthLabel = new JLabel("Width");
		widthLabel.setBounds(12, 51, 50, 15);
		add(widthLabel);
		
		JLabel heightLabel = new JLabel("Height");
		heightLabel.setBounds(12, 95, 50, 15);
		add(heightLabel);
		
		JRadioButton ogreRadioButton = new JRadioButton("Ogre");
		ogreRadioButton.setBounds(220, 71, 76, 23);
		add(ogreRadioButton);
		
		JRadioButton keyRadioButton = new JRadioButton("Key");
		keyRadioButton.setBounds(220, 43, 76, 23);
		add(keyRadioButton);
		
		JRadioButton heroRadioButton = new JRadioButton("Hero");
		heroRadioButton.setBounds(300, 43, 76, 23);
		add(heroRadioButton);
		
		JRadioButton wallRadioButton = new JRadioButton("Wall");
		wallRadioButton.setBounds(220, 98, 76, 23);
		add(wallRadioButton);
		
		JRadioButton doorRadioButton = new JRadioButton("Door");
		doorRadioButton.setBounds(300, 71, 76, 23);
		add(doorRadioButton);
		
	    ButtonGroup group = new ButtonGroup();
	    group.add(ogreRadioButton);
	    group.add(keyRadioButton);
	    group.add(heroRadioButton);
	    group.add(wallRadioButton);
	    group.add(doorRadioButton);
		
		JLabel gameObjectsLabel = new JLabel("Game Objects");
		gameObjectsLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		gameObjectsLabel.setBounds(222, 16, 119, 15);
		add(gameObjectsLabel);
		
		JLabel levelOptionsLabel = new JLabel("Level Options");
		levelOptionsLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		levelOptionsLabel.setBounds(12, 12, 119, 22);
		add(levelOptionsLabel);
		
		JLabel objectOptions = new JLabel("Object Options");
		objectOptions.setFont(new Font("Dialog", Font.BOLD, 14));
		objectOptions.setBounds(446, 16, 123, 15);
		add(objectOptions);
		
		JCheckBox heroArmedCheckBox = new JCheckBox("");
		heroArmedCheckBox.setHorizontalAlignment(SwingConstants.TRAILING);
		heroArmedCheckBox.setBounds(550, 47, 29, 23);
		add(heroArmedCheckBox);
		
		JLabel numOgresLabel = new JLabel("Num Ogres");
		numOgresLabel.setBounds(446, 85, 80, 15);
		add(numOgresLabel);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		spinner.setBounds(540, 80, 36, 26);
		add(spinner);
		
		JLabel heroArmedLabel = new JLabel("Hero is Armed");
		heroArmedLabel.setBounds(446, 51, 114, 15);
		add(heroArmedLabel);
		
		JButton btnNewButton = new JButton("This is simulating the 'Drawing' zone.");
		btnNewButton.setBounds(14, 140, 600, 600);
		add(btnNewButton);
	}
	
	@Override
	public void paintComponent(Graphics g){ 
		super.paintComponent(g);
	}
}
