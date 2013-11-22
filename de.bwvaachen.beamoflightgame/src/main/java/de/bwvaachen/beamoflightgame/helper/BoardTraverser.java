package de.bwvaachen.beamoflightgame.helper;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;

public class BoardTraverser {
	private final IBeamsOfLightPuzzleBoard board;
	int x, y;
	
	public BoardTraverser(final IBeamsOfLightPuzzleBoard b, int x, int y) {
		this.board = b;
		this.x = x;
		this.y = y;
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
	
	public boolean shiftNorth(int x, int y) {
		return shift(-1, 0);
	}
	
	public boolean shiftSouth(int x, int y) {
		return shift(1, 0);
	}
	
	public boolean shiftEast(int x, int y) {
		return shift(0, -1);
	}
	
	public boolean shiftWest(int x, int y) {
		return shift(0, 1);
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
