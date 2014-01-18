package de.bwvaachen.beamoflightgame.model;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.io.Serializable;

public interface IBeamsOfLightPuzzleBoard extends Iterable<ITile>, TileComposite , Serializable {
	public void enqueueTile(ITile tile);
	
	public void flush();
	public int getHeight();
	
	public int getNumOfNumberTiles();
	
	public ITile getTileAt(int x, int y) throws IndexOutOfBoundsException;
	public int getWidth();
    
	public boolean hasField(int x, int y);
	
	public void init(int x, int y);
	public boolean isPlacementOfTileStatePossible(ITileState state, int x, int y);
	
	public void putTile(ITile tile);
	
	public IBeamsOfLightPuzzleBoard clone() ;
	
}
