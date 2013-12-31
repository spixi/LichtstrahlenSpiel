package de.bwvaachen.beamoflightgame.logic.strategies;

import de.bwvaachen.beamoflightgame.logic.IStrategy;
import de.bwvaachen.beamoflightgame.model.ITile;

public abstract class AbstractStrategy implements IStrategy {
	protected ITile tile;
	
	protected void _init() {}
	
	@Override
	public final void init(ITile t) {
		tile = t;
		_init();
	}
}
