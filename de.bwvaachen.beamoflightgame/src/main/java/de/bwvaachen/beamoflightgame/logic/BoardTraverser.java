package de.bwvaachen.beamoflightgame.logic;

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

	
	public boolean shift(int x, int y) {
		if (! board.hasField(x, y)) return false;
		this.x += x;
		this.y += y;
		return true;
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
