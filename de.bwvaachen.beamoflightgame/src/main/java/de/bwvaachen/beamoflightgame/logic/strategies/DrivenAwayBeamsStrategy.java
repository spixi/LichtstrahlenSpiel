package de.bwvaachen.beamoflightgame.logic.strategies;

import de.bwvaachen.beamoflightgame.logic.PuzzleException;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.NumberTileState;

public class DrivenAwayBeamsStrategy extends AbstractStrategy<NumberTileState> {

	@Override
	public double getComplexity() {
		return 100;
	}

	@Override
	public boolean isAppliableForTile(ITile t) {
		return (t instanceof NumberTile) && ((NumberTile) t).getRemainingLightRange() > 0;
	}
	
	@Override
	public boolean tryToSolve() {
		
		//Wird aus Zeitmangel nicht implementiert
		return false;
		// TODO Auto-generated method stub
	}

}
