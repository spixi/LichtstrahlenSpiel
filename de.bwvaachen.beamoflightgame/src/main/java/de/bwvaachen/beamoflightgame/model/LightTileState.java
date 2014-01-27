package de.bwvaachen.beamoflightgame.model;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.bwvaachen.beamoflightgame.helper.TraverseDirection;
import de.bwvaachen.beamoflightgame.ui.RotatedIcon.Rotate;

public enum LightTileState implements ITileState {
	SOUTH('s', new TraverseDirection(0,1), Rotate.UPSIDE_DOWN) {
		@Override
		public LightTileState reverse() {
			return NORTH;
		}
	},
	WEST('w', new TraverseDirection(-1,0), Rotate.UP) {
		@Override
		public LightTileState reverse() {
			return EAST;
		}
	},
	NORTH('n', new TraverseDirection(0,-1), Rotate.NONE) {
		@Override
		public LightTileState reverse() {
			return SOUTH;
		}
	},
	EAST('e', new TraverseDirection(1,0), Rotate.DOWN) {
		@Override
		public LightTileState reverse() {
			return WEST;
		}
	},
	EMPTY('-', null, Rotate.NONE);
	
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
	private Rotate rotation;

	private LightTileState(char c, TraverseDirection d, Rotate r) {
		sign              = c;
		traverseDirection = d;
		rotation          = r;
	}
	
	public Rotate getRotation() {
		return rotation;
	}
	
	public boolean equals(ITileState tileState)
	{
		try{
			LightTileState lts = (LightTileState) tileState;
			return lts.getSign() == this.getSign();
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
