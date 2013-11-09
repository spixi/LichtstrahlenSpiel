package de.bwvaachen.braun.beamoflightpuzzle.controller;

import de.bwvaachen.braun.beamoflightpuzzle.model.IBeamsOfLightPuzzleBoard;

public interface ILightController {
	public IBeamsOfLightPuzzleBoard getCurrentModel();
	public int countEmptyFields();
	public int countLightFields();
	public int countLightedFields();
	

}
