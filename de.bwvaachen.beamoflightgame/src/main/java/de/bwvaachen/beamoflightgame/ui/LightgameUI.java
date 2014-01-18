package de.bwvaachen.beamoflightgame.ui;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import de.bwvaachen.beamoflightgame.controller.CreateRandomBoard;
import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.controller.SolverBuilder;
import de.bwvaachen.beamoflightgame.controller.impl.LightController;
import de.bwvaachen.beamoflightgame.editor.EditorMain;
import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.ITileVisitor;
import de.bwvaachen.beamoflightgame.helper.TraverseDirection;
import de.bwvaachen.beamoflightgame.logic.ISolver;
import de.bwvaachen.beamoflightgame.logic.strategies.IntersectionStrategy;
import de.bwvaachen.beamoflightgame.logic.strategies.LonelyFieldStrategy;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

public class LightgameUI extends JFrame {
	
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
	
	/**
	 * Actionklasse für den Klick auf ein LightTIle
	 * 
	 * @author gbraun
	 *
	 */
	class LightTileListener implements ActionListener {

		/**
		 * Strahlen setzen und entfernen.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				// Den auslï¿½senden Button holen
				TileButton btn = (TileButton) e.getSource();
				// Den Traverser initialisieren.
				BoardTraverser traverser = new BoardTraverser ( btn.getTile() ) ;
							
				// Lichtstrahl ziehen
				if ( ( activeNumberTile != null ) && ( btn . markiert ) )  {
					
					// *** Setzten von Lichtstrahlen ***
					
					int lightTileX = btn . getCol() ;
					int lightTileY = btn . getRow () ;
					int numberTileX = activeNumberTile . getX() ;
					int numberTileY = activeNumberTile . getY() ;
					
					// Ermitteln in welche Richtung gezogen wurde
					LightTileState lichtRichtung ;
										
					if ( lightTileY == numberTileY ) {
						if ( lightTileX < numberTileX ) {
							lichtRichtung = LightTileState.WEST ;
						} 
						else
						{
							lichtRichtung = LightTileState.EAST ;
						}
							
					} 
					else
					{
						if ( lightTileY < numberTileY ) {
							lichtRichtung = LightTileState.NORTH ;
						}
						else {
							lichtRichtung = LightTileState.SOUTH ;
						}
							 
					} // if ( lightTileY == numberTileY ) 
										
					// Den Traverser auf den Button Zielbutton (LightTile) setzen.
					traverser . moveTo ( lightTileX , lightTileY ) ;
					TraverseDirection traverseDirection = lichtRichtung . reverse() . getTraverseDirection() ;
					
					boolean alleGezeichnet = false ;
					boolean firstTile = true;
					do  {
						if ( ( numberTileX == traverser . getX() ) && ( numberTileY == traverser . getY() ) ) {
							break;
						}
						LightTile currentTile = (LightTile) traverser.get() ; 
						currentTile . setState( lichtRichtung, firstTile) ;
						firstTile = false;
						traverser . shift ( traverseDirection ) ;
					} while(true);
					
					activeNumberTile = null ;					
					
					// Entfernen aller Markierungen, da der Zug gemacht wurde.
					for ( TileButton aktButton : buttons ) {
						aktButton . markiert = false ;						
					} // for ( TileButton aktButton : buttons ) 					
					
				} // if ( ( activeNumberTile != null ) && ( btn . markiert ) )
				else {
					
					// *** Entfernen von Lichstrahlen ***				
					
					// Das ausgewählte Tile holen
					LightTile ausgangsTile 	     = (LightTile) btn . getTile() ;
					// Ermitteln der Richtung
					LightTileState lichtRichtung = (LightTileState) ausgangsTile . getTileState() ;
					
					if ( lichtRichtung != LightTileState . EMPTY ) {
						
						// Traverser setzen
						traverser . moveTo ( ausgangsTile . getX() , ausgangsTile . getY() ) ;
						// Ermitteln der Traverse-Richtung
						TraverseDirection traverseDirection = lichtRichtung . getTraverseDirection() ; 
						
						boolean significant = true ;
						// Vom Ausgangs-Tile bis zum "Strahlende" die TileStates auf EMPTY setzen.
						LightTile currentTile = ausgangsTile ;						
						while ( currentTile . getTileState() . getTraverseDirection() == traverseDirection ) {
							currentTile . setState ( LightTileState . EMPTY , significant ) ;
							significant = false ;
							traverser . shift ( traverseDirection ) ;
							currentTile = (LightTile) traverser . get() ;
						} // while ( currentTile . getTileState() . getTraverseDirection() == traverseDirection ) 
						
					} // if ( lichtRichtung != LightTileState . EMPTY ) 
					
				} // // if ( ( activeNumberTile != null ) && ( btn . markiert ) ) .. else				
						
				// Das Board neu zeichnen.
				IBeamsOfLightPuzzleBoard currentBoard = controller.getCurrentModel() ;
				Update( currentBoard ) ;
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // try .. catch	
		
		} // public void actionPerformed(ActionEvent e) 
		
	} // class LightTileListener implements ActionListener
	
	
	
	/**
	 * Action fï¿½r den Klick auf ein NumberTile
	 * 
	 * @author gbraun, pauls_and
	 */
	class NumberTileButtonListener implements ActionListener
	{

		/**
		 * ï¿½ndert die Hintergrundfarbe.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
		
			try {	
				
				activeNumberTile = null ;
				for ( TileButton aktButton : buttons ) {
					aktButton . markiert = false ;				
				} // for ( TileButton aktButton : buttons ) 
				
				// Den auslï¿½senden Button holen
				TileButton btn = (TileButton) e.getSource();

				// Prï¿½fen ob es sich um ein NumberTile handelt.
				if ( btn . getTile() instanceof NumberTile ) {
				
					// Variable deklarieren, die bei der Anzeige der mï¿½glichen Felder hilft.
					boolean CurrentTileIsLightTile ;
	
					// Die verbleibende Strahlstärke holen										
					NumberTile currentTile = (NumberTile) btn . getTile () ;
					int strahlStaerke = currentTile . getRemainingLightRange() ;
					
					// Den Traverser initialisieren.
					BoardTraverser traverser = new BoardTraverser ( btn.getTile() ) ;
					// Das Modell vom Controller holen.
					IBeamsOfLightPuzzleBoard currentModel = controller . getCurrentModel() ;
					
					
					// Schleife ï¿½ber alle "Himmelsrichtungen" (West, Ost, Sï¿½d, Nord). Dafï¿½r nehm ich den Aufzï¿½hlungstyp LightTileState.
					for ( LightTileState aktState : LightTileState . values() ) {
						
						// Zu dem LightTileState zï¿½hlt auch das Element "Empty", welches ich aber nicht fï¿½r die "Zug-ï¿½berprï¿½fung" brauche.
						if ( aktState != LightTileState . EMPTY ) {
							
	
							// Den Traverser auf den Button Ausgangsbutton (NumberTile) setzen.
							traverser . moveTo ( btn . getCol() , btn . getRow() ) ;
							TraverseDirection traverseDirection = aktState . getTraverseDirection() ;
													
							// Initial ist diese Aussage falsch, da man den Traverser auf das Ausgangs-Numbertile setzt. Ich setz das trotzdem auf true, 
							// da man sich so ein paar Abfragen spart. Der Status wird direkt als erstes in der Schleife geupdatet und ist ab dort "richtig".
							CurrentTileIsLightTile = true ;
							
							int verbrauchteStaerke = 0 ;
							boolean conflictWithBeam = false ;
							
							// Wandern in die aktuelle Himmelsrichtung unter folgenden Bedinungen:
							// 1. Es ist noch mï¿½glich weiter in die Richtung zu gehen
							// 2. Es handelt sich um ein LightTile Feld
							// 3. Die Anzahl der Felder (aus dem NumberTile) wird nicht ï¿½berschritten.
							while ( ( traverser . shift ( traverseDirection ) ) && ( CurrentTileIsLightTile ) && ( verbrauchteStaerke < strahlStaerke ) && ( conflictWithBeam == false ) ) {							
								
								conflictWithBeam = false ;
								
								// Prï¿½fen auf was fï¿½r einem Feld der Traverser aktuell steht.
								CurrentTileIsLightTile = ( currentModel . getTileAt( traverser . getX(), traverser . getY() ) instanceof LightTile );
								
								if ( CurrentTileIsLightTile ) {
									// Den Button holen, der das aktuelle Tile reprï¿½sentiert.
									int buttonArrayPos = ( ( traverser . getY() * currentModel . getWidth() ) + traverser . getX() )  ;
									TileButton aktButton = buttons . get ( buttonArrayPos ) ;

									LightTile aktTile = (LightTile) traverser . get() ;
									
									
									if ( aktTile . getTileState() == LightTileState.EMPTY ) {
										// Einfï¿½rben des Buttons
										aktButton . markiert = true ;
										// Die "verbrauchte Stï¿½rke" erhï¿½hen.
										verbrauchteStaerke += 1 ;
										// Es wurde mindestens ein mögliches Feld markiert. Deshalb wird dieses NumberTile gespeichert, damit man später beim Klick auf ein markiertes LightTile weiß
										// von wo der Strahl kommt.
										activeNumberTile = btn . getTile() ;
									}
									else
									{
										if ( aktTile . getTileState () != aktState ) {
											conflictWithBeam = true ;
										}
									}
								} // if ( CurrentTileIsLightTile ) 
												
							} // while ( .. ) 
							
						} // if ( aktState != LightTileState . EMPTY )
							
					} // for ( LightTileState aktState : LightTileState . values() )
					
				} // if ( btn . getTile() instanceof NumberTile ) {
				
				Update ( controller.getCurrentModel() ) ;
				
			} catch (Exception e2) {
				
			} // try .. catch
			
		} // public void actionPerformed(ActionEvent e)
		
	} // class TileButtonListener 

	private static final long serialVersionUID = -3961364231837270604L;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					LightgameUI frame = new LightgameUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} // try .. catch
			} // public void run()
		}); // EventQueue.invokeLater ( .. )
		
	} // public static void main(String[] args) 

	private JPanel contentPane;

	
	//private File lastSaveFile=null;
	private ILightController controller = new LightController() ;
	
	
	private ArrayList<TileButton> buttons = new ArrayList<TileButton>();
	

	private ITile activeNumberTile = null ;
	
	/**
	 *  Constructor
	 *  Initialisieren des Fensters
	 *  @author gbraun , pauls_and	 *  
	 */
	public LightgameUI()
	{
		 this(new PrototypModelForLonelyFieldStrategy());
	}
	
	public LightgameUI(IBeamsOfLightPuzzleBoard b) {
		
		try {	

			// Setzen der initialen Fensterposition und Grï¿½ï¿½e.
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//setBounds(100, 100, 450, 300);
			// Verändern der Fenstergröße verhindern
			setResizable( false ) ;
					
			// Erzeugen des Menus
			buildMenu();
			
			// Layout setzen
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);
			
			// Progressbar
			JProgressBar progressBar = new JProgressBar();
			contentPane.add(progressBar, BorderLayout.SOUTH);
			
			//  Spieleraster 
			JPanel rasterPanel = new JPanel();
			contentPane.add(rasterPanel, BorderLayout.CENTER);
	
			// Controller mit Test Prototyp fï¿½r GUI fï¿½llen.
			controller . setBoard ( b ) ;
			
			// Vom Controller die Musterlösung generieren lassen.
			controller . solve() ;
			
			
			
			javax.swing.JButton solverButton = new javax.swing.JButton("Puzzle loesen");
			solverButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					//Why the f*ck has the current model changed in this place ???
					try {
						ISolver s =
						SolverBuilder.buildWith(LonelyFieldStrategy.class).
							          and(IntersectionStrategy.class).
							          /*and(TryAndErrorStrategy.class).*/
							          forBoard(controller.getCurrentModel());
					    s.solve();
					
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			});
						
			contentPane.add(solverButton,BorderLayout.NORTH);
			
			//TODO refactor this stuff
			JPanel pnUndoRedo = new JPanel();
			pnUndoRedo.setLayout(new GridLayout(1,2));
			UndoButton btUndo = new UndoButton(controller.getUndoManager());
			pnUndoRedo.add(btUndo);
			RedoButton btRedo = new RedoButton(controller.getUndoManager());
			pnUndoRedo.add(btRedo);
			getContentPane().add(pnUndoRedo, BorderLayout.SOUTH);
			//TODO end
			
			// Das Spielfeld vom Controller holen:
			
			
			// TODO temporï¿½r feste Werte fï¿½r Tests eingetragen.
			int rows = b.getHeight() ;
			int cols = b.getWidth() ;
			
			//IBeamsOfLightPuzzleBoard currentModel = controller . getCurrentModel();
			rasterPanel . setLayout ( new GridLayout ( rows , cols , 0 , 0 ) ) ;
			
			// Fenstergröße anpassen.
			setBounds( 100 , 100 , ( cols * 127 ) + 30 , ( rows * 127 ) + 124 ) ;
			// Fenster in der Bildmitte anzeigen
			setLocationRelativeTo( null ) ;
			
			// Schleife ï¿½ber das "Spielfeld"
			for ( int row=0 ; row<rows ; row++ ) {
				for ( int col=0 ;col<cols ; col++ ) {
	
					// Neuen Button erzeugen
					final TileButton newTileButton = new TileButton ( b . getTileAt ( col , row ) ) ;
					newTileButton . setPreferredSize( new Dimension (128 , 128 ) ) ;
					// Action hinzufï¿½gen
					
					b . getTileAt ( col , row ) . accept( new ITileVisitor() {

						@Override
						public void visitLightTile(LightTile t) {
							newTileButton . addActionListener ( new LightTileListener() ) ;
							
						}

						@Override
						public void visitNumberTile(NumberTile t) {
							newTileButton . addActionListener ( new NumberTileButtonListener() ) ;
							
						}
						
					} );
					
					// Button auf das Panel setzen
					rasterPanel . add ( newTileButton ) ;
					
					// Aktuelle Button dem Button-Array hinzufï¿½gen.
					buttons . add (newTileButton);
					
				} // for ( int col=0 ;col<cols ; col++ )
			} // for ( int row=0 ; row<rows ; row++ )
			
			
			Update( b ) ;
			
		} catch (Exception e) {
			// TODO: handle exception
		} // try .. catch
		
	} // public LightgameUI()
	
	
	/**
	 * Add Icon to Button
	 * @author pauls_and
	 * @param btn 
	 * @return Button with (new) Icon
	 */
	private TileButton addIcon(TileButton btn, Icon ico)
	{
		btn.setIcon(ico) ;
		return btn;
		
	} // private TileButton addIcon(TileButton btn, Icon ico)
	
	
	
	
	/**
	 * Builds the Menubar
	 */
	private void buildMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		mntmNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//JOptionPane.showMessageDialog(null,"Dieses Feature ist nur in der Vollversion verfügbar","Fehler", JOptionPane.ERROR_MESSAGE);      
				IBeamsOfLightPuzzleBoard board = new BeamsOfLightPuzzleBoard();
				boolean boardOk = false;
				while(!boardOk)
				{
					boardOk = true;
					board.init(5, 5);
					CreateRandomBoard.createRandom(board);
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
				LightgameUI frame = new LightgameUI(board);
				frame.setVisible(true);
			
			}
		});
		
		JSeparator separator_2 = new JSeparator();
		mnFile.add(separator_2);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mnFile.add(mntmSaveAs);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mnFile.add(mntmLoad);
		
		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);
		
		
		
		
		JMenuItem mntmCheckGame = new JMenuItem ( "Check Game" ) ;
		mnGame . add ( mntmCheckGame ) . addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String message = new String() ;
				if ( controller . GameIsCorrect() ) {
					message = "Nice turn! It's correct." ;					
				} 
				else {
					message = "Wrong track dude ;)" ;
				}
				JOptionPane . showMessageDialog( null , message , "" ,  JOptionPane . PLAIN_MESSAGE ) ;
				
			} // public void actionPerformed(ActionEvent arg0)
			
		}) ; // .. new ActionListener() 
		
		
		mnGame.add ( new JSeparator() );
		
		JMenuItem mntmbacktoFailure = new JMenuItem("Back to failure");
		mnGame.add(mntmbacktoFailure);
		
		mntmbacktoFailure . addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getUndoManager().undoToLastStableState();
			}
		});
		
		JSeparator separator_3 = new JSeparator();
		mnGame.add(separator_3);
		
		JMenuItem mntmsetMarker = new JMenuItem("Set mark");
		mnGame.add(mntmsetMarker);
		
		mntmsetMarker . addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getUndoManager().addMarker();
				
			}
		});
		
		// Delete a mark
		JSeparator separator_4 = new JSeparator();
		mnGame.add(separator_4);
		
		JMenuItem mntmDeleteMarker = new JMenuItem("Delete mark");
		mnGame.add(mntmDeleteMarker);
		mntmDeleteMarker . addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getUndoManager().deleteLastMarker();
				
			}
		});
		
		// Delete all marks
		mnGame . add ( new JSeparator() ) ;
		JMenuItem mntmDeleteAllMarker = new JMenuItem( "Delete all marks" ) ;
		mnGame . add ( mntmDeleteAllMarker )  ;
		mntmDeleteAllMarker . addActionListener( new ActionListener () {
			
			@Override
			public void actionPerformed ( ActionEvent e ) {
				
				controller . getUndoManager() . deleteAllMarker() ;
				
			} // public void actionPerformed ( ActionEvent e ) 
		}) ; // mntmDeleteAllMarker
		
		
		// Go back to a mark
		JSeparator separator_5 = new JSeparator();
		mnGame.add(separator_5);
		
		JMenuItem mntmBackToMark = new JMenuItem("Back to mark");
		mntmBackToMark . addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getUndoManager().undoToLastMarker();
				
			}
		});
				
		
		mnGame.add(mntmBackToMark);
		
		JMenu mnEditor = new JMenu("Editor");
		menuBar.add(mnEditor);
		
		JMenuItem mntmStartEditor = new JMenuItem("Start Editor");
		mntmStartEditor.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				SwingUtilities.invokeLater(new Runnable(){
					public void run(){
					new EditorMain();
					}});
			}
		});
		
		mnEditor.add(mntmStartEditor);
		
		/*
		//TODO: J(Toggle)Button instead of JMenuItem
		JMenuItem mntmUndo = new JMenuItem("Undo");
		mntmUndo . addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: Fix the interfaces
				((LightController) controller).undo();
			}
			
			//TODO getPresentationName
		});
		
		mnGame.add(mntmUndo);
		

		//TODO: J(Toggle)Button instead of JMenuItem
		JMenuItem mntmRedo = new JMenuItem("Undo");
		mntmUndo . addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: Fix the interfaces
				((LightController) controller).redo();
			}
			
			//TODO getPresentationName
		}); */
		
		/**
		 * @autor Bastian/Andreas
		 */
		mntmLoad.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//Dialog zum ermitteln des Dateipfades einbinden
				
				JFileChooser fileChooser=new JFileChooser();
				FileFilter filter = new ExtensionFileFilter("Beam of light puzzle saves", new String[] { "BOL" });
				fileChooser.setFileFilter(filter);
				if(fileChooser.showOpenDialog(LightgameUI.this)!=JFileChooser.CANCEL_OPTION){
					File selectedFile = fileChooser.getSelectedFile();
					if(selectedFile!=null&& selectedFile.exists()){
							//oeffnen
					}
					
				}
				try {
					//im Design ist das hier nicht void sondern gibt ein Puzzle zurueck, das sollten wir dann aufbauen
					//Update(controller.loadGame(new File("")));
					//TODO obrigen Code einbinden sobald der Controller implementiert ist
					controller.loadGame(new File(""));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		mntmSaveAs.addActionListener(new ActionListener() {
				@Override
			public void actionPerformed(ActionEvent e) {
				//Dialog zum Speicherns
				JFrame parentFrame = new JFrame();
				 
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Save As");   
				 
				int userSelection = fileChooser.showSaveDialog(parentFrame);
				 
				if (userSelection == JFileChooser.APPROVE_OPTION) {
				    File fileToSave = fileChooser.getSelectedFile();
				    try {
						controller.saveGame(fileToSave);
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
				}
			
			}
		});
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
	} // private void buildMenu()
	
	
	
	/**
	 * Paints the GUI (Buttons) based on a Model Object
	 * @author pauls , gbraun
	 * @param model
	 */
	private void Update(IBeamsOfLightPuzzleBoard model)
	{
		// Erzeugen der Factory
		GraficFactory gf = new GraficFactory(model);
		// Über alle Buttons iterieren
		for(TileButton btn : buttons)
		{
			// Icon hinzufügen
			addIcon( btn , gf . getImage ( btn . getTile() ) ) ;
			
			// Wenn ein Numbertile ausgewählt ist, werden mögliche Züge durch einen anderen Rand angezeigt (der Buttonst ist also markiert)
			if ( btn . markiert ) {
				btn . setBorder( BorderFactory.createLineBorder ( Color.red ) ) ; 
			}
			else {
				btn . setBorder( BorderFactory.createLineBorder ( Color.black ) ) ; 
			} // if ( btn . markiert ) 
				
		} // for(TileButton btn : buttons)
	} // private void Update(IBeamsOfLightPuzzleBoard model)
}
