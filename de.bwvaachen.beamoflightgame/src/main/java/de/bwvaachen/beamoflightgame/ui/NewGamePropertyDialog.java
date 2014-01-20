package de.bwvaachen.beamoflightgame.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.bwvaachen.beamoflightgame.controller.CouldNotCreatePuzzleException;
import de.bwvaachen.beamoflightgame.controller.CreateRandomBoard;
import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.helper.GameProperties;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

public final class NewGamePropertyDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4944433339944945768L;
	private static GameProperties properties = GameProperties.INSTANCE;
	final ILightController controller;
	
	private class CreateRandomMediator implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ev) {     
			IBeamsOfLightPuzzleBoard board = new BeamsOfLightPuzzleBoard();
			
			boolean boardOk = false;

			final double density = (double) properties.get("newgame:density");
			final int height     = (int) properties.get("newgame:height");
			final int width      = (int) properties.get("newgame:width");
			final boolean zerotiles = (boolean)properties.get("newgame:zerotiles");
			
			int repeat = JOptionPane.NO_OPTION;
			
			do {
				try {
					board = CreateRandomBoard.createRandom(height, width, density, zerotiles);
					boardOk = true;
				}
				catch(CouldNotCreatePuzzleException ex) {
					Object[] options = {"Repeat", "Abort"};
					repeat = JOptionPane.showOptionDialog((Component)NewGamePropertyDialog.this, (Object)ex.getMessage(), "Error", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, (Icon) null, options, options[0]);
				}
			} while(!boardOk && (repeat == JOptionPane.YES_OPTION));
			
			if(boardOk) {
				try {
					controller.setBoard(board);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	//private class propertyListener implements 
	
	public NewGamePropertyDialog(final ILightController cntrl) {
		super();
		
		//Make the Dialog modal, so that all other Windows have to wait for it.
		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setTitle("Einstellungen für \"Neues Spiel\"");
		controller = cntrl;
		
		JPanel  mainPanel, densityPanel, widthPanel, heightPanel, noZeroPanel;
		JSlider densitySlider, widthSlider, heightSlider;
		JCheckBox noZeroCheckBox;
		JButton okButton;
		
		setLayout(new BorderLayout());
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		densityPanel = new JPanel();
		densityPanel.setLayout(new FlowLayout());
		
		densityPanel.add(new JLabel("Density"));
		
		densitySlider = new JSlider(Integer.MIN_VALUE, Integer.MAX_VALUE);
		Dictionary<Integer, JComponent> densitySliderLabels = new Hashtable<Integer, JComponent>();
		densitySliderLabels.put(Integer.MIN_VALUE, new JLabel("less"));
		densitySliderLabels.put(0, new JLabel("number tiles"));
		densitySliderLabels.put(Integer.MAX_VALUE, new JLabel("more"));
		densitySlider.setValue((int)Math.round((double)properties.get("newgame:density") * Integer.MAX_VALUE));
		densitySlider.setLabelTable(densitySliderLabels);
		densitySlider.setPaintLabels(true);
		densitySlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				double value = (double)((JSlider) e.getSource()).getValue() / (double)Integer.MAX_VALUE;
				properties.put("newgame:density", value);
			}
		});
		densityPanel.add(densitySlider);
		
		widthPanel = new JPanel();
		widthPanel.setLayout(new FlowLayout());
		
		widthPanel.add(new JLabel("Width"));
		
		widthSlider  = new JSlider(4,18);
		widthSlider.setValue((int)properties.get("newgame:width"));
		widthSlider.setPaintLabels(true);
		widthSlider.setMinorTickSpacing(1);
		widthSlider.setMajorTickSpacing(2);
		widthSlider.setPaintTicks(true);
		widthSlider.setSnapToTicks(true);
		widthSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider) e.getSource()).getValue();
				properties.put("newgame:width", value);
			}
		});
		
		widthPanel.add(widthSlider);
		
		heightPanel = new JPanel();
		heightPanel.setLayout(new FlowLayout());
		
		heightPanel.add(new JLabel("Height"));
		
		heightSlider = new JSlider(4,18);
		heightSlider.setValue((int)properties.get("newgame:height"));
		heightSlider.setPaintLabels(true);
		heightSlider.setMinorTickSpacing(1);
		heightSlider.setMajorTickSpacing(2);
		heightSlider.setPaintTicks(true);
		heightSlider.setSnapToTicks(true);
		heightPanel.add(heightSlider);
		heightSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int value = ((JSlider) e.getSource()).getValue();
				properties.put("newgame:height", value);
			}
		});
		
		noZeroPanel = new JPanel();
		noZeroPanel.setLayout(new FlowLayout());
		
		noZeroPanel.add(new JLabel("Allow zero tiles"));
		
		noZeroCheckBox = new JCheckBox();
		noZeroCheckBox.setSelected((boolean)properties.get("newgame:zerotiles"));
		noZeroCheckBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				boolean value = ((JCheckBox) e.getSource()).isSelected();
				properties.put("newgame:zerotiles", value);
			}
		});
		
		noZeroPanel.add(noZeroCheckBox);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(2,2));
		mainPanel.add(heightPanel);
		mainPanel.add(widthPanel);
		mainPanel.add(densityPanel);
		mainPanel.add(noZeroPanel);
		
		add(mainPanel,BorderLayout.CENTER);
		
		okButton = new JButton("Generate Puzzle!");
		okButton.addActionListener(new CreateRandomMediator());
		add(okButton, BorderLayout.SOUTH);
		
		pack();
		
	}
	

}
