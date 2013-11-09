package de.bwvaachen.braun.beamoflightpuzzle.model;

public enum LightTileState {
	SOUTH('s',0.0), WEST('w',Math.PI),NORTH('n',180),EAST('e',-90), EMPTY('-',0.0);
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
