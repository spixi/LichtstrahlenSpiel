package de.bwvaachen.beamoflightgame.logic.strategies;

import java.util.Iterator;
import de.bwvaachen.beamoflightgame.helper.BoardUtils;
import de.bwvaachen.beamoflightgame.logic.PuzzleException;
import de.bwvaachen.beamoflightgame.logic.solver.IStrategy;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ILightTile;
import de.bwvaachen.beamoflightgame.model.INumberTile;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTileState;

public class LonelyFieldStrategy implements IStrategy {
	@Override
	public boolean tryToSolve(IBeamsOfLightPuzzleBoard b, ITile t) throws PuzzleException {
		int row, col;
		
		INumberTile north = null, east = null, south = null, west = null;
		
		Iterator<ITile> it;
		ITile next;
		
		//get the next NumberTiles which could reach the tile
		it = BoardUtils.row(b, t.getRow()).iterator();
		while(it.hasNext()) {
			next = it.next();
			if (!(next instanceof INumberTile)) continue;
			if (next.getCol() > t.getCol()) {
				east = (INumberTile) next;
				break;
			}
			else west = (INumberTile) next;
		}
		
		
		it = BoardUtils.column(b, t.getCol()).iterator();
		while(it.hasNext()) {
			next = it.next();
			if (!(next instanceof INumberTile)) continue;
			if (next.getRow() > t.getRow()) {
				south = (INumberTile) next;
				break;
			}
			else north = (INumberTile) next;
		}
		
		//TODO
		//Now check for each of the four numberTiles whether it has enough range
		//left to reach the tile.
		
		
		//TODO Reusability, Math.abs(int,int) could be useful for the difference
		if(north != null && north.getRemainingLightRange() >= (t.getRow() - north.getRow())) {
			//TODO
			//check whether beams do not cross
			
		}
		if(east != null && east.getRemainingLightRange() >= (east.getCol() - t.getCol())) {
			//...
		}
		if(south != null && south.getRemainingLightRange() >= (south.getRow() - t.getRow())) {
			//...
		}
		if(west != null && west.getRemainingLightRange() >= (t.getCol() - west.getCol())) {
			//...
		}
		
		return false;

	}


	@Override
	public double getComplexity() {
		return 2.0;
	}
	
	@Override
	public boolean isAppliableForTile(ITile t) {
		return (t instanceof ILightTile) & ((ILightTile) t).getState() == LightTileState.EMPTY;
	}
}
