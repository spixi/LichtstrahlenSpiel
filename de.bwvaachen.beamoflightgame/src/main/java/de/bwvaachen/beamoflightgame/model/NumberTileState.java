package de.bwvaachen.beamoflightgame.model;

public class NumberTileState implements ITileState {
	private final int number;
	
	public NumberTileState(final int num) {
		this.number = num;
	}
	public int getNumber() {
		return number;
	}
}
