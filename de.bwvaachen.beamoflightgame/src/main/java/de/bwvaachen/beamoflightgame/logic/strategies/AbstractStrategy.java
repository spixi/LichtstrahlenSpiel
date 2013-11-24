package de.bwvaachen.beamoflightgame.logic.strategies;

import de.bwvaachen.beamoflightgame.logic.PuzzleException;
import de.bwvaachen.beamoflightgame.logic.solver.IStrategy;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;

public abstract class AbstractStrategy implements IStrategy {
	
	protected IBeamsOfLightPuzzleBoard board;
	protected ITile tile;

	@Override
	public final void init(IBeamsOfLightPuzzleBoard b, ITile t) {
		board = b;
		tile = t;
		_init();
	}
	
	protected void _init() {}

}
