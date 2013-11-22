package de.bwvaachen.beamoflightgame.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import org.easymock.EasyMock;

import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.controller.impl.LightController;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public class LightgameUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3961364231837270604L;
	private JPanel contentPane;
	private File lastSaveFile=null;
	private ILightController controller = new LightController() ;
	
	/*
	private ILightController controller=new ILightController() {
		
		@Override
		public IBeamsOfLightPuzzleBoard getCurrentModel() {
			return EasyMock.createMock(IBeamsOfLightPuzzleBoard.class);
		}

		@Override
		public void save(File f) throws IOException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void load(File f) throws FileNotFoundException, IOException,
				ClassNotFoundException {
			//Sobald die Controllerimplementation steht
			//IBeamsOfLightPuzzleBoard board = controller.load(new File("Dateiname.dat"));		
		}
	};
	*/
	
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
	 * Create the frame.
	 */
	public LightgameUI() {
		
		
		
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

		
		int rows = 4 ; // = currentModel.getHeight() ;
		int cols = 4 ; // = currentModel.getWidth() ;
		
		IBeamsOfLightPuzzleBoard currentModel = controller . getCurrentModel();
		rasterPanel . setLayout ( new GridLayout ( rows , cols , 0 , 0 ) ) ;
		for ( int row=0 ; row<rows ; row++ ) {
			for ( int col=0 ;col<cols ; col++ ) {
				JButton btn = new JButton();
				btn = addIcon(btn);
				btn.addActionListener(new TileButtonListener());
				
				
				rasterPanel.add(btn);
			} // for ( int col=0 ;col<cols ; col++ )
		} // for ( int row=0 ; row<rows ; row++ )
	} // public LightgameUI()

	/**
	 * @author pauls_and
	 * @param btn 
	 * @return Button mit Icon
	 */
	private JButton addIcon(JButton btn)
	{
		File f = new File("resources/themes/alternativ/LightBeam-end.png");
		ImageIcon ii =null;
		URL u = this.getClass().getClassLoader().getResource("themes/alternativ/LightBeam-row.png");
		
		ii = new ImageIcon(u);

		RotatedIcon ri = new RotatedIcon(ii,RotatedIcon.Rotate.UP);
		btn.setIcon(ri);
		return btn;
	} 

	
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
							//�ffnen
					}
					
				}
				try {
					//im Design ist das hier nicht void sondern gibt ein Puzzle zurueck, das sollten wir dann aufbauen
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
	class TileButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton)e.getSource();
			btn.setBackground(new Color(255,0,0));
		}
	
	}
	class Selection
	{
		
	}
}
