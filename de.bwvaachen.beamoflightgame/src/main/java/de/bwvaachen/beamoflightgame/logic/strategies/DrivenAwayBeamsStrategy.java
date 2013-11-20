package de.bwvaachen.beamoflightgame.logic.strategies;

import de.bwvaachen.beamoflightgame.logic.PuzzleException;
import de.bwvaachen.beamoflightgame.logic.solver.IStrategy;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.INumberTile;
import de.bwvaachen.beamoflightgame.model.ITile;

public class DrivenAwayBeamsStrategy implements IStrategy {

	@Override
	public boolean tryToSolve(IBeamsOfLightPuzzleBoard b, ITile t)  throws PuzzleException {
		return false;
		// TODO Auto-generated method stub
	}

	@Override
	public double getComplexity() {
		return 100;
	}
	
	@Override
	public boolean isAppliableForTile(ITile t) {
		return (t instanceof INumberTile) && ((INumberTile) t).getRemainingLightRange() > 0;
	}

}
