package de.bwvaachen.beamoflightgame.helper;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;

/**
 * 
 * @author Marius
 *
 */
public class BoardTraverser {
	private final IBeamsOfLightPuzzleBoard board;
	int x, y, startX, startY;

	public BoardTraverser(final IBeamsOfLightPuzzleBoard b, int x, int y) {
		this.board = b;
		this.x = startX = x;
		this.y = startY = y;
	}
	
	@Deprecated
	public BoardTraverser(final IBeamsOfLightPuzzleBoard b, final ITile t) {
		this(t);
	}
	
	public BoardTraverser(final ITile t) {
		this(t.getBoard(), t.getX(), t.getY());
	}
	
	public ITile get() {
		return board.getTileAt(x, y);
	}
	
	public int getStartX(){
		return startX;
	}
	
	public int getStartY(){
		return startY;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	public boolean moveTo(int x, int y) {
		if (! board.hasField(x, y)) return false;
		this.x = x;
		this.y = y;
		return true;
	}
	
	public void reset() {
		x = startX;
		y = startY;
	}
	
	//Move the cursor relatively
	public boolean shift(int x, int y) {
		return moveTo(this.x+x,this.y+y);
	}
	
	/**
	 * Shift the cursor using a TraverseDirection object
	 * @param d
	 * @return false if move impossible - otherwise true
	 */
	public boolean shift(TraverseDirection d) {
		return shift(d.x, d.y);
	}
	

}
