package de.bwvaachen.beamoflightgame.model;

public class LightTile extends AbstractTile<LightTileState> implements ILightTile  {
	public LightTile(int row, int col) {
		super(row, col,LightTileState.class);
	}

	public void setState(LightTileState state) {
		setTileState(state);	
	}
}
