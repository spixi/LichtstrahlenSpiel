package de.bwvaachen.beamoflightgame.ui;

import javax.swing.JButton;

/**
 * Eine Klasse die den JButton um Eigenschaften erweitert, die in der GUI hilfreich
 * sein können.
 * 
 * @author gbraun
 *
 */
public class TileButton extends JButton {
	
	/** Zeile des Buttons */
	private int row ;
	
	/** Spalte des Buttons*/
	private int col ;
	
	
	public int getRow() {
		return row;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public int getCol() {
		return col;
	}


	public void setCol(int col) {
		this.col = col;
	}


	/**
	 * Eigener Konstruktor
	 * @param _row Die Reihe/Zeile des Buttons 
	 * @param _col Die Spalte des Buttons
	 */
	public TileButton ( int _row , int _col ) {
		
		setRow ( _row ) ;
		setCol ( _col ) ;
				
	} // public TileButton ( int _row , int _col )

}
