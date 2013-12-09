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

import javax.security.sasl.AuthorizeCallback;
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
import de.bwvaachen.beamoflightgame.controller.impl.LightController;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.LightTile;

public class LightgameUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3961364231837270604L;
	private JPanel contentPane;
	//private File lastSaveFile=null;
	private ILightController controller = new LightController() ;
	private ArrayList<TileButton> buttons = new ArrayList<TileButton>();
	
	
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
				}
			}
		});
		
	}

	/**
	 *  Constructor
	 *  Initialisieren des Fensters
	 *  @author gbraun , pauls_and	 *  
	 */
	public LightgameUI() {
		
		try {	

			// Setzen der initialen Fensterposition und Grï¿½ï¿½e.
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
	
			// Controller mit Test Prototyp für GUI füllen.
			controller . setBoard ( new PrototypModelFuerGUI() ) ;
			
			// Das Spielfeld vom Controller holen:
			IBeamsOfLightPuzzleBoard currentModel = controller . getBoard() ;
			
			// TODO temporï¿½r feste Werte fï¿½r Tests eingetragen.
			int rows = currentModel.getHeight() ;
			int cols = currentModel.getWidth() ;
			
			//IBeamsOfLightPuzzleBoard currentModel = controller . getCurrentModel();
			rasterPanel . setLayout ( new GridLayout ( rows , cols , 0 , 0 ) ) ;
			
			// Schleife ï¿½ber das "Spielfeld"
			for ( int row=0 ; row<rows ; row++ ) {
				for ( int col=0 ;col<cols ; col++ ) {
	
					// Neuen Button erzeugen
					TileButton newTileButton = new TileButton ( currentModel . getTileAt ( col , row ) ) ;

					// Action hinzufügen
					newTileButton . addActionListener ( new TileButtonListener() ) ;
					
					// Button auf das Panel setzen
					rasterPanel . add ( newTileButton ) ;
					
					// Aktuelle Button dem Button-Array hinzufügen.
					buttons . add (newTileButton);
					
				} // for ( int col=0 ;col<cols ; col++ )
			} // for ( int row=0 ; row<rows ; row++ )
			
			//Der JButton implementiert ImageObserver ... Wir brauchen also eigentlich nur das Bild
			//austauschen und -schwupp- sollte der Button sich mitÃ¤ndern, oder etwa nicht?
			Update( currentModel ) ;
			
		} catch (Exception e) {
			// TODO: handle exception
		}
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
	 * Action fï¿½r den Klick auf ein Tile
	 * 
	 * @author gbraun, pauls_and
	 */
	class TileButtonListener implements ActionListener
	{

		/**
		 * ï¿½ndert die Hintergrundfarbe.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			TileButton btn = (TileButton)e.getSource();
			btn.setBackground(new Color(255,0,0));
		} // public void actionPerformed(ActionEvent e) 
		
	} // class TileButtonListener 
	
	
	
	class Selection
	{
		
	}
}
