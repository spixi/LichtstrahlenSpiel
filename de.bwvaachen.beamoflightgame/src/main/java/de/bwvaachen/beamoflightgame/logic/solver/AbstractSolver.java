package de.bwvaachen.beamoflightgame.logic.solver;

import de.bwvaachen.beamoflightgame.logic.ISolver;
import de.bwvaachen.beamoflightgame.logic.PuzzleException;
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
		int numofturns = board.getHeight() * board.getWidth(); //TODO - board.countNumberTiles()
		
		return Math.log10(complexity/numofturns); //level = complexity / number of turns
	}

}
