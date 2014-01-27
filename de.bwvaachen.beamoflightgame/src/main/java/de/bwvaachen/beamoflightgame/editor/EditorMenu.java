package de.bwvaachen.beamoflightgame.editor;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import static de.bwvaachen.beamoflightgame.i18n.I18N.*;

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
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.controller.impl.LightController;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

@SuppressWarnings("serial")
public class EditorMenu extends JMenuBar 
	implements ActionListener{
	
	private JMenu 					fileMenu;
	private JMenuItem 				menuItemNew;
	private JMenuItem				menuItemSave;
	private JMenuItem				menuItemSaveAs;
	private JMenuItem				menuItemLoad;
	private JMenuItem				menuItemExit;
	private JMenu					editMenu;
	private JMenuItem				menuItemResize;
	private ButtonGroup				buttonGroupDisplay;
	private JRadioButtonMenuItem	jrbMenuItemAllTiles;
	private JRadioButtonMenuItem	jrbMenuItemNumberTiles;
	private JMenuItem				menuItemSolve;
	private JMenuItem				menuItemReset;
	private JMenu					helpMenu;
	private JMenuItem				menuItemInstructions;
	private JMenuItem				menuItemAbout;
	
	private BeamsOfLightEditor 		editor;
	private BeamsOfLightPuzzleBoard board;
	private ILightController 		controller;
	private JFileChooser 			fileChooser;
	private FileFilter 				filter;
	private File 					file;
	private final String 			className;
	
	public EditorMenu(BeamsOfLightEditor editor){
		this.editor = editor;
		this.className = editor.getClass().getSimpleName();
				
		fileMenu = new JMenu(_("File"));
		this.add(fileMenu);
		
		menuItemNew = new JMenuItem(_("New"));
		menuItemNew.addActionListener(this);
		fileMenu.add(menuItemNew);
		
		JSeparator separator = new JSeparator();
		fileMenu.add(separator);
		
		menuItemSave = new JMenuItem(_("Save"));
		menuItemSave.addActionListener(this);
		fileMenu.add(menuItemSave);
		
		menuItemSaveAs = new JMenuItem(_("SaveAs"));
		menuItemSaveAs.addActionListener(this);
		fileMenu.add(menuItemSaveAs);
		
		JSeparator separator2 = new JSeparator();
		fileMenu.add(separator2);
		
		menuItemLoad = new JMenuItem(_("Load"));
		menuItemLoad.addActionListener(this);
		fileMenu.add(menuItemLoad);
		
		JSeparator separator3 = new JSeparator();
		fileMenu.add(separator3);
		
		menuItemExit = new JMenuItem(_("Exit"));
		menuItemExit.addActionListener(this);
		fileMenu.add(menuItemExit);
		
		editMenu = new JMenu(_("Edit"));
		this.add(editMenu);
		
		menuItemResize = new JMenuItem(_("EditorChangeBoardSize"));
		menuItemResize.addActionListener(this);
		editMenu.add(menuItemResize);
		
		JSeparator separator4 = new JSeparator();
		editMenu.add(separator4);
		
		buttonGroupDisplay = new ButtonGroup();
		
		jrbMenuItemAllTiles = new JRadioButtonMenuItem(_("EditorDisplayAllTiles"),editor.getDisplayAllTiles());
		jrbMenuItemAllTiles.addActionListener(this);
		jrbMenuItemAllTiles.setEnabled(false);
		jrbMenuItemAllTiles.setToolTipText(_("EditorToolTipEditorTypeSwitch"));
		buttonGroupDisplay.add(jrbMenuItemAllTiles);
		editMenu.add(jrbMenuItemAllTiles);
		
		jrbMenuItemNumberTiles = new JRadioButtonMenuItem("Display Number Tiles Only",!editor.getDisplayAllTiles());
		jrbMenuItemNumberTiles.addActionListener(this);
		jrbMenuItemNumberTiles.setEnabled(false);
		jrbMenuItemNumberTiles.setToolTipText(_("EditorToolTipEditorTypeSwitch"));
		buttonGroupDisplay.add(jrbMenuItemNumberTiles);
		editMenu.add(jrbMenuItemNumberTiles);
		
		JSeparator separator5 = new JSeparator();
		editMenu.add(separator5);
		
		menuItemSolve = new JMenuItem(_("Solve"));
		menuItemSolve.addActionListener(this);
		menuItemSolve.setEnabled(false);
		menuItemSolve.setToolTipText(_("EditorToolTipSolveButton"));
		editMenu.add(menuItemSolve);
		
		menuItemReset = new JMenuItem(_("Reset"));
		menuItemReset.addActionListener(this);
		editMenu.add(menuItemReset);
		
		helpMenu = new JMenu(_("Help"));
		this.add(helpMenu);
		
		menuItemInstructions = new JMenuItem("Instructions");
		menuItemInstructions.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				try {
					new HelpFrame(_f("EditorInstructionsFile", className));
				} catch (IOException ioe) {
					// TODO 
					ioe.printStackTrace();
				}
			}
		});
		helpMenu.add(menuItemInstructions);
		
		JSeparator separator6 = new JSeparator();
		helpMenu.add(separator6);
		
		menuItemAbout = new JMenuItem("About");
		menuItemAbout.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				try {
					new HelpFrame(_("CopyrightNotice"));
				} catch (IOException ioe) {
					// TODO 
					ioe.printStackTrace();
				}
			}
		});
		helpMenu.add(menuItemAbout);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		int userSelection ;
		
		if(ae.getSource() == menuItemNew){
			userSelection = JOptionPane.showConfirmDialog(
			    				editor,
			    				_("EditorRestartWarning"),
			    				_("Restart"),
			    				JOptionPane.YES_NO_OPTION);
			if(userSelection == JOptionPane.YES_OPTION){
				editor.dispose();
				new EditorMain();
			}
		}else if(ae.getSource() == menuItemExit){
			editor.dispose();
		}else if(ae.getSource() == menuItemResize){
			JTextField 	jtfHeight = new JTextField();
			JTextField 	jtfWidth  = new JTextField();
			Object[] 	message = {	_("EditorWidthPrompt"), jtfWidth,
									_("EditorHeightPrompt"), jtfHeight
									};

			
			userSelection = JOptionPane.showConfirmDialog(	null, 
															message, 
															_("EditorDimensionsPrompt"), 
															JOptionPane.OK_CANCEL_OPTION);
			if(userSelection == JOptionPane.OK_OPTION){
				
				switch(className){
					case "LineEditor": 		new LineEditor(Integer.valueOf(jtfWidth.getText()),Integer.valueOf(jtfHeight.getText()));
											editor.dispose();
											break;
					case "NumberEditor": 	new NumberEditor(Integer.valueOf(jtfWidth.getText()),Integer.valueOf(jtfHeight.getText()));
											editor.dispose();
											break;
					default: //TODO 
				}
			}
		}else if(ae.getSource() == jrbMenuItemAllTiles){
			editor.setDisplayAllTiles(true);
		}else if(ae.getSource() == jrbMenuItemNumberTiles){
			editor.setDisplayAllTiles(false);
		}else if(ae.getSource() == menuItemSolve){
			editor.getSolveButton().doClick();
		}else if(ae.getSource() == menuItemReset){
			editor.getResetButton().doClick();
		}else
			
		try {
			controller = new LightController();
			board = (BeamsOfLightPuzzleBoard) editor.convertToBoard();
			controller.setBoard(board);
				
			if(ae.getSource() == menuItemSave){
				fileChooser = new JFileChooser();
				filter = new ExtensionFileFilter(_p("SaveGameType",2), new String[] { "BOL" });
				fileChooser.setFileFilter(filter);
				fileChooser.setDialogTitle(_("Save"));   
			 
				userSelection = fileChooser.showSaveDialog(editor);
				 
				if(userSelection == JFileChooser.APPROVE_OPTION){
					file = fileChooser.getSelectedFile();
			    	controller.saveGame(file);
				}
			}else if(ae.getSource() == menuItemSaveAs){
				fileChooser = new JFileChooser();
				filter = new ExtensionFileFilter(_p("SaveGameType",2), new String[] { "BOL" });
				fileChooser.setFileFilter(filter);
				fileChooser.setDialogTitle(_("SaveAs"));   
				 
				userSelection = fileChooser.showSaveDialog(editor);
				
				if(userSelection == JFileChooser.APPROVE_OPTION){
					file = fileChooser.getSelectedFile();
			    	controller.saveGame(file);
				}
			}else if(ae.getSource() == menuItemLoad){
				fileChooser = new JFileChooser();
				filter = new ExtensionFileFilter(_p("SaveGameType",2), new String[] { "BOL" });
				fileChooser.setFileFilter(filter);
				fileChooser.setDialogTitle(_("Load"));   
			
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
	
	public JMenuItem getMenuItemSolve(){
		return menuItemSolve;
	}
	
	class ExtensionFileFilter extends FileFilter {
		  String description;

		  String extensions[];

		  public ExtensionFileFilter(String description, String extension) {
		    this(description, new String[] { extension });
		  }

		  public ExtensionFileFilter(String description, String extensions[]) {
		    if (description == null) {
		      this.description = extensions[0];
		    } else {
		      this.description = description;
		    }
		    this.extensions = extensions.clone();
		    toLower(this.extensions);
		  }

		  @Override
		public boolean accept(File file) {
		    if (file.isDirectory()) {
		      return true;
		    } else {
		      String path = file.getAbsolutePath().toLowerCase();
		      for (int i = 0, n = extensions.length; i < n; i++) {
		        String extension = extensions[i];
		        if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
		          return true;
		        }
		      }
		    }
		    return false;
		  }

		  @Override
		public String getDescription() {
		    return description;
		  }

		  private void toLower(String array[]) {
		    for (int i = 0, n = array.length; i < n; i++) {
		      array[i] = array[i].toLowerCase();
		    }
		  }
		}
}
