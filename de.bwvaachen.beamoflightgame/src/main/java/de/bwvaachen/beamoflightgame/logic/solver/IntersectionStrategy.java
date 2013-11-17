package de.bwvaachen.beamoflightgame.logic.solver;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;

public class IntersectionStrategy implements IStrategy {

	@Override
	public boolean solve(IBeamsOfLightPuzzleBoard b, ITile t) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private int getNumberOfPossibleSolutions() {
		//TODO
		return 0;
	}

	@Override
	public double getComplexity() {
		return (getNumberOfPossibleSolutions() == 1) ? 1.0 : 10.0;
	}

}
