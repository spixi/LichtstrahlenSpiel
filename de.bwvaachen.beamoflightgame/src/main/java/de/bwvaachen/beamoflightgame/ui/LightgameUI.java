package de.bwvaachen.beamoflightgame.ui;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

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

import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.controller.impl.LightController;
import de.bwvaachen.beamoflightgame.editor.EditorMain;
import de.bwvaachen.beamoflightgame.helper.BoardChangeListener;
import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.ITileVisitor;
import de.bwvaachen.beamoflightgame.helper.TraverseDirection;
import static de.bwvaachen.beamoflightgame.i18n.I18N.*;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;

public class LightgameUI extends JFrame implements BoardChangeListener {
	
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
					
					// Ermitteln was die letzte Turn-Nummer ist.
					int currentTurnNumber = controller . getCurrentModel() . getCurrentTurnNumber() ;
					
					// Falls es noch Turns gibt die eine höhere Turn-Nummer haben, müssen diese gelöscht werden (z.B. wenn Züge rückgängig gemacht werden und dann ein Strahl gezogen wird.. dann gibt es immer noch die alten
					// Turns in der Liste).
					controller . getUndoManager() . deleteTurns( currentTurnNumber ) ;
					
					// Diese Zug-Nummer für diesen neuen Zug erhöhen
					controller . getCurrentModel() . setCurrentTurnNumber( currentTurnNumber + 1 ) ;
					
					do  {
						if ( ( numberTileX == traverser . getX() ) && ( numberTileY == traverser . getY() ) ) {
							break;
						}
						LightTile currentTile = (LightTile) traverser.get() ; 
						currentTile . setState( lichtRichtung, true) ;						
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
						
						
						// Ermitteln was die letzte Zug-Nummer ist.
						int currentTurnNumber = controller . getCurrentModel() . getCurrentTurnNumber() ;

						// Falls es noch Turns gibt die eine höhere Turn-Nummer haben, müssen diese gelöscht werden (z.B. wenn Züge rückgängig gemacht werden und dann ein Strahl gezogen wird.. dann gibt es immer noch die alten
						// Turns in der Liste).
						controller . getUndoManager() . deleteTurns( currentTurnNumber ) ;
						
						// Diese Zug-Nummer für diesen neuen Zug erhöhen
						controller . getCurrentModel() . setCurrentTurnNumber( currentTurnNumber + 1 ) ;
						
						while ( currentTile . getTileState() . getTraverseDirection() == traverseDirection ) {
							currentTile . setState ( LightTileState . EMPTY , true ) ;
							significant = false ;
							traverser . shift ( traverseDirection ) ;
							currentTile = (LightTile) traverser . get() ;
						} // while ( currentTile . getTileState() . getTraverseDirection() == traverseDirection ) 
						
					} // if ( lichtRichtung != LightTileState . EMPTY ) 
					
				} // // if ( ( activeNumberTile != null ) && ( btn . markiert ) ) .. else				
						
				// Das Board neu zeichnen.
				updateButtonGraphics();
				
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
				
				updateButtonGraphics();
				
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
					ILightController c = new LightController();
					c.setBoard(new PrototypModelJanko());
					LightgameUI frame = new LightgameUI(c);
					c.addBoardChangeListener(frame);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				} // try .. catch
			} // public void run()
		}); // EventQueue.invokeLater ( .. )
		
	} // public static void main(String[] args) 

	private JPanel contentPane;

	
	//private File lastSaveFile=null;
	private ILightController controller;
	
	
	private ArrayList<TileButton> buttons;
	

	private ITile activeNumberTile;
	private JPanel rasterPanel;
	private JPanel pnUndoRedo;
	
	/**
	 *  init
	 *  Initialisieren von bestimmten Variablen
	 *  @author spix_mar
	 */
	private void initialize() {
		buttons = new ArrayList<TileButton>();
        activeNumberTile = null ;
	}
	
	/**
	 *  Constructor
	 *  Initialisieren des Fensters
	 *  @author gbraun , pauls_and	 *  
	 */
	public LightgameUI(ILightController cntrl) {
		controller = cntrl;
		
		
			// Setzen der initialen Fensterposition und Grï¿½ï¿½e.
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			//setBounds(100, 100, 450, 300);
			// Verändern der Fenstergröße verhindern
			setResizable( false ) ;
			
	        // Fenster in der Bildmitte anzeigen
	        setLocationRelativeTo( null ) ;
			
			//Initialisierung
			initialize();
					
			// Erzeugen des Menus
			buildMenu();
			
			// Layout setzen
			contentPane = new JPanel();
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPane.setLayout(new BorderLayout(0, 0));
			setContentPane(contentPane);
			
			// Progressbar ??? Wofür brauchen wir die?
			JProgressBar progressBar = new JProgressBar();
			contentPane.add(progressBar, BorderLayout.SOUTH);
			
			//SolverButton
			javax.swing.JButton solverButton = new javax.swing.JButton(_("Solve"));
			solverButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						controller.swapModelWithSolution();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			});
			contentPane.add(solverButton,BorderLayout.NORTH);
			
			buildRaster();
		
	} // public LightgameUI()
	
	public void boardHasChanged() {
		initialize();
		buildRaster();
		updateButtonGraphics();
	}
	
	/**
	 *  init
	 *  Initialisieren von bestimmten Variablen
	 *  @author gbraun, and_pauls, Refactoring: spix_mar
	 */
	private void buildRaster() {
		if(rasterPanel != null)
			this.remove(rasterPanel);
		if(pnUndoRedo != null) {
			this.remove(pnUndoRedo);
		}
		
		
		//  Spieleraster 
		rasterPanel = new JPanel();
		contentPane.add(rasterPanel, BorderLayout.CENTER);

		IBeamsOfLightPuzzleBoard b = controller.getCurrentModel();
		
		//TODO refactor this stuff
		pnUndoRedo = new JPanel();
		pnUndoRedo.setLayout(new GridLayout(1,2));
		UndoButton btUndo = new UndoButton(controller.getUndoManager());
		pnUndoRedo.add(btUndo);
		RedoButton btRedo = new RedoButton(controller.getUndoManager());
		pnUndoRedo.add(btRedo);
		getContentPane().add(pnUndoRedo, BorderLayout.SOUTH);
		//TODO end
		
		// Das Spielfeld vom Controller holen:
		
        // TODO temporaer feste Werte fuer Tests eingetragen.
        int rows = b.getHeight() ;
        int cols = b.getWidth() ;
        
        //IBeamsOfLightPuzzleBoard currentModel = controller . getCurrentModel();
        rasterPanel . setLayout ( new GridLayout ( rows , cols , 0 , 0 ) ) ;
        
        // Fenstergröße anpassen.
        setBounds( 100 , 100 , ( cols * 127 ) + 30 , ( rows * 127 ) + 124 ) ;
        
        // Schleife ueber das "Spielfeld"
        for ( int row=0 ; row<rows ; row++ ) {
                for ( int col=0 ;col<cols ; col++ ) {

                        // Neuen Button erzeugen
                        final TileButton newTileButton = new TileButton ( b . getTileAt ( col , row ) ) ;
                        newTileButton . setPreferredSize( new Dimension (128 , 128 ) ) ;
                        // Action hinzufuegen
                        
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
        
        
        updateButtonGraphics();

	}
	
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

		
		// -----------------------------------------------
		// Erzeugen des File Menus
		
		JMenu mnFile = new JMenu(_("File"));
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem(_("New"));
		mnFile.add(mntmNew);
		
		mntmNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				NewGamePropertyDialog newGameDialog = new NewGamePropertyDialog(controller);
				newGameDialog.setVisible(true);
			}
		});
		
		// Trennlinie - File
		mnFile.add( new JSeparator() );
		
		JMenuItem mntmSave = new JMenuItem(_("Save"));
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem(_("SaveAs"));
		mnFile.add(mntmSaveAs);
		
		// Trennlinie - File
		mnFile.add( new JSeparator() );
		
		JMenuItem mntmLoad = new JMenuItem(_("Load"));
		mnFile.add(mntmLoad);
		
		
		
		// -----------------------------------------------
		// Erzeugen des Game Menus
		
		JMenu mnGame = new JMenu(_("Game"));
		menuBar.add(mnGame);
		
		
		// Prüfen des Spiels
		JMenuItem mntmCheckGame = new JMenuItem ( _("Check") ) ;
		mnGame . add ( mntmCheckGame ) . addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String message = new String() ;
				if ( controller . GameIsCorrect() ) {
					message = _("GameIsCorrect") ;					
				} 
				else {
					message = _("GameIsNotCorrect") ;
				}
				JOptionPane . showMessageDialog( null , message , "" ,  JOptionPane . PLAIN_MESSAGE ) ;
				
			} // public void actionPerformed(ActionEvent arg0)
			
		}) ; // .. new ActionListener() 
		
		
		// Trennlinie - Game
		
		mnGame.add( new JSeparator() );
		
		// Prüfen des Spiels
				JMenuItem mntmShowTurns = new JMenuItem (_("ShowTurns")) ;
				mnGame . add ( mntmShowTurns ) . addActionListener( new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						controller . getUndoManager() . getTurnsInfo() ;
						
					} // public void actionPerformed(ActionEvent arg0)
					
				}) ; // .. new ActionListener() 
				
				
		// Trennlinie - Game
		mnGame.add( new JSeparator() );

				
		// Zurück zum Fehler
		JMenuItem mntmbacktoFailure = new JMenuItem(_("BackToFailure"));
		mnGame.add(mntmbacktoFailure);
		
		mntmbacktoFailure . addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getUndoManager().undoToLastStableState();
			}
		});
		
		// Trennlinie - Game
		mnGame.add( new JSeparator() ); 
		
		
		// Markierung setzen
		JMenuItem mntmsetMarker = new JMenuItem(_("SetMarker"));
		mnGame.add(mntmsetMarker);
		
		mntmsetMarker . addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getUndoManager().addMarker();
				
			}
		});
		
		
		// Markierung löschen
		JSeparator separator_4 = new JSeparator();
		mnGame.add(separator_4);
		
		JMenuItem mntmDeleteMarker = new JMenuItem(_p("DeleteMarker",1));
		mnGame.add(mntmDeleteMarker);
		mntmDeleteMarker . addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getUndoManager().deleteMarker();
				
			}
		});
		
		
		// Alle Markierungen löschen
		mnGame . add ( new JSeparator() ) ;
		JMenuItem mntmDeleteAllMarker = new JMenuItem(_p("DeleteMarker",2)) ;
		mnGame . add ( mntmDeleteAllMarker )  ;
		mntmDeleteAllMarker . addActionListener( new ActionListener () {
			
			@Override
			public void actionPerformed ( ActionEvent e ) {
				
				controller . getUndoManager() . deleteAllMarker() ;
				
			} // public void actionPerformed ( ActionEvent e ) 
		}) ; // mntmDeleteAllMarker
		
		
		// Zurück zur vorherigen Markierung
		mnGame.add(new JSeparator());
		
		JMenuItem mntmGoToLastMark = new JMenuItem(_("GoToLastMarker")); 
		mntmGoToLastMark . addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getUndoManager().goToLastMark();
				
				
			}
		});
				
		mnGame.add(mntmGoToLastMark);

		
		// Zur nächsten Markierung
		mnGame.add(new JSeparator());
		
		JMenuItem mntmGoToNextMark = new JMenuItem(_("GoToNextMarker"));
		mntmGoToNextMark . addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getUndoManager().goToNextMark();				
			}
		});
		mnGame.add(mntmGoToNextMark);
	
		// -----------------------------------------------
		// Erzeugen des Editor Menus
		
		JMenu mnEditor = new JMenu(_("Editor"));
		menuBar.add(mnEditor);
		
		JMenuItem mntmStartEditor = new JMenuItem(_("UIStartEditor"));
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
				FileFilter filter = new ExtensionFileFilter(_p("SaveGameType",2), new String[] { "BOL" });
				fileChooser.setFileFilter(filter);
				if(fileChooser.showOpenDialog(LightgameUI.this)!=JFileChooser.CANCEL_OPTION){
					File selectedFile = fileChooser.getSelectedFile();
					if(selectedFile!=null&& selectedFile.exists()){
						try {
					controller.loadGame(selectedFile);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
					}
					
				}
				
			}
		});
		
		mntmSaveAs.addActionListener(new ActionListener() {
				@Override
			public void actionPerformed(ActionEvent e) {
				//Dialog zum Speicherns
				JFrame parentFrame = new JFrame();
				 
				JFileChooser fileChooser = new JFileChooser();
				FileFilter filter = new ExtensionFileFilter(_p("SaveGameType",2), new String[] { "BOL" });
				fileChooser.setFileFilter(filter);
				fileChooser.setDialogTitle(_("SaveAs"));   
				 
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
		
		JMenuItem mntmExit = new JMenuItem(_("Exit"));
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
	private void updateButtonGraphics()
	{
		// Erzeugen der Factory
		GraficFactory gf = new GraficFactory(controller.getCurrentModel());
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
