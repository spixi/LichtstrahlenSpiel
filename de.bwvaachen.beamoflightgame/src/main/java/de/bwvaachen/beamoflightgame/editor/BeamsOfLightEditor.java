package de.bwvaachen.beamoflightgame.editor;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

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

import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.controller.SolverBuilder;
import de.bwvaachen.beamoflightgame.controller.impl.LightController;
import de.bwvaachen.beamoflightgame.logic.ISolver;
import de.bwvaachen.beamoflightgame.logic.UnsolvablePuzzleException;
import de.bwvaachen.beamoflightgame.logic.strategies.IntersectionStrategy;
import de.bwvaachen.beamoflightgame.logic.strategies.LonelyFieldStrategy;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

@SuppressWarnings("serial")
public abstract class BeamsOfLightEditor extends JFrame 
	implements ActionListener{
	
	private Dimension 		screenSize;
	protected EditorType 	editorType;
	protected EditorMenu 	editorMenu;
	protected int 			row;
	protected int 			col;
	protected int 			totalTiles;
	protected int 			remainingTiles;
	protected double 		rotationAngle;
	protected boolean		displayAllTiles;
	protected JPanel 		buttonPanel;
	protected JPanel 		southPanel;
	protected JButton 		solveButton; 
	protected JButton 		resetButton; 
	protected JTextArea 	tileStats;
	
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
		
		setSize(col*128, row*128 + 110);
		setMinimumSize(getSize());
		setLocation((screenSize.width - getSize().width)/2,(screenSize.height - getSize().height)/2);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("BeamsOfLightGame - " + editorType);
		setLayout(new BorderLayout());
		
		cl = new CardLayout();
		gl = new GridLayout(row,col);
		tiles = new JPanel(cl);
		tilesPanel = new TilesPanel();
		tilesPanel.setLayout(gl);
		
		onlyNumberTilesPanel = new TilesPanel();
		onlyNumberTilesPanel.setLayout(gl);
		
		solveButton = new JButton("Try to solve");
		resetButton = new JButton("Reset");
		
		initComponents();
		tileStats = new JTextArea(getTileStats());
		tiles.add(tilesPanel,"1");
		tiles.add(onlyNumberTilesPanel,"2");
		checkButtons();
		
		editorMenu = new EditorMenu(this);
		
		buttonPanel = new JPanel();
		buttonPanel.add(solveButton);
		buttonPanel.add(resetButton);
				
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.add(BorderLayout.CENTER,buttonPanel);
		southPanel.add(BorderLayout.SOUTH,tileStats);
		
		setJMenuBar(editorMenu);
		add(BorderLayout.CENTER,tiles);
		add(BorderLayout.SOUTH,southPanel);
		setVisible(true);
	}
	
	public void checkButtons(){
		if(remainingTiles == 0){
			solveButton.setEnabled(true);
		}else{
			solveButton.setEnabled(false);
		}
		if(!solveButton.isEnabled()){
			solveButton.setToolTipText("Can only solve if remaining fields = 0.");
		}else{
			solveButton.setToolTipText(null);
		}
		if(displayAllTiles){
			cl.show(tiles, "1");
		}else{
			cl.show(tiles, "2");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		try{
			if(ae.getSource() == resetButton){
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
				IBeamsOfLightPuzzleBoard board = this.convertToBoard();
				ILightController controller = new LightController();
				controller.setBoard(board);
				
				ISolver s =
				SolverBuilder.buildWith(LonelyFieldStrategy.class).
					          and(IntersectionStrategy.class).
					          /*and(TryAndErrorStrategy.class).*/
					          forBoard(board);
			    s.solve();
			    tilesPanel = new TilesPanel();
			    onlyNumberTilesPanel = new TilesPanel();
			    importPuzzleBoard(controller.getCurrentModel());
			    convertTilesPanel();
				//tiles.add(tilesPanel,"1");
			    tiles.add(onlyNumberTilesPanel,"2");
			}
		}catch(UnsolvablePuzzleException upe){
			int userSelection = JOptionPane.showConfirmDialog(	this,
																"Unable to find unique solution for this input.\nReset board?",
																"Error",
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
			tileStats.setText(getTileStats());
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
	public abstract String getTileStats();
}
