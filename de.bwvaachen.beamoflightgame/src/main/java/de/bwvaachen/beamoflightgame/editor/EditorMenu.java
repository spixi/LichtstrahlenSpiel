package de.bwvaachen.beamoflightgame.editor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.controller.impl.LightController;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;


@SuppressWarnings("serial")
public class EditorMenu extends JMenuBar 
	implements ActionListener{
	
	private JMenu 		fileMenu ;
	private JMenuItem 	menuItemNew ;
	private JMenuItem	menuItemSave ;
	private JMenuItem	menuItemSaveAs ;
	private JMenuItem	menuItemLoad ;
	private JMenuItem	menuItemExit ;
	private JMenu		helpMenu ;
	private JMenuItem	menuItemIns ;
	private JMenuItem	menuItemAbout ;
	
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
		
		helpMenu = new JMenu("Help");
		this.add(helpMenu);
		
		menuItemIns = new JMenuItem("Instructions");
		menuItemIns.addActionListener(this);
		helpMenu.add(menuItemIns);
		
		JSeparator separator4 = new JSeparator();
		helpMenu.add(separator4);
		
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
			    				"Soll der Editor wirklich neu gestartet werden?\nBisher gemachte Eingaben werden verworfen.",
			    				"Neustart",
			    				JOptionPane.YES_NO_OPTION);
			if(userSelection == JOptionPane.YES_OPTION){
				editor.dispose();
				new EditorMain();
			}
		}else if(ae.getSource() == menuItemExit){
			System.exit(0);
		}else
		
		try {
			controller = new LightController();
			board = editor.convertToBoard();
			System.out.println(board.toString());
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
			 
			}else if(ae.getSource() == menuItemIns){
				
			}else if(ae.getSource() == menuItemAbout){
			
			}
		}catch(IOException ioe){
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
