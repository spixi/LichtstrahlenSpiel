package de.bwvaachen.beamoflightgame.controller;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightModel;

public interface ILightController {
	public IBeamsOfLightModel getCurrentModel();
	public int countEmptyFields();
	public int countLightFields();
	public int countLightedFields();
	

}
