package de.bwvaachen.beamoflightgame.ui;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalButtonUI;

import de.bwvaachen.beamoflightgame.helper.Pair;
import de.bwvaachen.beamoflightgame.model.IChangeableTile;
import de.bwvaachen.beamoflightgame.model.ITile;

/**
 * This class extends JButton for properties that are needed by our GUI. 
 * @author gbraun
 */
public class TileButton extends JButton implements ChangeListener {
	
	private GraficFactory graphicFactory;
	
	/**
	 * 	Reference to tile in the model. 
	 */
	private final ITile tile;
	
	
	/**
	 * Indicates if the button (tile) is a possible turn.
	 */
	public boolean markiert = false ;
	
	
	/**
	 * Constructor
	 * @param t The tile in the model.
	 */
	public TileButton(final ITile t) {
		tile = t;
		if(t.isStateChangeable()) {
			t.addChangeListener(this);
		}
		
		//TODO: Remove this ugly stuff here:
		graphicFactory = new GraficFactory(t.getBoard());
	}
	

	/**
	 * @return The column of the tile.
	 */
	public int getCol() {
		return tile.getX();
	}
	
	
	/**
	 * @return The row of the tile.
	 */
	public int getRow() {
		return tile.getY();
	}


	/**
	 * Getter-Method Tile
	 * @return The Tile.
	 */
	public ITile getTile() {
		return tile;
	}
	
	/**
	 *  This method changes the direction of the tile and initiate the repaint.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		this.setIcon(graphicFactory.getImage(tile));
	    repaint();
	}

}
