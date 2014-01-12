package de.bwvaachen.beamoflightgame.logic.strategies;

import de.bwvaachen.beamoflightgame.logic.IStrategy;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.ITileState;

public abstract class AbstractStrategy<T extends ITileState> implements IStrategy<T> {
	protected ITile<T> tile;
	
	protected void _init() {}
	
	@Override
	public final void init(ITile<T> t) {
		tile = t;
		_init();
	}
}
