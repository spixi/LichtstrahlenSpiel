package de.bwvaachen.beamoflightgame.logic.solver;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public abstract class AbstractSolver implements ISolver {
	private ISolver successor;
	
	protected AbstractSolver(ISolver s) {
		this.successor = s;
	}
	
	public final boolean solve(IBeamsOfLightPuzzleBoard b) {
		boolean solved = _solve(b);
		if(!solved) successor.solve(b);
		return solved;
	}
	
	protected abstract boolean _solve(IBeamsOfLightPuzzleBoard b);
	
	public void setSuccessor(ISolver s) {
		successor = s;
	}
}
