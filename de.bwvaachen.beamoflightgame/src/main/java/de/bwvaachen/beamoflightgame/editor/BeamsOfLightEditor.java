package de.bwvaachen.beamoflightgame.editor;

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
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.controller.SolverBuilder;
import de.bwvaachen.beamoflightgame.controller.impl.LightController;
import de.bwvaachen.beamoflightgame.logic.ISolver;
import de.bwvaachen.beamoflightgame.logic.UnsolvablePuzzleException;
import de.bwvaachen.beamoflightgame.logic.strategies.IntersectionStrategy;
import de.bwvaachen.beamoflightgame.logic.strategies.LonelyFieldStrategy;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;


@SuppressWarnings("serial")
public abstract class BeamsOfLightEditor extends JFrame 
	implements ActionListener{
	
	private Dimension screenSize;
	protected int row;
	protected int col;
	protected int totalTiles;
	protected int remainingTiles;
	protected double rotationAngle ;
	protected TilesPanel tilesPanel;
	protected TilesPanel tilesPanelSolved;
	protected JPanel buttonPanel;
	protected JPanel southPanel;
	protected JButton solveButton; 
	protected JButton resetButton; 
	protected EditorType editorType ;
	protected JTextArea tileStats ;
	
	public BeamsOfLightEditor(EditorType editorType, int width, int height){
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.col = width ; this.row = height ;
		this.totalTiles = col * row ;
		this.editorType = editorType ;
		
		setSize(col*128, row*128 + 75);
		
		setLocation((screenSize.width - getSize().width)/2,(screenSize.height - getSize().height)/2);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("BeamsOfLightGame - " + editorType);
		setLayout(new BorderLayout());
		setJMenuBar(new EditorMenu(this));

		tilesPanelSolved = new TilesPanel() ;
		tilesPanelSolved.setLayout(new GridLayout(row,col));
		tilesPanel = new TilesPanel();
		tilesPanel.setLayout(new GridLayout(row,col));
		
		solveButton = new JButton("Loesen");
		resetButton = new JButton("Reset");
		
		initComponents();
		tileStats = new JTextArea(getTileStats());
		checkButtons();
		
		buttonPanel = new JPanel();
		buttonPanel.add(solveButton);
		buttonPanel.add(resetButton);
				
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.add(BorderLayout.CENTER,buttonPanel);
		southPanel.add(BorderLayout.SOUTH,tileStats);
		
		add(BorderLayout.CENTER,tilesPanel);
		add(BorderLayout.SOUTH,southPanel);
		setVisible(true);
	}
	

	public abstract void initComponents();
	
	public abstract TilesPanel initTilesPanel(IBeamsOfLightPuzzleBoard source, boolean isSolution) 
			throws NumberFormatException, IIOException, IOException;
	
	public abstract IBeamsOfLightPuzzleBoard convertToBoard();
	
	public abstract String getTileStats();
	
	public void importPuzzleBoard(IBeamsOfLightPuzzleBoard source) 
			throws NumberFormatException, IIOException, IOException{
			
		col = source.getWidth();
		row = source.getHeight();
		
		remove(tilesPanel);
		
		tilesPanel = initTilesPanel(source,false);
		//tilesPanelSolved = initTilesPanel(source,true);
		
		add(BorderLayout.CENTER,tilesPanel);
	}
	
	public void checkButtons(){
		if(remainingTiles == 0){
			solveButton.setEnabled(true);
		}else{
			solveButton.setEnabled(false);
		}
	//	if(tilesPanelSolved != null){
	//		middleButton.setEnabled(true);
	//	}else{
	//		middleButton.setEnabled(false);
	//	}
		
		if(!solveButton.isEnabled()){
			solveButton.setToolTipText("Loesen nur moeglich wenn verbleibende Felder = 0.");
		}else{
			solveButton.setToolTipText(null);
		}
		
	//	if(!middleButton.isEnabled()){
	//		middleButton.setToolTipText("Es existiert noch keine Lösung zur aktuellen Eingabe.");
	//	}else{
	//		middleButton.setToolTipText(null);
	//	}
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
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		try{
			if(ae.getSource() == solveButton){
				IBeamsOfLightPuzzleBoard board = convertToBoard();
				ILightController controller = new LightController();
				controller.setBoard(board);
				
				ISolver s =
				SolverBuilder.buildWith(LonelyFieldStrategy.class).
					          and(IntersectionStrategy.class).
					          /*and(TryAndErrorStrategy.class).*/
					          forBoard(board);
			    s.solve();
			    importPuzzleBoard(controller.getCurrentModel());
			    
			    System.out.println(tilesPanel);
			    System.out.println(tilesPanelSolved);
			}
		}catch(UnsolvablePuzzleException upe){
			int userSelection = JOptionPane.showConfirmDialog(	this,
																"Eingaben fuehren zu keiner eindeutigen Loesung!\nSpielfeld zuruecksetzen?",
																"Fehler",
																JOptionPane.YES_NO_OPTION);
			if(userSelection == JOptionPane.YES_OPTION){
				resetButton.doClick();
			}
			
		}catch(Exception e){
			// TODO 
			e.printStackTrace();
		}finally{
			setSize(col*128,row*128 + 75);
			setMinimumSize(getSize());
			tileStats.setText(getTileStats());
			checkButtons();
			pack();
			repaint();
		}
	}
	
	public int getRows(){
		return row ;
	}
	
	public int getCols(){
		return col ;
	}
	
	public EditorType getEditorType(){
		return editorType ;
	}
	
}
