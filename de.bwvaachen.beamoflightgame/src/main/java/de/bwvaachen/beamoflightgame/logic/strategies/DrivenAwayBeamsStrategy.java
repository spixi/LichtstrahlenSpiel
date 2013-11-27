package de.bwvaachen.beamoflightgame.logic.strategies;

import de.bwvaachen.beamoflightgame.logic.IStrategy;
import de.bwvaachen.beamoflightgame.logic.PuzzleException;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.NumberTile;

public class DrivenAwayBeamsStrategy extends AbstractStrategy {

	@Override
	public boolean tryToSolve()  throws PuzzleException {
		return false;
		// TODO Auto-generated method stub
	}

	@Override
	public double getComplexity() {
		return 100;
	}
	
	@Override
	public boolean isAppliableForTile(ITile t) {
		return (t instanceof NumberTile) && ((NumberTile) t).getRemainingLightRange() > 0;
	}

}
