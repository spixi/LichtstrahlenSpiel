package de.bwvaachen.beamoflightgame.controller;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.util.LinkedList;
import java.util.List;

import de.bwvaachen.beamoflightgame.logic.AmbiguousPuzzleException;
import de.bwvaachen.beamoflightgame.logic.ISolver;
import de.bwvaachen.beamoflightgame.logic.IStrategy;
import de.bwvaachen.beamoflightgame.logic.MaximumIterationsExceededException;
import de.bwvaachen.beamoflightgame.logic.UnsolvablePuzzleException;
import de.bwvaachen.beamoflightgame.logic.solver.AbstractSolver;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTileState;

public class SolverBuilder {
	
	private class SolverBuilderContext implements ISolverBuilderContext  {
		private List<IStrategy> strategies;
		
		private SolverBuilderContext() {
			strategies = new LinkedList<IStrategy>();
		}

		@Override
		public ISolverBuilderContext and(Class<? extends IStrategy> s)
				throws InstantiationException, IllegalAccessException {
		    strategies.add(s.newInstance());
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

				@Override
				public void solve() throws UnsolvablePuzzleException, MaximumIterationsExceededException, AmbiguousPuzzleException {
					boolean solved = false;
					for(int i = 0; i< MAX_ITERATIONS; i++) {
						for(ITile tile: board) {
							step(tile, 0);
						}
						//This is a assignment, not a comparation!
						if((solved = isSolved())) break;
					}
					if(!solved) throw new MaximumIterationsExceededException(board);
				}
				
				public boolean isSolved() {
					for(ITile tile: board) {
						//The puzzle is not solved if there are any empty fields remaining.
						if(tile.getTileState().equals(LightTileState.EMPTY))
							return false;
					}
					return true;
				}

				
				private void step(ITile tile, int stackPointer) throws UnsolvablePuzzleException, AmbiguousPuzzleException {	
					//if (stackPointer == strategies.size()) throw new UnsolvablePuzzleException();
					if (stackPointer == strategies.size()) return;
					
					IStrategy currentStrategy = strategies.get(stackPointer);
					
					if ( !currentStrategy.isAppliableForTile(tile) ) {
						step(tile, stackPointer+1);
					}
					else {
						System.out.printf("Feld: (%d,%d), Strategie: %s\n", tile.getX(), tile.getY(), currentStrategy.getClass().toString());
						currentStrategy.init(tile);
						boolean canSolve = currentStrategy.tryToSolve();
						if(!canSolve) step(tile, stackPointer+1);
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
