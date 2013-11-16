package de.bwvaachen.beamoflightgame.logic.solver;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public abstract class AbstractSolver implements ISolver {
	private int numofturns;
	private double complexity;
	

	@Override
	public abstract void solve(IBeamsOfLightPuzzleBoard board)
			throws PuzzleException;

	@Override
	public double getLevel() {
		return Math.log10(complexity/numofturns);
	}

}
