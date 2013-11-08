package de.bwvaacchen.braun.beamoflightpuzzle.controller;

import de.bwvaachen.braun.beamoflightpuzzle.controller.ILightController;
import de.bwvaachen.braun.beamoflightpuzzle.model.IBeamsOfLightModel;
import de.bwvaachen.braun.beamoflightpuzzle.model.IField;
import de.bwvaachen.braun.beamoflightpuzzle.model.ILightField;
import de.bwvaachen.braun.beamoflightpuzzle.model.LightFieldStatus;

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
