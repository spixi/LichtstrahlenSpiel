package de.bwvaachen.beamoflightgame.logic.strategies;

import java.util.Iterator;

import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
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
		BoardTraverser traverser = new BoardTraverser(b, t.getRow(), t.getCol());
		//get the next NumberTiles which could reach the tile
		
		ITile currentTile;
	    ILightTile neighbour = null;
		
	    findNeighbour: {
	    	//west neighbor
	    	while (traverser.shift(0, -1)) {
	    		currentTile = traverser.get();
	    		switch (currentTile.getClass().getSimpleName()) {
	    		case("ILightTile"):  //found an ILightTile
	    			if (((ILightTile)currentTile ).getState() != LightTileState.NORTH)
	    				break findNeighbour;
	    		case("INumberTile"): break;
	    		//prüfe, ob dieses Feld noch mit den Restfeldern erreichbar ist
	    		    
	    			
	    		}
	    		/*
				if (currentTile instanceof ILightTile);
				neighbour = (ILightTile)currentTile;
				*/
	    	}

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
