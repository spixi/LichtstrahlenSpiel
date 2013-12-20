package de.bwvaachen.beamoflightgame.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
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
		return map.get(c);
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
	
	public static List<LightTileState> allDirections() {
		return Arrays.asList(allDirections);
	}
}
