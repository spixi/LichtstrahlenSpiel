package de.bwvaacchen.braun.beamoflightpuzzle.controller;

import de.bwvaachen.braun.beamoflightpuzzle.controller.ILightController;
import de.bwvaachen.braun.beamoflightpuzzle.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.braun.beamoflightpuzzle.model.ITile;
import de.bwvaachen.braun.beamoflightpuzzle.model.ILightTile;
import de.bwvaachen.braun.beamoflightpuzzle.model.LightTileState;

public class LightController implements ILightController {

	@Override
	public IBeamsOfLightPuzzleBoard getCurrentModel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countEmptyFields() {
		int count=0;
		IBeamsOfLightPuzzleBoard currentModel = getCurrentModel();
		for(ITile field:currentModel){
			if(field instanceof ILightTile 
					&& ((ILightTile)field).getState()==LightTileState.EMPTY){
				count++;
			}
		}
		return count;
	}

	@Override
	public int countLightFields() {
		int count=0;
		IBeamsOfLightPuzzleBoard currentModel = getCurrentModel();
		for(ITile field:currentModel){
			if(field instanceof ILightTile){
				count++;
			}
		}
		return count;
	}

	@Override
	public int countLightedFields() {
		int count=0;
		IBeamsOfLightPuzzleBoard currentModel = getCurrentModel();
		for(ITile field:currentModel){
			if(field instanceof ILightTile 
					&& ((ILightTile)field).getState()!=LightTileState.EMPTY){
				count++;
			}
		}
		return count;
	}

}
