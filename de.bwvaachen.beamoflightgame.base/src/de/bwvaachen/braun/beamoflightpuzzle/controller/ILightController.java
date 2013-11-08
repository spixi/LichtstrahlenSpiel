package de.bwvaachen.braun.beamoflightpuzzle.controller;

import de.bwvaachen.braun.beamoflightpuzzle.model.IBeamsOfLightModel;

public interface ILightController {
	public IBeamsOfLightModel getCurrentModel();
	public int countEmptyFields();
	public int countLightFields();
	public int countLightedFields();
	

}
