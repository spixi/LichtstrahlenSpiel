package de.bwvaachen.beamoflightgame.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private final static Map<Character,LightTileState> map;
	private final static LightTileState allDirections[];
	static {
		map = new HashMap<Character,LightTileState>();
		for(LightTileState value: values()) {
			map.put(value.getSign(),value);
		}
		
		allDirections = new LightTileState[] {NORTH, EAST, SOUTH, WEST};
	}
	
	public static List<LightTileState> allDirections() {
		return Arrays.asList(allDirections);
	}
	public static LightTileState signToState(char c){
		return map.get(c);
	}

	private char sign;
	
	private TraverseDirection traverseDirection;

	private LightTileState(char c, TraverseDirection d) {
		sign              = c;
		traverseDirection = d;
	}
	
	public boolean equals(ITileState tileState)
	{
		try{
			LightTileState lts = (LightTileState) tileState;
			return lts.equals(this);
		}
		catch (Exception e){
			return false;
		}
	}
	
	public char getSign() {
		return sign;
	}

	public TraverseDirection getTraverseDirection() {
		return traverseDirection;
	}
	
	public LightTileState reverse() {
		return this;
	}
	
	@Override
	public String toString() {
		return ((Character) getSign()).toString();
	}
}
