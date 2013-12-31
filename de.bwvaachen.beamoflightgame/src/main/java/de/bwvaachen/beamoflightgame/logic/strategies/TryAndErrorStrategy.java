package de.bwvaachen.beamoflightgame.logic.strategies;

import de.bwvaachen.beamoflightgame.logic.PuzzleException;
import de.bwvaachen.beamoflightgame.model.ITile;

public class TryAndErrorStrategy extends AbstractStrategy {

	@Override
	public double getComplexity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isAppliableForTile(ITile t) {
		//TODO
		return false;
	}
	
	@Override
	public boolean tryToSolve()  throws PuzzleException {
		return false;
		// TODO Auto-generated method stub
	}

}
