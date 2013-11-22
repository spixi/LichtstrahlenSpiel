package de.bwvaachen.beamoflightgame.model;

import java.io.ObjectStreamException;
import java.io.Serializable;

public enum LightTileState implements ITileState {
	SOUTH(Math.PI, 's'), WEST(3.0 * Math.PI / 2.0, 'w'), NORTH(0.0, 'n'), EAST(
			Math.PI / 2.0, 'e'), EMPTY(0.0, '-');
	private double theta;
	private char sign;

	private LightTileState(double d, char c) {
		theta = d;
		sign = c;
	}

	public char getSign() {
		return sign;
	}

	public double getTheta() {
		return theta;
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
