package de.bwvaachen.beamoflightgame.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;


@SuppressWarnings("serial")
public abstract class BeamsOfLightEditor extends JFrame 
	implements ActionListener{
	
	private Dimension screenSize;
	protected Dimension frameSize;
	protected int row;
	protected int col;
	protected TilesPanel tilesPanel;
	protected JPanel buttonPanel;
	protected JButton leftButton; 
	protected JButton middleButton;
	protected JButton rightButton; 
	
	public BeamsOfLightEditor(String title, int width, int height){
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.col = width ; this.row = height ;
		
		setSize(width*128, height*128 + 75) ;
		frameSize = getSize() ;
		
		setMinimumSize(frameSize) ;
		setLocation((screenSize.width - frameSize.width)/2,(screenSize.height - frameSize.height)/2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(title);
		setLayout(new BorderLayout());
		setJMenuBar(new EditorMenu());
		
		tilesPanel = new TilesPanel();
		tilesPanel.setLayout(new GridLayout(height,width));
		
		initComponents();
		
		buttonPanel = new JPanel();
		buttonPanel.add(leftButton);
		buttonPanel.add(middleButton);
		buttonPanel.add(rightButton);
		
		add(BorderLayout.CENTER,tilesPanel);
		add(BorderLayout.SOUTH,buttonPanel);
		setVisible(true);
	}
	

	public abstract void initComponents();
	
	public abstract BeamsOfLightPuzzleBoard convertToBoard();
	
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
}
