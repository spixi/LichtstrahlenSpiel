package de.bwvaachen.beamoflightgame.model;

public interface ILightTile extends ITile {
	public LightTileState getState();
	public void setState(LightTileState state);
}
