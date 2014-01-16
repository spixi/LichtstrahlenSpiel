package de.bwvaachen.beamoflightgame.editor;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;

import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.controller.impl.LightController;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;


@SuppressWarnings("serial")
public class EditorMenu extends JMenuBar 
	implements ActionListener{
	
	private JMenu 					fileMenu ;
	private JMenuItem 				menuItemNew ;
	private JMenuItem				menuItemSave ;
	private JMenuItem				menuItemSaveAs ;
	private JMenuItem				menuItemLoad ;
	private JMenuItem				menuItemExit ;
	private JMenu					editMenu ;
	private JMenuItem				menuItemResize ;
	private ButtonGroup				buttonGroupDisplay ;
	private JRadioButtonMenuItem	jrbMenuItemAllTiles ;
	private JRadioButtonMenuItem	jrbMenuItemNumberTiles ;
	private JMenuItem				menuItemSolve ;
	private JMenuItem				menuItemReset ;
	private JMenu					helpMenu ;
	private JMenuItem				menuItemIns ;
	private JMenuItem				menuItemAbout ;
	
	private BeamsOfLightEditor editor ;
	private BeamsOfLightPuzzleBoard board ;
	private ILightController controller ;
	private JFileChooser fileChooser ;
	private File file ;

	public EditorMenu(BeamsOfLightEditor editor){
		this.editor = editor;
		
		fileMenu = new JMenu("File");
		this.add(fileMenu);
		
		menuItemNew = new JMenuItem("New...");
		menuItemNew.addActionListener(this);
		fileMenu.add(menuItemNew);
		
		JSeparator separator = new JSeparator();
		fileMenu.add(separator);
		
		menuItemSave = new JMenuItem("Save");
		menuItemSave.addActionListener(this);
		fileMenu.add(menuItemSave);
		
		menuItemSaveAs = new JMenuItem("Save As...");
		menuItemSaveAs.addActionListener(this);
		fileMenu.add(menuItemSaveAs);
		
		JSeparator separator2 = new JSeparator();
		fileMenu.add(separator2);
		
		menuItemLoad = new JMenuItem("Load");
		menuItemLoad.addActionListener(this);
		fileMenu.add(menuItemLoad);
		
		JSeparator separator3 = new JSeparator();
		fileMenu.add(separator3);
		
		menuItemExit = new JMenuItem("Exit");
		menuItemExit.addActionListener(this);
		fileMenu.add(menuItemExit);
		
		editMenu = new JMenu("Edit");
		this.add(editMenu);
		
		menuItemResize = new JMenuItem("Change Board Size...");
		menuItemResize.addActionListener(this);
		editMenu.add(menuItemResize);
		
		JSeparator separator4 = new JSeparator();
		editMenu.add(separator4);
		
		buttonGroupDisplay = new ButtonGroup();
		
		jrbMenuItemAllTiles = new JRadioButtonMenuItem("Display All Tiles",editor.getDisplayAllTiles());
		jrbMenuItemAllTiles.addActionListener(this);
		if(editor.getEditorType() == EditorType.NumberEditor){jrbMenuItemAllTiles.setEnabled(false);}
		buttonGroupDisplay.add(jrbMenuItemAllTiles);
		editMenu.add(jrbMenuItemAllTiles);
		
		
		jrbMenuItemNumberTiles = new JRadioButtonMenuItem("Display Number Tiles Only",!editor.getDisplayAllTiles());
		jrbMenuItemNumberTiles.addActionListener(this);
		if(editor.getEditorType() == EditorType.NumberEditor){jrbMenuItemAllTiles.setEnabled(false);}
		buttonGroupDisplay.add(jrbMenuItemNumberTiles);
		editMenu.add(jrbMenuItemNumberTiles);
		
		helpMenu = new JMenu("Help");
		this.add(helpMenu);
		
		menuItemIns = new JMenuItem("Instructions");
		menuItemIns.addActionListener(this);
		helpMenu.add(menuItemIns);
		
		JSeparator separator5 = new JSeparator();
		helpMenu.add(separator5);
		
		menuItemAbout = new JMenuItem("About");
		menuItemAbout.addActionListener(this);
		helpMenu.add(menuItemAbout);
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		int userSelection ;
		
		if(ae.getSource() == menuItemNew){
			userSelection = JOptionPane.showConfirmDialog(
			    				editor,
			    				"Do you really want to restart the editor?\nThe current board will be lost.",
			    				"Neustart",
			    				JOptionPane.YES_NO_OPTION);
			if(userSelection == JOptionPane.YES_OPTION){
				editor.dispose();
				new EditorMain();
			}
		}else if(ae.getSource() == menuItemExit){
			System.exit(0);
		}else if(ae.getSource() == jrbMenuItemAllTiles){
			editor.setDisplayAllTiles(true);
		}else if(ae.getSource() == jrbMenuItemNumberTiles){
			editor.setDisplayAllTiles(false);
		}else if(ae.getSource() == menuItemIns){
			
		}else if(ae.getSource() == menuItemAbout){
		
		}else
			
		try {
			controller = new LightController();
			board = (BeamsOfLightPuzzleBoard) editor.convertToBoard();
			//System.out.println(board.toString());
			controller.setBoard(board);
				
			if(ae.getSource() == menuItemSave){
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Save");   
			 
				userSelection = fileChooser.showSaveDialog(editor);
				 
				if(userSelection == JFileChooser.APPROVE_OPTION){
					file = fileChooser.getSelectedFile();
			    	controller.saveGame(file);
				}
			}else if(ae.getSource() == menuItemSaveAs){
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Save As...");   
			 
				userSelection = fileChooser.showSaveDialog(editor);
				 
				if(userSelection == JFileChooser.APPROVE_OPTION){
					file = fileChooser.getSelectedFile();
			    	controller.saveGame(file);
				}
			}else if(ae.getSource() == menuItemLoad){
				fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Load");   
			
				userSelection = fileChooser.showOpenDialog(editor);
				 
				if(userSelection == JFileChooser.APPROVE_OPTION){
					file = fileChooser.getSelectedFile();
			    	controller.loadGame(file);
				}
				editor.importPuzzleBoard(controller.getCurrentModel());
			 
			}
		}catch(IOException ioe){
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JRadioButtonMenuItem getJRBMenuItemAllTiles(){
		return jrbMenuItemAllTiles;
	}
	
	public JRadioButtonMenuItem getJRBMenuItemNumberTiles(){
		return jrbMenuItemNumberTiles;
	}
}
