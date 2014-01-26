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

import de.bwvaachen.beamoflightgame.helper.Pair;
import de.bwvaachen.beamoflightgame.model.IChangeableTile;
import de.bwvaachen.beamoflightgame.model.ITile;

/**
 * This class extends JButton for properties that are needed by our GUI.
 * 
 * @author gbraun
 *
 */
public class TileButton extends JButton implements ChangeListener {
	
	private GraficFactory graphicFactory;
	
	/**
	 *  Referenz auf das Tile aus dem Modell
	 */
	private final ITile tile;
	
	public boolean markiert = false ;
	
	/**
	 * Constructor
	 * @param t Das Tile aus dem Modell
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
	 * @return Die Spalte von dem Tile.
	 */
	public int getCol() {
		return tile.getX();
	}
	
	/**
	 * @return Die Zeile von dem Tile.
	 */
	public int getRow() {
		return tile.getY();
	}


	/**
	 * Getter vom Tile
	 * @return Das Tile aus dem Modell
	 */
	public ITile getTile() {
		return tile;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		Hashtable<Object,Object> foo = new Hashtable<Object,Object>();
		IChangeableTile tile = ( (Pair<?,IChangeableTile>) e.getSource() ).right;
		tile.storeState(foo);
		//tile.getPresentation()
		
		//TODO
		try {
			Thread.sleep(8);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//TODO
		this.setIcon(graphicFactory.getImage(tile));
		this.ui.update(this.getGraphics(), this);
	}

}
