package de.bwvaachen.beamoflightgame.logic.strategies;

import java.util.TreeMap;

import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.TraverseDirection;
import de.bwvaachen.beamoflightgame.logic.PuzzleException;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.ITileState;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;

public class IntersectionStrategy extends AbstractStrategy {
	NumberTile tile = (NumberTile) super.tile;


	@Override
	public boolean tryToSolve()  throws PuzzleException {
		//Overshadow the tile
		NumberTile tile = (NumberTile) super.tile;
		
		int remainingLightRange = tile.getRemainingLightRange();
		
		LightTileState states[] = new LightTileState[] {
				LightTileState.NORTH,LightTileState.EAST,LightTileState.SOUTH,LightTileState.WEST
				};
		int maxRange[] = new int[4];
		
		int index = 0;
		BoardTraverser traverser = tile.getTraverser();
		for(LightTileState state : states) {
			int counter = 0;
			
			while(traverser.shift(state.getTraverseDirection())) {
				ITileState nextState = traverser.get().getTileState();
				
				//stop if tile is crossed
				if (doesCross(nextState, state)) break;
				
				//ignore tiles of the same state
				if (nextState.equals(state)) continue;
				
				counter ++;
			}
			
			maxRange[index++] = counter;
		}
		
		System.out.printf("tile = {%s, %s}; remaining range = %d; range { n e s w } =  { ", tile.getX(), tile.getY(), remainingLightRange);
		for(int m : maxRange) System.out.printf("%d ", m);
		System.out.println("}");
		
		
		
		//TODO
		
		return false;
		// TODO Auto-generated method stub
	}
	

    private boolean doesCross(ITileState a, ITileState b) {
    	return !( (a==LightTileState.EMPTY) || (a.equals(b)) );
    }
	
	private int getNumberOfPossibleSolutions() {
		//TODO
		return 0;
	}

	@Override
	public double getComplexity() {
		return (getNumberOfPossibleSolutions() == 1) ? 1.0 : 10.0;
	}

	@Override
	public boolean isAppliableForTile(ITile t) {
		return (t instanceof NumberTile && ((NumberTile) t).getRemainingLightRange() > 0);
	}

}
