package de.bwvaachen.beamoflightgame.model;

import de.bwvaachen.beamoflightgame.helper.ITileVisitor;

public class LightTile extends AbstractTile<LightTileState> implements IChangeableTile<LightTileState>  {
	public LightTile(IBeamsOfLightPuzzleBoard board, int x, int y) {
		this(board, x, y, null);
	}
	
	public LightTile(IBeamsOfLightPuzzleBoard board, int x, int y, LightTileState state) {
		super(board, x, y, LightTileState.class);
		setState(state);
	}

	public void setState(LightTileState state) {
		if(state == null) state = LightTileState.EMPTY;
		setTileState(state);	
	}

	@Override
	public void accept(ITileVisitor v) {
		v.visitLightTile(this);
	}
	
	
}
