package de.bwvaachen.beamoflightgame.model;

public interface INumberTile extends ITile<NumberTileState> {
	public int getRemainingLightRange();
	public int getNumber();
}
