package de.bwvaachen.beamoflightgame.logic.solver;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public abstract class AbstractSolver implements ISolver {
	private IBeamsOfLightPuzzleBoard board;
	private double complexity;
	
	protected AbstractSolver(IBeamsOfLightPuzzleBoard b) {
		board = b;
	}
	

	@Override
	public abstract void solve()
			throws PuzzleException;

	@Override
	public double getLevel() {
		int numofturns = board.getHeight() * board.getWidth();

		
		return Math.log10(complexity/numofturns) //level = complexity / number of turns
				*Math.pow(numofturns, 0.25);     //very big puzzles should have 
	}

}
