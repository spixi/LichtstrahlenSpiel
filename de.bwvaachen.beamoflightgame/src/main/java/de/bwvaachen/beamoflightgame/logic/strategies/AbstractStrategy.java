package de.bwvaachen.beamoflightgame.logic.strategies;

import de.bwvaachen.beamoflightgame.logic.IStrategy;
import de.bwvaachen.beamoflightgame.logic.PuzzleException;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;

public abstract class AbstractStrategy implements IStrategy {
	protected ITile tile;
	
	@Override
	public final void init(ITile t) {
		tile = t;
		_init();
	}
	
	protected void _init() {}
}
