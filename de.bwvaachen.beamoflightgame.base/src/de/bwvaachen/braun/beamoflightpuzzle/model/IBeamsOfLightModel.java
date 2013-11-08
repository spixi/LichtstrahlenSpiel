package de.bwvaachen.braun.beamoflightpuzzle.model;

public interface IBeamsOfLightModel extends Iterable<IField> {
	public int getWidth();
	public int getHeight();
	
	public IField getFieldAt(int row, int col);
	public IField getFieldByIndex(int index);
	
	
	
	
}
