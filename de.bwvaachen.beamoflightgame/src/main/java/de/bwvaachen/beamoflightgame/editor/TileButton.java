package de.bwvaachen.beamoflightgame.editor;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;

/**
 * Eine Klasse die den JButton um Eigenschaften erweitert, die in der GUI hilfreich
 * sein können.
 * 
 * @author gbraun,cm
 *
 */

@SuppressWarnings("serial")
public class TileButton extends JButton {
	
	private int col ;
	private int row ;
	private BufferedImage image ;
	private TileState state ;
	private int lightPower ;
	
	/**
	 * Eigener Konstruktor
	 * @param row Die Zeile des Buttons 
	 * @param col Die Spalte des Buttons
	 */
	public TileButton(int col, int row ) {
		this.col = col ;
		this.row = row ;
		this.image = null ;
	} 
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public TileState getState(){
		return state ;
	}
	
	public int getLightPower(){
		return lightPower ;
	}
	
	public void reset(){
		this.image = null ;
		this.state = TileState.EMPTY;
		this.lightPower = 0 ;
		repaint();
	}
	
	public void setLightPower(int lightPower) throws IIOException, IOException{
		this.lightPower = lightPower ;
		this.state = TileState.NUMBER ;
		this.setImage("themes/moon/"+this.lightPower+".png");
	}
	
	public void setImage(String path) throws IOException, IIOException {
		this.image = ImageIO.read(new File(path));
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
		super.paintComponent(g2);
		
		if(image != null){
			g2.drawImage(image,0,0,null);
		}
	}
}
