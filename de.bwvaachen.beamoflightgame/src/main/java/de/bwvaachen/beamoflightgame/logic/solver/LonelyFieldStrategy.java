package de.bwvaachen.beamoflightgame.logic.solver;

import java.util.Iterator;
import java.util.List;
import de.bwvaachen.beamoflightgame.helper.BoardUtils;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.INumberTile;
import de.bwvaachen.beamoflightgame.model.ITile;

public class LonelyFieldStrategy implements IStrategy {
	@Override
	public boolean solve(IBeamsOfLightPuzzleBoard b, ITile t) {
		int row, col;
		
		INumberTile north, east, south, west;
		
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
		
		
		return false;
	}

	@Override
	public double getComplexity() {
		return 2.0;
	}
}
