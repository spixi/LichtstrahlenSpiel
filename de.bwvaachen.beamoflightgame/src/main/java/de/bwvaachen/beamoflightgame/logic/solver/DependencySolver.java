package de.bwvaachen.beamoflightgame.logic.solver;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public class DependencySolver extends AbstractSolver {

	public DependencySolver() {//TODO ICh will kompilieren
	super(null);
	}
	protected DependencySolver(ISolver s) {
		super(s);
	}

	@Override
	protected boolean _solve(IBeamsOfLightPuzzleBoard b) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ISolver setSuccessor() {
		// TODO Auto-generated method stub
		return null;
	}

}
