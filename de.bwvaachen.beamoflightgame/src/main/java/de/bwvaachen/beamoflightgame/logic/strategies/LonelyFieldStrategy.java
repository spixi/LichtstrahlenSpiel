package de.bwvaachen.beamoflightgame.logic.strategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.TraverseDirection;
import de.bwvaachen.beamoflightgame.logic.PuzzleException;
import de.bwvaachen.beamoflightgame.logic.UnsolvablePuzzleException;
import de.bwvaachen.beamoflightgame.logic.solver.IStrategy;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ILightTile;
import de.bwvaachen.beamoflightgame.model.INumberTile;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.ITileState;
import de.bwvaachen.beamoflightgame.model.LightTileState;

public class LonelyFieldStrategy extends AbstractStrategy {
	private BoardTraverser traverser;
	
	protected void _init() {
		traverser = new BoardTraverser(board, tile.getRow(), tile.getCol());
	}
	
	@Override
	public boolean tryToSolve() throws PuzzleException {
		//get the next NumberTiles which could reach the tile
		
		//TODO ...
		
		ITile currentTile;
	    INumberTile neighbour = null;
	    Collection<INumberTile> neighbours
	    	= new ArrayList<INumberTile>(2);
	    
	    for(LightTileState lts :
	    	new LightTileState[]{
	    		LightTileState.NORTH,
	    		LightTileState.EAST,
	    		LightTileState.SOUTH,
	    		LightTileState.WEST}) {
		
	    	
	    	neighbour = findNeighbour(lts);
	    	if (neighbour != null) {
	    		
	    		//determine the distance to the neighbour INumberTile.
	    		int distance =  Math.abs(tile.getRow()-neighbour.getRow())
	    		              + Math.abs(tile.getCol()-neighbour.getCol());
	    		
	    		//Check whether the neighbour INumberTile actually can reach the ITile
	    		if (distance > neighbour.getRemainingLightRange()) {
	    			neighbours.add(neighbour);
	    		}     
	    		     
	    	}
	    	
	    	if (neighbours.size() > 1) break;
	    
	    }
	    
	    switch(neighbours.size()) {
	    case 0: throw new UnsolvablePuzzleException(); //The tile is unreachable 
	    case 1: ;//TODO: implement the only solution here !!!
	    default: return false; //Ambigious solution => try next Step
	    }

	}

    private boolean doesCross(ITileState a, ITileState b) {
    	return ( (a==b) || (a==LightTileState.EMPTY) );
    }
    
    
    private INumberTile findNeighbour(LightTileState lts) {
    	ITile currentTile = null;
    	
    	while (traverser.shift(lts.getTraverseDirection())) {
    		currentTile = traverser.get();
    		if(currentTile instanceof ILightTile)  //found an ILightTile 
    		{
    			//Does another ILightTile cross the line?
    			//Then stop looking for the neighbour.
    			if (doesCross(currentTile.getTileState(), lts ))
    				return null;
    		}
    		else 
    		
    		if(currentTile instanceof INumberTile) //found an INumberTile
    			                                   //This is our neighbour.
    			return (INumberTile) currentTile;    
    		}
    	//We have reached the end of the board ...
    	//So there is no neighbour.
	    return null;
    }


	@Override
	public double getComplexity() {
		return 2.0;
	}
	
	@Override
	public boolean isAppliableForTile(ITile t) {
		return (t instanceof ILightTile) & t.getTileState() == LightTileState.EMPTY;
	}
}
