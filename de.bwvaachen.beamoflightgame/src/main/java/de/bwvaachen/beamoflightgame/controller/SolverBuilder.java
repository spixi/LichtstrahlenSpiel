package de.bwvaachen.beamoflightgame.controller;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.bwvaachen.beamoflightgame.helper.Holder;
import de.bwvaachen.beamoflightgame.helper.ITileVisitor;
import de.bwvaachen.beamoflightgame.helper.Pair;
import de.bwvaachen.beamoflightgame.logic.AmbiguousPuzzleException;
import de.bwvaachen.beamoflightgame.logic.ISolver;
import de.bwvaachen.beamoflightgame.logic.IStrategy;
import de.bwvaachen.beamoflightgame.logic.MaximumIterationsExceededException;
import de.bwvaachen.beamoflightgame.logic.UnsolvablePuzzleException;
import de.bwvaachen.beamoflightgame.logic.solver.AbstractSolver;
import de.bwvaachen.beamoflightgame.logic.ISolver.Hook;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;

public class SolverBuilder {
	
	private class SolverBuilderContext implements ISolverBuilderContext  {
		private List<IStrategy> strategies;
		private List<Hook>      hooks;
		
		private SolverBuilderContext() {
			strategies = new LinkedList<IStrategy>();
			hooks      = new LinkedList<Hook>();
		}

		@Override
		public ISolverBuilderContext and(Class<? extends IStrategy> s)
				throws InstantiationException, IllegalAccessException {
			IStrategy theStrategy = s.newInstance();
		    strategies.add(theStrategy);
		    
		    if(theStrategy.hasHooks()) {
		    	hooks.addAll(theStrategy.getHooks());
		    }
		    
			return this;
		}

		@Override
		public ISolver forBoard(final IBeamsOfLightPuzzleBoard board) {
			return new AbstractSolver(board) {
				
				//Because of the halting problem (see Wikipedia)
				//we are unable to find out, whether a puzzle is
				//solvable or not, so we stop after a fix number of
				//iterations
				final int MAX_ITERATIONS = 100;
				
				private boolean isPlausible() {
					
					final Holder<Integer> sumOfNumberTiles = new Holder<Integer>(0);
					final Holder<Integer> numOfLightTiles  = new Holder<Integer>(0);
					
					for(ITile tile : board) {
						tile.accept(new ITileVisitor() {

							@Override
							public void visitLightTile(LightTile t) {
								numOfLightTiles.value ++;
							}

							@Override
							public void visitNumberTile(NumberTile t) {
								sumOfNumberTiles.value += t.getNumber();
							}
							
						});
					}
					
					return (sumOfNumberTiles.value == numOfLightTiles.value);
				}

				@Override
				public void solve() throws UnsolvablePuzzleException, MaximumIterationsExceededException, AmbiguousPuzzleException {
					boolean solved = false;
					
					if(!isPlausible())
						throw new UnsolvablePuzzleException(board.getTileAt(board.getWidth()-1, board.getHeight()-1));
					
					
					for(int i = 0; i< MAX_ITERATIONS; i++) {
						for(ITile tile: board) {
							try {
								step(tile, 0);
							}
							//Temporär
							catch (Exception ex) {
								ex.printStackTrace();
							}
						}
						//This is a assignment, not a comparation!
						if((solved = isSolved())) break;
					}
					if(!solved) throw new MaximumIterationsExceededException(board);
				}
				
				public boolean isSolved() {
					return isSolved_v2();
				}
				
				public boolean isSolved_v1() {
					for(ITile tile: board) {
						//The puzzle is not solved if there are any empty fields remaining.
						if(tile.getTileState().equals(LightTileState.EMPTY))
							return false;
					}
					return true;
				}
				
				//TODO
				//Maybe we could move this method (together with isPlausible())
				//to the IBeamsOfLightPuzzleBoard class
				
				public boolean isSolved_v2() {
					//New implementation
					//The puzzle board is solved when no number tile has remaining range
					Iterator<NumberTile> it = board.numberTileIterator();
					int max = board.getNumOfNumberTiles();
					for(int i=max; i>0; i--) {
						NumberTile nt = it.next();
						if(nt.getRemainingLightRange() != 0)
							return false;
					}
					return true;
				}

				
				private void step(ITile tile, int stackPointer) throws UnsolvablePuzzleException, AmbiguousPuzzleException {	
					//if (stackPointer == strategies.size()) throw new UnsolvablePuzzleException();
					if (stackPointer == strategies.size()) return;
					
					IStrategy currentStrategy = strategies.get(stackPointer);
					
					if ( !currentStrategy.isApplicableForTile(tile) ) {
						step(tile, stackPointer+1);
					}
					else {
						currentStrategy.init(tile);
						try {
							boolean canSolve = currentStrategy.tryToSolve();
							if(canSolve) {
								addComplexity(currentStrategy.getComplexity());
							}
							else
							{
								//recursion
								step(tile, stackPointer+1);
							}
						}
						catch (UnsolvablePuzzleException e) {
							if(hooks.size() == 0) throw e;
							for(ISolver.Hook h : hooks) {
								h.run();
							}
						}
					}
				}
				
			};
		}
		
	}
	
	
	private static SolverBuilder instance = new SolverBuilder();

	public static ISolverBuilderContext buildWith(Class<? extends IStrategy> s)
			   throws InstantiationException, IllegalAccessException{
		return instance.new SolverBuilderContext().and(s);
	}
}
