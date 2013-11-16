package de.bwvaachen.beamoflightgame.logic.solver;

public abstract class AbstractStrategy implements IStrategy {
	private double complexity;
	
	protected AbstractStrategy(double complexity) {
		this.complexity = complexity;
	}
	
	public double getComplexity() {
		return complexity;
	}
}
