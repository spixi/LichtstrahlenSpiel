package de.bwvaachen.beamoflightgame.ui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
			
			while(!boardOk)
			{
				boardOk = true;
				board = CreateRandomBoard.createRandom(height, width, density);
				for(int x =0;x < board.getWidth() && boardOk;x++)
				{
					for(int y =0;y < board.getHeight() && boardOk;y++)
					{
						ITile t = board.getTileAt(x, y);
						if(! (t instanceof ITile))
						{
							boardOk = false;
						}
						else if (t instanceof NumberTile)
							if(((NumberTile)t).getNumber() == 0)
								boardOk = false;
					}
				}//Iteration over Tiles
				
				
			}//while Board not Ok
			try {
				controller.setBoard(board);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		//Close the window
		dispose();
		}
		
	}
	
	//private class propertyListener implements 
	
	public NewGamePropertyDialog(final ILightController cntrl) {
		super();
		
		//Make the Dialog modal, so that all other Windows have to wait for it.
		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		
		setTitle("Einstellungen für \"Neues Spiel\"");
		controller = cntrl;
		
		JPanel  mainPanel, densityPanel, widthPanel, heightPanel;
		JSlider densitySlider, widthSlider, heightSlider;
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
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3,1));
		mainPanel.add(heightPanel);
		mainPanel.add(widthPanel);
		mainPanel.add(densityPanel);
		
		add(mainPanel,BorderLayout.CENTER);
		
		okButton = new JButton("Generate Puzzle!");
		okButton.addActionListener(new CreateRandomMediator());
		add(okButton, BorderLayout.SOUTH);
		
		pack();
		
	}
	

}
