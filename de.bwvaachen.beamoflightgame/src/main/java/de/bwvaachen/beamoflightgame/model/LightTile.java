package de.bwvaachen.beamoflightgame.model;

public class LightTile extends AbstractTile<LightTileState> implements IChangeableTile<LightTileState>  {
	public LightTile(int row, int col) {
		this(row, col, null);
	}
	
	public LightTile(int row, int col, LightTileState state) {
		super(row, col, LightTileState.class);
		setState(state);
	}

	public void setState(LightTileState state) {
		if(state == null) state = LightTileState.EMPTY;
		setTileState(state);	
	}
}
