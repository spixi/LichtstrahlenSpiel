package de.bwvaachen.braun.beamoflightpuzzle.model;

public enum LightTileState {
	SOUTH('s',Math.PI), WEST('w',3.0*Math.PI/2.0), NORTH('n',0.0), EAST('e',Math.PI/2.0), EMPTY('-',0.0);
	private double theta;
	private char saveValue;
	private LightTileState(char c, double d) {
		saveValue=c;
		theta=d;
	}
	public char getSaveValue(){
		return saveValue;
	}
	public double getTheta(){
		return theta;
	}
}
