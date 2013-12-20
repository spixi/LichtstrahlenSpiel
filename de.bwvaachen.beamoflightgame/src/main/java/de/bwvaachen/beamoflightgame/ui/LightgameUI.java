package de.bwvaachen.beamoflightgame.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;


import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.controller.SolverBuilder;
import de.bwvaachen.beamoflightgame.controller.impl.LightController;
import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.ITileVisitor;
import de.bwvaachen.beamoflightgame.helper.TraverseDirection;
import de.bwvaachen.beamoflightgame.logic.ISolver;
import de.bwvaachen.beamoflightgame.logic.strategies.LonelyFieldStrategy;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.NumberTileState;

public class LightgameUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3961364231837270604L;
	private JPanel contentPane;
	//private File lastSaveFile=null;
	private ILightController controller = new LightController() ;
	private ArrayList<TileButton> buttons = new ArrayList<TileButton>();
	private ITile activeNumberTile = null ;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
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

	/**
	 *  Constructor
	 *  Initialisieren des Fensters
	 *  @author gbraun , pauls_and	 *  
	 */
	public LightgameUI() {
		
		try {	

			// Setzen der initialen Fensterposition und Gr��e.
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 450, 300);
			
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
	
			// Controller mit Test Prototyp f�r GUI f�llen.
			controller . setBoard ( new PrototypModelFuerGUI() ) ;
			
			javax.swing.JButton solverButton = new javax.swing.JButton("Rätsel auflösen");
			solverButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						ISolver s = SolverBuilder.buildWith(LonelyFieldStrategy.class).forBoard(controller.getBoard());
					    s.solve();
					
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			});
			
			
			contentPane.add(solverButton,BorderLayout.NORTH);
			
			// Das Spielfeld vom Controller holen:
			IBeamsOfLightPuzzleBoard currentModel = controller . getBoard() ;
			
			// TODO tempor�r feste Werte f�r Tests eingetragen.
			int rows = currentModel.getHeight() ;
			int cols = currentModel.getWidth() ;
			
			//IBeamsOfLightPuzzleBoard currentModel = controller . getCurrentModel();
			rasterPanel . setLayout ( new GridLayout ( rows , cols , 0 , 0 ) ) ;
			
			// Schleife �ber das "Spielfeld"
			for ( int row=0 ; row<rows ; row++ ) {
				for ( int col=0 ;col<cols ; col++ ) {
	
					// Neuen Button erzeugen
					final TileButton newTileButton = new TileButton ( currentModel . getTileAt ( col , row ) ) ;
					// Action hinzuf�gen
					
					currentModel . getTileAt ( col , row ) . accept( new ITileVisitor() {

						public void visitLightTile(LightTile t) {
							newTileButton . addActionListener ( new LightTileListener() ) ;
							
						}

						public void visitNumberTile(NumberTile t) {
							newTileButton . addActionListener ( new NumberTileButtonListener() ) ;
							
						}
						
					} );
					
					// Button auf das Panel setzen
					rasterPanel . add ( newTileButton ) ;
					
					// Aktuelle Button dem Button-Array hinzuf�gen.
					buttons . add (newTileButton);
					
				} // for ( int col=0 ;col<cols ; col++ )
			} // for ( int row=0 ; row<rows ; row++ )
			
			//Der JButton implementiert ImageObserver ... Wir brauchen also eigentlich nur das Bild
			//austauschen und -schwupp- sollte der Button sich mitändern, oder etwa nicht?
			Update( currentModel ) ;
			
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
		btn.setIcon(ico);
		return btn;
	} // private TileButton addIcon(TileButton btn, Icon ico)
	
	
	/**
	 * Paints the GUI (Buttons) based on a Model Object
	 * @author pauls 
	 * @param model
	 */
	private void Update(IBeamsOfLightPuzzleBoard model)
	{
		GraficFactory gf = new GraficFactory(model);
		for(TileButton btn : buttons)
		{
			addIcon( btn , gf . getImage ( btn . getTile() ) ) ;	
			if ( btn . markiert ) {
				btn . setBackground ( new Color(255,0,0) ) ;
			}
			else {
				btn . setBackground ( new Color(238,238,238) ) ;
			}
				
		} // for(TileButton btn : buttons)
	} // private void Update(IBeamsOfLightPuzzleBoard model)
	

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
		
		JMenuItem mntmbacktoFailure = new JMenuItem("Back to failure");
		mnGame.add(mntmbacktoFailure);
		
		JSeparator separator_3 = new JSeparator();
		mnGame.add(separator_3);
		
		JMenuItem mntmsetMarker = new JMenuItem("Set mark");
		mnGame.add(mntmsetMarker);
		
		JSeparator separator_4 = new JSeparator();
		mnGame.add(separator_4);
		
		JMenuItem mntmDeleteMarker = new JMenuItem("Delete mark");
		mnGame.add(mntmDeleteMarker);
		
		JSeparator separator_5 = new JSeparator();
		mnGame.add(separator_5);
		
		JMenuItem mntmBackToMark = new JMenuItem("Back to mark");
		mnGame.add(mntmBackToMark);
		
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
				} catch (ClassNotFoundException
						| IOException e1) {
					e1.printStackTrace();
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
		    this.extensions = (String[]) extensions.clone();
		    toLower(this.extensions);
		  }

		  private void toLower(String array[]) {
		    for (int i = 0, n = array.length; i < n; i++) {
		      array[i] = array[i].toLowerCase();
		    }
		  }

		  public String getDescription() {
		    return description;
		  }

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
		}
	
	
	/**
	 * Action f�r den Klick auf ein Tile
	 * 
	 * @author gbraun, pauls_and
	 */
	class NumberTileButtonListener implements ActionListener
	{

		/**
		 * �ndert die Hintergrundfarbe.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
		
			try {	
				
				activeNumberTile = null ;
				for ( TileButton aktButton : buttons ) {
					aktButton . markiert = false ;				
				} // for ( TileButton aktButton : buttons ) 
				
				// Den ausl�senden Button holen
				TileButton btn = (TileButton) e.getSource();

				// Pr�fen ob es sich um ein NumberTile handelt.
				if ( btn . getTile() instanceof NumberTile ) {
				
					// Variable deklarieren, die bei der Anzeige der m�glichen Felder hilft.
					boolean CurrentTileIsLightTile ;
	
					// Strahlst�rke holen
					int strahlStaerke = ((NumberTileState) btn . getTile() . getTileState() ) . getNumber() ;					
					
					// Den Traverser initialisieren.
					BoardTraverser traverser = new BoardTraverser ( controller.getBoard() , btn.getTile() ) ;
					// Das Modell vom Controller holen.
					IBeamsOfLightPuzzleBoard currentModel = controller . getBoard() ;
					
					
					// Schleife �ber alle "Himmelsrichtungen" (West, Ost, S�d, Nord). Daf�r nehm ich den Aufz�hlungstyp LightTileState.
					for ( LightTileState aktState : LightTileState . values() ) {
						
						// Zu dem LightTileState z�hlt auch das Element "Empty", welches ich aber nicht f�r die "Zug-�berpr�fung" brauche.
						if ( aktState != LightTileState . EMPTY ) {
							
	
							// Den Traverser auf den Button Ausgangsbutton (NumberTile) setzen.
							traverser . moveTo ( btn . getCol() , btn . getRow() ) ;
							TraverseDirection traverseDirection = aktState . getTraverseDirection() ;
													
							// Initial ist diese Aussage falsch, da man den Traverser auf das Ausgangs-Numbertile setzt. Ich setz das trotzdem auf true, 
							// da man sich so ein paar Abfragen spart. Der Status wird direkt als erstes in der Schleife geupdatet und ist ab dort "richtig".
							CurrentTileIsLightTile = true ;
							
							int verbrauchteStaerke = 0 ;
							
							// Wandern in die aktuelle Himmelsrichtung unter folgenden Bedinungen:
							// 1. Es ist noch m�glich weiter in die Richtung zu gehen
							// 2. Es handelt sich um ein LightTile Feld
							// 3. Die Anzahl der Felder (aus dem NumberTile) wird nicht �berschritten.
							while ( ( traverser . shift ( traverseDirection ) ) && ( CurrentTileIsLightTile ) && ( verbrauchteStaerke < strahlStaerke ) ) {							
								
								// Pr�fen auf was f�r einem Feld der Traverser aktuell steht.
								CurrentTileIsLightTile = ( currentModel . getTileAt( traverser . getX(), traverser . getY() ) instanceof LightTile );
								
								if ( CurrentTileIsLightTile ) {
									// Den Button holen, der das aktuelle Tile repr�sentiert.
									int buttonArrayPos = ( ( traverser . getY() * currentModel . getWidth() ) + traverser . getX() )  ;
									TileButton aktButton = buttons . get ( buttonArrayPos ) ;

									// Einf�rben des Buttons
									aktButton . markiert = true ;
									// Die "verbrauchte St�rke" erh�hen.
									verbrauchteStaerke += 1 ;
									activeNumberTile = btn . getTile() ;
								} // if ( CurrentTileIsLightTile ) 
												
							} // while ( .. ) 
							
						} // if ( aktState != LightTileState . EMPTY )
							
					} // for ( LightTileState aktState : LightTileState . values() )
					
				} // if ( btn . getTile() instanceof NumberTile ) {
				
				Update ( controller.getBoard() ) ;
				
			} catch (Exception e2) {
				
			} // try .. catch
			
		} // public void actionPerformed(ActionEvent e)
		
	} // class TileButtonListener 
	
	
	
	
	class LightTileListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			try {
				// Den ausl�senden Button holen
				TileButton btn = (TileButton) e.getSource();
							
				if ( ( activeNumberTile != null ) && ( btn . markiert ) )  {
					
					int lightTileX = btn . getCol() ;
					int lightTileY = btn . getRow () ;
					int numberTileX = activeNumberTile . getX() ;
					int numberTileY = activeNumberTile . getY() ;
					
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
					
					IBeamsOfLightPuzzleBoard currentBoard = controller.getBoard() ;
					
					// Den Traverser initialisieren.
					BoardTraverser traverser = new BoardTraverser ( currentBoard , btn.getTile() ) ;
					
					// Den Traverser auf den Button Zielbutton (LightTile) setzen.
					traverser . moveTo ( lightTileX , lightTileY ) ;
					TraverseDirection traverseDirection = lichtRichtung . reverse() . getTraverseDirection() ;
					
					boolean alleGezeichnet = false ;
					do  {
						if ( ( numberTileX == traverser . getX() ) && ( numberTileY == traverser . getY() ) ) {
							break;
						}
						LightTile currentTile = (LightTile) traverser.get() ; 
						currentTile . setState( lichtRichtung ) ;
						traverser . shift ( traverseDirection ) ;
					} while(true);
					
					activeNumberTile = null ;
					
					
					
					for ( TileButton aktButton : buttons ) {
						aktButton . markiert = false ;						
					} // for ( TileButton aktButton : buttons ) 					
					
					
					Update( currentBoard ) ;
				} // if ( ( activeNumberTile != null ) && ( btn . markiert ) )
				
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} // try .. catch	
		
		} // public void actionPerformed(ActionEvent e) 
		
	} // class LightTileListener implements ActionListener
	
	
	
	class Selection
	{
		
	}
}
