package de.bwvaachen.beamoflightgame.model;

import de.bwvaachen.beamoflightgame.helper.TraverseDirection;

public enum LightTileState implements ITileState {
	SOUTH('s', new TraverseDirection(0,1)) {
		@Override
		public LightTileState reverse() {
			return NORTH;
		}
	},
	WEST('w', new TraverseDirection(-1,0)) {
		@Override
		public LightTileState reverse() {
			return EAST;
		}
	},
	NORTH('n', new TraverseDirection(0,-1)) {
		@Override
		public LightTileState reverse() {
			return SOUTH;
		}
	},
	EAST('e', new TraverseDirection(1,0)) {
		@Override
		public LightTileState reverse() {
			return WEST;
		}
	},
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
	
	public LightTileState reverse() {
		return this;
	}
	
	public String toString() {
		return ((Character) getSign()).toString();
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
	public boolean equals(ITileState tileState)
	{
		try{
			LightTileState lts = (LightTileState) tileState;
			return lts == this;
		}
		catch (Exception e){
		return false;
		}
	}
}
