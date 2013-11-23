package de.bwvaachen.beamoflightgame.helper;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;

public class BoardTraverser {
	private final IBeamsOfLightPuzzleBoard board;
	int x, y, startX, startY;

	public void reset() {
		x = startX;
		y = startY;
	}
	
	
	public BoardTraverser(final IBeamsOfLightPuzzleBoard b, int x, int y) {
		this.board = b;
		this.x = startX = x;
		this.y = startY = y;
	}
	
	public ITile get() {
		return board.getTileAt(x, y);
	}

	//Move the cursor relative 
	public boolean shift(int x, int y) {
		if (! board.hasField(x, y)) return false;
		this.x += x;
		this.y += y;
		return true;
	}
	
	public boolean shift(TraverseDirection d) {
		return shift(d.x, d.y);
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
	

}
