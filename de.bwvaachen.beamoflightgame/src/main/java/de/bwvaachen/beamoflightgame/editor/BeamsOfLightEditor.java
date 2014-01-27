package de.bwvaachen.beamoflightgame.editor;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import static de.bwvaachen.beamoflightgame.i18n.I18N.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.IIOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.bwvaachen.beamoflightgame.controller.SolverBuilder;
import de.bwvaachen.beamoflightgame.logic.AmbiguousPuzzleException;
import de.bwvaachen.beamoflightgame.logic.ISolver;
import de.bwvaachen.beamoflightgame.logic.MaximumIterationsExceededException;
import de.bwvaachen.beamoflightgame.logic.UnsolvablePuzzleException;
import de.bwvaachen.beamoflightgame.logic.strategies.IntersectionStrategy;
import de.bwvaachen.beamoflightgame.logic.strategies.LonelyFieldStrategy;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

@SuppressWarnings("serial")
public abstract class BeamsOfLightEditor extends JFrame 
	implements ActionListener{
	
	public static final double		NORTH = 0.0;
	public static final double		EAST  = 90.0;
	public static final double		SOUTH = 180.0;
	public static final double		WEST  = 270.0;
	
	private Dimension 		screenSize;
	public boolean 			isSolved;
	protected EditorType 	editorType;
	protected EditorMenu 	editorMenu;
	protected int 			row;
	protected int 			col;
	protected int 			totalTiles;
	protected int 			remainingTiles;
	protected double 		rotationAngle;
	protected String		tileStats;
	protected boolean		displayAllTiles;
	protected JPanel 		buttonPanel;
	protected JPanel 		southPanel;
	protected JButton 		solveButton; 
	protected JButton 		resetButton; 
	protected JTextArea 	tileStatsTextArea;
	
	protected JPanel 		tiles;
	protected CardLayout 	cl;
	protected GridLayout 	gl;
	protected TilesPanel 	tilesPanel;
	protected TilesPanel 	onlyNumberTilesPanel;
	
	public BeamsOfLightEditor(EditorType editorType, int width, int height){
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.col = width; this.row = height;
		this.totalTiles = col * row;
		this.editorType = editorType;
		this.rotationAngle = 0.0;
		this.isSolved = false;
		
		setSize(col*128, row*128 + 110);
		setMinimumSize(getSize());
		setLocation((screenSize.width - getSize().width)/2,(screenSize.height - getSize().height)/2);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(_f("EditorTitle", editorType));
		setLayout(new BorderLayout());
		
		cl = new CardLayout();
		gl = new GridLayout(row,col);
		tiles = new JPanel(cl);
		tilesPanel = new TilesPanel();
		tilesPanel.setLayout(gl);
		tiles.add(tilesPanel,"1");
		
		onlyNumberTilesPanel = new TilesPanel();
		onlyNumberTilesPanel.setLayout(gl);
		tiles.add(onlyNumberTilesPanel,"2");
		
		solveButton = new JButton(_("EditorSolveButton"));
		resetButton = new JButton(_("Reset"));
		
		tileStatsTextArea = new JTextArea();
		
		initComponents();
		updateTileStats();
		checkButtons();
		
		editorMenu = new EditorMenu(this);
		
		buttonPanel = new JPanel();
		buttonPanel.add(solveButton);
		buttonPanel.add(resetButton);
				
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.add(BorderLayout.CENTER,buttonPanel);
		southPanel.add(BorderLayout.SOUTH,tileStatsTextArea);
		
		setJMenuBar(editorMenu);
		add(BorderLayout.CENTER,tiles);
		add(BorderLayout.SOUTH,southPanel);
		setVisible(true);
	}
	
	public void checkButtons(){
		if(remainingTiles == 0 && !isSolved){
			solveButton.setEnabled(true);
		}else{
			solveButton.setEnabled(false);
		}
		if(!solveButton.isEnabled()){
			solveButton.setToolTipText(_("EditorToolTipSolveButton"));
		}else{
			solveButton.setToolTipText(null);
		}
		if(displayAllTiles){
			cl.show(tiles,"1");
		}else{
			cl.show(tiles,"2");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		try{
			if(ae.getSource() == resetButton){
				isSolved = false;
				switch(editorType){
					case LineEditor: 	new LineEditor(col,row);
										this.dispose();
										break;
					case NumberEditor: 	new NumberEditor(col,row);
										this.dispose();
										break;
					default: //TODO 
				}
			}
			
			if(ae.getSource() == solveButton){
				IBeamsOfLightPuzzleBoard 	board = this.convertToBoard();
				
				ISolver s =
				SolverBuilder.buildWith(LonelyFieldStrategy.class).
					          and(IntersectionStrategy.class).
					          /*and(TryAndErrorStrategy.class).*/
					          forBoard(board);
			    s.solve();
			    this.isSolved = true;
			    remove(tiles);
			    importPuzzleBoard(board);
			    convertTilesPanel();
			    add(BorderLayout.CENTER,tiles);
			   	editorMenu.getJRBMenuItemAllTiles().setEnabled(true);
			   	editorMenu.getJRBMenuItemAllTiles().setToolTipText(null);
			   	editorMenu.getJRBMenuItemNumberTiles().setEnabled(true);
			   	editorMenu.getJRBMenuItemNumberTiles().setToolTipText(null);
			}
		}catch(MaximumIterationsExceededException miee){
			int userSelection = JOptionPane.showConfirmDialog(	this,
																_("EditorSolverMaximumIterationsExceeded"),
																_("Error"),
																JOptionPane.YES_NO_OPTION);
			if(userSelection == JOptionPane.YES_OPTION){
				resetButton.doClick();
			}
		}catch(UnsolvablePuzzleException upe){
			int userSelection = JOptionPane.showConfirmDialog(	this,
																_("EditorSolverUnsolvablePuzzle"),
																_("Error"),
																JOptionPane.YES_NO_OPTION);
			if(userSelection == JOptionPane.YES_OPTION){
				resetButton.doClick();
			}
		}catch(AmbiguousPuzzleException ape){
			int userSelection = JOptionPane.showConfirmDialog(	this,
																_("EditorSolverAmbiguousPuzzle"),
																_("Error"),
																JOptionPane.YES_NO_OPTION);
			if(userSelection == JOptionPane.YES_OPTION){
				resetButton.doClick();
			}
		}catch(Exception e){
			// TODO 
			e.printStackTrace();
		}finally{
			setSize(col*128,row*128 + 110);
			setMinimumSize(getSize());
			if(isSolved){
				JOptionPane.showMessageDialog(	this,
					    						_("EditorSolverUniqueSolution"));
			}
			updateTileStats();
			checkButtons();
			pack();
			repaint();
		}
	}
	
	public static BufferedImage rotate(Image image, double angle) {
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		BufferedImage temp = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = temp.createGraphics();
		g2.rotate(Math.toRadians(angle), height / 2, height / 2);
		g2.drawImage(image, 0, 0, Color.WHITE, null);
		g2.dispose();
		return temp;
	}
	
	public void setDisplayAllTiles(boolean newDisplayAllTiles){
		this.displayAllTiles = newDisplayAllTiles;
		checkButtons();
		repaint();
	}
	
	public int getRows(){return row;}
	public int getCols(){return col;}
	public EditorType getEditorType(){return editorType;}
	public boolean getDisplayAllTiles(){return displayAllTiles;}
	public JButton getResetButton(){return resetButton;}
	public JButton getSolveButton(){return solveButton;}
	
	public abstract void initComponents();
	public abstract void importPuzzleBoard(IBeamsOfLightPuzzleBoard source)	throws NumberFormatException, IIOException, IOException;
	public abstract void convertTilesPanel();
	public abstract IBeamsOfLightPuzzleBoard convertToBoard();
	public abstract void updateTileStats();
}
