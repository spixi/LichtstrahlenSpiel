package de.bwvaachen.beamoflightgame.logic.strategies;

import de.bwvaachen.beamoflightgame.logic.PuzzleException;
import de.bwvaachen.beamoflightgame.logic.solver.IStrategy;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.INumberTile;
import de.bwvaachen.beamoflightgame.model.ITile;

public class TryAndErrorStrategy extends AbstractStrategy {

	@Override
	public boolean tryToSolve()  throws PuzzleException {
		return false;
		// TODO Auto-generated method stub
	}

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

}
