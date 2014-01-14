package de.bwvaachen.beamoflightgame.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;


@SuppressWarnings("serial")
public abstract class BeamsOfLightEditor extends JFrame 
	implements ActionListener{
	
	private Dimension screenSize;
	protected Dimension frameSize;
	protected int row;
	protected int col;
	protected int totalTiles;
	protected int remainingTiles;
	protected TilesPanel tilesPanel;
	protected JPanel buttonPanel;
	protected JPanel southPanel;
	protected JButton leftButton; 
	protected JButton middleButton;
	protected JButton rightButton; 
	protected EditorType editorType ;
	protected JTextArea tileStats ;
	
	public BeamsOfLightEditor(EditorType editorType, int width, int height){
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.col = width ; this.row = height ;
		this.totalTiles = col * row ;
		this.editorType = editorType ;
		
		setSize(width*128, height*128 + 75) ;
		frameSize = getSize() ;
		
		setMinimumSize(frameSize) ;
		setLocation((screenSize.width - frameSize.width)/2,(screenSize.height - frameSize.height)/2);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("BeamsOfLightGame - " + editorType);
		setLayout(new BorderLayout());
		setJMenuBar(new EditorMenu(this));
		
		tilesPanel = new TilesPanel();
		tilesPanel.setLayout(new GridLayout(row,col));
		
		leftButton = new JButton("Solve");
		middleButton = new JButton("DO NOTHING");
		rightButton = new JButton("Reset");
		
		initComponents();
		tileStats = new JTextArea(getTileStats());
		
		buttonPanel = new JPanel();
		buttonPanel.add(leftButton);
		buttonPanel.add(middleButton);
		buttonPanel.add(rightButton);
				
		southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.add(BorderLayout.CENTER,buttonPanel);
		southPanel.add(BorderLayout.SOUTH,tileStats);
		
		add(BorderLayout.CENTER,tilesPanel);
		add(BorderLayout.SOUTH,southPanel);
		setVisible(true);
	}
	

	public abstract void initComponents();
	
	public abstract BeamsOfLightPuzzleBoard convertToBoard();
	
	public abstract String getTileStats();
	
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
