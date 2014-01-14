package de.bwvaachen.beamoflightgame.editor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;


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

	public EditorMenu(){
		fileMenu = new JMenu("File");
		this.add(fileMenu);
		
		menuItemNew = new JMenuItem("New");
		menuItemNew.addActionListener(this);
		fileMenu.add(menuItemNew);
		
		JSeparator separator = new JSeparator();
		fileMenu.add(separator);
		
		menuItemSave = new JMenuItem("Save");
		menuItemSave.addActionListener(this);
		fileMenu.add(menuItemSave);
		
		menuItemSaveAs = new JMenuItem("Save As");
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
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == menuItemNew){
			
		}
		if(e.getSource() == menuItemSave){
			
		}
		if(e.getSource() == menuItemSaveAs){
	
		}
		if(e.getSource() == menuItemLoad){
	
		}
		if(e.getSource() == menuItemExit){
			System.exit(0);
		}
		if(e.getSource() == menuItemIns){
			
		}
		if(e.getSource() == menuItemAbout){
			
		}
	}
}
