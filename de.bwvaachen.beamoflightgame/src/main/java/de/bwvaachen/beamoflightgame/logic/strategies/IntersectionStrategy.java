package de.bwvaachen.beamoflightgame.logic.strategies;

import de.bwvaachen.beamoflightgame.logic.PuzzleException;
import de.bwvaachen.beamoflightgame.logic.solver.IStrategy;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.INumberTile;
import de.bwvaachen.beamoflightgame.model.ITile;

public class IntersectionStrategy extends AbstractStrategy {

	@Override
	public boolean tryToSolve()  throws PuzzleException {
		return false;
		// TODO Auto-generated method stub
	}
	
	private int getNumberOfPossibleSolutions() {
		//TODO
		return 0;
	}

	@Override
	public double getComplexity() {
		return (getNumberOfPossibleSolutions() == 1) ? 1.0 : 10.0;
	}

	@Override
	public boolean isAppliableForTile(ITile t) {
		return (t instanceof INumberTile && ((INumberTile) t).getRemainingLightRange() > 0);
	}

}
