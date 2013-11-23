package de.bwvaachen.beamoflightgame.model;

import java.io.ObjectStreamException;
import java.io.Serializable;

import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.TraverseDirection;

public enum LightTileState implements ITileState {
	SOUTH('s', new TraverseDirection(0,1)),
	WEST('w', new TraverseDirection(-1,0)),
	NORTH('n', new TraverseDirection(0,-1)),
	EAST('e', new TraverseDirection(1,0)),
	EMPTY('-', null);
	private char sign;
	private TraverseDirection traverseDirection;

	private LightTileState(char c, TraverseDirection d) {
		sign              = c;
		traverseDirection = d;
	}
	
	public TraverseDirection getTraverseDirection() {
		return traverseDirection;
	}

	public char getSign() {
		return sign;
	}

	public static LightTileState signToState(char c){
		switch (c) {
		case 's':
			return LightTileState.SOUTH;
		case 'w':
			return LightTileState.WEST;
		case 'n':
			return LightTileState.NORTH;
		case 'e':
			return LightTileState.EAST;
		default:
			return LightTileState.EMPTY;
		}
	}
}
