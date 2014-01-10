package de.bwvaachen.beamoflightgame.logic.strategies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import de.bwvaachen.beamoflightgame.helper.AbstractTileVisitor;
import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.BoardUtils;
import static de.bwvaachen.beamoflightgame.helper.MathHelper.*;
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
		
		int maxRange[] = new int[states.length];
		
		int index = 0;
		BoardTraverser traverser = tile.getTraverser();
		for(LightTileState state : states) {
			int counter = 0;
			
			traverser.reset();
			while(traverser.shift(state.getTraverseDirection())) {
				ITileState nextState = traverser.get().getTileState();
				
				if(traverser.get().getY() == 3) {
					int a = 1 + 2;
				}
				
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
		System.out.println("======================");
		
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
		
		//case 3: there is more than one possibility to cover the whole remaining range of
		//        the number tile

		else {
			for(int[] searchPath: searchPaths) {
				
				int sum = multiplicateVector(searchPath, maxRange);
				
				if(sum < remainingLightRange) {
					//Only sum light tiles can be distributed to the given searchPath
					//Therefore some tiles are forced to the other directions
					int[] otherDirections =
							subtractVector(V(1,1,1,1),searchPath);
					
					int tilesToDistribute = remainingLightRange - sum;
					System.out.printf("tilesToDistribute = %s\n",tilesToDistribute);
				
					for(int i=0; i<otherDirections.length; i++) {
						if(otherDirections[i] == 0) continue;
						LightTileState currentDirection = states[i];
						//TODO
						System.out.printf("currentDirection: %s, maxRange: %d \n", currentDirection, maxRange[i]);	
					}
					System.out.print("------------\n");	
				}
				
			}
			
		}

		
		
		//TODO
		
		return false;
		// TODO Auto-generated method stub
	}
	
	private static LightTileState states[];
	private static int[][] searchPaths;
	private static HashMap<LightTileState,Integer> directions;
	
	static {
		states = (LightTileState[]) LightTileState.allDirections().toArray();
		
		directions = new HashMap<LightTileState,Integer>(states.length);
		for(int i=0; i<states.length; i++) {
			directions.put(states[i],i);
		}
		
		int[] N = new int[4],
			  E = new int[4],
			  S = new int[4],
			  W = new int[4];
		
		N[directions.get(LightTileState.NORTH)] = 1;
		E[directions.get(LightTileState.EAST)]  = 1;
		S[directions.get(LightTileState.SOUTH)] = 1;
		W[directions.get(LightTileState.WEST)]  = 1;
		
		searchPaths = new int[14][4];
		
		int i = -1;
		
		//three directions
		searchPaths[++i]=vectorSum(E,S,W);
		searchPaths[++i]=vectorSum(N,S,W);
		searchPaths[++i]=vectorSum(N,E,W);
		searchPaths[++i]=vectorSum(N,E,S);
		
		//two directions
		searchPaths[++i]=vectorSum(N,E);
		searchPaths[++i]=vectorSum(N,S);
		searchPaths[++i]=vectorSum(N,W);
		searchPaths[++i]=vectorSum(E,S);
		searchPaths[++i]=vectorSum(E,W);
		searchPaths[++i]=vectorSum(S,W);
		
		//one direction
		searchPaths[++i]=N;
		searchPaths[++i]=E;
		searchPaths[++i]=S;
		searchPaths[++i]=W;
	}


}
