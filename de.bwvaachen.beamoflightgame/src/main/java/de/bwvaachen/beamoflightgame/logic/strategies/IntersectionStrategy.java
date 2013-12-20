package de.bwvaachen.beamoflightgame.logic.strategies;

import java.util.TreeMap;

import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.TraverseDirection;
import de.bwvaachen.beamoflightgame.logic.PuzzleException;
import de.bwvaachen.beamoflightgame.logic.UnsolvablePuzzleException;
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
			
			//maxRange[state.ordinal()] = counter
			//This is not save because a bad guy could change the order of the LightTileState enum
			
			maxRange[index++] = counter;
		}
		
		System.out.printf("tile = {%s, %s}; remaining range = %d; range { n e s w } =  { ", tile.getX(), tile.getY(), remainingLightRange);
		for(int m : maxRange) System.out.printf("%d ", m);
		System.out.println("}");
		
		int availableRange = (maxRange[0]+maxRange[1]+maxRange[2]+maxRange[3]);
		
		//case 1: the remaining range of the NumberTile cannot be covered by the
		//        all four directions together
		if(availableRange < remainingLightRange) 
			throw new UnsolvablePuzzleException();
		
		//case 2: there is only one possibility to cover the remaining range of the
		//        NumberTile
		else if(availableRange == remainingLightRange) {
			//TODO: We have only one possible solution
			
			
			
		}
		
		//case 2: there is more than one possibility to cover the whole remaining range of
		//        the number tile

		else {
			//TODO: We have more than one possible solutions
			//Now we have to check if some LightTiles are filled by every possible solution
			//first: determine how many LightTiles can be filled by each combination of
			//three, two or one direction
			//
			//The difference of the availableRange and the remainingRange determines
			//how many LightTiles are forced to be covered by the other directions
			//
			// E. g.:
			//
			//          [X]
			//          [ ]
			// [X][ ][ ][6][ ][ ][ ][ ][X]
			//          [ ]
			//          [X]
			//
			// The NumberTile 6 has a remainingRange of 6
			// Because NORTH+SOUTH can only cover up to (1 + 1 = 2) tiles
			// the remaining 4 tiles must be covered by WEST+SOUTH
			//
			// We are required to distribute those 4 tiles to the WEST and SOUTH
			// direction. We must always point the beam to the direction with the most
			// remaining range.
			//
			// So we will produce this figure:
			//
			//          [X]
			//          [ ]
			// [X][ ][ ][6][-][-][ ][ ][X]
			//          [ ]
			//          [X]
			//
			//
			//We can hard code the following terms because they are relatively comprehensible
			//
			//three directions
			//maxRange[1]+maxRange[2]+maxRange[3]
			//maxRange[0]+maxRange[2]+maxRange[3]
			//maxRange[0]+maxRange[1]+maxRange[3]
			//maxRange[0]+maxRange[1]+maxRange[2]
			//
			//two directions
			//maxRange[0]+maxRange[1]
			//maxRange[0]+maxRange[2]
			//maxRange[0]+maxRange[3]
			//maxRange[1]+maxRange[2]
			//maxRange[1]+maxRange[3]
			//maxRange[2]+maxRange[3]
			//
			//one direction
			//maxRange[0]
			//maxRange[1]
			//maxRange[2]
			//maxRange[3]
			
			
			
		}

		
		
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
