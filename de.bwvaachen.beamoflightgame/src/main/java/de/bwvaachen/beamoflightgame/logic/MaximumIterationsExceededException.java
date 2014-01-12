package de.bwvaachen.beamoflightgame.logic;

import de.bwvaachen.beamoflightgame.helper.ITileVisitor;
import de.bwvaachen.beamoflightgame.model.AbstractTile;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITileState;

public class MaximumIterationsExceededException extends UnsolvablePuzzleException {
	private static final long serialVersionUID = -5182021097440559937L;

	private static class NullTile extends AbstractTile<ITileState> {
		protected NullTile(IBeamsOfLightPuzzleBoard board) {
			super(board, 0, 0, ITileState.class);
		}

		@Override
		public void accept(ITileVisitor v) {}
		
	}
	
	public MaximumIterationsExceededException(IBeamsOfLightPuzzleBoard b) {
		super(new NullTile(b));
	}
}
