package de.bwvaachen.braun.beamoflightpuzzle.model;

public enum LightFieldStatus {
	HORIZONTAL('h'), VERTICAL('v'), EMPTY('-');
	private char saveValue;
	private LightFieldStatus(char c) {
		saveValue=c;
	}
	public char getSaveValue(){
		return saveValue;
	}
}
