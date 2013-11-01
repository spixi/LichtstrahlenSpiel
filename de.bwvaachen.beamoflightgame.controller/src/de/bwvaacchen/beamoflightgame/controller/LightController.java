package de.bwvaacchen.beamoflightgame.controller;

import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightModel;
import de.bwvaachen.beamoflightgame.model.IField;
import de.bwvaachen.beamoflightgame.model.ILightField;
import de.bwvaachen.beamoflightgame.model.LightFieldStatus;

public class LightController implements ILightController {

	@Override
	public IBeamsOfLightModel getCurrentModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countEmptyFields() {
		int count=0;
		IBeamsOfLightModel currentModel = getCurrentModel();
		for(IField field:currentModel){
			if(field instanceof ILightField 
					&& ((ILightField)field).getStatus()==LightFieldStatus.EMPTY){
				count++;
			}
		}
		return count;
	}

	@Override
	public int countLightFields() {
		int count=0;
		IBeamsOfLightModel currentModel = getCurrentModel();
		for(IField field:currentModel){
			if(field instanceof ILightField){
				count++;
			}
		}
		return count;
	}

	@Override
	public int countLightedFields() {
		int count=0;
		IBeamsOfLightModel currentModel = getCurrentModel();
		for(IField field:currentModel){
			if(field instanceof ILightField 
					&& ((ILightField)field).getStatus()!=LightFieldStatus.EMPTY){
				count++;
			}
		}
		return count;
	}

}
