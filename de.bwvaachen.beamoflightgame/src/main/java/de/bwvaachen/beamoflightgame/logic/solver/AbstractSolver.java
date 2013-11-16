package de.bwvaachen.beamoflightgame.logic.solver;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public abstract class AbstractSolver implements ISolver {

	@Override
	public abstract void solve(IBeamsOfLightPuzzleBoard board)
			throws PuzzleException;

	@Override
	public int getLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

}
