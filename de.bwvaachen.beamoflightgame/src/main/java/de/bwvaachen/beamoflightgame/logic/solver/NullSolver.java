package de.bwvaachen.beamoflightgame.logic.solver;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public class NullSolver extends AbstractSolver {

	@Override
	public ISolver setSuccessor() {
		return null;
	}

	@Override
	protected boolean _solve(IBeamsOfLightPuzzleBoard b) {
		throw new UnsolvableException();
	}

}
