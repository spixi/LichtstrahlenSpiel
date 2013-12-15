package de.bwvaachen.beamoflightgame.model;

public class NumberTileState implements ITileState {
	private final int number;
	
	public NumberTileState(final int num) {
		this.number = num;
	}
	public int getNumber() {
		return number;
	}
	public boolean equals(ITileState tileState)
	{
		try{
			NumberTileState lts = (NumberTileState) tileState;
			return lts == this;
		}
		catch (Exception e){
		return false;
		}
	}
	public String toString() {
		return ((Integer) number).toString();
	}
}
