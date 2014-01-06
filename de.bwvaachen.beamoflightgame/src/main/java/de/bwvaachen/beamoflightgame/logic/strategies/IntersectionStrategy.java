package de.bwvaachen.beamoflightgame.logic.strategies;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.bwvaachen.beamoflightgame.helper.AbstractTileVisitor;
import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.BoardUtils;
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


	private boolean doesCross(ITileState a, ITileState b) {
    	return !( (a==LightTileState.EMPTY) || (a.equals(b)) );
    }
	

    @Override
	public double getComplexity() {
		return (getNumberOfPossibleSolutions() == 1) ? 1.0 : 10.0;
	}
	
	private int getNumberOfPossibleSolutions() {
		//TODO
		return 0;
	}

	@Override
	public boolean isAppliableForTile(ITile t) {
		return (t instanceof NumberTile && ((NumberTile) t).getRemainingLightRange() > 0);
	}

	@Override
	public boolean tryToSolve()  throws PuzzleException {
		//Overshadow the tile
		NumberTile tile = (NumberTile) super.tile;
		
		int remainingLightRange = tile.getRemainingLightRange();
		
		LightTileState states[] = (LightTileState[]) LightTileState.allDirections().toArray();
		
		int maxRange[] = new int[states.length];
		
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
			throw new UnsolvablePuzzleException(tile);
		
		//case 2: there is only one possibility to cover the remaining range of the
		//        NumberTile
		else if(availableRange == remainingLightRange) {
			final BoardUtils<LightTileState> utils = BoardUtils.getInstance(LightTileState.class);
			
			for(int i=states.length-1; i<=0; i--) {
				final LightTileState currentState = states[i];
				final int range = maxRange[i];
				final TraverseDirection currentDirection = currentState.getTraverseDirection();
				traverser.reset();
				if(!traverser.shift(currentDirection)) continue;
				traverser.get().accept(new AbstractTileVisitor() {
					public void visitLightTile(LightTile lt) {
						utils.fillBoard(lt, range, currentDirection, currentState);
					}
				});
			}
		}
		
		//case 2: there is more than one possibility to cover the whole remaining range of
		//        the number tile

		else {
			
			for(Iterable<Integer> searchPath: searchPaths) {
				
			}
			
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
	
	private static List<List<Integer>> searchPaths;
	
	static {
		searchPaths = new LinkedList<List<Integer>>();
		
		final Integer N=Integer.valueOf(0),
				      E=Integer.valueOf(1),
				      S=Integer.valueOf(2),
				      W=Integer.valueOf(3);
		
		//three directions
		searchPaths.add(Arrays.asList(E,S,W));
		searchPaths.add(Arrays.asList(N,S,W));
		searchPaths.add(Arrays.asList(N,E,W));
		searchPaths.add(Arrays.asList(N,E,S));
		
		//two directions
		searchPaths.add(Arrays.asList(N,E));
		searchPaths.add(Arrays.asList(N,S));
		searchPaths.add(Arrays.asList(N,W));
		searchPaths.add(Arrays.asList(E,S));
		searchPaths.add(Arrays.asList(E,W));
		searchPaths.add(Arrays.asList(S,W));
		
		//one direction
		searchPaths.add(Collections.singletonList(N));
		searchPaths.add(Collections.singletonList(E));
		searchPaths.add(Collections.singletonList(S));
		searchPaths.add(Collections.singletonList(W));
	}
	
	//TODO
	private static <T extends Number> T sum(Iterable<? extends T> it) {
		Iterator<? extends T> iterator = it.iterator();
		Number first = iterator.next();
		
		if(first instanceof Integer) {
			Integer _result = (Integer) first;
			for(T next : it)
				_result+=(Integer)next;
			return (T) _result;
		}
		else if(first instanceof Double) {
			Double _result = (Double) first;
			for(T next : it)
				_result+=(Double)next;
			return (T) _result;
		}
		else if(first instanceof Long) {
			Long _result = (Long) first;
			for(T next : it)
				_result+=(Long)next;
			return (T) _result;
		}
		else if(first instanceof Float) {
			Float _result = (Float) first;
			for(T next : it)
				_result+=(Float)next;
			return (T) _result;
		}
		else if(first instanceof Byte) {
			Byte _result = (Byte) first;
			for(T next : it)
				_result= (byte)(_result + (Byte)next);
			return (T) _result;
		}
		else if(first instanceof Short) {
			Short _result = (Short) first;
			for(T next : it)
				_result= (short)(_result + (Short)next);
			return (T) _result;
		}
		else if(first instanceof java.math.BigInteger) {
			java.math.BigInteger _result = (java.math.BigInteger) first;
			for(T next : it)
				_result=((java.math.BigInteger)next).add((BigInteger) next);
			return (T) _result;
		}
		else if(first instanceof java.math.BigDecimal) {
			java.math.BigDecimal _result = (java.math.BigDecimal) first;
			for(T next : it)
				_result=((java.math.BigDecimal)next).add((BigDecimal) next);
			return (T) _result;
		}
		else {
			throw new IllegalArgumentException(String.format("Type %s not supported.", first.getClass()));
		}
	}

}
