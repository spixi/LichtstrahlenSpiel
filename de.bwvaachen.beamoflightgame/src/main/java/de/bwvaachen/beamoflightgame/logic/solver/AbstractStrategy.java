package de.bwvaachen.beamoflightgame.logic.solver;

public abstract class AbstractStrategy implements IStrategy {
	private int complexity;
	
	protected AbstractStrategy(int complexity) {
		this.complexity = complexity;
	}
	
	public int getComplexity() {
		return complexity;
	}
}
