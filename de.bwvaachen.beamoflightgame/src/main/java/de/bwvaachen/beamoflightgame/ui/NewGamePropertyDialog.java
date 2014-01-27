package de.bwvaachen.beamoflightgame.ui;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.awt.BorderLayout;
import static de.bwvaachen.beamoflightgame.i18n.I18N.*;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.concurrent.Executors;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.OverlayLayout;
import javax.swing.ProgressMonitor;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.bwvaachen.beamoflightgame.controller.CouldNotCreatePuzzleException;
import de.bwvaachen.beamoflightgame.controller.CreateRandomBoard;
import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.helper.GameProperties;
import de.bwvaachen.beamoflightgame.helper.Holder;
import de.bwvaachen.beamoflightgame.helper.Timer;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

public final class NewGamePropertyDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4944433339944945768L;
	private static GameProperties properties = GameProperties.INSTANCE;
	final ILightController controller;
	private JButton okButton;
	
	private class CreateRandomMediator implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ev) {     
			IBeamsOfLightPuzzleBoard board = new BeamsOfLightPuzzleBoard();
			
			final Holder<Boolean> boardOk = new Holder<Boolean>(false);

			final double density = (double) properties.get("newgame:density");
			final int height     = (int) properties.get("newgame:height");
			final int width      = (int) properties.get("newgame:width");
			final boolean zerotiles = (boolean)properties.get("newgame:zerotiles");
			
			  class CreateRandomWorker extends SwingWorker<Void, Void> {
					private final Cursor waitCursor = new Cursor(Cursor.WAIT_CURSOR);
					private final Cursor defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
					private IBeamsOfLightPuzzleBoard board;
				  
				    @Override
				    public Void doInBackground() {
				    okButton.setEnabled(false);
				    setCursor(waitCursor);
				    
						boardOk.value = false;
						final Timer timer = new Timer(150000000);
						CreateRandomBoard crb = new CreateRandomBoard();

						while(! boardOk.value && ! timer.timeOver()) {
							setProgress(timer.pastTimePercentage());
							try {
								board = crb.createRandom(height, width, density, zerotiles);
								boardOk.value = true;
							}
							catch (CouldNotCreatePuzzleException e) {
								// TODO Auto-generated catch block
								boardOk.value = false;
							}
						}
						return null;
				    }

				    @Override
				    public void done() {
				      okButton.setEnabled(true);
				      setCursor(defaultCursor);
				      try {
						controller.setBoard(board);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				      setProgress(0);
				    }
			}
			
			int repeat = JOptionPane.NO_OPTION;
			
			do {
				SwingWorker w = new CreateRandomWorker();
				//w.addPropertyChangeListener(this); //TODO!!!
				w.run();
			} while(!boardOk.value && (repeat == JOptionPane.YES_OPTION));
			
			if(boardOk.value) {
				try {
					controller.setBoard(board);
					controller.solve();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println(e.getClass());
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	public NewGamePropertyDialog(final ILightController cntrl) {
		super();
		
		//Make the Dialog modal, so that all other Windows have to wait for it.
		setModalityType(Dialog.DEFAULT_MODALITY_TYPE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setTitle(_("NewGameProperties"));
		controller = cntrl;
		
		JPanel  mainPanel, densityPanel, widthPanel, heightPanel, noZeroPanel;
		JSlider densitySlider, widthSlider, heightSlider;
		JCheckBox noZeroCheckBox;
		
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
		
		okButton = new JButton(_("GeneratePuzzle"));
		okButton.addActionListener(new CreateRandomMediator());
		add(okButton, BorderLayout.SOUTH);
		
		pack();
	}
	

}
