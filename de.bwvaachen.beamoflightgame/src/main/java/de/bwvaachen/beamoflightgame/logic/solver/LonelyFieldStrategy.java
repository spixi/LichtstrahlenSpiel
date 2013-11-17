package de.bwvaachen.beamoflightgame.logic.solver;

import java.util.Iterator;

import de.bwvaachen.beamoflightgame.helper.BoardUtils;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ILightTile;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTileState;

public class LonelyFieldStrategy implements IStrategy {
	@Override
	public boolean solve(IBeamsOfLightPuzzleBoard b) {
		for(ITile tile: b) {
			if(tile instanceof ILightTile) {
				if (((ILightTile) tile).getState() == LightTileState.EMPTY) {
					int row, col;
					
					row = tile.getRow();
					col = 0;
					
					for (;col < b.getWidth();col++) {
						//TODO
					}
					
					row = 0;
					col = tile.getCol();
					
					for (;row < b.getHeight(); row++) {
						//TODO
					}
					
				}
			}
		}
		
		//TODO
		return false;
	}

	@Override
	public double getComplexity() {
		return 10.0;
	}
}
