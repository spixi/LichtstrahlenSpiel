package de.bwvaachen.beamoflightgame.logic.strategies;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import de.bwvaachen.beamoflightgame.helper.AbstractTileVisitor;
import de.bwvaachen.beamoflightgame.helper.BoardTraverser;
import de.bwvaachen.beamoflightgame.helper.BoardUtils;
import static de.bwvaachen.beamoflightgame.helper.MathHelper.*;
import de.bwvaachen.beamoflightgame.helper.TraverseDirection;
import de.bwvaachen.beamoflightgame.logic.UnsolvablePuzzleException;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.ITileState;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.NumberTileState;

public class IntersectionStrategy extends AbstractStrategy<NumberTileState> {
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
	public boolean isApplicableForTile(ITile<?> t) {
		return (t instanceof NumberTile && ((NumberTile) t).getRemainingLightRange() > 0);
	}

	@Override
	public boolean tryToSolve() throws UnsolvablePuzzleException {
		//Overshadow the tile
		NumberTile tile = (NumberTile) super.tile;

		int remainingLightRange = tile.getRemainingLightRange();
		
		final int maxRange[] = new int[states.length];
		
		int index = 0;
		BoardTraverser traverser = tile.getTraverser();
		for(LightTileState state : states) {
			int counter = 0;
			
			traverser.reset();
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
		
		int availableRange = (maxRange[0]+maxRange[1]+maxRange[2]+maxRange[3]);
		
		//case 1: the remaining range of the NumberTile cannot be covered by the
		//        all four directions together
		if(availableRange < remainingLightRange) 
			throw new UnsolvablePuzzleException(tile);
		
		//case 2: there is only one possibility to cover the remaining range of the
		//        NumberTile
		else if(availableRange == remainingLightRange) {
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
			final AtomicInteger distributedTiles = new AtomicInteger(0);
			loopOverSearchPaths:
			for(int[] searchPath: searchPaths) {
				
 				int sum = multiplicateVector(searchPath, maxRange);

				if(sum < remainingLightRange-distributedTiles.get()) {
					//Only sum light tiles can be distributed to the given searchPath
					//Therefore some tiles are forced to the other directions
					int[] otherDirections =
							subtractVector(V(1,1,1,1),searchPath);
					
					final int tilesToDistribute = remainingLightRange - sum - distributedTiles.get();
					
					for(int i=0; i<otherDirections.length; i++) {
						final LightTileState currentState = states[i];
						
						//Too few tiles to distribute
						if(tilesToDistribute <= 0)
							throw new UnsolvablePuzzleException(tile);
						
						//More than one possibility?
						if(tilesToDistribute > remainingLightRange)
							continue;

						//Only for the other directions
						if(otherDirections[i] == 0){ continue; }
						
						else {
							for(int j=0; j<otherDirections.length;j++){
								
								int temp = multiplicateVector(otherDirections, maxRange);
								
								if(getValue(otherDirections) != 1){
									if(i!=j && maxRange[i] >= tilesToDistribute && maxRange[j] >= tilesToDistribute)
										continue loopOverSearchPaths;
									
									if(temp > tilesToDistribute)
										continue loopOverSearchPaths;
								}
							}
						}
						
						final TraverseDirection currentDirection = currentState.getTraverseDirection();
						final int range = Math.min(tilesToDistribute, maxRange[i]);
						
						traverser.reset();
						if(!traverser.shift(currentDirection)) continue;
						
						if(range > 0) {
							maxRange[i] -= range;
							traverser.get().accept(new AbstractTileVisitor() {
								public void visitLightTile(LightTile lt) {
									int filledTiles =
								    utils.fillBoard(lt, range, currentDirection, currentState);
									System.out.printf("Tile: (%d, %d):\n", lt.getX(), lt.getY());
									System.out.printf("utils.fillBoard(%s, %d, %s, %s);\n", lt, range, currentDirection, currentState);
									distributedTiles.addAndGet(filledTiles);
								}
							});
						}
					}
				}
				
			}
		}
		
		//We assume that this strategy will always solve a puzzle
		return true;
	}
	
	private static LightTileState states[];
	private static int[][] searchPaths;
	private static HashMap<LightTileState,Integer> directions;
	private final static BoardUtils<LightTileState> utils;
	
	static {
		states = (LightTileState[]) LightTileState.allDirections().toArray();
		utils = BoardUtils.getInstance(LightTileState.class);
		
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
		//Erst einmal rausgenommen!
		//three directions
		//searchPaths[++i]=vectorSum(E,S,W);
		//searchPaths[++i]=vectorSum(N,S,W);
		//searchPaths[++i]=vectorSum(N,E,W);
		//searchPaths[++i]=vectorSum(N,E,S);
		
		
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
